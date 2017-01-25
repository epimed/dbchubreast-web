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

import dbchubreast_web.dao.ChuMetastaseDao;
import dbchubreast_web.entity.ChuMetastase;

@Service
public class ChuMetastaseServiceImpl implements ChuMetastaseService {

	@Autowired
	private ChuMetastaseDao metastaseDao;

	public List<ChuMetastase> list() {
		return metastaseDao.list();
	}

	public ChuMetastase find(Integer idMetastase) {
		return metastaseDao.find(idMetastase);
	}

	public List<ChuMetastase> list(List<Integer> listIdMetastases) {
		return metastaseDao.list(listIdMetastases);
	}

	public List<ChuMetastase> list(Integer idPhaseTumeur) {
		return metastaseDao.list(idPhaseTumeur);
	}

}
