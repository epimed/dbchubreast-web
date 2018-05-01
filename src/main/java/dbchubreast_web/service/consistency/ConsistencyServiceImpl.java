package dbchubreast_web.service.consistency;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;
import dbchubreast_web.service.business.ChuPrelevementBiomarqueurService;
import dbchubreast_web.service.business.ChuPrelevementService;
import dbchubreast_web.service.business.ChuTraitementService;
import dbchubreast_web.service.business.ChuTumeurService;
import dbchubreast_web.service.util.FormatService;

@Service
public class ConsistencyServiceImpl extends BaseService implements ConsistencyService {

	@Autowired
	private ChuTumeurService tumeurService;

	@Autowired
	private ChuPhaseTumeurService phaseTumeurService;

	@Autowired
	private ChuPrelevementService prelevementService;
	
	@Autowired
	private ChuPrelevementBiomarqueurService prelevementBiomarqueurService;

	@Autowired
	private ChuTraitementService traitementService;

	@Autowired
	private FormatService formatService;


	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private String message;
	private Map<String, List<String>> mapMessages = new HashMap<String, List<String>>();

	public Map<String, List<String>> getMapMessages() {
		return mapMessages;
	}

	public void setMapMessages(Map<String, List<String>> mapMessages) {
		this.mapMessages = mapMessages;
	}
	
	public void clearMessages() {
		this.mapMessages.clear();
	}




	/** =================================================================================== */

	@Override
	public void checkConsistency(List<ChuPatient> listPatients) {

		for (int i=0; i<listPatients.size(); i++) {
			ChuPatient patient = listPatients.get(i);

			this.checkPatient(patient);
			this.checkTumeursWithDependancies(patient);
		}

	}
	/** ====================================================================================== */

	private void checkTumeursWithDependancies(ChuPatient patient) {

		List<ChuTumeur> listTumeurs = tumeurService.listByIdPatient(patient.getIdPatient());

		Date dateNaissance = patient.getDateNaissance();
		Date dateDeces = patient.getDateDeces();

		for (int i=0; i<listTumeurs.size(); i++) {

			ChuTumeur tumeur = listTumeurs.get(i);
			int nTumeur = i+1;


			// === Tumeur ===
			this.checkTumeur(patient, tumeur, nTumeur, dateNaissance, dateDeces);

			// === Phase initiale ===
			this.checkPhaseInitiale(patient, tumeur, nTumeur);

			// === Rechutes ===
			this.checkRechutes(patient, tumeur, nTumeur);

			// === Prelevements ===
			this.checkPrelevements(patient, tumeur, nTumeur);

			// === Traitements ===
			this.checkTraitements(patient, tumeur, nTumeur);

		}

	}

	/** ====================================================================================== */

	public void checkTumeur (ChuPatient patient, ChuTumeur tumeur, int nTumeur, Date dateNaissance, Date dateDeces) {

		// === Date diagnostic / Date de naissance / Date deces ===

		Date dateDiagnostic = tumeur.getDateDiagnostic();
		Date dateEvolution = tumeur.getDateEvolution();
		Double ageDiagnostic = tumeur.getAgeDiagnostic();
		Integer idEvolution = tumeur.getChuEvolution()==null ? null : tumeur.getChuEvolution().getIdEvolution();

		if (dateDiagnostic==null) {
			message = "La date du diagnostic de la tumeur " +  nTumeur + " n'est pas renseignée";

			if (dateNaissance!=null && ageDiagnostic!=null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateNaissance);
				int year = cal.get(Calendar.YEAR);
				Double anneeDiagnosticMin = year + ageDiagnostic;
				Double anneeDiagnosticMax = anneeDiagnosticMin + 1;
				message = message + ". Si l'âge au diagnostic est " + ageDiagnostic 
						+ " ans, l'année du diagnostic devrait alors être " 
						+ Integer.toString(anneeDiagnosticMin.intValue())
						+ " ou " + Integer.toString(anneeDiagnosticMax.intValue()) ;
			}

			this.addMessage(patient.getIdPatient(), message);
		}

		if (dateDiagnostic!=null && dateNaissance!=null && dateDiagnostic.before(dateNaissance)) {
			message = "La date de naissance " + dateFormat.format(dateNaissance) + " est postérieure à la date du diagnostic "
					+ dateFormat.format(dateDiagnostic) + " pour la tumeur " + nTumeur;
			this.addMessage(patient.getIdPatient(), message);
		}

