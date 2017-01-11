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
package dbchubreast_web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuTumeur;




@Service
public class ChuTumeurService {

	@Autowired
	private ChuTumeurDao tumeurDao;
	
	public List<ChuTumeur> find(String idPatient) {
		return tumeurDao.find(idPatient);
	}
	
	public ChuTumeur find(Integer idTumeur) {
		return tumeurDao.find(idTumeur);
	}
	
}
