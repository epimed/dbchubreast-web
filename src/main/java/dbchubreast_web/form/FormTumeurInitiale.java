package dbchubreast_web.form;
// Generated 26 d�c. 2016 14:27:40 by Hibernate Tools 4.3.1.Final

import java.util.Date;

import org.hibernate.validator.constraints.Length;

public class FormTumeurInitiale extends AbstractFormPhaseTumeur {

	private Date dateDeces;

	private Double ageDiagnostic;
	private String cote;

	private String idTopographie;

	private Date dateEvolution;
	private Integer idEvolution;

	@Length(max = 100, message = "100 caractères au maximum")
	private String natureDiagnostic;
	
	@Length(max = 50, message = "50 caractères au maximum")
	private String profondeur;
	
	private Boolean consentement;

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

	public Date getDateDeces() {
		return dateDeces;
	}

	public void setDateDeces(Date dateDeces) {
		this.dateDeces = dateDeces;
	}

	public Boolean getConsentement() {
		return consentement;
	}

	public void setConsentement(Boolean consentement) {
		this.consentement = consentement;
	}

	@Override
	public String toString() {
		return "FormTumeurInitiale [dateDeces=" + dateDeces + ", ageDiagnostic=" + ageDiagnostic + ", cote=" + cote
				+ ", idTopographie=" + idTopographie + ", dateEvolution=" + dateEvolution + ", idEvolution="
				+ idEvolution + ", natureDiagnostic=" + natureDiagnostic + ", profondeur=" + profondeur + 
				", consentement=" + consentement + "]";
	}

	/** ====================================================================================== */

	public boolean isNew() {
		return this.idTumeur == null;
	}

	/** ====================================================================================== */

}
