package dbchubreast_web.entity;
// Generated 25 janv. 2017 10:58:08 by Hibernate Tools 4.3.5.Final

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * ChuPatient generated by hbm2java
 */
@Entity
@Table(name = "chu_patient", schema = "db_chu_breast")
public class ChuPatient implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idPatient;
	private ChuAntecedentGyneco chuAntecedentGyneco;
	private ChuModeVie chuModeVie;
	private ChuProfession chuProfession;
	private ChuStatutMarital chuStatutMarital;
	private String tk;
	private String rcp;
	private String nom;
	private String prenom;
	private String nomNaissance;
	private Date dateNaissance;
	private String sexe;
	private Double imc20Ans;
	private Integer scoreCharlsonCumule;
	private String statutBrca;
	private Date dateDeces;
	private String causeDeces; // old, to replace with ChuCauseDeces
	private ChuCauseDeces chuCauseDeces;
	private Date dateEvolution; // copy from tumeur.dateEvolution
	private String remarque;
	
	private List<ChuAntecedentGeneral> chuAntecedentGenerals = new ArrayList<ChuAntecedentGeneral>(0);
	private List<ChuAdresse> chuAdresses = new ArrayList<ChuAdresse>(0);
	private List<ChuTumeur> chuTumeurs = new ArrayList<ChuTumeur>(0);
	private List<ChuTumeur> chuTumeursCauseDeces = new ArrayList<ChuTumeur>(0);

	public ChuPatient() {
	}

	public ChuPatient(String idPatient) {
		this.idPatient = idPatient;
	}


	public ChuPatient(String idPatient, ChuAntecedentGyneco chuAntecedentGyneco, ChuModeVie chuModeVie,
			ChuProfession chuProfession, ChuStatutMarital chuStatutMarital, String tk, String rcp, String nom,
			String prenom, String nomNaissance, Date dateNaissance, String sexe, Double imc20Ans,
			Integer scoreCharlsonCumule, String statutBrca, Date dateDeces, String causeDeces,
			dbchubreast_web.entity.ChuCauseDeces chuCauseDeces, Date dateEvolution, String remarque,
			List<ChuAntecedentGeneral> chuAntecedentGenerals, List<ChuAdresse> chuAdresses,
			List<ChuTumeur> chuTumeurs) {
		super();
		this.idPatient = idPatient;
		this.chuAntecedentGyneco = chuAntecedentGyneco;
		this.chuModeVie = chuModeVie;
		this.chuProfession = chuProfession;
		this.chuStatutMarital = chuStatutMarital;
		this.tk = tk;
		this.rcp = rcp;
		this.nom = nom;
		this.prenom = prenom;
		this.nomNaissance = nomNaissance;
		this.dateNaissance = dateNaissance;
		this.sexe = sexe;
		this.imc20Ans = imc20Ans;
		this.scoreCharlsonCumule = scoreCharlsonCumule;
		this.statutBrca = statutBrca;
		this.dateDeces = dateDeces;
		this.causeDeces = causeDeces;
		this.chuCauseDeces = chuCauseDeces;
		this.dateEvolution = dateEvolution;
		this.remarque = remarque;
		this.chuAntecedentGenerals = chuAntecedentGenerals;
		this.chuAdresses = chuAdresses;
		this.chuTumeurs = chuTumeurs;
	}

	@Id

	@Column(name = "id_patient", unique = true, nullable = false, length = 20)
	public String getIdPatient() {
		return this.idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_antecedent_gyneco")
	public ChuAntecedentGyneco getChuAntecedentGyneco() {
		return this.chuAntecedentGyneco;
	}

	public void setChuAntecedentGyneco(ChuAntecedentGyneco chuAntecedentGyneco) {
		this.chuAntecedentGyneco = chuAntecedentGyneco;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_mode_vie")
	public ChuModeVie getChuModeVie() {
		return this.chuModeVie;
	}

	public void setChuModeVie(ChuModeVie chuModeVie) {
		this.chuModeVie = chuModeVie;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profession")
	public ChuProfession getChuProfession() {
		return this.chuProfession;
	}

	public void setChuProfession(ChuProfession chuProfession) {
		this.chuProfession = chuProfession;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_statut_marital")
	public ChuStatutMarital getChuStatutMarital() {
		return this.chuStatutMarital;
	}

	public void setChuStatutMarital(ChuStatutMarital chuStatutMarital) {
		this.chuStatutMarital = chuStatutMarital;
	}

	@Column(name = "tk", length = 50)
	public String getTk() {
		return this.tk;
	}

	public void setTk(String tk) {
		this.tk = tk;
	}

	@Column(name = "rcp", length = 100)
	public String getRcp() {
		return this.rcp;
	}

	public void setRcp(String rcp) {
		this.rcp = rcp;
	}

	@Column(name = "nom")
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "prenom")
	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	@Column(name = "nom_naissance")
	public String getNomNaissance() {
		return this.nomNaissance;
	}

	public void setNomNaissance(String nomNaissance) {
		this.nomNaissance = nomNaissance;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_naissance", length = 13)
	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	@Column(name = "sexe", length = 1)
	public String getSexe() {
		return this.sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	@Column(name = "imc_20_ans", precision = 17, scale = 17)
	public Double getImc20Ans() {
		return this.imc20Ans;
	}

	public void setImc20Ans(Double imc20Ans) {
		this.imc20Ans = imc20Ans;
	}

	@Column(name = "score_charlson_cumule")
	public Integer getScoreCharlsonCumule() {
		return this.scoreCharlsonCumule;
	}

	public void setScoreCharlsonCumule(Integer scoreCharlsonCumule) {
		this.scoreCharlsonCumule = scoreCharlsonCumule;
	}

	@Column(name = "statut_brca", length = 100)
	public String getStatutBrca() {
		return this.statutBrca;
	}

	public void setStatutBrca(String statutBrca) {
		this.statutBrca = statutBrca;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_deces", length = 13)
	public Date getDateDeces() {
		return this.dateDeces;
	}

	public void setDateDeces(Date dateDeces) {
		this.dateDeces = dateDeces;
	}

	@Column(name = "cause_deces")
	public String getCauseDeces() {
		return this.causeDeces;
	}

	public void setCauseDeces(String causeDeces) {
		this.causeDeces = causeDeces;
	}
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cause_deces")
	public ChuCauseDeces getChuCauseDeces() {
		return this.chuCauseDeces;
	}

	public void setChuCauseDeces(ChuCauseDeces chuCauseDeces) {
		this.chuCauseDeces = chuCauseDeces;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_evolution", length = 13)
	public Date getDateEvolution() {
		return this.dateEvolution;
	}

	public void setDateEvolution(Date dateEvolution) {
		this.dateEvolution = dateEvolution;
	}

	@Column(name = "remarque")
	public String getRemarque() {
		return this.remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuPatient")
	public List<ChuAntecedentGeneral> getChuAntecedentGenerals() {
		return this.chuAntecedentGenerals;
	}

	public void setChuAntecedentGenerals(List<ChuAntecedentGeneral> chuAntecedentGenerals) {
		this.chuAntecedentGenerals = chuAntecedentGenerals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuPatient")
	public List<ChuAdresse> getChuAdresses() {
		return this.chuAdresses;
	}

	public void setChuAdresses(List<ChuAdresse> chuAdresses) {
		this.chuAdresses = chuAdresses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuPatient")
	public List<ChuTumeur> getChuTumeurs() {
		return this.chuTumeurs;
	}

	public void setChuTumeurs(List<ChuTumeur> chuTumeurs) {
		this.chuTumeurs = chuTumeurs;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "chu_tumeur_deces", joinColumns = {
			@JoinColumn(name = "id_patient", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_tumeur", nullable = false, updatable = false) })
	public List<ChuTumeur> getChuTumeursCauseDeces() {
		return this.chuTumeursCauseDeces;
	}

	public void setChuTumeursCauseDeces(List<ChuTumeur> chuTumeursCauseDeces) {
		this.chuTumeursCauseDeces = chuTumeursCauseDeces;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPatient == null) ? 0 : idPatient.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChuPatient other = (ChuPatient) obj;
		if (idPatient == null) {
			if (other.idPatient != null)
				return false;
		} else if (!idPatient.equals(other.idPatient))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuPatient [idPatient=" + idPatient + ", tk=" + tk + ", rcp=" + rcp + ", nom=" + nom + ", prenom="
				+ prenom + ", dateNaissance=" + dateNaissance + ", sexe=" + sexe + ", statutBrca=" + statutBrca
				+ ", dateDeces=" + dateDeces + ", causeDeces=" + causeDeces + "]";
	}
}
