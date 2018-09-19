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

import dbchubreast_web.dao.ChuTraitementDao;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.service.BaseService;

@Service
public class ChuTraitementServiceImpl extends BaseService implements ChuTraitementService {

	@Autowired
	private ChuTraitementDao traitementDao;

	public ChuTraitement find(Integer idTraitement) {
		return traitementDao.find(idTraitement);
	}
	
	public List<ChuTraitement> list() {
		return traitementDao.list();
	}

	public List<ChuTraitement> listByIdPatient(String idPatient) {
		return traitementDao.listByIdPatient(idPatient);
	}

	public List<ChuTraitement> listByIdProtocole(Integer idProtocole) {
		return traitementDao.listByIdProtocole(idProtocole);
	}
	
	public List<ChuTraitement> listByIdTumeur(Integer idTumeur) {
		return traitementDao.listByIdTumeur(idTumeur);
	}
	
	public ChuTraitement findDernierTraitement(Integer idTumeur) {
		return traitementDao.findDernierTraitement(idTumeur);
	}
	
	public List<ChuTraitement> listByIdPhase(Integer idPhase) {
		return traitementDao.listByIdPhase(idPhase);
	}
	
	public ChuTraitement findChirurgieReference(Integer idTumeur) {
		return traitementDao.findChirurgieReference(idTumeur);
	}

	public void save(ChuTraitement traitement) {
		traitementDao.save(traitement);
	}

	public void update(ChuTraitement traitement) {
		traitementDao.update(traitement);
	}

	public void saveOrUpdate(ChuTraitement traitement) {
		traitementDao.saveOrUpdate(traitement);
	}
	
	public void delete(ChuTraitement traitement) {
		traitementDao.delete(traitement);
	}
}
