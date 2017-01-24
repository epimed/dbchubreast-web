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


import java.util.Date;
import java.util.List;

import dbchubreast_web.entity.ChuPatient;


public interface ChuPatientDao {

	public List<ChuPatient> list(); 
	public String getLastIdPatient(); 
	public ChuPatient find(String nom, String prenom, Date dateNaissance); 
	public Long count(); 
	public ChuPatient find(String idPatient); 
	public ChuPatient find(Integer idTumeur); 
	public ChuPatient findByIdPrelevement(Integer idPrelevement); 
	public List<ChuPatient> findInAttributes(String text); 
	public void update(ChuPatient patient);  
	public void save(ChuPatient patient); 
}