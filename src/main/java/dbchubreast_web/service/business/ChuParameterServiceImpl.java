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

import dbchubreast_web.dao.ChuParameterDao;
import dbchubreast_web.entity.ChuParameter;

@Service
public class ChuParameterServiceImpl implements ChuParameterService {

	@Autowired
	private ChuParameterDao parameterDao;

	
	@Override
	public ChuParameter find(Integer idParameter) {
		return parameterDao.find(idParameter);
	}

	
	@Override
	public ChuParameter find(String nom) {
		return parameterDao.find(nom);
	}

	@Override
	public List<ChuParameter> list() {
		return parameterDao.list();
	}

	@Override
	public void save(ChuParameter parameter) {
		parameterDao.save(parameter);
		
	}

	@Override
	public void update(ChuParameter parameter) {
		parameterDao.update(parameter);
		
	}

	@Override
	public void saveOrUpdate(ChuParameter parameter) {
		parameterDao.saveOrUpdate(parameter);
		
	}

	@Override
	public void delete(ChuParameter parameter) {
		parameterDao.delete(parameter);
		
	}

	

}
