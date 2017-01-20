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

public enum StatutBiomarqueur {
	TRUE,
	FALSE,
	POSITIVE,
	NEGATIVE,
	HIGH,
	LOW,
	BORDERLINE,
	NULL;
	
	public String stringValue() {
		
		if (this==NULL) {
			return null;
		}
		else {
			return this.toString().toLowerCase();
		}
	}
}
