package dbchubreast_web.form;
// Generated 26 d�c. 2016 14:27:40 by Hibernate Tools 4.3.1.Final

import java.util.Date;

public class FormTumeurInitiale extends AbstractFormPhaseTumeur {

	private Date dateDeces;

	private Double ageDiagnostic;
	private String cote;

	private String idTopographie;

	private Date dateEvolution;
	private Integer idEvolution;

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

	/** ========================================================================== */

	public FormTumeurInitiale(String idPatient, Date dateDeces) {
		super();
		this.idTypePhase = 1; // phase initiale
		this.idPatient = idPatient;
		this.dateDeces = dateDeces;

		if (dateDeces != null) {
			this.dateEvolution = dateDeces;
			this.idEvolution = 5;
		}
	}

	public FormTumeurInitiale() {
		super();
		this.idTypePhase = 1; // phase initiale
	}

	/** ========================================================================== */

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

	public Date getDateDeces() {
		return dateDeces;
	}

	public void setDateDeces(Date dateDeces) {
		this.dateDeces = dateDeces;
	}

	@Override
	public String toString() {
		return "FormTumeurInitiale [dateDeces=" + dateDeces + ", ageDiagnostic=" + ageDiagnostic + ", cote=" + cote
				+ ", idTopographie=" + idTopographie + ", dateEvolution=" + dateEvolution + ", idEvolution="
				+ idEvolution + ", natureDiagnostic=" + natureDiagnostic + ", profondeur=" + profondeur + ", cT=" + cT
				+ ", cN=" + cN + ", cM=" + cM + ", cTaille=" + cTaille + ", pT=" + pT + ", pN=" + pN + ", pM=" + pM
				+ ", pTaille=" + pTaille + "]";
	}

	/** ====================================================================================== */

	public boolean isNew() {
		return this.idTumeur == null;
	}

	/** ====================================================================================== */

}
