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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuEvolutionDao;
import dbchubreast_web.dao.ChuPatientDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuTumeur;

@Service
public class UpdaterEvolution extends AbstractUpdater {

	@Autowired
	private ChuPatientDao patientDao;

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuEvolutionDao evolutionDao;

	/** ================================================================================= */

	public void update(List<?> list) {

		logger.debug("=== " + this.getClass().getName() + " ===");

		for (int i = 0; i < list.size(); i++) {

			ChuTumeur tumeur = (ChuTumeur) list.get(i);
			ChuPatient patient = patientDao.find(tumeur.getIdTumeur());

			if (patient.getDateDeces()!=null) {
				tumeur.setChuEvolution(evolutionDao.find(5)); // dead
				tumeur.setDateEvolution(patient.getDateDeces());
			}
			else {
				if (tumeur.getChuEvolution()!=null && tumeur.getChuEvolution().getIdEvolution().equals(5)) {
					tumeur.setChuEvolution(null);
					tumeur.setDateEvolution(null);
				}
			}
			
			tumeurDao.update(tumeur);
		}
	}

	/** ================================================================================= */

}