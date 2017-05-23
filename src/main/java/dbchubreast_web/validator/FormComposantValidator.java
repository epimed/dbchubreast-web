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

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dbchubreast_web.form.FormComposant;
import dbchubreast_web.service.BaseService;

@Component
public class FormComposantValidator extends BaseService implements Validator {

	public boolean supports(Class<?> clazz) {
		return FormComposant.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		logger.debug("===== " + this.getClass().getName() + " =====");

		FormComposant form = (FormComposant) target;

		// === Champs obligatoires ===

		if (form.getNomInternational()==null) {
			String message = "Ne peut pas être vide !";
			errors.rejectValue("nomInternational", "NotEmpty.formComposant.nomInternational", message);
		}
		
		int minNbLetters = 2;
		if (form.getNomInternational()!=null && form.getNomInternational().length()<minNbLetters) {
			String message = "Le libellé doit comporter au moins " + minNbLetters + " charactères !"
					+ " Actuellement, il en comporte " + form.getNomInternational().length() + ".";
			errors.rejectValue("nomInternational", "Min.formComposant.nomInternational", message);
		}
		
		int maxNbLetters = 500;
		if (form.getNomInternational()!=null && form.getNomInternational().length()>maxNbLetters) {
			String message = "Le libellé ne peut pas comporter plus de " + maxNbLetters + " charactères !"
					+ " Actuellement, il en comporte " + form.getNomInternational().length() + ".";
			errors.rejectValue("nomInternational", "Max.formComposant.nomInternational", message);
		}
	}
}
