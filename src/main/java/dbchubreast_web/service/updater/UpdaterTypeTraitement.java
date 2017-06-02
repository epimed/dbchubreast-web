/**
 * EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)

 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package dbchubreast_web.service.updater;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.service.business.ChuTraitementService;
import dbchubreast_web.service.business.ChuTypeTraitementService;

@Service
public class UpdaterTypeTraitement extends AbstractUpdater {

	@Autowired
	private ChuTraitementService traitementService;

	@Autowired
	private ChuTypeTraitementService typeTraitementService;



	/** ================================================================================= */

	public void update(ChuTumeur tumeur) {

		logger.debug("=== " + this.getClass() + " ===");

		ChuTraitement chirurgieReference = traitementService.findChirurgieReference(tumeur.getIdTumeur());
		List<ChuTraitement> listTraitements = traitementService.listByIdTumeur(tumeur.getIdTumeur());


		for (ChuTraitement traitement : listTraitements) {

			traitement.setChuTypeTraitement(null);

			if (chirurgieReference!=null) {

				Date dateChirurgie = chirurgieReference.getDateDebut();
				Date dateTraitement = traitement.getDateDebut();

				// Phase initiale, traitement autre que la chirurgie de reference 

				if (traitement.getChuPhaseTumeur().getChuTypePhase().getIdTypePhase().equals(1) 
						&& !traitement.getIdTraitement().equals(chirurgieReference.getIdTraitement())) {

					// Traitement neoadjuvant

					if (dateTraitement!=null && dateChirurgie!=null && dateTraitement.before(dateChirurgie)) {
						traitement.setChuTypeTraitement(typeTraitementService.find(1));
						if (traitement.getDateFin()==null) {
							traitement.setDateFin(dateChirurgie);
						}
					}

					// Traitement adjuvent

					if (dateTraitement!=null && dateChirurgie!=null && dateTraitement.after(dateChirurgie)) {
						traitement.setChuTypeTraitement(typeTraitementService.find(2));
					}

				}

			}

			traitementService.update(traitement);
		}

	}

	/** ================================================================================= */

	@Override
	public void update(List<?> list) {
		// TODO Auto-generated method stub
	}

	/** ================================================================================= */

}