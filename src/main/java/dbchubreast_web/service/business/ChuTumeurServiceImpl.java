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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuTumeur;




@Service
public class ChuTumeurServiceImpl implements ChuTumeurService {

	@Autowired
	private ChuTumeurDao tumeurDao;
	
	public List<ChuTumeur> find(String idPatient) {
		return tumeurDao.find(idPatient);
	}
	
	public List<ChuTumeur> findWithDependencies(String idPatient) {
		return tumeurDao.findWithDependencies(idPatient);
	}
	
	public ChuTumeur findWithDependencies(Integer idTumeur) {
		return tumeurDao.findWithDependencies(idTumeur);
	}
	
	public List<ChuTumeur> findAsListWithDependencies(Integer idTumeur) {
		return tumeurDao.findAsListWithDependencies(idTumeur);
	}
	
	public List<ChuTumeur> findInAttributesWithDependencies(String text) {
		return tumeurDao.findInAttributesWithDependencies(text);
	}
	
}
