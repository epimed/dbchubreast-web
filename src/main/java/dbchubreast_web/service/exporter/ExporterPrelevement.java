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

import dbchubreast_web.dao.ChuBiomarqueurDao;
import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.dao.ChuPrelevementBiomarqueurDao;
import dbchubreast_web.dao.ChuPrelevementDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuBiomarqueur;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.entity.ChuTumeur;

@Service
public class ExporterPrelevement extends AbstractExporter {

	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;
	
	@Autowired
	private ChuTumeurDao tumeurDao;
	
	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;
	
	@Autowired
	private ChuPrelevementDao prelevementDao;
	
	@Autowired
	private ChuBiomarqueurDao biomarqueurDao;

	public Table export() {

		
		logger.debug("=== " + this.getClass().getName() + ": export()" + " ===");
		
		// ====== SHEET PRELEVEMENTS =====

		List<ChuBiomarqueur> biomarqueurs = biomarqueurDao.list();
		List<ChuPrelevement> listPrelevements = prelevementDao.list();

		Table table = new Table(listPrelevements.size());

		for (int i=0; i<listPrelevements.size(); i++) {
			// for (int i=0; i<10; i++) {

			ChuPrelevement prelevement = listPrelevements.get(i);
			ChuPhaseTumeur phase = phaseTumeurDao.findByIdPrelevementWithDependencies(prelevement.getIdPrelevement());
			ChuTumeur tumeur = tumeurDao.findByIdPhaseWithDependencies(phase.getIdPhase());
			ChuPatient patient = tumeur.getChuPatient();


			// ===== Patient =====

			table.addToTable(i, "id_patient", patient.getIdPatient());



			// ===== Tumeur =====

			table.addToTable(i, "id_tumeur", tumeur.getIdTumeur());
			table.addToTable(i, "id_topographie", tumeur.getChuTopographie()==null ? null : tumeur.getChuTopographie().getIdTopographie());
			table.addToTable(i, "cote", tumeur.getCote());
			table.addToTable(i, "age_diagnostic", tumeur.getAgeDiagnostic());
			table.addToTable(i, "date_evolution", tumeur.getDateEvolution()==null ? null : dateFormat.format(tumeur.getDateEvolution()));
			table.addToTable(i, "code_evolution", tumeur.getChuEvolution()==null ? null : tumeur.getChuEvolution().getCode());
			table.addToTable(i, "cause_deces", patient.getCauseDeces());


			// ===== Phase de tumeur =====

			table.addToTable(i, "id_phase", phase.getIdPhase());
			table.addToTable(i, "type_phase_tumeur", phase.getChuTypePhase().getNom());
			table.addToTable(i, "date_diagnostic (initiale ou rechute)", phase.getDateDiagnostic()==null ? null : dateFormat.format(phase.getDateDiagnostic()));
			table.addToTable(i, "nature_diagnostic (initiale ou rechute)", phase.getNatureDiagnostic());
			table.addToTable(i, "profondeur", phase.getProfondeur());
			table.addToTable(i, "performance_status", phase.getChuPerformanceStatus()==null ? null : phase.getChuPerformanceStatus().getIdPs());


			// ===== Prelevement =====

			table.addToTable(i, "id_prelevement", prelevement.getIdPrelevement());
			table.addToTable(i, "type_prelevement", prelevement.getChuTypePrelevement().getNom());
			table.addToTable(i, "date_prelevement", prelevement.getDatePrelevement()==null ? null : dateFormat.format(prelevement.getDatePrelevement()));
			table.addToTable(i, "site_prelevement", prelevement.getSitePrelevement());
			table.addToTable(i, "id_morphologie", prelevement.getChuMorphologie()==null ? null : prelevement.getChuMorphologie().getIdMorphologie());
			table.addToTable(i, "type_histologique", prelevement.getTypeHistologique());

			// ===== Biomarquers =====

			for (int j=0; j<biomarqueurs.size(); j++) {
				ChuPrelevementBiomarqueur b = prelevementBiomarqueurDao.find(prelevement.getIdPrelevement(), biomarqueurs.get(j).getIdBiomarqueur());
				if (b!=null) {
					// System.out.println(b.getChuBiomarqueur().getNom() + ":\t" + b);
					table.addToTable(i, b.getChuBiomarqueur().getNom(), b.getValeur());
				}
			}
		}
		
		return table;

	}
}
