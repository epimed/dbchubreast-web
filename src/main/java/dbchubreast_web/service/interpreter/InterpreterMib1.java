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
package dbchubreast_web.service.interpreter;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.service.util.FormatService;

@Service
public class InterpreterMib1 extends AbstractInterpreter {

	@Autowired
	private FormatService formatService;

	public StatutBiomarqueur getStatut(String value) {

		if (value == null) {
			return StatutBiomarqueur.NULL;
		}

		Set<String> setValue = this.populateSetValue(value);

		// ===== Evaluate each value =====
		for (String valueString : setValue) {

			Integer valueInteger = this.formatService.recognizeInteger(valueString);

			// === Integer value ===
			if (valueString != null && valueInteger != null) {
				if (valueInteger < 35) {
					setStatutBiomarqueur.add(StatutBiomarqueur.LOW);
				} else {
					setStatutBiomarqueur.add(StatutBiomarqueur.HIGH);
				}
			}

			// === String value ===
			if (valueString != null && valueInteger == null) {

				if (valueString.equals("<")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.LOW);
				}
				if (valueString.equals(">")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.HIGH);
				}
				if (valueString.equals("+")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.LOW);
				}
				if (valueString.equals("++")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.BORDERLINE);
				}
				if (valueString.equals("+++")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.HIGH);
				}
				if (valueString.toLowerCase().equals("faible")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.LOW);
				}

			}

		}

		System.out.println(setStatutBiomarqueur);

		if (setStatutBiomarqueur.size() == 1) {
			return setStatutBiomarqueur.iterator().next();
		}

		return StatutBiomarqueur.NULL;
	}

}
