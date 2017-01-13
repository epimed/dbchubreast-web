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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuEvolutionDao;
import dbchubreast_web.dao.ChuMetastaseDao;
import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.dao.ChuTnmDao;
import dbchubreast_web.dao.ChuTopographieDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.dao.ChuTypePhaseDao;
import dbchubreast_web.entity.ChuEvolution;
import dbchubreast_web.entity.ChuMetastase;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTnm;
import dbchubreast_web.entity.ChuTopographie;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.entity.ChuTypePhase;
import dbchubreast_web.form.FormTumeurInitiale;
import dbchubreast_web.service.BaseService;


@Service
public class ChuPhaseTumeurServiceImpl extends BaseService implements ChuPhaseTumeurService {

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuTypePhaseDao typePhaseDao;

	@Autowired
	private ChuTopographieDao topographieDao;

	@Autowired
	private ChuEvolutionDao evolutionDao;

	@Autowired
	private ChuMetastaseDao metastaseDao;

	@Autowired
	private ChuTnmDao tnmDao;

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

	public List<ChuPhaseTumeur> list(Integer idTumeur, Integer idTypePhase) {
		return phaseTumeurDao.list(idTumeur, idTypePhase);
	}

	/** =================================================================== */

	public void saveOrUpdateForm(FormTumeurInitiale form) {

		ChuTumeur tumeur =  null;
		ChuPhaseTumeur phaseTumeur = null;

		if (form.isNew()) {
			tumeur = new ChuTumeur();
			phaseTumeur = new ChuPhaseTumeur();
			tumeur.getChuPhaseTumeurs().add(phaseTumeur);
			phaseTumeur.setChuTumeur(tumeur);
		}
		else {
			tumeur = tumeurDao.find(form.getIdTumeur());
			phaseTumeur = phaseTumeurDao.findWithDependencies(form.getIdPhase());
		}

		// === Tumeur ===

		tumeur.setIdTumeur(form.getIdTumeur());
		tumeur.setDateDiagnostic(form.getDateDiagnostic());


		// Age diagnostic
		// Calculate !!!
		tumeur.setAgeDiagnostic(form.getAgeDiagnostic());


		tumeur.setCote(form.getCote());

		ChuTopographie topographie = topographieDao.find(form.getIdTopographie());	
		tumeur.setChuTopographie(topographie);

		tumeur.setDateEvolution(form.getDateEvolution());
		ChuEvolution evolution = evolutionDao.find(form.getIdEvolution());
		tumeur.setChuEvolution(evolution);

		// === Phase tumeur ===

		phaseTumeur.setDateDiagnostic(form.getDateDiagnostic());
		phaseTumeur.setNatureDiagnostic(form.getNatureDiagnostic());

		// === Type phase ===

		ChuTypePhase typePhase = typePhaseDao.find(form.getIdTypePhase());
		phaseTumeur.setChuTypePhase(typePhase);

		// === cTNM ===

		ChuTnm cTnm = tnmDao.find(phaseTumeur.getIdPhase(), "c");
		if (cTnm==null) {
			cTnm = new ChuTnm();
			cTnm.setType("c");
			cTnm.setChuPhaseTumeur(phaseTumeur);
		}
		cTnm.setT(form.getcT());
		cTnm.setN(form.getcN());
		cTnm.setM(form.getcM());
		cTnm.setTaille(form.getcTaille());
		phaseTumeur.getChuTnms().add(cTnm);

		// === pTNM ===

		ChuTnm pTnm = tnmDao.find(phaseTumeur.getIdPhase(), "p");
		if (pTnm==null) {
			pTnm = new ChuTnm();
			pTnm.setType("p");
			pTnm.setChuPhaseTumeur(phaseTumeur);
		}
		pTnm.setT(form.getpT());
		pTnm.setN(form.getpN());
		pTnm.setM(form.getpM());
		pTnm.setTaille(form.getpTaille());
		phaseTumeur.getChuTnms().add(pTnm);

		// === Metastases ===

		if (form.getListIdMetastases()!=null && !form.getListIdMetastases().isEmpty()) {
			List<ChuMetastase> metastases = metastaseDao.list(form.getListIdMetastases());
			phaseTumeur.setChuMetastases(metastases);
			phaseTumeur.setMetastases(true);
		}
		else {
			phaseTumeur.setMetastases(false);
		}

		logger.debug("Tumeur {}", tumeur);
		logger.debug("Phase tumeur {}", phaseTumeur);


		// ====== Save or Update =======

		if (form.isNew()) {
			// Save new
			tumeurDao.save(tumeur);
			phaseTumeurDao.save(phaseTumeur);
			if (!this.isEmptyTnm(cTnm)) {
				tnmDao.save(cTnm);
			}
			if (!this.isEmptyTnm(pTnm)) {
				tnmDao.save(pTnm);
			}
		}
		else {

			// update existing

			tumeurDao.update(tumeur);
			phaseTumeurDao.saveOrUpdate(phaseTumeur);

			if (!this.isEmptyTnm(cTnm)) {
				tnmDao.saveOrUpdate(cTnm);
			}

			if (!this.isEmptyTnm(pTnm)) {
				tnmDao.saveOrUpdate(pTnm);
			}

		}
	}

