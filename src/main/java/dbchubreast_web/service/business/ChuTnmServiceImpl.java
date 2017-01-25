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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuTnmDao;
import dbchubreast_web.entity.ChuTnm;

@Service
public class ChuTnmServiceImpl implements ChuTnmService {

	@Autowired
	private ChuTnmDao tnmDao;

	public ChuTnm find(Integer idPhase, String type) {
		return tnmDao.find(idPhase, type);
	}

}
