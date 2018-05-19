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

import dbchubreast_web.dao.ChuPatientParameterDao;
import dbchubreast_web.entity.ChuPatientParameter;

@Service
public class ChuPatientParameterServiceImpl implements ChuPatientParameterService {

	@Autowired
	private ChuPatientParameterDao patientParameterDao;

	@Override
	public ChuPatientParameter find(String idPatient, Integer idParameter) {
		return patientParameterDao.find(idPatient, idParameter);
	}

	@Override
	public void save(ChuPatientParameter patientParameter) {
		patientParameterDao.save(patientParameter);
	}

	@Override
	public void update(ChuPatientParameter patientParameter) {
		patientParameterDao.update(patientParameter);	
	}

	@Override
	public void saveOrUpdate(ChuPatientParameter patientParameter) {
		patientParameterDao.saveOrUpdate(patientParameter);
	}

	@Override
	public void delete(ChuPatientParameter patientParameter) {
		patientParameterDao.delete(patientParameter);
	}

	
}