	/** =================================================================== */

	public FormTumeurInitiale getFormTumeurInitiale(ChuTumeur tumeur) {

		ChuPatient patient = tumeur.getChuPatient();
		FormTumeurInitiale formTumeurInitiale = new FormTumeurInitiale(patient.getIdPatient());

		// === Tumeur ===

		formTumeurInitiale.setIdTumeur(tumeur.getIdTumeur());
		formTumeurInitiale.setDateDiagnostic(tumeur.getDateDiagnostic());
		formTumeurInitiale.setAgeDiagnostic(tumeur.getAgeDiagnostic());
		formTumeurInitiale.setIdTopographie(tumeur.getChuTopographie()==null ? null : tumeur.getChuTopographie().getIdTopographie());
		formTumeurInitiale.setCote(tumeur.getCote());
		formTumeurInitiale.setDateEvolution(tumeur.getDateEvolution());
		formTumeurInitiale.setIdEvolution(tumeur.getChuEvolution()==null ? null : tumeur.getChuEvolution().getIdEvolution());

		// === Phase ===

		List<ChuPhaseTumeur> listPhases = phaseTumeurDao.list(tumeur.getIdTumeur(), 1);
		ChuPhaseTumeur phase = listPhases.get(0);
		formTumeurInitiale.setIdPhase(phase.getIdPhase());
		formTumeurInitiale.setNatureDiagnostic(phase.getNatureDiagnostic());
		formTumeurInitiale.setProfondeur(phase.getProfondeur());

		// === cTNM ===

		ChuTnm cTnm = tnmDao.find(phase.getIdPhase(), "c");
		if (cTnm==null) {
			cTnm = new ChuTnm();
		}
		formTumeurInitiale.setcT(cTnm.getT());
		formTumeurInitiale.setcN(cTnm.getN());
		formTumeurInitiale.setcM(cTnm.getM());
		formTumeurInitiale.setcTaille(cTnm.getTaille());

		// === pTNM ===

		ChuTnm pTnm = tnmDao.find(phase.getIdPhase(), "p");
		if (pTnm==null) {
			pTnm = new ChuTnm();
		}
		formTumeurInitiale.setpT(pTnm.getT());
		formTumeurInitiale.setpN(pTnm.getN());
		formTumeurInitiale.setpM(pTnm.getM());
		formTumeurInitiale.setpTaille(pTnm.getTaille());

		// === Metastases ===

		List<ChuMetastase> listMetastases = metastaseDao.list(phase.getIdPhase());
		for (ChuMetastase metastase : listMetastases) {
			formTumeurInitiale.getListIdMetastases().add(metastase.getIdMetastase());
		}

		formTumeurInitiale.setRemarque(phase.getRemarque());

		return formTumeurInitiale;
	}

	/** =================================================================== */

	private boolean isEmptyTnm(ChuTnm tnm) {
		return tnm.getIdTnm()==null && tnm.getT()==null && tnm.getN()==null && tnm.getM()==null && tnm.getTaille()==null;
	}

	/** =================================================================== */

}
