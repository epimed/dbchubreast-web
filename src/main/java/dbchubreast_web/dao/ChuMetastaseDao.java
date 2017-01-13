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

import dbchubreast_web.entity.ChuMetastase;

public interface ChuMetastaseDao  {
	public ChuMetastase find(Integer idMetastase);
	public List<ChuMetastase> list();
	public List<ChuMetastase> list(List<Integer> listIdMetastases);
	public List<ChuMetastase> list(Integer idPhaseTumeur);
}
