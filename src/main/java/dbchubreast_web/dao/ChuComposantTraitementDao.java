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

import dbchubreast_web.entity.ChuComposantTraitement;

public interface ChuComposantTraitementDao {
	public ChuComposantTraitement find(Integer idComposant);
	public List<ChuComposantTraitement> list();
	public List<ChuComposantTraitement> listById(List<Integer> listIdComposants);
	public List<ChuComposantTraitement> listByMethode(Integer idMethode);
	public List<ChuComposantTraitement> listByProtocole(Integer idProtocole);
}
