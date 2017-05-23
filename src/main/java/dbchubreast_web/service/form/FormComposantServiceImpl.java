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
package dbchubreast_web.service.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.ChuComposantTraitement;
import dbchubreast_web.entity.ChuMethodeTraitement;
import dbchubreast_web.form.FormComposant;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuComposantTraitementService;
import dbchubreast_web.service.business.ChuMethodeTraitementService;

@Service
public class FormComposantServiceImpl extends BaseService implements FormComposantService {

	@Autowired
	private ChuMethodeTraitementService methodeTraitementService;
	
	@Autowired
	private ChuComposantTraitementService composantTraitementService;

	/** ====================================================================== */

	public void saveOrUpdateForm(FormComposant form) {

		logger.debug("=== " + this.getClass().getName() + ": saveOrUpdateForm" + " ===");
		
		ChuComposantTraitement composant = null;
		if (form.isNew()) {
			composant = new ChuComposantTraitement();
		}
		else {
			composant = composantTraitementService.find(form.getIdComposant());
		}
		
		ChuMethodeTraitement methode = methodeTraitementService.find(form.getIdMethode());
		composant.setChuMethodeTraitement(methode);
		composant.setNomInternational(form.getNomInternational());
		composant.setNomCommercial(form.getNomCommercial());
		composant.setClasse(form.getClasse());
		composant.setAction(form.getAction());
		
		composantTraitementService.saveOrUpdate(composant);
		form.setIdComposant(composant.getIdComposant());
	}

	/** ====================================================================== */

	public FormComposant getForm(ChuComposantTraitement composant) {

		logger.debug("=== " + this.getClass().getName() + ": getForm" + " ===");

		FormComposant form = new FormComposant();
		
		form.setIdComposant(composant.getIdComposant());
		form.setIdMethode(composant.getChuMethodeTraitement().getIdMethode());
		form.setNomMethode(composant.getChuMethodeTraitement().getNom());
		form.setNomInternational(composant.getNomInternational());
		form.setNomCommercial(composant.getNomCommercial());
		form.setClasse(composant.getClasse());
		form.setAction(composant.getAction());

		return form;
	}

	/** ====================================================================== */

}
