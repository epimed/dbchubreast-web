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
public class InterpreterUpa extends AbstractInterpreter{

	@Autowired 
	private FormatService formatService;

	public StatutBiomarqueur getStatut(String value) {


		if (value == null) {
			return StatutBiomarqueur.NULL;
		}

		Set<String> setValue = this.populateSetValue(value);

		// ===== Evaluate each value =====
		for (String valueString : setValue) {

			Double valueDouble = this.formatService.recognizeDouble(valueString);

			// === Integer value ===
			if (valueString!=null && valueDouble!=null) {
				if (valueDouble<3) {
					setStatutBiomarqueur.add(StatutBiomarqueur.LOW);
				}
				else {
					setStatutBiomarqueur.add(StatutBiomarqueur.HIGH);
				}
			}

			// === String value ===
			if (valueString!=null && valueDouble==null) {

				if (valueString.contains("<") && !valueString.contains("?")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.LOW);
				}
				if (valueString.contains(">") && !valueString.contains("?")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.HIGH);
				}
				if (valueString.toLowerCase().equals("normal") && !valueString.contains("?")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.LOW);
				}

			}

		}


		if (setStatutBiomarqueur.size()==1) {
			return setStatutBiomarqueur.iterator().next();
		}

		return StatutBiomarqueur.NULL;
	}

}
