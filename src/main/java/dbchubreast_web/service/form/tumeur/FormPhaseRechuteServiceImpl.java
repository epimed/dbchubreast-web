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
package dbchubreast_web.service.form.tumeur;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuMetastaseDao;
import dbchubreast_web.dao.ChuPerformanceStatusDao;
import dbchubreast_web.dao.ChuTnmDao;
import dbchubreast_web.dao.ChuTopographieDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.dao.ChuTypePhaseDao;
import dbchubreast_web.entity.ChuMetastase;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTnm;
import dbchubreast_web.entity.ChuTopographie;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.entity.ChuTypePhase;
import dbchubreast_web.form.tumeur.FormPhaseRechute;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;
import dbchubreast_web.service.updater.UpdaterNodule;
import dbchubreast_web.service.updater.UpdaterSurvival;

@Service
public class FormPhaseRechuteServiceImpl extends BaseService implements FormPhaseRechuteService {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuPhaseTumeurService phaseTumeurService;

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuTypePhaseDao typePhaseDao;

	@Autowired
	private ChuTopographieDao topographieDao;

	@Autowired
	private ChuMetastaseDao metastaseDao;

	@Autowired
	private ChuPerformanceStatusDao performanceStatusDao;

	@Autowired
	private ChuTnmDao tnmDao;

	@Autowired
	private UpdaterSurvival updaterSurvival;

	@Autowired
	private UpdaterNodule updaterNodule;


	/** =================================================================== */

	@Override
	public FormPhaseRechute createNewFormPhaseRechute(ChuTumeur tumeur) {

		ChuPatient patient = patientService.find(tumeur.getIdTumeur());

		FormPhaseRechute form = new FormPhaseRechute(patient.getIdPatient(), tumeur.getIdTumeur());

		ChuPhaseTumeur phaseInitiale = phaseTumeurService.findPhaseInitiale(tumeur.getIdTumeur());

		// === Pre-remplir la topographie si elle est connue pour la phase initiale ===

		if (phaseInitiale!=null && phaseInitiale.getChuTopographie()!=null) {
			form.setIdTopographieTumeurInitiale(phaseInitiale.getChuTopographie().getIdTopographie());
			form.setNomTopographieTumeurInitiale(phaseInitiale.getChuTopographie().getNomFr());
		}

		return form;
	}

	/** =================================================================== */

	@Override
	public FormPhaseRechute getFormPhaseRechute(ChuPhaseTumeur phase) {

		ChuTumeur tumeur = phase.getChuTumeur();
		ChuPatient patient = patientService.find(tumeur.getIdTumeur());
		ChuPhaseTumeur phaseInitiale = phaseTumeurService.findPhaseInitiale(tumeur.getIdTumeur());

		FormPhaseRechute form = new FormPhaseRechute(patient.getIdPatient(), tumeur.getIdTumeur());

		form.setIdPhase(phase.getIdPhase());
		form.setDateDiagnostic(phase.getDateDiagnostic());
		form.setLocale(phase.getLocale());

		if (phase.getChuPerformanceStatus()!=null) {
			form.setIdPs(phase.getChuPerformanceStatus().getIdPs());
		}


		// === Rechute locale et / ou metastatique  ===
		form.setLocale(phase.getLocale());
		form.setMetastases(phase.getMetastases());


		// === Topographie de la rechute locale  ===

		form.setIdTopographie(phase.getChuTopographie() == null ? null : phase.getChuTopographie().getIdTopographie());


		// === Topographie de la phase initiale ===

		if (phaseInitiale!=null && phaseInitiale.getChuTopographie()!=null) {
			form.setIdTopographieTumeurInitiale(phaseInitiale.getChuTopographie().getIdTopographie());
			form.setNomTopographieTumeurInitiale(phaseInitiale.getChuTopographie().getNomFr());
		}


		// === cTNM ===

		ChuTnm cTnm = tnmDao.find(phase.getIdPhase(), "c");
		if (cTnm == null) {
			cTnm = new ChuTnm();
		}
		form.setcT(cTnm.getT());
		form.setcN(cTnm.getN());
		form.setcM(cTnm.getM());
		form.setcTaille(cTnm.getTaille());

		// === pTNM ===

		ChuTnm pTnm = tnmDao.find(phase.getIdPhase(), "p");
		if (pTnm == null) {
			pTnm = new ChuTnm();
		}
		form.setpT(pTnm.getT());
		form.setpN(pTnm.getN());
		form.setpM(pTnm.getM());
		form.setpTaille(pTnm.getTaille());


		// === Metastases ===

		List<ChuMetastase> listMetastases = metastaseDao.list(phase.getIdPhase());
		for (ChuMetastase metastase : listMetastases) {
			form.getListIdMetastases().add(metastase.getIdMetastase());
		}

		form.setRemarque(phase.getRemarque());
		return form;
	}

	/** =================================================================== */

