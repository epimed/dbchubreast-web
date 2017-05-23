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

import dbchubreast_web.entity.ChuProtocoleTraitement;

public interface ChuProtocoleTraitementService {
	public ChuProtocoleTraitement find(Integer idProtocole);
	public List<ChuProtocoleTraitement> list();
	public List<ChuProtocoleTraitement> listByMethode(Integer idMethode);
	public List<ChuProtocoleTraitement> listByComposant(Integer idComposant);
	public void save(ChuProtocoleTraitement protocole);
	public void update(ChuProtocoleTraitement protocole);
	public void saveOrUpdate(ChuProtocoleTraitement protocole);
	public void delete(ChuProtocoleTraitement protocole);
}
