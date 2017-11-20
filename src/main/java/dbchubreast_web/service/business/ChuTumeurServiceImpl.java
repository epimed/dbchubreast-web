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

	public ChuTumeur find(Integer idTumeur) {
		return tumeurDao.find(idTumeur);
	}

	public ChuTumeur findWithDependencies(Integer idTumeur) {
		return tumeurDao.findWithDependencies(idTumeur);
	}

	public List<ChuTumeur> listByIdPatient(String idPatient) {
		return tumeurDao.listByIdPatient(idPatient);
	}

	public List<ChuTumeur> listByIdPatientWithDependencies(String idPatient, String dependency) {
		return tumeurDao.listByIdPatientWithDependencies(idPatient, dependency);
	}

	public List<ChuTumeur> findAsListWithDependencies(Integer idTumeur) {
		return tumeurDao.findAsListWithDependencies(idTumeur);
	}

	public List<ChuTumeur> findInAttributesWithDependencies(String text) {
		return tumeurDao.findInAttributesWithDependencies(text);
	}

	public ChuTumeur findByIdPhaseWithDependencies(Integer idPhase) {
		return tumeurDao.findByIdPhaseWithDependencies(idPhase);
	}

	public List<ChuTumeur> list() {
		return tumeurDao.list();
	}

	public List<ChuTumeur> listWithDependencies() {
		return tumeurDao.listWithDependencies();
	}

	public void save(ChuTumeur tumeur) {
		tumeurDao.save(tumeur);
	}

	public void update(ChuTumeur tumeur) {
		tumeurDao.update(tumeur);
	}
	
	public Long count() {
		return tumeurDao.count();
	}

}
