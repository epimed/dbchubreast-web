package dbchubreast_web.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import dbchubreast_web.entity.ChuCauseDeces;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.service.BaseService;

public class FormPatient extends BaseService implements IForm {

	private String idPatient;

	@Length(max = 100, message = "100 caractères au maximum")
	private String rcp;
	
	@Length(max = 50, message = "50 caractères au maximum")
	private String tk;

	@NotNull(message = "Ne peut pas être vide")
	@Length(max = 255, message = "255 caractères au maximum")
	private String prenom;

	@NotNull(message = "Ne peut pas être vide")
	@Length(max = 255, message = "255 caractères au maximum")
	private String nom;

	private String nomNaissance;

	@NotNull(message = "Ne peut pas être vide")
	private Date dateNaissance;

	@NotNull(message = "Ne peut pas être vide")
	private String sexe;

	@Length(max = 100, message = "100 caractères au maximum")
	private String statutBrca;
	
	private Date dateDeces;
	
	// === Cause deces selectionnee ===
	private Integer idCauseDeces;
	
	// === Liste de causes deces possibles ===
	private List<ChuCauseDeces> listCausesDeces = new ArrayList<ChuCauseDeces>();

	
	// === Liste de toutes tumeurs du patient ===
	private List<ChuTumeur> listTumeurs = new ArrayList<ChuTumeur>();
	
	// === Liste des tumeurs qui ont cause le deces ===
	private List<Integer> listIdTumeursCausesDeces = new ArrayList<Integer>();
	
	private String remarque;
	
	public FormPatient() {
		super();
	}

	/**
	 * @param idPatient
	 */
	public FormPatient(String idPatient) {
		super();
		this.idPatient = idPatient;
	}

	public String getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	public String getRcp() {
		return rcp;
	}

	public void setRcp(String rcp) {
		this.rcp = rcp;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getStatutBrca() {
		return statutBrca;
	}

	public void setStatutBrca(String statutBrca) {
		this.statutBrca = statutBrca;
	}

	public Date getDateDeces() {
		return dateDeces;
	}

	public void setDateDeces(Date dateDeces) {
		this.dateDeces = dateDeces;
	}

	public String getTk() {
		return tk;
	}

	public void setTk(String tk) {
		this.tk = tk;
	}


	public String getNomNaissance() {
		return nomNaissance;
	}

	public void setNomNaissance(String nomNaissance) {
		this.nomNaissance = nomNaissance;
	}
	
	public Integer getIdCauseDeces() {
		return idCauseDeces;
	}

	public void setIdCauseDeces(Integer idCauseDeces) {
		this.idCauseDeces = idCauseDeces;
	}

	public List<ChuCauseDeces> getListCausesDeces() {
		return listCausesDeces;
	}

	public void setListCausesDeces(List<ChuCauseDeces> listCausesDeces) {
		this.listCausesDeces = listCausesDeces;
	}

	public List<ChuTumeur> getListTumeurs() {
		return listTumeurs;
	}

	public void setListTumeurs(List<ChuTumeur> listTumeurs) {
		this.listTumeurs = listTumeurs;
	}

	public List<Integer> getListIdTumeursCausesDeces() {
		return listIdTumeursCausesDeces;
	}

	public void setListIdTumeursCausesDeces(List<Integer> listIdTumeursCausesDeces) {
		this.listIdTumeursCausesDeces = listIdTumeursCausesDeces;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}



	@Override
	public String toString() {
		return "FormPatient [idPatient=" + idPatient + ", rcp=" + rcp + ", tk=" + tk + ", prenom=" + prenom + ", nom="
				+ nom + ", nomNaissance=" + nomNaissance + ", dateNaissance=" + dateNaissance + ", sexe=" + sexe
				+ ", statutBrca=" + statutBrca + ", dateDeces=" + dateDeces + ", idCauseDeces=" + idCauseDeces
				+ ", listCausesDeces=" + listCausesDeces + ", listTumeurs=" + listTumeurs
				+ ", listIdTumeursCausesDeces=" + listIdTumeursCausesDeces + ", remarque=" + remarque + "]";
	}

	/** ====================================================================================== */

	public boolean isNew() {
		return this.idPatient == null;
	}

	/** ====================================================================================== */

}
