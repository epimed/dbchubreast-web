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

import dbchubreast_web.form.FormProtocole;
import dbchubreast_web.service.BaseService;

@Component
public class FormProtocoleValidator extends BaseService implements Validator {

	public boolean supports(Class<?> clazz) {
		return FormProtocole.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		logger.debug("===== " + this.getClass().getName() + " =====");

		FormProtocole form = (FormProtocole) target;

		// === Champs obligatoires ===

		if (form.getNom()==null) {
			String message = "Ne peut pas être vide !";
			errors.rejectValue("nom", "NotEmpty.formProtocole.nom", message);
		}
		
		int minNbLetters = 2;
		if (form.getNom()!=null && form.getNom().length()<minNbLetters) {
			String message = "Le nom de protocole doit comporter au moins " + minNbLetters + " charactères !"
					+ " Actuellement, il en comporte " + form.getNom().length() + ".";
			errors.rejectValue("nom", "Min.formProtocole.nom", message);
		}
		
		int maxNbLetters = 255;
		if (form.getNom()!=null && form.getNom().length()>maxNbLetters) {
			String message = "Le nom de protocole ne peut pas comporter plus de " + maxNbLetters + " charactères !"
					+ " Actuellement, il en comporte " + form.getNom().length() + ".";
			errors.rejectValue("nom", "Max.formProtocole.nom", message);
		}
		
		if (form.getCode()!=null && form.getCode().length()>10) {
			String message = "Le code ne peut pas comporter plus de 10 charactères !"
					+ " Actuellement, il en comporte " + form.getCode().length() + ".";
			errors.rejectValue("code", "Max.formProtocole.code", message);
		}
	}

}
