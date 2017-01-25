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

import dbchubreast_web.entity.ChuMetastase;

public interface ChuMetastaseService {
	public ChuMetastase find(Integer idMetastase);

	public List<ChuMetastase> list();

	public List<ChuMetastase> list(List<Integer> listIdMetastases);

	public List<ChuMetastase> list(Integer idPhaseTumeur);
}
