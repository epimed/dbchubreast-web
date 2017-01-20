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

import dbchubreast_web.entity.ChuPrelevementBiomarqueur;

public interface ChuPrelevementBiomarqueurService {

	public ChuPrelevementBiomarqueur find(Integer idPrelevement, String idBiomarqueur);
	public List<ChuPrelevementBiomarqueur> list(Integer idPrelevement);
	public List<ChuPrelevementBiomarqueur> list(List<Integer> listIdPrelevements);
	
	public void save(ChuPrelevementBiomarqueur prelBio);
	public void update(ChuPrelevementBiomarqueur prelBio);
	public void saveOrUpdate(ChuPrelevementBiomarqueur prelBio);
	public void delete(ChuPrelevementBiomarqueur prelBio);
}
