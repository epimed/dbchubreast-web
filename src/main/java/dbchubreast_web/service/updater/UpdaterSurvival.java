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
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTumeur;

@Service
public class UpdaterSurvival extends AbstractUpdater {

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	/** ================================================================================= */

	public void update(List<?> list) {

		logger.debug("=== " + this.getClass().getName() + " ===");

		for (int i = 0; i < list.size(); i++) {

			ChuTumeur tumeur = (ChuTumeur) list.get(i);

			Date dateDiagnostic = tumeur.getDateDiagnostic();
			Date dateEvolution = tumeur.getDateEvolution();
			Date dateDeces = tumeur.getChuPatient().getDateDeces();
			ChuPhaseTumeur phaseRechute = phaseTumeurDao.findFirstRelapse(tumeur.getIdTumeur());

			logger.debug("Phase rechute {}", phaseRechute);

			Date dateRechute = null;
			if (phaseRechute != null && phaseRechute.getDateDiagnostic() != null) {
				dateRechute = phaseRechute.getDateDiagnostic();
			}

			boolean hasSurvival = dateDiagnostic != null
					&& (dateEvolution != null || dateDeces != null || dateRechute != null);
			boolean dead = (dateDeces != null);
			boolean relapsed = (phaseRechute != null);

			logger.debug("dateDiagnostic=" + dateDiagnostic + ", dateEvolution=" + dateEvolution + ", dateDeces="
					+ dateDeces + ", dateRechute=" + dateRechute);

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

				tumeurDao.update(tumeur);
			}
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