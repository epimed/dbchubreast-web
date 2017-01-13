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
package dbchubreast_web.service.business;

import java.util.List;

import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.FormTumeurInitiale;

public interface ChuPhaseTumeurService {

	public ChuPhaseTumeur find(Integer idPhase);
	public ChuPhaseTumeur findWithDependencies(Integer idPhase);
	public List<ChuPhaseTumeur> list();
	public List<ChuPhaseTumeur> list(Integer idTumeur, Integer idTypePhase);
	
	public void saveOrUpdateForm(FormTumeurInitiale form);
	public FormTumeurInitiale getFormTumeurInitiale(ChuTumeur tumeur);
}
