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
package dbchubreast_web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.FormPhaseRechute;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuTumeurService;

@Component
public class FormPhaseRechuteValidator extends BaseService implements Validator{


	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuTumeurService tumeurService;

	public boolean supports(Class<?> clazz) {
		return FormPhaseRechute.class.isAssignableFrom(clazz);
	}


	public void validate(Object target, Errors errors) {

		logger.debug("===== " + this.getClass().getName() + " =====");

		FormPhaseRechute form = (FormPhaseRechute) target;

		ChuPatient patient = patientService.find(form.getIdPatient());
		ChuTumeur tumeur = tumeurService.find(form.getIdTumeur());


		// === Date diagnostic not empty ===

		if (form.getDateDiagnostic()==null) {
			String message = "Ne peut pas être vide !";
			errors.rejectValue("dateDiagnostic", "NotEmpty.formPhaseRechute.dateDiagnostic", message);
		}


		// === Date de diagnostic ne peut pas être avant la date de naissance ===

		if (form.getDateDiagnostic()!=null && patient.getDateNaissance()!=null) {
			if (form.getDateDiagnostic().before(patient.getDateNaissance())) {
				String message = "La date du diagnostic ne peut pas être antérieure à la date de naissance !";
				errors.rejectValue("dateDiagnostic", "Consistency.formPhaseRechute.dateDiagnostic", message);
			}
		}

		// === Date de diagnostic de la rechute ne peut pas être avant la date du diagnostic de la tumeur ===

		if (form.getDateDiagnostic()!=null && tumeur.getDateDiagnostic()!=null) {
			if (form.getDateDiagnostic().before(tumeur.getDateDiagnostic())) {
				String message = "La date du diagnostic de la rechute ne peut pas être antérieure à la date du diagnostic de la phase initiale "
						+ tumeur.getDateDiagnostic() + " !";
				errors.rejectValue("dateDiagnostic", "Consistency.formPhaseRechute.dateDiagnostic", message);
			}
		}

		// === Date de diagnostic ne peut pas être apres la date de deces ===

		if (form.getDateDiagnostic()!=null && patient.getDateDeces()!=null) {
			if (form.getDateDiagnostic().after(patient.getDateDeces())) {
				String message = "La date du diagnostic ne peut pas être postéreure à la date de décès " 
						+ patient.getDateDeces() + " !";
				errors.rejectValue("dateDiagnostic", "Consistency.formPhaseRechute.dateDiagnostic", message);
			}
		}

	}

}
