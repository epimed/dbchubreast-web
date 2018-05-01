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
package dbchubreast_web.service.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.entity.ChuMetastase;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.entity.ChuTnm;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.updater.UpdaterSurvival;

@Service
public class ChuPhaseTumeurServiceImpl extends BaseService implements ChuPhaseTumeurService {

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired
	private ChuTumeurService tumeurService;

	@Autowired
	private ChuTnmService tnmService;

	@Autowired
	private ChuPrelevementBiomarqueurService prelevementBiomarqueurService;

	@Autowired
	private ChuPrelevementService prelevementService;

	@Autowired
	private ChuTraitementService traitementService;

	@Autowired 
	private UpdaterSurvival updaterSurvival;



	/** =================================================================== */

	public ChuPhaseTumeur find(Integer idPhase) {
		return phaseTumeurDao.find(idPhase);
	}

	/** =================================================================== */

	public ChuPhaseTumeur findWithDependencies(Integer idPhase) {
		return phaseTumeurDao.findWithDependencies(idPhase);
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> list() {
		return phaseTumeurDao.list();
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> listWithDependencies() {
		return phaseTumeurDao.listWithDependencies();
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> list(Integer idTumeur) {
		return phaseTumeurDao.list(idTumeur);
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> listWithDependencies(Integer idTumeur) {
		return phaseTumeurDao.listWithDependencies(idTumeur);
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> list(Integer idTumeur, Integer idTypePhase) {
		return phaseTumeurDao.list(idTumeur, idTypePhase);
	}

	/** =================================================================== */

	public ChuPhaseTumeur findFirstRelapse(Integer idTumeur) {
		return phaseTumeurDao.findFirstRelapse(idTumeur);
	}

	/** =================================================================== */

	public ChuPhaseTumeur findByIdPrelevementWithDependencies(Integer idPrelevement) {
		return phaseTumeurDao.findByIdPrelevementWithDependencies(idPrelevement);
	}

	/** =================================================================== */

	public List<Object> listChronoPrelevementsTraitements(Integer idPhase) {
		return phaseTumeurDao.listChronoPrelevementsTraitements(idPhase);
	}

	/** =================================================================== */

	public void save(ChuPhaseTumeur phaseTumeur) {
		phaseTumeurDao.save(phaseTumeur);
	}

	/** =================================================================== */

	public void update(ChuPhaseTumeur phaseTumeur) {
		phaseTumeurDao.update(phaseTumeur);
	}

	/** =================================================================== */

	public void saveOrUpdate(ChuPhaseTumeur phaseTumeur) {
		phaseTumeurDao.saveOrUpdate(phaseTumeur);
	}

	/** =================================================================== */

	public ChuPhaseTumeur findPhaseInitiale(Integer idTumeur) {
		return phaseTumeurDao.findPhaseInitiale(idTumeur);
	}

	/** =================================================================== */

	public void deleteWithDependencies(ChuPhaseTumeur phaseTumeur) {

		logger.debug("Suppression en cours");

		// idPhase
		Integer idPhase = phaseTumeur.getIdPhase();

		// === TNM === 
		List<ChuTnm> listTnms = tnmService.find(idPhase);
		for (ChuTnm tnm: listTnms) {
			logger.debug("Suppression TNM " + tnm);
			tnmService.delete(tnm);
		}

		// === Metastases ===
		logger.debug("Suppression metastases ");
		List<ChuMetastase> emptyMetastaseList = new ArrayList<ChuMetastase>();
		phaseTumeur.setChuMetastases(emptyMetastaseList);
		this.update(phaseTumeur);


		// === Prelevements ===
		List<ChuPrelevement> listPrelevements = prelevementService.listByIdPhase(idPhase);
		for (ChuPrelevement prelevement : listPrelevements) {
			List<ChuPrelevementBiomarqueur> listPrelBio = prelevementBiomarqueurService.list(prelevement.getIdPrelevement());
			for (ChuPrelevementBiomarqueur prelBio: listPrelBio) {
				logger.debug("Suppression PB " + prelBio);
				prelevementBiomarqueurService.delete(prelBio);
			}
			logger.debug("Suppression prelevement " + prelevement);
			prelevementService.delete(prelevement);
		}

		// === Traitements ===
		List<ChuTraitement> listTraitements = traitementService.listByIdPhase(idPhase);
		for (ChuTraitement traitement: listTraitements) {
			logger.debug("Suppression traitement " + traitement);
			traitementService.delete(traitement);
		}

		// === Phase ===
		ChuTumeur tumeur = tumeurService.findByIdPhaseWithDependencies(idPhase);
		List<ChuTumeur> listTumeurs = new ArrayList<ChuTumeur>();
		listTumeurs.add(tumeur);
		phaseTumeurDao.delete(phaseTumeur);

		
		// === Update survival ===
		updaterSurvival.update(listTumeurs);
		
		/*

		//suppression des prelevements et des biomarqueurs de ces prelevements
		if(!listPrelevements.isEmpty()) {
			List<Integer> listIdPrelevement = new ArrayList<Integer>();
			for(int i=0; i<listPrelevements.size(); i++) {
				listIdPrelevement.add(listPrelevements.indexOf(listPrelevements.get(i)));
				prelevementService.delete(listPrelevements.get(i));
			}
			List<ChuPrelevementBiomarqueur> prelevementBio = prelevementBiomarqueurService.list(listIdPrelevement); 
			for(int i=0; i<prelevementBio.size(); i++) {
				prelevementBiomarqueurService.delete(prelevementBio.get(i));
			}
		}

		//suppression des tnm
		if(phaseTumeur!=null && phaseTumeur.getChuTnms()!=null) {
			for (ChuTnm tnm: phaseTumeur.getChuTnms()) {
				tnmService.delete(tnm);
			}
		}

		// metastases
		if (phaseTumeur!=null && phaseTumeur.getChuMetastases()!=null) {
			phaseTumeur.getChuMetastases().clear();
			phaseTumeurService.update(phaseTumeur);
		}

		//suppression des traitements
		if(phaseTumeur!=null && phaseTumeur.getChuTraitements()!=null) {
			for (ChuTraitement traitement: phaseTumeur.getChuTraitements()) {
				traitementService.delete(traitement);
			}
		}

		//suppressiond de la rechute
		phaseTumeurService.delete(phaseTumeur);
	}

	//mise a jour date derniere nouvelle
	if(phaseTumeur.getDateDiagnostic().equals(tumeur.getDateEvolution())) {
		List<ChuTumeur> tumeurs = tumeurService.listByIdPatient(patient.getIdPatient());
		for(int i=0; i<tumeurs.size(); i++) {
			tumeurs.get(i).setDateEvolution(null);
			tumeurService.update(tumeurs.get(i));	
		}
	}

	// !!! updaterSurvival.updateRechute(tumeur.getIdTumeur()); 

		phaseTumeurDao.delete(phaseTumeur);

		 */
	}

	/** =================================================================== */

}
