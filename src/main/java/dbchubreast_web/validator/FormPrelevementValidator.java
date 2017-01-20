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
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.form.FormPrelevement;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;

@Component
public class FormPrelevementValidator extends BaseService implements Validator {

	
	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuPhaseTumeurService phaseTumeurService;

	public boolean supports(Class<?> clazz) {
		return FormPrelevement.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		logger.debug("===== " + this.getClass().getName() + " =====");

		FormPrelevement form = (FormPrelevement) target;


		// === Champs obligatoires ===

		String message = "Ne peut pas être vide !";

		if (form.getIdTumeur()==null) {
			errors.rejectValue("idTumeur", "NotEmpty.formPrelevement.idTumeur", message);
		}

		if (form.getIdPhase()==null) {
			errors.rejectValue("idPhase", "NotEmpty.formPrelevement.idPhase", message);
		}

		if (form.getIdTypePrelevement()==null) {
			errors.rejectValue("idTypePrelevement", "NotEmpty.formPrelevement.idTypePrelevement", message);
		}

		if (form.getDatePrelevement()==null) {
			errors.rejectValue("datePrelevement", "NotEmpty.formPrelevement.datePrelevement", message);
		}


		// === Date de prélèvement ===
		
		Date datePrelevement = form.getDatePrelevement();
		
		ChuPhaseTumeur phaseTumeur = phaseTumeurService.find(form.getIdPhase());
		Date dateDiagnosticPhase = phaseTumeur==null ? null : phaseTumeur.getDateDiagnostic();
		
		if (datePrelevement!=null && dateDiagnosticPhase!=null && datePrelevement.before(dateDiagnosticPhase)) {
			message = "La date de prélèvement ne peut pas être antérieure à la date du diagnostic de la phase de tumeur " + dateDiagnosticPhase + " !";
			errors.rejectValue("datePrelevement", "Consistensy.formPrelevement.datePrelevement", message);
		}
		
		ChuPatient patient = patientService.find(form.getIdPatient());
		Date dateDeces = patient == null ? null : patient.getDateDeces();

		if (datePrelevement!=null && dateDeces!=null && datePrelevement.after(dateDeces)) {
			message = "La date de prélèvement ne peut pas être postérieure à la date de décès du patient " + dateDeces + " !";
			errors.rejectValue("datePrelevement", "Consistensy.formPrelevement.datePrelevement", message);
		}
	}

}
