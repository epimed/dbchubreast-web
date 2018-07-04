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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.form.FormPatient;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuPatientService;

@Component
public class FormPatientValidator extends BaseService implements Validator {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private ChuPatientService patientService;
	
	public boolean supports(Class<?> clazz) {
		return FormPatient.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		logger.debug("===== " + this.getClass().getName() + " =====");

		FormPatient form = (FormPatient) target;
		
		Date dateCourante = new Date();
		Date dateDeces = form.getDateDeces();
		Date dateNaissance = form.getDateNaissance();
		Integer idCauseDeces = form.getIdCauseDeces();
		List<Integer> listIdTumeursCausesDeces = form.getListIdTumeursCausesDeces();

		
		ChuPatient patient = patientService.find(form.getIdPatient());
		Date dateEvolution = patient==null ? null : patient.getDateEvolution();

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

		// === La date de deces ne peut pas etre antérieure à la date de la dernière nouvelle ===

		if (dateEvolution!=null && dateDeces!=null && dateDeces.before(dateEvolution)) {
			String message = "La date de décès " + dateFormat.format(dateDeces) 
					+ " ne peut pas être antérieure à la date de la dernière nouvelle " 
					+ dateFormat.format(dateEvolution) + " !";
			errors.rejectValue("dateDeces", "Consistensy.formPatient.dateDeces", message);
		}


		// === Si la date de déces est saisie alors la cause de deces doit etre saisie aussi ===

		if (dateDeces!=null && idCauseDeces==null) {
			String message = "Veuillez saisir la cause de décès !";
			errors.rejectValue("idCauseDeces", "Consistensy.formPatient.idCauseDeces", message);
		}

		// === Si la cause de deces est saisie alors la date de deces doit etre renseignee ===
		// === Si une tumeur cause de deces est saisie alors la date de deces doit etre renseignee ===

		boolean condition1 = dateDeces==null && idCauseDeces!=null;
		boolean condition2 = listIdTumeursCausesDeces!=null && !listIdTumeursCausesDeces.isEmpty() && dateDeces==null;
		if (condition1 || condition2) {
			String message = "Veuillez saisir la date de décès !";
			errors.rejectValue("dateDeces", "Consistensy.formPatient.dateDeces", message);
		}

		/* Si une tumeur cause de deces est saisie alors la cause de deces doit etre 
		 * soit 1 - Cancer(s) du sein 
		 * soit 3 - Multiples cancer(s) dont le sein 
		 */
		if (listIdTumeursCausesDeces!=null 
				&& !listIdTumeursCausesDeces.isEmpty() 
				&& idCauseDeces!=null 
				&& !idCauseDeces.equals(1) // cancer du sein 
				&& !idCauseDeces.equals(3) // multiples cancers dont le sein
				){
			String message = "La cause de décès choisie n'est pas compatible avec le choix des tumeurs de sein qui ont causé le décès. "
					+ "Veuillez choisir la cause de décès compatible ou décocher les tumeurs de sein !";
			errors.rejectValue("idCauseDeces", "Consistensy.formPatient.idCauseDeces", message);
		}


	}



}
