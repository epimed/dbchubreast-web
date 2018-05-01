package dbchubreast_web.service.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

import dbchubreast_web.dao.ChuMetastaseDao;
import dbchubreast_web.dao.ChuPatientDao;
import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.dao.ChuPrelevementBiomarqueurDao;
import dbchubreast_web.dao.ChuPrelevementDao;
import dbchubreast_web.dao.ChuTnmDao;
import dbchubreast_web.dao.ChuTraitementDao;
import dbchubreast_web.dao.ChuTumeurDao;
import dbchubreast_web.entity.ChuMetastase;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPerformanceStatus;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.entity.ChuTnm;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;


@Service
public class PdfService {

	@Autowired
	private ChuPatientDao patientDao;

	@Autowired
	private ChuTumeurDao tumeurDao;

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	@Autowired
	private ChuTnmDao tnmDao;

	@Autowired 
	private ChuMetastaseDao metastaseDao;

	@Autowired 
	private ChuPrelevementDao prelevementDao;

	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;

	@Autowired 
	private ChuTraitementDao traitementDao;


	public static final Color VERY_LIGHT_GRAY = new DeviceRgb(230, 230, 230);
	public static final Color GRAY = new DeviceRgb(120, 120, 120);
	public static final Color VERY_LIGHT_PINK = new DeviceRgb(255, 220, 220);
	public static final Color DARK_PINK = new DeviceRgb(200, 0, 0);

	protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


	/** ====================================================================================== */

