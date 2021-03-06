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

import dbchubreast_web.dao.ChuCauseDecesDao;
import dbchubreast_web.entity.ChuCauseDeces;

@Service
public class ChuCauseDecesServiceImpl implements ChuCauseDecesService {

	@Autowired
	private ChuCauseDecesDao causeDecesDao;

	@Override
	public ChuCauseDeces find(Integer idCauseDeces) {
		return causeDecesDao.find(idCauseDeces);
	}

	@Override
	public List<ChuCauseDeces> list() {
		return causeDecesDao.list();
	}

	@Override
	public ChuCauseDeces findByIdPatient(String idPatient) {
		return causeDecesDao.findByIdPatient(idPatient);
	}


}
