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

import dbchubreast_web.entity.ChuPatientParameter;

public interface ChuPatientParameterService {

	public ChuPatientParameter find(String idPatient, Integer idParameter);

	public void save(ChuPatientParameter patientParameter);

	public void update(ChuPatientParameter patientParameter);

	public void saveOrUpdate(ChuPatientParameter patientParameter);

	public void delete(ChuPatientParameter patientParameter);
}
