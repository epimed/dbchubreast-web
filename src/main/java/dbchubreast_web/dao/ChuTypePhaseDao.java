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


import dbchubreast_web.entity.ChuTypePhase;

public interface ChuTypePhaseDao  {
	public ChuTypePhase find(Integer idTypePhase);
	public ChuTypePhase findByIdPhase(Integer idPhase);
}
