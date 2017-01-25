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

import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.dao.ChuPrelevementBiomarqueurDao;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;

@Service
public class UpdaterNodule extends AbstractUpdater {

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;

	/** ================================================================================= */

	public void update(ChuPrelevement prelevement) {

		logger.debug("=== " + this.getClass().getName() + " ===");

		ChuPhaseTumeur phase = phaseTumeurDao.findByIdPrelevementWithDependencies(prelevement.getIdPrelevement());

		List<ChuPrelevementBiomarqueur> listPrelevementBiomarqueurs = prelevementBiomarqueurDao
				.list(prelevement.getIdPrelevement());

		boolean isFound = false;
		int i = 0;
		while (!isFound && i < listPrelevementBiomarqueurs.size()) {
			ChuPrelevementBiomarqueur prelBio = listPrelevementBiomarqueurs.get(i);
			if (prelBio != null && prelBio.getValeur() != null) {
				if (prelBio.getValeur().contains("/")) {
					isFound = true;
				}
			}
			i++;
		}

		if (isFound) {
			phase.setNodules(true);
		} else {
			phase.setNodules(false);
		}

		phaseTumeurDao.update(phase);
	}

	/** ================================================================================= */

	public void update(List<?> list) {

	}

	/** ================================================================================= */

}