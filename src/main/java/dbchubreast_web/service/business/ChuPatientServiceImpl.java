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

import dbchubreast_web.dao.ChuPatientDao;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.service.util.FormatService;

@Service
public class ChuPatientServiceImpl implements ChuPatientService {

	@Autowired
	private ChuPatientDao patientDao;

	@Autowired
	private FormatService formatService;

	/** ================================================================= */
	
	public List<ChuPatient> list() {
		return patientDao.list();
	}
	
	/** ================================================================= */

	public String getLastIdPatient() {
		return patientDao.getLastIdPatient();
	}
	
	/** ================================================================= */

	public ChuPatient find(String idPatient) {
		return patientDao.find(idPatient);
	}
	
	/** ================================================================= */
	
	public List<ChuPatient> findAsList(String idPatient) {
		return patientDao.findAsList(idPatient);
	}
	
	/** ================================================================= */
	
	public List<String> findAllIdPatients() {
		return patientDao.findAllIdPatients();
	}
	
	/** ================================================================= */

	public Long count() {
		return patientDao.count();
	}

	/** ================================================================= */
	
	public ChuPatient find(Integer idTumeur) {
		return patientDao.find(idTumeur);
	}

	/** ================================================================= */
	
	public ChuPatient findByIdPrelevement(Integer idPrelevement) {
		return patientDao.findByIdPrelevement(idPrelevement);
	}
	
	/** ================================================================= */
	
	public ChuPatient findByIdTraitement(Integer idTraitement) {
		return patientDao.findByIdTraitement(idTraitement);
	}
	
	/** ================================================================= */

	public List<ChuPatient> findInAttributes(String text) {
		return patientDao.findInAttributes(text);
	}

	/** ================================================================= */
	
	public void saveOrUpdate(ChuPatient patient) {

		// === Format nom prenom ===

		String nom = patient.getNom();
		if (nom != null) {
			patient.setNom(nom.toUpperCase());
		}

		String prenom = patient.getPrenom();
		if (prenom != null) {
			patient.setPrenom(formatService.formatFisrtName(prenom));
		}

		// New patient
		if (patient.getIdPatient() == null) {
			patientDao.save(patient);
		}

		// Existing patient
		else {
			patientDao.update(patient);
		}

	}
	
	/** ================================================================= */
	
	public boolean delete(ChuPatient patient) {
		return patientDao.delete(patient);
	}
	
	/** ================================================================= */

}
