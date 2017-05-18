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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.form.FormTraitement;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;

@Component
public class FormTraitementValidator extends BaseService implements Validator {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private ChuPhaseTumeurService phaseTumeurService;
	
	

	public boolean supports(Class<?> clazz) {
		return FormTraitement.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		logger.debug("===== " + this.getClass().getName() + " =====");

		FormTraitement form = (FormTraitement) target;

		// === Champs obligatoires ===

		String message = "Ne peut pas être vide !";

		if (form.getIdTumeur() == null) {
			errors.rejectValue("idTumeur", "NotEmpty.formTraitement.idTumeur", message);
		}

		if (form.getIdPhase() == null) {
			errors.rejectValue("idPhase", "NotEmpty.formTraitement.idPhase", message);
		}
		
		if (form.getIdMethode() == null) {
			errors.rejectValue("idMethode", "NotEmpty.formTraitement.idMethode", message);
		}
		
		if (form.getIdProtocole() == null) {
			errors.rejectValue("idProtocole", "NotEmpty.formTraitement.idProtocole", message);
		}
		
		if (form.getDateDebut() == null) {
			errors.rejectValue("dateDebut", "NotEmpty.formTraitement.dateDebut", message);
		}
		
		// === Dates ===
		
		Date dateDebut = form.getDateDebut();
		Date dateFin = form.getDateFin();
		
		ChuPhaseTumeur phaseTumeur = phaseTumeurService.find(form.getIdPhase());
		Date dateDiagnosticPhase = phaseTumeur == null ? null : phaseTumeur.getDateDiagnostic();
		
		if (dateDebut!=null && dateDiagnosticPhase!=null && dateDiagnosticPhase.after(dateDebut)) {
			message = "La date de début du traitement ne peut pas être antérieure à la date du diagnostic " 
					+ dateFormat.format(dateDiagnosticPhase)+ " !";
			errors.rejectValue("dateDebut", "Consistensy.formTraitement.dateDebut", message);
		}
		
		if (dateDebut!=null && dateFin!=null && dateDebut.after(dateFin)) {
			message = "La date de début ne peut pas être postérieure à la date de fin !";
			errors.rejectValue("dateDebut", "Consistensy.formTraitement.dateDebut", message);
			errors.rejectValue("dateFin", "Consistensy.formTraitement.dateFin", message);
		}
		
	}

}
