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

import dbchubreast_web.dao.ChuPrelevementBiomarqueurDao;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.service.interpreter.InterpreterMib1;
import dbchubreast_web.service.interpreter.InterpreterPai1;
import dbchubreast_web.service.interpreter.InterpreterRecepteurHormonal;
import dbchubreast_web.service.interpreter.InterpreterUpa;
import dbchubreast_web.service.interpreter.StatutBiomarqueur;


@Service
public class UpdaterStatutBiomarqueur extends AbstractUpdater {

	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;
	
	@Autowired
	private InterpreterRecepteurHormonal interpreterRecepteurHormonal;

	@Autowired
	private InterpreterMib1 interpreterMib1;
	
	@Autowired
	private InterpreterPai1 interpreterPai1;
	
	@Autowired
	private InterpreterUpa interpreterUpa;
	
	/** ================================================================================= */

	public void update(List<?> list) {

		logger.debug("=== " + this.getClass().getName() + " ===");

		for (int i=0; i<list.size(); i++) {

			ChuPrelevementBiomarqueur prelBio = (ChuPrelevementBiomarqueur) list.get(i);
			String idBiomarqueur = prelBio.getId().getIdBiomarqueur();
			String valeur = prelBio.getValeur();
			
			if (idBiomarqueur!=null && valeur!=null) {
				StatutBiomarqueur statut = this.getStatut(idBiomarqueur, valeur);
				if (statut!=null) {
					prelBio.setStatut(statut.stringValue());
					prelevementBiomarqueurDao.update(prelBio);
				}
			}
		}
	}


	/** ================================================================================= */
	
	private StatutBiomarqueur getStatut(String idBiomarqueur, String valeur) {
		
		StatutBiomarqueur statut = null;  
		
		if (idBiomarqueur.equals("rp") || idBiomarqueur.equals("re") || idBiomarqueur.equals("her2")) {
			statut = interpreterRecepteurHormonal.getStatut(valeur);
		}
		
		if (idBiomarqueur.equals("mib1")) {
			statut = interpreterMib1.getStatut(valeur);
		}
		
		if (idBiomarqueur.equals("pai1")) {
			statut = interpreterPai1.getStatut(valeur);
		}
		
		if (idBiomarqueur.equals("upa")) {
			statut = interpreterUpa.getStatut(valeur);
		}
		
		return statut;
	}
	
	/** ================================================================================= */

}