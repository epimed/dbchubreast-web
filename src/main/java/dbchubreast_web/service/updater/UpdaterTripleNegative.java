/**
 * EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)

 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package dbchubreast_web.service.updater;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.dao.ChuPrelevementBiomarqueurDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.entity.ChuTumeur;

@Service
public class UpdaterTripleNegative extends AbstractUpdater {

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;

	private String[] biomarqueurs = { "her2", "rp", "re" };
	private List<String> listBiomarqueurs = new ArrayList<String>();
	private Set<String> setBiomarqueurs = new HashSet<String>();

	/** ================================================================================= */

	public void update(ChuPrelevement prelevement) {

		logger.debug("=== " + this.getClass().getName() + " ===");

		if (prelevement.getChuTypePrelevement().getIdTypePrelevement() == 3) {

			listBiomarqueurs.clear();
			setBiomarqueurs.clear();

			ChuPhaseTumeur phase = phaseTumeurDao.findByIdPrelevementWithDependencies(prelevement.getIdPrelevement());
			ChuTumeur tumeur = tumeurDao.findByIdPhaseWithDependencies(phase.getIdPhase());

			for (int i = 0; i < biomarqueurs.length; i++) {
				ChuPrelevementBiomarqueur biomarqueur = prelevementBiomarqueurDao.find(prelevement.getIdPrelevement(),
						biomarqueurs[i]);
				listBiomarqueurs.add(biomarqueur == null ? null : biomarqueur.getStatut());
			}

			setBiomarqueurs.addAll(listBiomarqueurs);

			// ===== Analyse =====

			// Biomarqueurs : positive, negative, borderline, null

			// ===== NOT TN : at least one positive =====
			if (listBiomarqueurs.contains("positive")) {
				tumeur.setTripleNegative(false);
			}

			// ===== TN : all markers are negative =====
			if (setBiomarqueurs.contains("negative") && setBiomarqueurs.size() == 1 && !listBiomarqueurs.contains(null)
					&& listBiomarqueurs.size() == biomarqueurs.length) {
				tumeur.setTripleNegative(true);
			}

			// ===== Borderline =====
			if (!setBiomarqueurs.contains("positive") && setBiomarqueurs.contains("borderline")) {
				tumeur.setTripleNegative(null);
			}

			// System.out.println("TRIPLE NEGATIVE: " + "\t" +
			// tumeur.getTripleNegative());

			tumeurDao.update(tumeur);
		}

	}

	/** ================================================================================= */

	@Override
	public void update(List<?> list) {
		// TODO Auto-generated method stub
	}

	/** ================================================================================= */

}