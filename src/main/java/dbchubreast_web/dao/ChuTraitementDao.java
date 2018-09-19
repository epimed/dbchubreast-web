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

import dbchubreast_web.entity.ChuTraitement;

public interface ChuTraitementDao {

	public ChuTraitement find(Integer idTraitement);
	public List<ChuTraitement> list();
	public List<ChuTraitement> listByIdPatient(String idPatient);
	public List<ChuTraitement> listByIdProtocole(Integer idProtocole);
	public List<ChuTraitement> listByIdTumeur(Integer idTumeur);
	public ChuTraitement findDernierTraitement(Integer idTumeur);
	public List<ChuTraitement> listByIdPhase(Integer idPhase);
	public ChuTraitement findChirurgieReference(Integer idTumeur);
	public void save(ChuTraitement traitement);
	public void update(ChuTraitement traitement);
	public void saveOrUpdate(ChuTraitement traitement);
	public void delete(ChuTraitement traitement);
}
