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

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractInterpreter {

	protected Set<StatutBiomarqueur> setStatutBiomarqueur = new HashSet<StatutBiomarqueur>();

	/** ========================================================= */

	public Set<String> populateSetValue(String value) {

		Set<String> setValue = new HashSet<String>();

		// ===== Put all values into a set =====

		value = value.replaceAll("%", "");

		if (value.contains("/") || value.contains("et") || value.contains("puis")) {
			String[] parts = value.split("[et/puis]+");
			for (int i = 0; i < parts.length; i++) {
				setValue.add(parts[i].trim());
			}
			// System.out.println(value + " : " + setValue);
		} else {
			setValue.add(value.trim());
		}

		return setValue;
	}

	/** ================= ABSTRACT METHODS ==================== */

	public abstract StatutBiomarqueur getStatut(String value);

}
