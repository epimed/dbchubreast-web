package dbchubreast_web.form.tumeur;

public class FormPhaseRechute extends AbstractFormPhaseTumeur {

	private Integer idPs;
	private Boolean locale;
	private Boolean metastases;
	private String idTopographieTumeurInitiale;
	private String nomTopographieTumeurInitiale;

	/** ========================================================================== */

	public FormPhaseRechute() {
		super();
		this.idTypePhase = 2; // phase rechute
	}
	
	public FormPhaseRechute(String idPatient, Integer idTumeur) {
		super();
		this.idPatient = idPatient;
		this.idTumeur = idTumeur;
		this.idTypePhase = 2; // phase rechute
	}

	/** ========================================================================== */

	public Integer getIdPs() {
		return idPs;
	}

	public void setIdPs(Integer idPs) {
		this.idPs = idPs;
	}

	public Boolean getLocale() {
		return locale;
	}

	public void setLocale(Boolean locale) {
		this.locale = locale;
	}
	

	public Boolean getMetastases() {
		return metastases;
	}


	public void setMetastases(Boolean metastases) {
		this.metastases = metastases;
	}


	public String getIdTopographieTumeurInitiale() {
		return idTopographieTumeurInitiale;
	}

	public void setIdTopographieTumeurInitiale(String idTopographieTumeurInitiale) {
		this.idTopographieTumeurInitiale = idTopographieTumeurInitiale;
	}


	public String getNomTopographieTumeurInitiale() {
		return nomTopographieTumeurInitiale;
	}

	public void setNomTopographieTumeurInitiale(String nomTopographieTumeurInitiale) {
		this.nomTopographieTumeurInitiale = nomTopographieTumeurInitiale;
	}

	/** ====================================================================================== */
	
	@Override
	public boolean isNew() {
		return this.idPhase == null;
	}

	
	/** ====================================================================================== */
	
	@Override
	public String toString() {
		return "FormPhaseRechute [idPs=" + idPs + ", locale=" + locale + ", metastases=" + metastases
				+ ", idTopographieTumeurInitiale=" + idTopographieTumeurInitiale + ", nomTopographieTumeurInitiale="
				+ nomTopographieTumeurInitiale + ", dateDiagnostic=" + dateDiagnostic + ", idPhase=" + idPhase
				+ ", idTopographie=" + idTopographie + ", remarque=" + remarque + "]";
	}

	
	/** ====================================================================================== */

}