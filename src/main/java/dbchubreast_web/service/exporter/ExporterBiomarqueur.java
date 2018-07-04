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
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuBiomarqueur;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.entity.ChuTumeur;

@Service
public class ExporterBiomarqueur extends AbstractExporter {

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;

	@Autowired
	private ChuBiomarqueurDao biomarqueurDao;

	public Table export() {

		logger.debug("=== " + this.getClass().getName() + ": export()" + " ===");

		// ===== Biomarqueurs =====
		// String [] noms = {"mib1", "upa", "pai1", "rp", "re", "her2"};
		List<ChuBiomarqueur> biomarqueurs = biomarqueurDao.list();

		List<ChuTumeur> list = tumeurDao.listWithDependencies();

		Table table = new Table(list.size());

		for (int i = 0; i < list.size(); i++) {

			ChuTumeur tumeur = list.get(i);
			ChuPatient patient = tumeur.getChuPatient();

			ChuPhaseTumeur phaseInitiale = phaseTumeurDao.findPhaseInitiale(tumeur.getIdTumeur());

			table.addToTable(i, "id_patient", patient.getIdPatient());
			table.addToTable(i, "sexe", patient.getSexe());
			table.addToTable(i, "id_tumeur", tumeur.getIdTumeur().toString());
			table.addToTable(i, "age", tumeur.getAgeDiagnostic() == null ? null : tumeur.getAgeDiagnostic().toString());
			table.addToTable(i, "id_topographie",
					phaseInitiale.getChuTopographie() == null ? null : phaseInitiale.getChuTopographie().getIdTopographie());
			table.addToTable(i, "topographie",
					phaseInitiale.getChuTopographie() == null ? null : phaseInitiale.getChuTopographie().getNomFr());
			table.addToTable(i, "cote", tumeur.getCote());
			table.addToTable(i, "triple_negative",
					tumeur.getTripleNegative() == null ? null : tumeur.getTripleNegative().toString());
			table.addToTable(i, "categorie",
					tumeur.getCategorie() == null ? null : tumeur.getCategorie());
			table.addToTable(i, "dfs_months", tumeur.getDfsMonths() == null ? null : tumeur.getDfsMonths().toString());
			table.addToTable(i, "os_months", tumeur.getOsMonths() == null ? null : tumeur.getOsMonths().toString());
			table.addToTable(i, "relapsed", tumeur.getRelapsed() == null ? null : tumeur.getRelapsed().toString());
			table.addToTable(i, "dead", tumeur.getDead() == null ? null : tumeur.getDead().toString());
			table.addToTable(i, "cause_deces", patient.getChuCauseDeces() == null ? null : patient.getChuCauseDeces().getNom());
			table.addToTable(i, "id_dernier_etat_connu",
					tumeur.getChuEvolution() == null ? null : tumeur.getChuEvolution().getCode());
			table.addToTable(i, "dernier_etat_connu",
					tumeur.getChuEvolution() == null ? null : tumeur.getChuEvolution().getNom());
			table.addToTable(i, "brca_statut", patient.getStatutBrca());

			for (int j = 0; j < biomarqueurs.size(); j++) {
				ChuPrelevementBiomarqueur b = selectSurgery(prelevementBiomarqueurDao
						.list(phaseInitiale.getIdPhase(), biomarqueurs.get(j).getIdBiomarqueur()));
				
				if (b != null) {
					table.addToTable(i, b.getChuBiomarqueur().getIdBiomarqueur() + "_valeur", b.getValeur());
					table.addToTable(i, b.getChuBiomarqueur().getIdBiomarqueur() + "_statut", b.getStatut());
				}
			}
		}

		return table;

	}

	/**
	 * ======================================================================================
	 * 
	 * @return
	 * @throws IncoherenceException
	 */

	public ChuPrelevementBiomarqueur selectSurgery(List<ChuPrelevementBiomarqueur> list) {

		int i = 0;
		boolean isFound = false;
		while (!isFound && i < list.size()) {
			if (list.get(i).getChuPrelevement().getChuTypePrelevement().getIdTypePrelevement().equals(3)) {
				return list.get(i);
			}
			i++;
		}

		return null;

	}

	/** ====================================================================================== */
}
