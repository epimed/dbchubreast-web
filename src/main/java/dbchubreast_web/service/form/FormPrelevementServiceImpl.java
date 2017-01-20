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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuBiomarqueurDao;
import dbchubreast_web.dao.ChuMorphologieDao;
import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.dao.ChuPrelevementBiomarqueurDao;
import dbchubreast_web.dao.ChuPrelevementDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.dao.ChuTypePrelevementDao;
import dbchubreast_web.entity.ChuBiomarqueur;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.entity.ChuPrelevementBiomarqueurId;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.FormBiomarqueur;
import dbchubreast_web.form.FormPrelevement;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.updater.UpdaterNodule;
import dbchubreast_web.service.updater.UpdaterStatutBiomarqueur;
import dbchubreast_web.service.updater.UpdaterTripleNegative;


@Service
public class FormPrelevementServiceImpl extends BaseService implements FormPrelevementService {

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired
	private ChuPrelevementDao prelevementDao;

	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;

	@Autowired
	private ChuTypePrelevementDao typePrelevementDao;

	@Autowired
	private ChuMorphologieDao morphologieDao;

	@Autowired
	private ChuBiomarqueurDao biomarqueurDao;

	@Autowired
	private UpdaterStatutBiomarqueur updaterStatutBiomarqueur;

	@Autowired
	private UpdaterTripleNegative updaterTripleNegative;
	
	@Autowired
	private UpdaterNodule updaterNodule;


	/** ====================================================================== */

	public void saveOrUpdateForm(FormPrelevement form) {


		logger.debug("=== " + this.getClass().getName() + ": saveOrUpdateForm" + " ===");

		// === Prelevement ===

		ChuPrelevement prelevement = null;

		if (form.isNew()) {
			prelevement = new ChuPrelevement();
		}
		else {
			prelevement = prelevementDao.find(form.getIdPrelevement());
		}

		prelevement.setChuPhaseTumeur(phaseTumeurDao.find(form.getIdPhase()));
		prelevement.setChuTypePrelevement(typePrelevementDao.find(form.getIdTypePrelevement()));
		prelevement.setDatePrelevement(form.getDatePrelevement());
		prelevement.setSitePrelevement(form.getSitePrelevement());
		prelevement.setChuMorphologie(morphologieDao.find(form.getIdMorphologie()));
		prelevement.setTypeHistologique(form.getTypeHistologique());
		prelevement.setAssociationCis(form.getAssociationCis());


		prelevementDao.saveOrUpdate(prelevement);

		form.setIdPrelevement(prelevement.getIdPrelevement());

		// === Biomarqueurs ===

		for (FormBiomarqueur formBiomarqueur : form.getListFormBiomarqueurs()) {

			if (formBiomarqueur.getIdBiomarqueur()!=null) {


				ChuPrelevementBiomarqueur prelBio = prelevementBiomarqueurDao.find(prelevement.getIdPrelevement(), formBiomarqueur.getIdBiomarqueur());

				if (formBiomarqueur.getValeur()!=null && !formBiomarqueur.getValeur().isEmpty()) {

					if (prelBio==null) {
						ChuPrelevementBiomarqueurId id = new ChuPrelevementBiomarqueurId();
						id.setIdPrelevement(prelevement.getIdPrelevement());
						id.setIdBiomarqueur(formBiomarqueur.getIdBiomarqueur());
						prelBio = new ChuPrelevementBiomarqueur();
						prelBio.setId(id);
					}
					prelBio.setValeur(formBiomarqueur.getValeur());

					prelevementBiomarqueurDao.saveOrUpdate(prelBio);

				}
				else {
					if (prelBio!=null) {

						// Une valeur de biomarqueur a ete supprimee
						// On supprime alors la table de liaison correspondante

						prelevementBiomarqueurDao.delete(prelBio);

					}
				}
			}
		}

		// ====== UPDATE DEPENDENCIES =====

		// === Update les statuts de biomarqueurs === 
		List<ChuPrelevementBiomarqueur> listPrelevementBiomarqueurs = prelevementBiomarqueurDao.list(prelevement.getIdPrelevement());
		updaterStatutBiomarqueur.update(listPrelevementBiomarqueurs);

		// === Update triple negative ===
		updaterTripleNegative.update(prelevement);

		// === Update nodules ===
		updaterNodule.update(prelevement);
		
	}

	/** ====================================================================== */

	public FormPrelevement getForm(ChuPrelevement prelevement) {

		logger.debug("=== " + this.getClass().getName() + ": getForm" + " ===");

		FormPrelevement form = new FormPrelevement();

		form.setIdPrelevement(prelevement.getIdPrelevement());
		form.setIdMorphologie(prelevement.getChuMorphologie().getIdMorphologie());
		form.setIdTypePrelevement(prelevement.getChuTypePrelevement().getIdTypePrelevement());
		form.setTypeHistologique(prelevement.getTypeHistologique());
		form.setAssociationCis(prelevement.getAssociationCis());
		form.setDatePrelevement(prelevement.getDatePrelevement());
		form.setSitePrelevement(prelevement.getSitePrelevement());

		ChuPhaseTumeur phase = phaseTumeurDao.findByIdPrelevementWithDependencies(prelevement.getIdPrelevement());
		form.setIdPhase(phase.getIdPhase());

		ChuTumeur tumeur = tumeurDao.findByIdPhaseWithDependencies(phase.getIdPhase());
		form.setIdTumeur(tumeur.getIdTumeur());
		form.setIdPatient(tumeur.getChuPatient().getIdPatient());

		// === Biomarqueurs ===

		List<ChuBiomarqueur> listBiomarqueurs = biomarqueurDao.list();
		List<FormBiomarqueur> listFormBiomarqueurs = new ArrayList<FormBiomarqueur>();
		for (ChuBiomarqueur biomarqueur : listBiomarqueurs) {
			FormBiomarqueur formBio = new FormBiomarqueur();
			formBio.setIdBiomarqueur(biomarqueur.getIdBiomarqueur());
			formBio.setNom(biomarqueur.getNom());
			ChuPrelevementBiomarqueur prelBio = prelevementBiomarqueurDao.find(prelevement.getIdPrelevement(), biomarqueur.getIdBiomarqueur());
			if (prelBio!=null) {
				formBio.setValeur(prelBio.getValeur());
				formBio.setStatut(prelBio.getStatut());
			}
			listFormBiomarqueurs.add(formBio);
		}
		form.setListFormBiomarqueurs(listFormBiomarqueurs);

		return form;
	}

	/** ====================================================================== */

}
