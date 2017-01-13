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

import dbchubreast_web.entity.ChuPhaseTumeur;

public interface ChuPhaseTumeurDao  {

	public ChuPhaseTumeur find(Integer idPhase);
	public ChuPhaseTumeur findWithDependencies(Integer idPhase);
	public List<ChuPhaseTumeur> list();
	public void save (ChuPhaseTumeur phaseTumeur);
	public void update (ChuPhaseTumeur phaseTumeur);
	public void saveOrUpdate (ChuPhaseTumeur phaseTumeur);
	public List<ChuPhaseTumeur> list(Integer idTumeur, Integer idTypePhase);
}
