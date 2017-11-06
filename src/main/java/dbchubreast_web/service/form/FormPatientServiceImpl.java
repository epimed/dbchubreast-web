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

import dbchubreast_web.dao.ChuPatientDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.FormPatient;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.updater.UpdaterEvolution;
import dbchubreast_web.service.updater.UpdaterSurvival;
import dbchubreast_web.service.util.FormatService;

@Service
public class FormPatientServiceImpl extends BaseService implements FormPatientService {

	@Autowired
	private ChuPatientDao patientDao;

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private FormatService formatService;

	@Autowired
	private UpdaterSurvival updaterSurvival;

	@Autowired
	private UpdaterEvolution updaterEvolution;

	/** =========================================================================================== */

	public void saveOrUpdateForm(FormPatient form) {

		ChuPatient patient = null;

		if (form.isNew()) {
			patient = new ChuPatient();
		} else {
			patient = patientDao.find(form.getIdPatient());
		}

		// === Format nom prenom ===

		String nom = form.getNom();
		if (nom != null) {
			patient.setNom(nom.toUpperCase().trim());
		}
		
		String nomNaissance = form.getNomNaissance();
		if (nomNaissance != null) {
			patient.setNomNaissance(nomNaissance.toUpperCase().trim());
		}

		String prenom = form.getPrenom();
		if (prenom != null) {
			patient.setPrenom(formatService.formatFisrtName(prenom));
		}

		// === Attributs ===
		patient.setTk(form.getTk());
		patient.setRcp(form.getRcp());
		patient.setSexe(form.getSexe());
		patient.setDateNaissance(form.getDateNaissance());
		patient.setDateDeces(form.getDateDeces());
		patient.setCauseDeces(form.getCauseDeces());
		patient.setStatutBrca(form.getStatutBrca());

		// New patient
		if (patient.getIdPatient() == null) {
			patientDao.save(patient);
			form.setIdPatient(patient.getIdPatient());
		}

		// Existing patient
		else {
			patientDao.update(patient);
		}

		// === Linked actions ===
		List<ChuTumeur> listTumeurs = tumeurDao.listByIdPatientWithDependencies(patient.getIdPatient(), "tumeurs");

		// === Survival ===
		updaterSurvival.update(listTumeurs);

		// === Statut deces ===
		updaterEvolution.update(listTumeurs);

	}

	/** =========================================================================================== */

	public FormPatient getForm(ChuPatient patient) {
		FormPatient form = new FormPatient();
		if (patient!=null) {
			form.setIdPatient(patient.getIdPatient());
			form.setTk(patient.getTk());
			form.setRcp(patient.getRcp());
			form.setPrenom(patient.getPrenom());
			form.setNom(patient.getNom());
			form.setNomNaissance(patient.getNomNaissance());
			form.setSexe(patient.getSexe());
			form.setDateNaissance(patient.getDateNaissance());
			form.setDateDeces(patient.getDateDeces());
			form.setCauseDeces(patient.getCauseDeces());
			form.setStatutBrca(patient.getStatutBrca());
		}
		return form;
	}

	/** =========================================================================================== */

}
