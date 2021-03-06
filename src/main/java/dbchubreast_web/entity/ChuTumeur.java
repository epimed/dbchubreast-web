package dbchubreast_web.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "chu_tumeur", schema = "db_chu_breast")
public class ChuTumeur implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idTumeur;
	private ChuEvolution chuEvolution;
	private ChuPatient chuPatient;
	private String cote;
	private Date dateDiagnostic;
	private Double ageDiagnostic;
	private Double imcDiagnostic;
	private Boolean tripleNegative;
	private Double dfsMonths;
	private Double osMonths;
	private Boolean relapsed;
	private Boolean dead;
	private Boolean consentement;
	private String categorie;
	private List<ChuPhaseTumeur> chuPhaseTumeurs = new ArrayList<ChuPhaseTumeur>(0);

	public ChuTumeur() {
	}

	public ChuTumeur(Integer idTumeur, ChuPatient chuPatient) {
		this.idTumeur = idTumeur;
		this.chuPatient = chuPatient;
	}

	public ChuTumeur(Integer idTumeur, ChuEvolution chuEvolution, ChuPatient chuPatient, 
			String cote, Date dateDiagnostic, Double ageDiagnostic, Double imcDiagnostic,
			Boolean tripleNegative, Double dfsMonths, Double osMonths, Boolean relapsed, Boolean dead,
			List<ChuPhaseTumeur> chuPhaseTumeurs) {
		this.idTumeur = idTumeur;
		this.chuEvolution = chuEvolution;
		this.chuPatient = chuPatient;
		this.cote = cote;
		this.dateDiagnostic = dateDiagnostic;
		this.ageDiagnostic = ageDiagnostic;
		this.imcDiagnostic = imcDiagnostic;
		this.tripleNegative = tripleNegative;
		this.dfsMonths = dfsMonths;
		this.osMonths = osMonths;
		this.relapsed = relapsed;
		this.dead = dead;
		this.chuPhaseTumeurs = chuPhaseTumeurs;
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "id_tumeur", unique = true, nullable = false)
	public Integer getIdTumeur() {
		return this.idTumeur;
	}

	public void setIdTumeur(Integer idTumeur) {
		this.idTumeur = idTumeur;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_evolution")
	public ChuEvolution getChuEvolution() {
		return this.chuEvolution;
	}

	public void setChuEvolution(ChuEvolution chuEvolution) {
		this.chuEvolution = chuEvolution;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_patient", nullable = false)
	public ChuPatient getChuPatient() {
		return this.chuPatient;
	}

	public void setChuPatient(ChuPatient chuPatient) {
		this.chuPatient = chuPatient;
	}

	@Column(name = "cote", length = 100)
	public String getCote() {
		return this.cote;
	}

	public void setCote(String cote) {
		this.cote = cote;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_diagnostic", length = 13)
	public Date getDateDiagnostic() {
		return this.dateDiagnostic;
	}

	public void setDateDiagnostic(Date dateDiagnostic) {
		this.dateDiagnostic = dateDiagnostic;
	}

	@Column(name = "age_diagnostic", precision = 17, scale = 17)
	public Double getAgeDiagnostic() {
		return this.ageDiagnostic;
	}

	public void setAgeDiagnostic(Double ageDiagnostic) {
		this.ageDiagnostic = ageDiagnostic;
	}

	@Column(name = "imc_diagnostic", precision = 17, scale = 17)
	public Double getImcDiagnostic() {
		return this.imcDiagnostic;
	}

	public void setImcDiagnostic(Double imcDiagnostic) {
		this.imcDiagnostic = imcDiagnostic;
	}

	@Column(name = "triple_negative")
	public Boolean getTripleNegative() {
		return this.tripleNegative;
	}

	public void setTripleNegative(Boolean tripleNegative) {
		this.tripleNegative = tripleNegative;
	}

	@Column(name = "dfs_months", precision = 17, scale = 17)
	public Double getDfsMonths() {
		return this.dfsMonths;
	}

	public void setDfsMonths(Double dfsMonths) {
		this.dfsMonths = dfsMonths;
	}

	@Column(name = "os_months", precision = 17, scale = 17)
	public Double getOsMonths() {
		return this.osMonths;
	}

	public void setOsMonths(Double osMonths) {
		this.osMonths = osMonths;
	}

	@Column(name = "relapsed")
	public Boolean getRelapsed() {
		return this.relapsed;
	}

	public void setRelapsed(Boolean relapsed) {
		this.relapsed = relapsed;
	}

	@Column(name = "dead")
	public Boolean getDead() {
		return this.dead;
	}

	public void setDead(Boolean dead) {
		this.dead = dead;
	}

	@Column(name = "consentement")
	public Boolean getConsentement() {
		return this.consentement;
	}

	public void setConsentement(Boolean consentement) {
		this.consentement = consentement;
	}
	
	@Column(name = "categorie", length = 100)
	public String getCategorie() {
		return this.categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuTumeur")
	@OrderBy("idPhase")
	public List<ChuPhaseTumeur> getChuPhaseTumeurs() {
		return this.chuPhaseTumeurs;
	}

	public void setChuPhaseTumeurs(List<ChuPhaseTumeur> chuPhaseTumeurs) {
		this.chuPhaseTumeurs = chuPhaseTumeurs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTumeur == null) ? 0 : idTumeur.hashCode());
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
		ChuTumeur other = (ChuTumeur) obj;
		if (idTumeur == null) {
			if (other.idTumeur != null)
				return false;
		} else if (!idTumeur.equals(other.idTumeur))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuTumeur [idTumeur=" + idTumeur + ", cote=" + cote + ", dateDiagnostic=" + dateDiagnostic
				+ ", ageDiagnostic=" + ageDiagnostic  + ", tripleNegative="
				+ tripleNegative + ", dfsMonths=" + dfsMonths + ", osMonths=" + osMonths + ", relapsed=" + relapsed
				+ ", dead=" + dead + ", consentement=" + consentement +  ", categorie=" + categorie + "]";
	}
	
	

}
