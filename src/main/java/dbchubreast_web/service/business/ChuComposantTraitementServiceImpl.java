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

import dbchubreast_web.dao.ChuComposantTraitementDao;
import dbchubreast_web.entity.ChuComposantTraitement;

@Service
public class ChuComposantTraitementServiceImpl implements ChuComposantTraitementService {

	@Autowired
	private ChuComposantTraitementDao composantTraitementDao;

	
	public ChuComposantTraitement find(Integer idComposant) {
		return composantTraitementDao.find(idComposant);
	}

	public List<ChuComposantTraitement> list() {
		return composantTraitementDao.list();
	}

	public List<ChuComposantTraitement> listById(List<Integer> listIdComposants) {
		return composantTraitementDao.listById(listIdComposants);
	}

	public List<ChuComposantTraitement> listByMethode(Integer idMethode) {
		return composantTraitementDao.listByMethode(idMethode);
	}

	public List<ChuComposantTraitement> listByProtocole(Integer idProtocole) {
		return composantTraitementDao.listByProtocole(idProtocole);
	}

	

}
