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
package dbchubreast_web.service.exporter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuPatientDao;
import dbchubreast_web.entity.ChuPatient;


@Service
public class ExporterPatient extends AbstractExporter {


	@Autowired
	private ChuPatientDao patientDao;

	@Override
	public Table export() {
	
		List<ChuPatient> list = patientDao.list();

		Table table = new Table(list.size());

		for (int i=0; i<list.size(); i++) {

			ChuPatient patient = list.get(i);

			// ===== Patient =====

			table.addToTable(i, "id_patient", patient.getIdPatient());
			// table.addToTable(i, "id_encrypted", patient.getIdEncrypted());
			table.addToTable(i, "rcp", patient.getRcp());
			table.addToTable(i, "nom", patient.getNom());
			table.addToTable(i, "prenom", patient.getPrenom());
			table.addToTable(i, "sexe", patient.getSexe());
			table.addToTable(i, "date_naissance", patient.getDateNaissance()==null ? null : dateFormat.format(patient.getDateNaissance()));	
			table.addToTable(i, "date_deces", patient.getDateDeces()==null ? null : dateFormat.format(patient.getDateDeces()));	
			table.addToTable(i, "cause_deces", patient.getCauseDeces());	
			table.addToTable(i, "statut_brca", patient.getStatutBrca());
			table.addToTable(i, "consentement", patient.getConsentement());
		}
		
		return table;

	}
}
