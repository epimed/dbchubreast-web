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

import dbchubreast_web.entity.ChuPrelevement;

public interface ChuPrelevementService {
	public ChuPrelevement find(Integer idPrelevement);
	public List<ChuPrelevement> list();
	public List<ChuPrelevement> listByIdPhase(Integer idPhase);
	public List<ChuPrelevement> listByIdTumeur(Integer idTumeur);
	public List<ChuPrelevement> listByIdPatient(String idPatient);
	
	public void save(ChuPrelevement prelevement);
	public void update(ChuPrelevement prelevement);
	public void saveOrUpdate(ChuPrelevement prelevement);
}