		if (dateDiagnostic!=null && dateDeces!=null && dateDeces.before(dateDiagnostic)) {
			message = "La date de décès " + dateFormat.format(dateDeces) + " est antérieure à la date du diagnostic "
					+ dateFormat.format(dateDiagnostic) + " pour la tumeur " + nTumeur;
			this.addMessage(patient.getIdPatient(), message);
		}

		if (dateDeces!=null  && (dateEvolution==null || !dateEvolution.equals(dateDeces) )) {
			message = "La date de la dernière nouvelle n'est pas cohérente avec la date de décès du patient " 
					+ dateFormat.format(dateDeces) + " pour la tumeur " + nTumeur;
			this.addMessage(patient.getIdPatient(), message);
		}

		if (idEvolution!=null && dateEvolution==null) {
			message = "La date de la dernière nouvelle n'est pas renseignée pour la tumeur " +  nTumeur;
			this.addMessage(patient.getIdPatient(), message);
		}

		if (dateEvolution!=null && idEvolution==null) {
			message = "L'état du patient n'est pas renseigné pour la date de la dernière nouvelle " 
					+ dateFormat.format(dateEvolution) + " pour la tumeur " +  nTumeur;
			this.addMessage(patient.getIdPatient(), message);
		}

		if (dateDeces!=null && idEvolution!=null && !idEvolution.equals(5)) {
			message = "L'état du patient à la dernière nouvelle pour la tumeur " +  nTumeur
					+ " doit être DCD, puisque le patient a décédé le " + dateFormat.format(dateDeces);
			this.addMessage(patient.getIdPatient(), message);
		}

		if (idEvolution!=null && idEvolution.equals(5) && (dateDeces==null || !dateDeces.equals(dateEvolution))) {
			message = "L'état du patient à la dernière nouvelle (DCD) n'est pas cohérent avec la date de décès"
					+ " et/ou la date de la dernière nouvelle pour la tumeur " + nTumeur;
			this.addMessage(patient.getIdPatient(), message);
		}

		// === Age au diagnostic ===

		if (dateDiagnostic!=null && dateNaissance!=null && ageDiagnostic==null) {
			message = "L'âge au diagnostic de la tumeur " +  nTumeur + " n'est pas renseigné";
			this.addMessage(patient.getIdPatient(), message);
		}

		if (dateNaissance!=null && dateDiagnostic!=null && ageDiagnostic!=null) {
			Double calculatedAge = formatService.calculateAge(dateNaissance, dateDiagnostic);
			Double ageDiff = Math.abs(calculatedAge-ageDiagnostic);
			if (ageDiff>=1) {
				message = "L'âge au diagnostic n'est pas cohérent avec la date du diagnostic pour la tumeur " +  nTumeur 
						+ " : l'âge renseigné est " + ageDiagnostic + " ans alors qu'il doit être " + calculatedAge 
						+ " ans";
				this.addMessage(patient.getIdPatient(), message);
			}
		}

