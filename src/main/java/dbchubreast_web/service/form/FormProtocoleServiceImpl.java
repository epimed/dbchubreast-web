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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.ChuComposantTraitement;
import dbchubreast_web.entity.ChuMethodeTraitement;
import dbchubreast_web.entity.ChuProtocoleTraitement;
import dbchubreast_web.form.FormProtocole;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuComposantTraitementService;
import dbchubreast_web.service.business.ChuMethodeTraitementService;
import dbchubreast_web.service.business.ChuProtocoleTraitementService;

@Service
public class FormProtocoleServiceImpl extends BaseService implements FormProtocoleService {

	@Autowired
	private ChuProtocoleTraitementService protocoleTraitementService;
	
	@Autowired
	private ChuMethodeTraitementService methodeTraitementService;
	
	@Autowired
	private ChuComposantTraitementService composantTraitementService;

	/** ====================================================================== */

	public void saveOrUpdateForm(FormProtocole form) {

		logger.debug("=== " + this.getClass().getName() + ": saveOrUpdateForm" + " ===");
		
		ChuProtocoleTraitement protocole = null;
		if (form.isNew()) {
			protocole = new ChuProtocoleTraitement();
		}
		else {
			protocole = protocoleTraitementService.find(form.getIdProtocole());
		}
		
		ChuMethodeTraitement methode = methodeTraitementService.find(form.getIdMethode());
		protocole.setChuMethodeTraitement(methode);
		protocole.setCode(form.getCode());
		protocole.setNom(form.getNom());
		
		protocole.setChuComposantTraitements(null);
		if (form.getListIdComposants()!=null && !form.getListIdComposants().isEmpty()) {
			List<ChuComposantTraitement> listComposants = composantTraitementService.listById(form.getListIdComposants());
			protocole.setChuComposantTraitements(listComposants);
		}
		protocoleTraitementService.saveOrUpdate(protocole);
		form.setIdProtocole(protocole.getIdProtocole());
	}

	/** ====================================================================== */

	public FormProtocole getForm(ChuProtocoleTraitement protocole) {

		logger.debug("=== " + this.getClass().getName() + ": getForm" + " ===");

		FormProtocole form = new FormProtocole();
		
		form.setIdProtocole(protocole.getIdProtocole());
		form.setIdMethode(protocole.getChuMethodeTraitement().getIdMethode());
		form.setNomMethode(protocole.getChuMethodeTraitement().getNom());
		form.setCode(protocole.getCode());
		form.setNom(protocole.getNom());
		
		if (protocole.getChuComposantTraitements()!=null) {
			for (ChuComposantTraitement composant : protocole.getChuComposantTraitements()) {
				form.getListIdComposants().add(composant.getIdComposant());
			}
		}

		return form;
	}

	/** ====================================================================== */

}
