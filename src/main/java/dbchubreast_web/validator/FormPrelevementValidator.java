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

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.form.FormPrelevement;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuPatientService;

@Component
public class FormPrelevementValidator extends BaseService implements Validator {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private ChuPatientService patientService;

	public boolean supports(Class<?> clazz) {
		return FormPrelevement.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		logger.debug("===== " + this.getClass().getName() + " =====");

		FormPrelevement form = (FormPrelevement) target;

		// === Champs obligatoires ===

		String message = "Ne peut pas être vide !";

		if (form.getIdTumeur() == null) {
			errors.rejectValue("idTumeur", "NotEmpty.formPrelevement.idTumeur", message);
		}

		if (form.getIdPhase() == null) {
			errors.rejectValue("idPhase", "NotEmpty.formPrelevement.idPhase", message);
		}

		if (form.getIdTypePrelevement() == null) {
			errors.rejectValue("idTypePrelevement", "NotEmpty.formPrelevement.idTypePrelevement", message);
		}

		if (form.getDatePrelevement() == null) {
			errors.rejectValue("datePrelevement", "NotEmpty.formPrelevement.datePrelevement", message);
		}

		// === Date de prélèvement ===

		Date datePrelevement = form.getDatePrelevement();

		ChuPatient patient = patientService.find(form.getIdPatient());
		Date dateDeces = patient == null ? null : patient.getDateDeces();

		if (datePrelevement != null && dateDeces != null && datePrelevement.after(dateDeces)) {
			message = "La date de prélèvement ne peut pas être postérieure à la date de décès du patient " + dateFormat.format(dateDeces)
			+ " !";
			errors.rejectValue("datePrelevement", "Consistensy.formPrelevement.datePrelevement", message);
		}
	}

}
