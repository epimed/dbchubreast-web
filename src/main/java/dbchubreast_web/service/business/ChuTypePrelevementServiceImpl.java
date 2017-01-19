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

import dbchubreast_web.dao.ChuTypePrelevementDao;
import dbchubreast_web.entity.ChuTypePrelevement;


@Service
public class ChuTypePrelevementServiceImpl implements ChuTypePrelevementService {

	@Autowired
	private ChuTypePrelevementDao typePrelevementDao;

	public ChuTypePrelevement find(Integer idTypePrelevement) {
		return  typePrelevementDao.find(idTypePrelevement);
	}

	public List<ChuTypePrelevement> list(Integer[] listIdTypePrelevement) {
		return  typePrelevementDao.list(listIdTypePrelevement);
	}

	public List<ChuTypePrelevement> listPhaseInitiale() {
		return  typePrelevementDao.listPhaseInitiale();
	}

	public List<ChuTypePrelevement> listPhaseRechute() {
		return  typePrelevementDao.listPhaseRechute();
	}

	
}
