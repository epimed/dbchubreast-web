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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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


	/** ================================================================================= */

	public void update(List<?> list) {

		logger.debug("=== " + this.getClass().getName() + " ===");

		for (int i = 0; i < list.size(); i++) {

			ChuTumeur tumeur = (ChuTumeur) list.get(i);

			ChuPatient patient = patientService.find(tumeur.getIdTumeur());

			Date dateDiagnostic = tumeur.getDateDiagnostic();
			Date dateEvolution = patient.getDateEvolution();
			Date dateDeces = patient.getDateDeces();

			List<ChuPhaseTumeur> listRelapses = phaseTumeurDao.findRelapses(tumeur.getIdTumeur());
			ChuPrelevement dernierPrelevement = prelevementService.findDernierPrelevement(tumeur.getIdTumeur());
			ChuTraitement dernierTraitement  = traitementService.findDernierTraitement(tumeur.getIdTumeur());

			// === Rechute ===

			ChuPhaseTumeur phaseRechute = phaseTumeurDao.findFirstRelapse(tumeur.getIdTumeur());
			logger.debug("Phase rechute {}", phaseRechute);
			Date dateRechute = null;
			if (phaseRechute != null && phaseRechute.getDateDiagnostic() != null) {
				dateRechute = phaseRechute.getDateDiagnostic();
			}

			// === Derniere nouvelle ===

			/*
			 * Date derniere nouvelle = derniere date connue dans la base pour ce patient.
			 * Cela peut etre : 
			 * - date d'evolution
			 * - date de deces
			 * - date de la derniere tumeur en phase initiale
			 * - date de la derniere rechute
			 * - date du dernier prelevement 
			 * - date du dernier traitement
			 */

			List<Date> collectionDateDerniereNouvelle = new ArrayList<Date>();
			this.addDateToCollection(collectionDateDerniereNouvelle, dateDiagnostic);
			this.addDateToCollection(collectionDateDerniereNouvelle, dateEvolution);
			this.addDateToCollection(collectionDateDerniereNouvelle, dateDeces);
			for (ChuPhaseTumeur relapse : listRelapses) {
				this.addDateToCollection(collectionDateDerniereNouvelle, relapse.getDateDiagnostic());
			}
			if (dernierPrelevement!=null) {
				this.addDateToCollection(collectionDateDerniereNouvelle, dernierPrelevement.getDatePrelevement());
			}
			if (dernierTraitement!=null) {
				this.addDateToCollection(collectionDateDerniereNouvelle, dernierTraitement.getDateDebut());
			}

			Collections.sort(collectionDateDerniereNouvelle, Collections.reverseOrder());
			System.out.println(collectionDateDerniereNouvelle);

			Date dateDerniereNouvelle = collectionDateDerniereNouvelle.isEmpty() ? null : collectionDateDerniereNouvelle.get(0);



			// === Survie ===

			boolean hasSurvival = (dateDiagnostic != null) && (dateDerniereNouvelle!= null);
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

				Double os = calculateSurvival(dateDiagnostic, dateDerniereNouvelle);
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

	private void addDateToCollection(Collection<Date> collection, Date date) {
		if (date!=null) {
			collection.add(date);
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