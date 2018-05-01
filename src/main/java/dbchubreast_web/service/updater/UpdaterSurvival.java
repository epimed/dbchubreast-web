/**
 * EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * GNU GENERAL PUBLIC LICENSE
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package dbchubreast_web.service.updater;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPrelevementService;
import dbchubreast_web.service.business.ChuTraitementService;
import dbchubreast_web.service.util.FormatService;

@Service
public class UpdaterSurvival extends AbstractUpdater {

	@Autowired
	private ChuTumeurDao tumeurDao;
	
	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired 
	private ChuPrelevementService prelevementService;

	@Autowired 
	private ChuTraitementService traitementService;
	
	@Autowired 
	private FormatService formatService;


	/** ================================================================================= */

	public void update(List<?> list) {

		logger.debug("=== " + this.getClass().getName() + " ===");

		for (int i = 0; i < list.size(); i++) {

			ChuTumeur tumeur = (ChuTumeur) list.get(i);

			Date dateDiagnostic = tumeur.getDateDiagnostic();
			Date dateEvolution = tumeur.getDateEvolution();
			ChuPatient patient = patientService.find(tumeur.getIdTumeur());
			Date dateDeces = patient.getDateDeces();

			// === Rechute ===

			ChuPhaseTumeur phaseRechute = phaseTumeurDao.findFirstRelapse(tumeur.getIdTumeur());
			logger.debug("Phase rechute {}", phaseRechute);
			Date dateRechute = null;
			if (phaseRechute != null && phaseRechute.getDateDiagnostic() != null) {
				dateRechute = phaseRechute.getDateDiagnostic();
			}

			// === Derniere nouvelle ===

			// Si la date de la derniere nouvelle n'est pas remplie
			// on prend soit la date de deces, soit le dernier prelevement/traitement, soit la date de rechute.
			// La date de la derniere nouvelle est calculee a la volee a chaque fois, elle n'est pas sauvegardee dans la base.
			
			if (dateDeces!=null) {
				dateEvolution = dateDeces;
			}
			else {
				ChuPrelevement dernierPrelevement = prelevementService.findDernierPrelevement(tumeur.getIdTumeur());
				ChuTraitement dernierTraitement  = traitementService.findDernierTraitement(tumeur.getIdTumeur());
				dateEvolution = formatService.getDerniereDate(dateEvolution, dernierPrelevement.getDatePrelevement());
				dateEvolution = formatService.getDerniereDate(dateEvolution, dernierTraitement.getDateDebut());
				dateEvolution = formatService.getDerniereDate(dateEvolution, dateRechute);
			}
			

			// === Survie ===

			boolean hasSurvival = dateDiagnostic != null
					&& (dateEvolution != null || dateDeces != null || dateRechute != null);
			boolean dead = (dateDeces != null);
			boolean relapsed = (phaseRechute != null);

			logger.debug("dateDiagnostic=" + dateDiagnostic + ", dateEvolution=" + dateEvolution + ", dateDeces="
					+ dateDeces + ", dateRechute=" + dateRechute);
			logger.debug("hasSurvival=" + hasSurvival);

			// === Initialisation de la survie ===

			tumeur.setRelapsed(null);
			tumeur.setDead(null);
			tumeur.setOsMonths(null);
			tumeur.setDfsMonths(null);

			// === Calcul de la nouvelle survie si possible ===

			if (hasSurvival) {

				tumeur.setRelapsed(relapsed);
				tumeur.setDead(dead);

				Double os = calculateSurvival(dateDiagnostic, dateEvolution);
				Double dfs = calculateSurvival(dateDiagnostic, dateRechute);

				if (dead) {
					os = calculateSurvival(dateDiagnostic, dateDeces);
				}

				if (!relapsed) {
					dfs = os;
				}

				tumeur.setOsMonths(os);
				tumeur.setDfsMonths(dfs);

			}

			tumeurDao.update(tumeur);

		}
	}

	/** ============================================================================= */

	public Double calculateSurvival(Date dateStart, Date dateEnd) {

		if (dateStart == null || dateEnd == null) {
			return null;
		}

		Double daysInAMonth = 365.24 / 12;

		DateTime startDate = new DateTime(dateStart);
		DateTime endDate = new DateTime(dateEnd);

		int days = Days.daysBetween(startDate, endDate).getDays();
		// int months = Months.monthsBetween(startDate, endDate).getMonths();
		// int years = Years.yearsBetween(startDate, endDate).getYears();

		Double survival = days / daysInAMonth;
		survival = Math.round(survival * 100d) / 100d;

		// System.out.println("years=" + years + ", months=" + months + ",
		// days=" + days + "\t" + "survival=" + survival);

		return survival;
	}

	
	/** ================================================================================= */

}