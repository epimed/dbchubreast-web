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

import dbchubreast_web.dao.ChuPrelevementBiomarqueurDao;
import dbchubreast_web.dao.ChuPrelevementDao;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;

@Service
public class ChuPrelevementServiceImpl implements ChuPrelevementService {

	@Autowired
	private ChuPrelevementDao prelevementDao;

	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;

	public ChuPrelevement find(Integer idPrelevement) {
		return prelevementDao.find(idPrelevement);
	}

	public List<ChuPrelevement> listByIdPhase(Integer idPhase) {
		return prelevementDao.listByIdPhase(idPhase);
	}

	public List<ChuPrelevement> listByIdTumeur(Integer idTumeur) {
		return prelevementDao.listByIdTumeur(idTumeur);
	}

	public List<ChuPrelevement> listByIdPatient(String idPatient) {
		return prelevementDao.listByIdPatient(idPatient);
	}

	public void save(ChuPrelevement prelevement) {
		prelevementDao.save(prelevement);
	}

	public void update(ChuPrelevement prelevement) {
		prelevementDao.update(prelevement);
	}

	public void delete(ChuPrelevement prelevement) {
		List<ChuPrelevementBiomarqueur> listPrelBio = prelevementBiomarqueurDao.list(prelevement.getIdPrelevement());
		for (ChuPrelevementBiomarqueur prelBio: listPrelBio) {
			prelevementBiomarqueurDao.delete(prelBio);
		}
		prelevementDao.delete(prelevement);
	}
	
	public void saveOrUpdate(ChuPrelevement prelevement) {
		prelevementDao.saveOrUpdate(prelevement);
	}

	public List<ChuPrelevement> list() {
		return prelevementDao.list();
	}

}
