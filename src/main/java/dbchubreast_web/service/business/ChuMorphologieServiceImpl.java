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

import dbchubreast_web.dao.ChuMorphologieDao;
import dbchubreast_web.entity.ChuMorphologie;


@Service
public class ChuMorphologieServiceImpl implements ChuMorphologieService {

	@Autowired
	private ChuMorphologieDao morphologieDao;

	@Override
	public ChuMorphologie find(String idMorphologie) {
		return morphologieDao.find(idMorphologie);
	}

	public List<ChuMorphologie> list() {
		return morphologieDao.list();
	}
	
}
