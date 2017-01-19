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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.form.FormTumeurInitiale;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.util.FormatService;

@Component
public class FormTumeurInitialeValidator extends BaseService implements Validator{


	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private FormatService formatService;

	public boolean supports(Class<?> clazz) {
		return FormTumeurInitiale.class.isAssignableFrom(clazz);
	}


	public void validate(Object target, Errors errors) {

		logger.debug("===== " + this.getClass().getName() + " =====");

		FormTumeurInitiale form = (FormTumeurInitiale) target;


		ChuPatient patient = patientService.find(form.getIdPatient());

		// === Date diagnostic / age diagnostic not empty ===

		if (form.getDateDiagnostic()==null && form.getAgeDiagnostic()==null) {
			String message = "Saisir soit la date du diagnostic soit l'age au diagnostic !";
			errors.rejectValue("dateDiagnostic", "NotEmpty.formTumeurInitiale.dateDiagnostic", message);
			errors.rejectValue("ageDiagnostic", "NotEmpty.formTumeurInitiale.ageDiagnostic", message);
		}

		// === Age diagnostic incoherent avec les dates ===

		if (form.getDateDiagnostic()!=null && form.getAgeDiagnostic()!=null && patient.getDateNaissance()!=null) {
			Date dateNaissance =  patient.getDateNaissance();
			Double calculatedAge = formatService.calculateAge(dateNaissance, form.getDateDiagnostic());
			if (calculatedAge!=null && Math.abs(calculatedAge-form.getAgeDiagnostic())>1) {
				String message = "L'age saisi " + form.getAgeDiagnostic() 
				+ " ne correspond pas aux dates de naissance et du diagnostic. L'age calculé pour ces dates est " + calculatedAge + ".";
				errors.rejectValue("ageDiagnostic", "Consistency.formTumeurInitiale.ageDiagnostic", message);
			}
		}

		// === Date de diagnostic ne peut pas être avant la date de naissance ===
		
		if (form.getDateDiagnostic()!=null && patient.getDateNaissance()!=null) {
			if (form.getDateDiagnostic().before(patient.getDateNaissance())) {
				String message = "La date du diagnostic ne peut pas être antérieure à la date de naissance !";
				errors.rejectValue("dateDiagnostic", "Consistency.formTumeurInitiale.dateDiagnostic", message);
			}
		}
		
		// === Date de diagnostic ne peut pas être apres la date de deces ===
		
		if (form.getDateDiagnostic()!=null && form.getDateDeces()!=null) {
			if (form.getDateDiagnostic().after(form.getDateDeces())) {
				String message = "La date du diagnostic ne peut pas être postéreure à la date de décès !";
				errors.rejectValue("dateDiagnostic", "Consistency.formTumeurInitiale.dateDiagnostic", message);
			}
		}

	}

}
