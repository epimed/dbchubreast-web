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

import dbchubreast_web.dao.ChuTopographieDao;
import dbchubreast_web.entity.ChuTopographie;


@Service
public class ChuTopographieServiceImpl implements ChuTopographieService {

	@Autowired
	private ChuTopographieDao topographieDao;
	
	public List<ChuTopographie> list(String idGroupeTopo){
		return topographieDao.list(idGroupeTopo);
	}
	
}
