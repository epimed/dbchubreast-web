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

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dbchubreast_web.form.FormPatient;
import dbchubreast_web.service.BaseService;

@Component
public class FormPatientValidator extends BaseService implements Validator {


	public boolean supports(Class<?> clazz) {
		return FormPatient.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		logger.debug("===== " + this.getClass().getName() + " =====");

		FormPatient form = (FormPatient) target;



		Date dateCourante = new Date();
		Date dateDeces = form.getDateDeces();
		Date dateNaissance = form.getDateNaissance();

		// === La date de deces ne peut pas etre posterieure à la date courante ===

		if (dateDeces!=null && dateDeces.after(dateCourante)) {
			String message = "La date de décès ne peut pas être postérieure à la date courante !";
			errors.rejectValue("dateDeces", "Consistensy.formPatient.dateDeces", message);
		}

		// === La date de naissance ne peut pas etre posterieure à la date courante ===

		if (dateNaissance!=null && dateNaissance.after(dateCourante)) {
			String message = "La date de naissance ne peut pas être postérieure à la date courante !";
			errors.rejectValue("dateNaissance", "Consistensy.formPatient.dateNaissance", message);
		}


		// === La date de deces ne peut pas etre antérieure à la date de naissance ===

		if (dateNaissance!=null && dateDeces!=null && dateDeces.before(dateNaissance)) {
			String message = "La date de décès ne peut pas être antérieure à la date de naissance !";
			errors.rejectValue("dateDeces", "Consistensy.formPatient.dateDeces", message);
		}
	}

}
