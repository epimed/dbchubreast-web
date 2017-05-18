package dbchubreast_web.form;

import java.util.Date;

import dbchubreast_web.service.BaseService;

public class FormTraitement extends BaseService implements IForm {

	private Integer idTraitement;
	private String idPatient;
	private Integer idTumeur;
	private Integer idPhase;
	private Integer idMethode;
	private Integer idProtocole;
	private Integer idReponse;
	private Date dateDebut;
	private Date dateFin;
	private String nbCures;
	private Boolean ggSentinelle;
	private String remarque;
	
	
	/** ========================================================= */
	
	public FormTraitement() {
		super();
	}

	public FormTraitement(String idPatient) {
		this.idPatient = idPatient;
	}

	/** ========================================================= */

	public boolean isNew() {
		return this.idTraitement == null;
	}

	public Integer getIdTraitement() {
		return idTraitement;
	}

	public void setIdTraitement(Integer idTraitement) {
		this.idTraitement = idTraitement;
	}

	public String getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	public Integer getIdTumeur() {
		return idTumeur;
	}

	public void setIdTumeur(Integer idTumeur) {
		this.idTumeur = idTumeur;
	}

	public Integer getIdPhase() {
		return idPhase;
	}

	public void setIdPhase(Integer idPhase) {
		this.idPhase = idPhase;
	}

	public Integer getIdMethode() {
		return idMethode;
	}

	public void setIdMethode(Integer idMethode) {
		this.idMethode = idMethode;
	}

	public Integer getIdProtocole() {
		return idProtocole;
	}

	public void setIdProtocole(Integer idProtocole) {
		this.idProtocole = idProtocole;
	}

	public Integer getIdReponse() {
		return idReponse;
	}

	public void setIdReponse(Integer idReponse) {
		this.idReponse = idReponse;
	}
	
	

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	
	public String getNbCures() {
		return nbCures;
	}

	public void setNbCures(String nbCures) {
		this.nbCures = nbCures;
	}

	public Boolean getGgSentinelle() {
		return ggSentinelle;
	}

	public void setGgSentinelle(Boolean ggSentinelle) {
		this.ggSentinelle = ggSentinelle;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	@Override
	public String toString() {
		return "FormTraitement [idTraitement=" + idTraitement + ", idPatient=" + idPatient + ", idTumeur=" + idTumeur
				+ ", idPhase=" + idPhase + ", idMethode=" + idMethode + ", idProtocole=" + idProtocole + ", idReponse="
				+ idReponse + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", nbCures=" + nbCures
				+ ", ggSentinelle=" + ggSentinelle + ", remarque=" + remarque + "]";
	}

	

	/** ====================================================================================== */

	

}
