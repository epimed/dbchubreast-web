package dbchubreast_web.form;
// Generated 26 d�c. 2016 14:27:40 by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormTumeurInitiale implements IForm {

	
	private Integer idTumeur;

	private String idPatient;
	private Date dateDiagnostic;
	private Double ageDiagnostic;
	private String cote;

	private String idTopographie;

	private Date dateEvolution;
	private Integer idEvolution;

	private Integer idPhase;
	private Integer idTypePhase = 1; // phase initiale
	private String natureDiagnostic;
	private String profondeur;

	private String cT;
	private String cN;
	private String cM;
	private String cTaille;

	private String pT;
	private String pN;
	private String pM;
	private String pTaille;

	private String remarque;

	private List<Integer> listIdMetastases = new ArrayList<Integer>(0);


	public FormTumeurInitiale(String idPatient) {
		super();
		this.idPatient = idPatient;
	}


	public FormTumeurInitiale() {
		super();
	}


	public Integer getIdTumeur() {
		return idTumeur;
	}


	public void setIdTumeur(Integer idTumeur) {
		this.idTumeur = idTumeur;
	}


	public String getIdPatient() {
		return idPatient;
	}


	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}


	public Date getDateDiagnostic() {
		return dateDiagnostic;
	}


	public void setDateDiagnostic(Date dateDiagnostic) {
		this.dateDiagnostic = dateDiagnostic;
	}


	public Double getAgeDiagnostic() {
		return ageDiagnostic;
	}


	public void setAgeDiagnostic(Double ageDiagnostic) {
		this.ageDiagnostic = ageDiagnostic;
	}


	public String getCote() {
		return cote;
	}


	public void setCote(String cote) {
		this.cote = cote;
	}


	public String getIdTopographie() {
		return idTopographie;
	}


	public void setIdTopographie(String idTopographie) {
		this.idTopographie = idTopographie;
	}


	public Date getDateEvolution() {
		return dateEvolution;
	}


	public void setDateEvolution(Date dateEvolution) {
		this.dateEvolution = dateEvolution;
	}


	public Integer getIdEvolution() {
		return idEvolution;
	}


	public void setIdEvolution(Integer idEvolution) {
		this.idEvolution = idEvolution;
	}


	public Integer getIdPhase() {
		return idPhase;
	}


	public void setIdPhase(Integer idPhase) {
		this.idPhase = idPhase;
	}


	public Integer getIdTypePhase() {
		return idTypePhase;
	}


	public void setIdTypePhase(Integer idTypePhase) {
		this.idTypePhase = idTypePhase;
	}


	public String getNatureDiagnostic() {
		return natureDiagnostic;
	}


	public void setNatureDiagnostic(String natureDiagnostic) {
		this.natureDiagnostic = natureDiagnostic;
	}


	public String getProfondeur() {
		return profondeur;
	}


	public void setProfondeur(String profondeur) {
		this.profondeur = profondeur;
	}


	public String getcT() {
		return cT;
	}


	public void setcT(String cT) {
		this.cT = cT;
	}


	public String getcN() {
		return cN;
	}


	public void setcN(String cN) {
		this.cN = cN;
	}


	public String getcM() {
		return cM;
	}


	public void setcM(String cM) {
		this.cM = cM;
	}


	public String getcTaille() {
		return cTaille;
	}


	public void setcTaille(String cTaille) {
		this.cTaille = cTaille;
	}


	public String getpT() {
		return pT;
	}


	public void setpT(String pT) {
		this.pT = pT;
	}


	public String getpN() {
		return pN;
	}


	public void setpN(String pN) {
		this.pN = pN;
	}


	public String getpM() {
		return pM;
	}


	public void setpM(String pM) {
		this.pM = pM;
	}


	public String getpTaille() {
		return pTaille;
	}


	public void setpTaille(String pTaille) {
		this.pTaille = pTaille;
	}


	public String getRemarque() {
		return remarque;
	}


	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}


	public List<Integer> getListIdMetastases() {
		return listIdMetastases;
	}


	public void setListIdMetastases(List<Integer> listIdMetastases) {
		this.listIdMetastases = listIdMetastases;
	}


	@Override
	public String toString() {
		return "FormTumeurInitiale [idTumeur=" + idTumeur + ", idPatient=" + idPatient + ", dateDiagnostic="
				+ dateDiagnostic + ", ageDiagnostic=" + ageDiagnostic + ", cote=" + cote + ", idTopographie="
				+ idTopographie + ", dateEvolution=" + dateEvolution + ", idEvolution=" + idEvolution + ", idPhase="
				+ idPhase + ", idTypePhase=" + idTypePhase + ", natureDiagnostic=" + natureDiagnostic + ", profondeur="
				+ profondeur + ", cT=" + cT + ", cN=" + cN + ", cM=" + cM + ", cTaille=" + cTaille + ", pT=" + pT
				+ ", pN=" + pN + ", pM=" + pM + ", pTaille=" + pTaille + ", remarque=" + remarque
				+ ", listIdMetastases=" + listIdMetastases + "]";
	}

	/** ====================================================================================== */

	public boolean isNew() {
		return this.idTumeur==null;
	}

	/** ====================================================================================== */


}