	public void saveOrUpdateForm(FormPhaseRechute form) {

		logger.debug("=== " + this.getClass().getName() + " ===");


		ChuTumeur tumeur = tumeurDao.findWithDependencies(form.getIdTumeur());
		ChuPhaseTumeur phaseTumeur = null;

		if (form.isNew()) {
			phaseTumeur = new ChuPhaseTumeur();
			phaseTumeur.setChuTumeur(tumeur);
		} 
		else {
			phaseTumeur = phaseTumeurService.find(form.getIdPhase());
		}

		// === Type phase ===
		ChuTypePhase typePhase = typePhaseDao.find(form.getIdTypePhase());
		phaseTumeur.setChuTypePhase(typePhase);

		// === Phase tumeur ===
		phaseTumeur.setDateDiagnostic(form.getDateDiagnostic());
		phaseTumeur.setLocale(form.getLocale());
		phaseTumeur.setRemarque(form.getRemarque());

		// === Rechute locale et / ou metastatique  ===
		phaseTumeur.setLocale(form.getLocale());
		phaseTumeur.setMetastases(form.getMetastases());


		// === Topographie de la rechute ===
		if (phaseTumeur.getLocale()!=null && phaseTumeur.getLocale()==true) {
			ChuTopographie topographie = topographieDao.find(form.getIdTopographie());
			phaseTumeur.setChuTopographie(topographie);
		}
		else {
			phaseTumeur.setChuTopographie(null);
		}


		// === Metastases ===
		if (phaseTumeur.getMetastases()!=null && phaseTumeur.getMetastases()==true) {
			this.addMetastases(phaseTumeur, form.getListIdMetastases());
		}
		else {
			phaseTumeur.setChuMetastases(new ArrayList<ChuMetastase>());
		}



		// === Performance status ===

		phaseTumeur.setChuPerformanceStatus(performanceStatusDao.find(form.getIdPs()));


		// === cTNM ===

		ChuTnm cTnm = tnmDao.find(phaseTumeur.getIdPhase(), "c");
		if (cTnm == null) {
			cTnm = new ChuTnm();
			cTnm.setType("c");
			cTnm.setChuPhaseTumeur(phaseTumeur);
		}
		cTnm.setT(form.getcT());
		cTnm.setN(form.getcN());
		cTnm.setM(form.getcM());
		cTnm.setTaille(form.getcTaille());


		// === pTNM ===

		ChuTnm pTnm = tnmDao.find(phaseTumeur.getIdPhase(), "p");
		if (pTnm == null) {
			pTnm = new ChuTnm();
			pTnm.setType("p");
			pTnm.setChuPhaseTumeur(phaseTumeur);
		}
		pTnm.setT(form.getpT());
		pTnm.setN(form.getpN());
		pTnm.setM(form.getpM());
		pTnm.setTaille(form.getpTaille());


		// ====== Save or Update =======

		if (form.isNew()) {
			// Save new
			phaseTumeurService.save(phaseTumeur);
			form.setIdPhase(phaseTumeur.getIdPhase());

			if (!this.isEmptyTnm(cTnm)) {
				logger.debug("Saving cTnm {}", cTnm);
				tnmDao.save(cTnm);
			}
			if (!this.isEmptyTnm(pTnm)) {
				logger.debug("Saving pTnm {}", pTnm);
				tnmDao.save(pTnm);
			}
		} 
		else {
			// update existing
			phaseTumeurService.saveOrUpdate(phaseTumeur);

			if (!this.isEmptyTnm(cTnm)) {
				tnmDao.saveOrUpdate(cTnm);
			}

			if (!this.isEmptyTnm(pTnm)) {
				tnmDao.saveOrUpdate(pTnm);
			}
		}

		// ====== UPDATE DEPENDENCIES =====

		this.updateDependencies(tumeur, phaseTumeur);



	}

	/** =================================================================== */

	private void updateDependencies(ChuTumeur tumeur, ChuPhaseTumeur phaseTumeur) {

		// ====== UPDATE DEPENDENCIES =====

		// === Survival ===
		List<ChuTumeur> listTumeurs = new ArrayList<ChuTumeur>();
		listTumeurs.add(tumeur);
		updaterSurvival.update(listTumeurs);


		// === Update nodules ===
		updaterNodule.update(phaseTumeur);
	}

	/** =================================================================== */

	private boolean isEmptyTnm(ChuTnm tnm) {
		return tnm.getIdTnm() == null && tnm.getT() == null && tnm.getN() == null && tnm.getM() == null
				&& tnm.getTaille() == null;
	}

	/** =================================================================== */

	private void addMetastases(ChuPhaseTumeur phaseTumeur, List<Integer> listIdMetastases) {

		if (listIdMetastases != null && !listIdMetastases.isEmpty()) {
			List<ChuMetastase> metastases = metastaseDao.list(listIdMetastases);
			phaseTumeur.setChuMetastases(metastases);
			phaseTumeur.setMetastases(true);
		} else {
			phaseTumeur.setMetastases(false);
			phaseTumeur.setChuMetastases(null);
		}

	}

	/** =================================================================== */

}
