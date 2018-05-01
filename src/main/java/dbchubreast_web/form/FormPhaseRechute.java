package dbchubreast_web.form;
// Generated 26 d�c. 2016 14:27:40 by Hibernate Tools 4.3.1.Final

public class FormPhaseRechute extends AbstractFormPhaseTumeur {

	private Integer idPs;
	private Boolean locale;

	/** ========================================================================== */

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

	public FormPhaseRechute() {
		super();
		this.idTypePhase = 2; // phase rechute
	}

	/** ====================================================================================== */

	public boolean isNew() {
		return this.idPhase == null;
	}
	
	/** ====================================================================================== */


	@Override
	public String toString() {
		return "FormPhaseRechute [idPs=" + idPs + ", locale=" + locale + ", idTumeur=" + idTumeur + ", idPatient="
				+ idPatient + ", dateDiagnostic=" + dateDiagnostic + ", idPhase=" + idPhase + ", idTypePhase="
				+ idTypePhase + ", idTopographie=" + idTopographie + ", remarque=" + remarque + ", listIdMetastases="
				+ listIdMetastases + "]";
	}

	
	/** ====================================================================================== */

}
