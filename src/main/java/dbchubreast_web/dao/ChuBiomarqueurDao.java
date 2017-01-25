/**
 * EpiMed - Information system for bioinformatics developments in the field of epiOmGenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * GNU OmGeneRAL PUBLIC LICENSE
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */

package dbchubreast_web.dao;

import java.util.List;

import dbchubreast_web.entity.ChuBiomarqueur;

public interface ChuBiomarqueurDao {
	public ChuBiomarqueur find(String idBiomarqueur);

	public List<ChuBiomarqueur> list();

	public List<ChuBiomarqueur> list(Object[] noms);
}
