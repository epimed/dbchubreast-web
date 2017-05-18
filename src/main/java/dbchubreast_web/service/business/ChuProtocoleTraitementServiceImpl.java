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

import dbchubreast_web.dao.ChuProtocoleTraitementDao;
import dbchubreast_web.entity.ChuProtocoleTraitement;

@Service
public class ChuProtocoleTraitementServiceImpl implements ChuProtocoleTraitementService {

	@Autowired
	private ChuProtocoleTraitementDao protocoleTraitementDao;

	public ChuProtocoleTraitement find(Integer idProtocole) {
		return protocoleTraitementDao.find(idProtocole);
	}
	
	public List<ChuProtocoleTraitement> list() {
		return protocoleTraitementDao.list();
	}

	public List<ChuProtocoleTraitement> list(Integer idMethode) {
		return protocoleTraitementDao.list(idMethode);
	}
	
	public void save(ChuProtocoleTraitement protocole) {
		protocoleTraitementDao.save(protocole);
	}
	
	public void update(ChuProtocoleTraitement protocole) {
		protocoleTraitementDao.update(protocole);
	}
	
	public void saveOrUpdate(ChuProtocoleTraitement protocole) {
		protocoleTraitementDao.saveOrUpdate(protocole);
	}
	
	public void delete(ChuProtocoleTraitement protocole) {
		protocoleTraitementDao.delete(protocole);
	}

}
