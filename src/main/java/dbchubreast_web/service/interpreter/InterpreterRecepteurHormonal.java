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
public class InterpreterRecepteurHormonal extends AbstractInterpreter{

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
			if (valueString!=null && valueInteger!=null) {
				if (valueInteger<10) {
					setStatutBiomarqueur.add(StatutBiomarqueur.NEGATIVE);
				}
				else {
					setStatutBiomarqueur.add(StatutBiomarqueur.POSITIVE);
				}
			}

			// === String value ===
			if (valueString!=null && valueInteger==null) {

				if (valueString.equals("<")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.NEGATIVE);
				}
				if (valueString.equals(">")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.POSITIVE);
				}
				if (valueString.equals("+")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.NEGATIVE);
				}
				if (valueString.equals("++")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.BORDERLINE);
				}
				if (valueString.equals("+++")) {
					setStatutBiomarqueur.add(StatutBiomarqueur.POSITIVE);
				}

			}

		}


		if (setStatutBiomarqueur.size()==1) {
			return setStatutBiomarqueur.iterator().next();
		}

		return StatutBiomarqueur.NULL;
	}

}