	public void createHeaderFooter(PdfDocument pdfDoc, Document document) {
		pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new PdfHeaderFooterEventHandler(document));
	}

	/** ====================================================================================== */

	/**
	 * 
	 * @param listIdPatients
	 * @param document
	 * @param version : "confidentielle" ou "publique" 
	 */

	public void createPdf(List<String> listIdPatients, Document document, Map<String, List<String>> mapMessages, String version) {

		for (int i=0; i<listIdPatients.size(); i++) {

			String idPatient = listIdPatients.get(i);
			ChuPatient patient = patientDao.find(idPatient);

			if (patient!=null) {

				// === Identification du patient ===
				this.addIdPatient(patient, document, version);

				// === Messages de coherence de donnees ===
				if (mapMessages!=null) {
					this.addConsistencyMessages(mapMessages.get(patient.getIdPatient()), document);
				}
				
				// === Tumeurs ===
				this.addTumeurs(patient, document, version);

			}
			else {
				document.add(new Paragraph("Le patient " + idPatient + " n'est pas trouvé dans la base de données."));
			}

			if (i<listIdPatients.size()-1) {
				document.add(new AreaBreak());
			}

		}

	}

	/** ====================================================================================== */

	public void addConsistencyMessages (List<String> messages, Document document) {

		if (messages!=null && !messages.isEmpty()) {

			this.addEmptyLines(document, 1);

			Table table = new Table(new float[] {1});
			table.setWidthPercent(100);
			Cell cell = new Cell();

			Paragraph p = new Paragraph("Vérification de données :");
			p.setFontColor(DARK_PINK);
			p.setBold();
			cell.add(p);
			cell.add(new Paragraph(""));

			String content = "";
			for (int i=0; i<messages.size(); i++) {
				String message = messages.get(i);
				content = content + "- " + message;
				if (i<messages.size()-1) {
					content = content + "\n";
				}
			}
			Paragraph mp = new Paragraph(content);
			mp.setFontColor(DARK_PINK);
			cell.add(mp);

			cell.setBackgroundColor(VERY_LIGHT_PINK);
			cell.setBorder(new SolidBorder(DARK_PINK, 1.0f));
			table.addCell(cell);
			document.add(table);
		}

	}

	
	/** ====================================================================================== */

	public void addTumeurs(ChuPatient patient, Document document, String version) {

		List<ChuTumeur> listTumeurs = tumeurDao.listByIdPatientWithDependencies(patient.getIdPatient(), "tumeurs");
		for (int i=0; i<listTumeurs.size(); i++) {

			ChuTumeur tumeur = listTumeurs.get(i);

			// === Titre TUMEUR ===

			this.addEmptyLines(document, 2);
			Paragraph pTitre = new Paragraph("TUMEUR " + (i+1));
			pTitre.setBold();
			document.add(pTitre);

			if (tumeur.getTripleNegative()!=null && tumeur.getTripleNegative()==true) {
				Paragraph tn = new Paragraph ("Triple negative");
				tn.setFontColor(GRAY);
				document.add(tn);
			}	

			// === Phase initiale ===
			this.addPhaseInitiale(tumeur, document);

			// === Rechutes ===
			this.addRechutes(tumeur, document);


			// === Deniere nouvelle ===
			this.addEmptyLines(document, 1);
			this.addDerniereNouvelle(patient, tumeur, document);

		}

	}


	/** ====================================================================================== */

	public void addDerniereNouvelle(ChuPatient patient, ChuTumeur tumeur, Document document) {

		Paragraph p = new Paragraph("Dernière nouvelle").setBold();
		document.add(p);

		this.addEmptyLines(document, 1);

		// === Data ===
		float[] columnWidths = {1, 1};
		Table t = new Table(columnWidths);
		t.setWidthPercent(100);


		String derniereNouvelle = tumeur.getDateEvolution()==null ? "" :  dateFormat.format(tumeur.getDateEvolution());
		t.addCell("Date de la dernière nouvelle : " + derniereNouvelle);

		String statut = tumeur.getChuEvolution()==null ? "" : 
			tumeur.getChuEvolution().getCode() + " - " + tumeur.getChuEvolution().getNom();
		t.addCell("Statut : " + statut);

		if (patient.getDateDeces()!=null) {
			String dateDeces = patient.getDateDeces()==null ? "" : dateFormat.format(patient.getDateDeces());
			t.addCell("Date de décès : " + dateDeces);

			String causeDeces =  patient.getCauseDeces()==null ? ""  : patient.getCauseDeces();
			t.addCell("Cause de décès : " + causeDeces);
		}

		String survieGlobale = tumeur.getOsMonths()==null ?  ""  : tumeur.getOsMonths().toString();
		t.addCell("Survie globale (mois) : " + survieGlobale);

		String survieSansRechute  = tumeur.getDfsMonths()==null ?  ""  : tumeur.getDfsMonths().toString();
		t.addCell("Survie sans rechute (mois) : " + survieSansRechute);

		document.add(t);

	}

	/** ====================================================================================== */

	public void addPhaseInitiale(ChuTumeur tumeur, Document document) {

		this.addEmptyLines(document, 1);
		Paragraph pPhaseInitiale = new Paragraph("Phase initiale").setBold();
		document.add(pPhaseInitiale);


		this.addEmptyLines(document, 1);

		ChuPhaseTumeur phaseInitiale = phaseTumeurDao.findPhaseInitiale(tumeur.getIdTumeur());

		Table table = new Table(new float[] {1, 1});
		table.setWidthPercent(100);

		String dateDiagnostic = tumeur.getDateDiagnostic()==null ? "" : dateFormat.format(tumeur.getDateDiagnostic());
		table.addCell("Date du diagnostic : " + dateDiagnostic);

		String ageDiagnostic = tumeur.getAgeDiagnostic()==null ? "" : tumeur.getAgeDiagnostic().toString();
		table.addCell("Age au diagnostic : " + ageDiagnostic);

		String natureDiagnostic = phaseInitiale.getNatureDiagnostic()==null ? "" : phaseInitiale.getNatureDiagnostic();
		table.addCell("Nature du diagnostic : " + natureDiagnostic);

		String profondeur = phaseInitiale.getProfondeur()==null ? "" : phaseInitiale.getProfondeur();
		table.addCell("Profondeur : " + profondeur);

		String topographie = phaseInitiale.getChuTopographie()==null ? "" : 
			phaseInitiale.getChuTopographie().getIdTopographie() + " - " + phaseInitiale.getChuTopographie().getNomFr();
		table.addCell("Topographie : " + topographie);

		String cote = tumeur.getCote()==null ? "" : tumeur.getCote();
		table.addCell("Côté : " + cote);

		// === TNM ==

		ChuTnm cTnm = tnmDao.find(phaseInitiale.getIdPhase(), "c");
		table.addCell("cTNM : " + this.generateTextTnm(cTnm));

		String textCtaille = cTnm==null ? "" : cTnm.getTaille()==null ? "" : cTnm.getTaille();
		table.addCell("c Taille (mm) : " + textCtaille);

		ChuTnm pTnm = tnmDao.find(phaseInitiale.getIdPhase(), "p");
		table.addCell("pTNM : " + this.generateTextTnm(pTnm));

		String textPtaille = pTnm==null ? "" : pTnm.getTaille()==null ? "" : pTnm.getTaille();
		table.addCell("p Taille (mm) : " + textPtaille);


		// === Metastases ===
		this.addMetastases(phaseInitiale.getIdPhase(), table);

		document.add(table);

		String remarque = phaseInitiale.getRemarque();
		if (remarque !=null && !remarque.isEmpty()) {
			this.addEmptyLines(document, 1);
			document.add(new Paragraph ("Remarque : " + remarque));
		}

		// === Prelevements, traitements ===
		this.addPrelevementTraitements(phaseInitiale, document);

	}


	/** ====================================================================================== */

	public void addRechutes(ChuTumeur tumeur, Document document) {



		List<ChuPhaseTumeur> listRechutes = phaseTumeurDao.list(tumeur.getIdTumeur(), 2);

		for (int i=0; i<listRechutes.size(); i++) {

			this.addEmptyLines(document, 1);
			Paragraph p = new Paragraph("Rechute " + (i+1)).setBold();
			document.add(p);

			ChuPhaseTumeur rechute = listRechutes.get(i);

			Table table = new Table(new float[] {1, 1});
			table.setWidthPercent(100);

			String dateRechute = rechute.getDateDiagnostic()==null ? "" : dateFormat.format(rechute.getDateDiagnostic());
			table.addCell("Date de rechute : " + dateRechute);

			String locale = rechute.getLocale()==null ? "" : rechute.getLocale()==true ? "oui" : "non";
			table.addCell("Rechute locale : " + locale);

			String topographie = rechute.getChuTopographie()==null ? "" : 
				rechute.getChuTopographie().getIdTopographie() + " - " + rechute.getChuTopographie().getNomFr();
			table.addCell("Topographie : " + topographie);

			String cote = tumeur.getCote()==null ? "" : tumeur.getCote();
			table.addCell("Côté : " + cote);


			// === TNM ==

			ChuTnm cTnm = tnmDao.find(rechute.getIdPhase(), "c");
			table.addCell("cTNM : " + this.generateTextTnm(cTnm));

			String textCtaille = cTnm==null ? "" : cTnm.getTaille()==null ? "" : cTnm.getTaille();
			table.addCell("c Taille (mm) : " + textCtaille);

			ChuTnm pTnm = tnmDao.find(rechute.getIdPhase(), "p");
			table.addCell("pTNM : " + this.generateTextTnm(pTnm));

			String textPtaille = pTnm==null ? "" : pTnm.getTaille()==null ? "" : pTnm.getTaille();
			table.addCell("p Taille (mm) : " + textPtaille);

			// === PS ===
			Cell cell = new Cell(1,2);
			ChuPerformanceStatus ps = rechute.getChuPerformanceStatus();
			String textPs = ps==null ? "" : ps.getIdPs() + "" ; // + " - " + ps.getDescription();
			cell.add("PS : " + textPs);
			table.addCell(cell);


			// === Metastases ===
			this.addMetastases(rechute.getIdPhase(), table);

			this.addEmptyLines(document, 1);
			// table.setBackgroundColor(VERY_LIGHT_PINK);
			document.add(table);

			// === Remarque ===

			if (rechute.getNumeroRechute()!=null) {
				document.add(new Paragraph ("Numéro de la rechute = " + rechute.getNumeroRechute())); ;
			}

			String remarque = rechute.getRemarque();
			if (remarque!=null && !remarque.isEmpty()) {
				this.addEmptyLines(document, 1);
				document.add(new Paragraph ("Remarque : " + remarque));
			}

			// === Prelevements, traitements ===
			this.addPrelevementTraitements(rechute, document);

		}

		if (listRechutes.size()==0) {
			this.addEmptyLines(document, 1);
			document.add(new Paragraph("Aucune rechute n'est enregistrée pour cette tumeur."));
		}

	}

	/** ====================================================================================== */

	public void addMetastases (Integer idPhase, Table table) {

		// === Metastases ===

		List<ChuMetastase> listMetastases = metastaseDao.list(idPhase);	
		Cell cell = new Cell(1,2);
		String textMetastases = "";
		for (ChuMetastase metastase : listMetastases) {
			if (!textMetastases.isEmpty()) {
				textMetastases = textMetastases + ", ";
			}
			textMetastases = textMetastases + metastase.getNom();
		}
		cell.add("Métastases : " + textMetastases);
		table.addCell(cell);
	}

	/** ====================================================================================== */

	public void addPrelevementTraitements(ChuPhaseTumeur phase, Document document) {

		List<Object> list = phaseTumeurDao.listChronoPrelevementsTraitements(phase.getIdPhase());

		/*
		 * list data structure
		 * id (prel ou trait), date, type
		1;"2015-01-15";"prelevement"
		3;"2015-03-10";"traitement"
		2;"2015-02-18";"prelevement"
		1;"2015-02-06";"traitement"
		2;"2015-02-18";"traitement"
		4;"2015-04-13";"traitement"
		 */

		for (int i=0; i<list.size(); i++) {
			Object [] line = (Object[]) list.get(i);
			Integer id = (Integer) line[0];
			String type = (String) line[2];

			if (type.equals("prelevement")) {
				ChuPrelevement prelevement = prelevementDao.find(id);
				this.addPrelevement(prelevement, document);
			}

			if (type.equals("traitement")) {
				ChuTraitement traitement = traitementDao.find(id);
				this.addTraitement(traitement, document);
			}
		}

	}

	/** ====================================================================================== */

	public void addTraitement(ChuTraitement traitement, Document document) {

		this.addEmptyLines(document, 1);


		// === Title ===
		Paragraph p = new Paragraph();
		String title = "";
		if (traitement.getDateDebut()!=null) {
			title = title + dateFormat.format(traitement.getDateDebut()) + " - ";
		}
		title = title + "traitement";
		if (traitement.getChuTypeTraitement()!=null) {
			title = title + " " + traitement.getChuTypeTraitement().getNom();
		}
		if (traitement.getChuMethodeTraitement()!=null) {
			title = title + " - " + traitement.getChuMethodeTraitement().getNom();
		}

		Text text = new Text(title);
		text.setBold();
		p.add(text);

		// === Protocole ===
		if (traitement.getChuProtocoleTraitement()!=null) {
			String protocole = "Protocole : " + traitement.getChuProtocoleTraitement().getNom();
			p.add("\n" + protocole);
		}

		// === Date fin ===
		if (traitement.getDateFin()!=null) {
			String dateFin = "Date de fin : " + dateFormat.format(traitement.getDateFin());
			p.add("\n" + dateFin);
		}

		// === Dose ===
		if (traitement.getDose()!=null) {
			String dose = "Dose : " + traitement.getDose();
			p.add("\n" + dose);
		}

		// === Nb cures ===
		if (traitement.getNbCures()!=null) {
			String nbCures = "Nombre de cures : " + traitement.getNbCures();
			p.add("\n" + nbCures);
		}

		// === GG sentinelle ===
		if (traitement.getGgSentinelle()!=null) {
			String ggSentinelle = "Technique de ganglion sentinelle : " + (traitement.getGgSentinelle() ? "oui" : "non");
			p.add("\n" + ggSentinelle);
		}

		// === Reponse ===
		if (traitement.getChuReponse()!=null) {
			String reponse = "Réponse : " + traitement.getChuReponse().getNom();
			p.add("\n" + reponse);
		}

		// === Remarque ===
		if (traitement.getRemarque()!=null) {
			p.add("\n" + traitement.getRemarque());
		}

		document.add(p);

	}


	/** ====================================================================================== */


	public void addPrelevement(ChuPrelevement prelevement, Document document) {
		this.addEmptyLines(document, 1);


		// === Title ===
		Paragraph p = new Paragraph();
		String title = "";
		if (prelevement.getDatePrelevement()!=null) {
			title = title + dateFormat.format(prelevement.getDatePrelevement()) + " - ";
		}
		title = title + "prélèvement"; 
		if (prelevement.getChuTypePrelevement()!=null) {
			title = title + " - " + prelevement.getChuTypePrelevement().getNom();
		}

		Text text = new Text(title);
		text.setBold();
		p.add(text);


		// === Site ===
		if (prelevement.getSitePrelevement()!=null) {
			String site = "Site : " + prelevement.getSitePrelevement();
			p.add("\n" + site);
		}


		// === Morphologie ===
		if (prelevement.getChuMorphologie()!=null) {
			String morpho = "Morphologie : " + prelevement.getChuMorphologie().getIdMorphologie();
			if (prelevement.getChuMorphologie().getNomEn()!=null) {
				morpho = morpho + " - " +  prelevement.getChuMorphologie().getNomEn();
			}
			if (prelevement.getChuMorphologie().getNomFr()!=null) {
				morpho = morpho + " - " +  prelevement.getChuMorphologie().getNomFr();
			}
			p.add("\n" + morpho);
		}

		// === Histologie ===
		if (prelevement.getTypeHistologique()!=null) {
			String histo = "Histologie : " + prelevement.getTypeHistologique();
			p.add("\n" + histo);
		}

		// === Association CIS ===
		if (prelevement.getAssociationCis()!=null) {
			String cis = "Association CIS : " + (prelevement.getAssociationCis() ? "oui" : "non");
			p.add("\n" + cis);
		}

		// === Biomarqueurs ===

		List<ChuPrelevementBiomarqueur> listPB = prelevementBiomarqueurDao.list(prelevement.getIdPrelevement());

		if (listPB!=null && !listPB.isEmpty()) {
			String bio = "";
			for (int i=0; i<listPB.size(); i++) {
				ChuPrelevementBiomarqueur pb  = listPB.get(i);
				bio = bio + pb.getChuBiomarqueur().getNom() + " : " + pb.getValeur();
				if (pb.getStatut()!=null) {
					bio = bio + " (" + pb.getStatut() + ")";
				}
				if (i<listPB.size()-1) {
					bio = bio + ", ";
				}
			}
			p.add("\n" + bio);
		}

		document.add(p);

	}

	/** ====================================================================================== */

	public void addIdPatient(ChuPatient patient, Document document, String version) {

		// === Title ===
		Paragraph p = new Paragraph();
		Text text = new Text("IDENTIFICATION DU PATIENT " + patient.getIdPatient());
		text.setBold();
		p.add(text);

		document.add(p);

		this.addEmptyLines(document, 1);

		if (version.equals("confidentielle")) {

			// === Data ===
			float[] columnWidths = {1, 1};
			Table t = new Table(columnWidths);
			t.setWidthPercent(100);


			String textNom = "Nom : " + patient.getNom();
			if (patient.getNomNaissance()!=null) {
				textNom = textNom + " (" + patient.getNomNaissance() + ")";
			}
			t.addCell(textNom);
			t.addCell("Prénom : " + patient.getPrenom());
			t.addCell("Sexe : " + patient.getSexe());

			String dateNaissance = patient.getDateNaissance()==null ? "" : dateFormat.format(patient.getDateNaissance());
			t.addCell("Date de naissance : " + dateNaissance);

			String rcp = patient.getRcp()==null ? "" :  patient.getRcp();
			t.addCell("RCP : " + rcp);

			String tk = patient.getTk()==null ? "" :  patient.getTk();
			t.addCell("TK : " + tk);

			document.add(t);

		}
		else {
			document.add(new Paragraph("Les informations sur l'identité du patient ne sont pas disponibles."));
		}


	}

	/** ====================================================================================== */

	public String generateTextTnm(ChuTnm tnm) {

		String text = "";
		if (tnm!=null) {
			if (tnm.getT()!=null) {
				text = text + tnm.getT();
			}
			if (tnm.getN()!=null) {
				text = text + " " + tnm.getN();
			}
			if (tnm.getM()!=null) {
				text = text + " " + tnm.getM();
			}
		}
		return text;
	}

	/** ====================================================================================== */

	public void addEmptyLines(Document document, int nbLines) {
		for (int i=0; i<nbLines; i++) {
			document.add(new Paragraph(""));
		}
	}

	/** ====================================================================================== */


}
