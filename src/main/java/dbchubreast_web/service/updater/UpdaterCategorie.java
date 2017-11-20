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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.dao.ChuPrelevementBiomarqueurDao;
import dbchubreast_web.dao.ChuPrelevementDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.entity.ChuTumeur;

@Service
public class UpdaterCategorie extends AbstractUpdater {

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired
	private ChuPrelevementDao prelevementDao;
	
	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;

	private String[] biomarqueurs = { "her2", "rp", "re" };
	private List<String> listBiomarqueurs = new ArrayList<String>();
	private Set<String> setBiomarqueurs = new HashSet<String>();
	private Map<String, String> mapBiomarqueurs = new HashMap<String, String>();

	/** ================================================================================= */

	public void update(ChuPrelevement prelevement) {

		logger.debug("=== " + this.getClass().getName() + " ===");

		if (prelevement.getChuTypePrelevement().getIdTypePrelevement() == 3) {

			listBiomarqueurs.clear();
			setBiomarqueurs.clear();
			mapBiomarqueurs.clear();

			ChuPhaseTumeur phase = phaseTumeurDao.findByIdPrelevementWithDependencies(prelevement.getIdPrelevement());
			ChuTumeur tumeur = tumeurDao.findByIdPhaseWithDependencies(phase.getIdPhase());

			for (int i = 0; i < biomarqueurs.length; i++) {
				ChuPrelevementBiomarqueur biomarqueur = prelevementBiomarqueurDao.find(prelevement.getIdPrelevement(),
						biomarqueurs[i]);
				String statut = biomarqueur == null ? null : biomarqueur.getStatut();
				listBiomarqueurs.add(statut);
				mapBiomarqueurs.put(biomarqueurs[i], statut);
			}

			setBiomarqueurs.addAll(listBiomarqueurs);

			// ===== Analyse TN  =====

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

			
			// ===== Analyse Categorie =====
			
			String erStatus = mapBiomarqueurs.get("re");
			String prStatus = mapBiomarqueurs.get("rp");
			String her2Status = mapBiomarqueurs.get("her2");
			
			
			// === Hormonodependant ou pas ===
			Boolean hormonoDependant = null;
			if ((erStatus!=null && erStatus.equals("positive")) || (prStatus!=null && prStatus.equals("positive"))) {
				hormonoDependant = true;
			}
			if ((erStatus!=null && erStatus.equals("negative")) && (prStatus!=null && prStatus.equals("negative"))) {
				hormonoDependant = false;
			}
			
			// === Her2 +/- ===
			if (hormonoDependant!=null && her2Status!=null) {
				
				if (hormonoDependant && her2Status.equals("positive")) {
					tumeur.setCategorie("ERPR+HER2+");
				}
				if (hormonoDependant && her2Status.equals("negative")) {
					tumeur.setCategorie("ERPR+HER2-");
				}
				if (!hormonoDependant && her2Status.equals("positive")) {
					tumeur.setCategorie("ERPR-HER2+");
				}
				if (!hormonoDependant && her2Status.equals("negative")) {
					tumeur.setCategorie("ERPR-HER2-");
				}
			}
			
			tumeurDao.update(tumeur);
		}

	}

	/** ================================================================================= */
	
	public void update(ChuTumeur tumeur) {

		logger.debug("=== " + this.getClass().getName() + " ===");

		tumeur.setTripleNegative(null);
		tumeur.setCategorie(null);
		tumeurDao.update(tumeur);
		
		ChuPhaseTumeur phaseInitiale = phaseTumeurDao.findPhaseInitiale(tumeur.getIdTumeur());
		List<ChuPrelevement> listPrelevements = prelevementDao.listByIdPhase(phaseInitiale.getIdPhase());
		
		int i = 0;
		boolean isFound = false;
		while (!isFound && i < listPrelevements.size()) {
			ChuPrelevement prelevement = listPrelevements.get(i);
			if (prelevement.getChuTypePrelevement().getIdTypePrelevement() == 3) {
				this.update(prelevement);
				isFound = true;
			}
			i++;
		}

	}
	
	
	/** ================================================================================= */

	@Override
	public void update(List<?> list) {
		// TODO Auto-generated method stub
	}

	/** ================================================================================= */

}