		if (tumeur.getCote()==null) {
			message = "Le côté n'est pas renseigné pour la tumeur " +  nTumeur;
			this.addMessage(patient.getIdPatient(), message);
		}

	}


	/** ====================================================================================== */

	private void checkTraitements(ChuPatient patient, ChuTumeur tumeur, int nTumeur) {

		Date dateDiagnostic = tumeur.getDateDiagnostic();
		List<ChuTraitement> listTraitements = traitementService.listByIdTumeur(tumeur.getIdTumeur());

		Date dateChirurgie = null;

		for (int i=0; i<listTraitements.size(); i++) {
			ChuTraitement traitement = listTraitements.get(i);
			int nTraitement = i+1;

			Date dateTraitement = traitement.getDateDebut();
			String methode = traitement.getChuMethodeTraitement()==null ? "" : traitement.getChuMethodeTraitement().getNom();
			String type = traitement.getChuTypeTraitement()==null ? "" : traitement.getChuTypeTraitement().getNom();


			if (dateTraitement==null) {
				message = "La date de début de traitement n'est pas renseignée pour le traitement " + nTraitement 
						+ " (" + methode + " " + type +  ")" 
						+ " de la tumeur " + nTumeur;
				this.addMessage(patient.getIdPatient(), message);
			}

			if (dateTraitement!=null && dateDiagnostic!=null && dateDiagnostic.after(dateTraitement)) {
				
				Integer nbJoursAvantDiagnistic = formatService.getPeriodInDays(dateTraitement, dateDiagnostic);
				
				message = "Vérifier la date de début du traitement " + nTraitement 
						+ " (" + methode + " " + type +  ")" 
						+ " de la tumeur " + nTumeur 
						+ " : le traitement a été fait le " + dateFormat.format(dateTraitement) 
						+ ", c'est à dire " + nbJoursAvantDiagnistic + " jours avant la date du diagnostic " + dateFormat.format(dateDiagnostic);
				this.addMessage(patient.getIdPatient(), message);
			}

			// === Date chirurgie / Date traitement neoadjuvent ===

			if (traitement.getChuMethodeTraitement()!=null && traitement.getChuMethodeTraitement().getIdMethode().equals(1)) {
				// chirurgie
				if (dateTraitement!=null) {
					dateChirurgie = dateTraitement;
				}

			}

			if (dateChirurgie!=null && traitement.getChuTypeTraitement()!=null 
					&& traitement.getChuTypeTraitement().getIdTypeTraitement().equals(1)) {

				// neoadjuvent
				if (dateTraitement!=null && dateTraitement.after(dateChirurgie)) {

					message = "Vérifier la date de début du traitement "
							+ nTraitement 
							+ " (" + methode + " " + type +  ")" 
							+ " de la tumeur " + nTumeur 
							+ " : le traitement est néoadjuvant du " 
							+ dateFormat.format(dateTraitement)
							+ " mais il semble être appliqué après la chirurgie du " 
							+ dateFormat.format(dateChirurgie);
					this.addMessage(patient.getIdPatient(), message);	
				}

			}

		}

	}

	/** ====================================================================================== */

	private void checkPrelevements(ChuPatient patient, ChuTumeur tumeur, int nTumeur) {

		Date dateDiagnostic = tumeur.getDateDiagnostic();
		List<ChuPrelevement> listPrelevements = prelevementService.listByIdTumeur(tumeur.getIdTumeur());

		for (int i=0; i<listPrelevements.size(); i++) {

			int nPrelevement = i+1;
			ChuPrelevement prelevement = listPrelevements.get(i);

			Date datePrelevement = prelevement.getDatePrelevement();
			String typePrelevement = prelevement.getChuTypePrelevement()==null ? "" : 
				prelevement.getChuTypePrelevement().getNom();
			Integer idTypePrelevement = prelevement.getChuTypePrelevement()==null ? null : prelevement.getChuTypePrelevement().getIdTypePrelevement();
			
			if (datePrelevement==null) {
				message = "La date de prélèvement n'est pas renseignée pour le prélèvement " + nPrelevement 
						+ " (" + typePrelevement + ")"
						+ " de la tumeur " + nTumeur;
				this.addMessage(patient.getIdPatient(), message);
			}

			
			// === Date de prel avant date de diag ===
			// Tolerance 30 jours
			
			if (datePrelevement!=null && dateDiagnostic!=null && dateDiagnostic.after(datePrelevement)) {

				Integer nbJoursAvantDiagnostic = formatService.getPeriodInDays(datePrelevement, dateDiagnostic);
				
				boolean alertePrelevementDiagnostic = idTypePrelevement!=null && idTypePrelevement.equals(1) && nbJoursAvantDiagnostic!=null && nbJoursAvantDiagnostic>30;
				boolean alertePrelevementAutreQueDiagnostic = idTypePrelevement!=null && !idTypePrelevement.equals(1);
				
				if (alertePrelevementDiagnostic || alertePrelevementAutreQueDiagnostic) {
					message = "Vérifier la date de prélèvement " + nPrelevement 
							+ " (" + typePrelevement + ")"
							+ " de la tumeur " + nTumeur 
							+ " : le prélèvement a été fait le " + dateFormat.format(datePrelevement) 
							+ ", c'est à dire " + nbJoursAvantDiagnostic + " jours avant la date du diagnostic " 
							+ dateFormat.format(dateDiagnostic)  
							+ ". Est-ce que c'est normal ?";
					this.addMessage(patient.getIdPatient(), message);

				}
			}
			

			// === Biomarqueurs ===

			List<ChuPrelevementBiomarqueur> listPB = prelevementBiomarqueurService.list(prelevement.getIdPrelevement());

			for (ChuPrelevementBiomarqueur pb : listPB) {
				if (pb.getValeur()!=null && pb.getValeur().contains("?")) {
					String biomarqueur = pb.getChuBiomarqueur().getNom();
					message = "Vérifier la valeur du biomarqueur '" + biomarqueur + "'" +
							" du prélèvement "   + nPrelevement 
							+ " (" + typePrelevement + ")"
							+ " de la tumeur " + nTumeur 
							+ " : " + pb.getValeur();
					this.addMessage(patient.getIdPatient(), message);
				}
			}

		}

	}


	/** ====================================================================================== */

	private void checkRechutes(ChuPatient patient, ChuTumeur tumeur, int nTumeur) {


		List<ChuPhaseTumeur> listRechutes = phaseTumeurService.findRelapses(tumeur.getIdTumeur());

		Date dateDiagnostic = tumeur.getDateDiagnostic();

		for (int i=0; i<listRechutes.size(); i++) {

			ChuPhaseTumeur rechute = listRechutes.get(i);
			Date dateRechute = rechute.getDateDiagnostic();

			if (dateRechute==null) {
				message = "La date de rechute n'est pas renseignée pour la tumeur " + nTumeur;
				this.addMessage(patient.getIdPatient(), message);
			}

			if (dateRechute!=null && dateDiagnostic!=null && dateRechute.before(dateDiagnostic)) {
				message = "La date de rechute " + dateFormat.format(dateRechute) + " précède la date du diagnostic initial " 
						+ dateFormat.format(dateDiagnostic) + " pour la tumeur " + nTumeur;
				this.addMessage(patient.getIdPatient(), message);
			}

		}

	}

	/** ====================================================================================== */

	private void checkPhaseInitiale(ChuPatient patient, ChuTumeur tumeur, int nTumeur) {

		ChuPhaseTumeur phaseInitiale = phaseTumeurService.findPhaseInitiale(tumeur.getIdTumeur());

		Date dateDiagnostic = tumeur.getDateDiagnostic();
		Date datePhaseInitiale = phaseInitiale.getDateDiagnostic();

		if (dateDiagnostic!=null && datePhaseInitiale!=null && !datePhaseInitiale.equals(dateDiagnostic)) {
			message = "PROBLEME DE BASE DE DONNEES : La date de la phase initiale ne correspond pas à la date du diagnostic pour la tumeur " + nTumeur;
			this.addMessage(patient.getIdPatient(), message);
		}

		if (phaseInitiale.getNatureDiagnostic()==null || phaseInitiale.getNatureDiagnostic().contains("?")) {
			message = "La nature du diagnostic n'est pas renseignée pour la tumeur " + nTumeur;
		}

	}


	/** ====================================================================================== */

	private void checkPatient(ChuPatient patient) {

		// === Nom ===
		if (patient.getNom()==null) {
			message = "Le nom du patient n'est pas renseigné";
			this.addMessage(patient.getIdPatient(), message);
		}

		// === Prénom ===
		if (patient.getPrenom()==null) {
			message = "Le prénom du patient n'est pas renseigné";
			this.addMessage(patient.getIdPatient(), message);
		}
		if (patient.getPrenom()!=null && patient.getPrenom().contains("?")) {
			message = "Vérifier le prénom du patient : " + patient.getPrenom();
			this.addMessage(patient.getIdPatient(), message);
		}

		// === Sexe ===
		if (patient.getSexe()==null) {
			message = "Le sexe n'est pas renseigné";
			this.addMessage(patient.getIdPatient(), message);
		}

		// === Date de naissance ===
		if (patient.getDateNaissance()==null) {
			message = "La date de naissance n'est pas renseignée";
			this.addMessage(patient.getIdPatient(), message);
		}

		// === Statut brca ===
		if (patient.getStatutBrca()!=null && patient.getStatutBrca().contains("?")) {
			message = "Le statut BRCA n'est pas validé : " + patient.getStatutBrca();
			this.addMessage(patient.getIdPatient(), message);
		}

		// === Deces ===
		if (patient.getDateDeces()!=null && patient.getCauseDeces()==null) {
			message = "La cause du décès n'est pas renseignée";
			this.addMessage(patient.getIdPatient(), message);
		}
		if (patient.getCauseDeces()!=null && patient.getDateDeces()==null) {
			message = "La date de décès n'est pas renseignée";
			this.addMessage(patient.getIdPatient(), message);
		}
		if (patient.getDateDeces()!=null && patient.getDateNaissance()!=null &&
				patient.getDateNaissance().after(patient.getDateDeces())) {
			message = "La date de décès " + dateFormat.format(patient.getDateDeces()) 
			+ " précède la date de naissance " + dateFormat.format(patient.getDateNaissance());
			this.addMessage(patient.getIdPatient(), message);
		}


	}

	/** ====================================================================================== */

	private void addMessage(String key, String value) {

		if (!this.mapMessages.containsKey(key)) {
			this.mapMessages.put(key, new ArrayList<String>());
		}
		this.mapMessages.get(key).add(value);
	}

	/** ====================================================================================== */


	/** =================================================================================== */
}
