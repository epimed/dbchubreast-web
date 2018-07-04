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

import dbchubreast_web.entity.ChuCauseDeces;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.FormPatient;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuCauseDecesService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuTumeurService;
import dbchubreast_web.service.updater.UpdaterSurvival;
import dbchubreast_web.service.util.FormatService;

@Service
public class FormPatientServiceImpl extends BaseService implements FormPatientService {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuTumeurService tumeurService;

	@Autowired
	private ChuCauseDecesService chuCauseDecesService;

	@Autowired
	private FormatService formatService;

	@Autowired
	private UpdaterSurvival updaterSurvival;

	/** =========================================================================================== */

	public void loadLists(FormPatient form) {
		List<ChuCauseDeces> listCausesDeces = chuCauseDecesService.list();
		form.setListCausesDeces(listCausesDeces);
		List<ChuTumeur> listTumeurs = tumeurService.listByIdPatientWithDependencies(form.getIdPatient(), "tumeurs");
		form.setListTumeurs(listTumeurs);
	}


	/** =========================================================================================== */

	public FormPatient getForm(ChuPatient patient) {

		FormPatient form = new FormPatient();

		patient = patientService.findByIdPatientWithDependencies(patient.getIdPatient());

		List<ChuCauseDeces> listCausesDeces = chuCauseDecesService.list();
		ChuCauseDeces causeDeces = chuCauseDecesService.findByIdPatient(patient.getIdPatient());

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
			form.setIdCauseDeces(causeDeces==null ? null : causeDeces.getIdCauseDeces());
			form.setListCausesDeces(listCausesDeces);
			form.setStatutBrca(patient.getStatutBrca());

			List<ChuTumeur> listTumeurs = tumeurService.listByIdPatientWithDependencies(patient.getIdPatient(), "tumeurs");
			form.setListTumeurs(listTumeurs);

			for (ChuTumeur tumeurCauseDeces : patient.getChuTumeursCauseDeces()) {
				form.getListIdTumeursCausesDeces().add(tumeurCauseDeces.getIdTumeur());
			}

		}
		return form;
	}

	/** =========================================================================================== */

	public void saveOrUpdateForm(FormPatient form) {

		ChuPatient patient = null;

		if (form.isNew()) {
			patient = new ChuPatient();
		} else {
			patient = patientService.findByIdPatientWithDependencies(form.getIdPatient());
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
		patient.setStatutBrca(form.getStatutBrca());

		// === Cause Deces ===
		ChuCauseDeces causeDeces = chuCauseDecesService.find(form.getIdCauseDeces());
		patient.setChuCauseDeces(causeDeces);

		// === Tumeurs qui ont cause le deces ===
		patient.getChuTumeursCauseDeces().clear();
		List<Integer> listIdTumeursCausesDeces = form.getListIdTumeursCausesDeces();
		for (Integer idTumeurCauseDeces: listIdTumeursCausesDeces) {
			patient.getChuTumeursCauseDeces().add(tumeurService.find(idTumeurCauseDeces));
		}
		
		patientService.saveOrUpdate(patient);
		form.setIdPatient(patient.getIdPatient());


		// === Linked actions ===
		List<ChuTumeur> listTumeurs = tumeurService.listByIdPatientWithDependencies(patient.getIdPatient(), "tumeurs");

		// === Survival ===
		updaterSurvival.update(listTumeurs);

	}

	/** =========================================================================================== */

}
