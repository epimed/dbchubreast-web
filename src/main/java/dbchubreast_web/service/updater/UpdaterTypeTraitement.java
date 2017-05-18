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

import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.service.business.ChuTraitementService;
import dbchubreast_web.service.business.ChuTypeTraitementService;

@Service
public class UpdaterTypeTraitement extends AbstractUpdater {

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired
	private ChuTraitementService traitementService;
	
	@Autowired
	private ChuTypeTraitementService typeTraitementService;

	

	/** ================================================================================= */

	public void update(ChuTraitement traitement) {

		logger.debug("=== " + this.getClass() + " ===");

		Integer idPhase = traitement.getChuPhaseTumeur().getIdPhase();
		ChuPhaseTumeur phase = phaseTumeurDao.findWithDependencies(idPhase);
		ChuTumeur tumeur = tumeurDao.findByIdPhaseWithDependencies(idPhase);
		ChuTraitement chirurgieReference = traitementService.findChirurgieReference(tumeur.getIdTumeur());
		
		Date dateChirurgie = chirurgieReference.getDateDebut();
		Date dateTraitement = traitement.getDateDebut();
		
		traitement.setChuTypeTraitement(null);
		
		// Phase initiale, traitement autre que la chirurgie de reference 
		
		if (phase.getChuTypePhase().getIdTypePhase().equals(1) 
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

		traitementService.update(traitement);
		
		
	}

	/** ================================================================================= */

	@Override
	public void update(List<?> list) {
		// TODO Auto-generated method stub
	}

	/** ================================================================================= */

}