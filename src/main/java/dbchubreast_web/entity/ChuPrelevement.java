package dbchubreast_web.entity;
// Generated 21 d�c. 2016 10:07:28 by Hibernate Tools 4.3.1.Final

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * ChuPrelevement generated by hbm2java
 */
@Entity
@Table(name = "chu_prelevement", schema = "db_chu_breast")
public class ChuPrelevement implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idPrelevement;
	private ChuMorphologie chuMorphologie;
	private ChuPhaseTumeur chuPhaseTumeur;
	private ChuTypePrelevement chuTypePrelevement;
	private String typeHistologique;
	private Boolean associationCis;
	private Date datePrelevement;
	private String sitePrelevement;
	private List<ChuPrelevementBiomarqueur> chuPrelevementBiomarqueurs = new ArrayList<ChuPrelevementBiomarqueur>(0);
	private List<ChuRessourceBiologique> chuRessourceBiologiques = new ArrayList<ChuRessourceBiologique>(0);

	public ChuPrelevement() {
	}

	public ChuPrelevement(Integer idPrelevement, ChuPhaseTumeur chuPhaseTumeur, ChuTypePrelevement chuTypePrelevement) {
		this.idPrelevement = idPrelevement;
		this.chuPhaseTumeur = chuPhaseTumeur;
		this.chuTypePrelevement = chuTypePrelevement;
	}

	public ChuPrelevement(Integer idPrelevement, ChuMorphologie chuMorphologie, ChuPhaseTumeur chuPhaseTumeur,
			ChuTypePrelevement chuTypePrelevement, String typeHistologique, Boolean associationCis,
			Date datePrelevement, String sitePrelevement, List<ChuPrelevementBiomarqueur> chuPrelevementBiomarqueurs,
			List<ChuRessourceBiologique> chuRessourceBiologiques) {
		this.idPrelevement = idPrelevement;
		this.chuMorphologie = chuMorphologie;
		this.chuPhaseTumeur = chuPhaseTumeur;
		this.chuTypePrelevement = chuTypePrelevement;
		this.typeHistologique = typeHistologique;
		this.associationCis = associationCis;
		this.datePrelevement = datePrelevement;
		this.sitePrelevement = sitePrelevement;
		this.chuPrelevementBiomarqueurs = chuPrelevementBiomarqueurs;
		this.chuRessourceBiologiques = chuRessourceBiologiques;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id_prelevement", unique = true, nullable = false)
	public Integer getIdPrelevement() {
		return this.idPrelevement;
	}

	public void setIdPrelevement(Integer idPrelevement) {
		this.idPrelevement = idPrelevement;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_morphologie")
	public ChuMorphologie getChuMorphologie() {
		return this.chuMorphologie;
	}

	public void setChuMorphologie(ChuMorphologie chuMorphologie) {
		this.chuMorphologie = chuMorphologie;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_phase", nullable = false)
	public ChuPhaseTumeur getChuPhaseTumeur() {
		return this.chuPhaseTumeur;
	}

	public void setChuPhaseTumeur(ChuPhaseTumeur chuPhaseTumeur) {
		this.chuPhaseTumeur = chuPhaseTumeur;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_prelevement", nullable = false)
	public ChuTypePrelevement getChuTypePrelevement() {
		return this.chuTypePrelevement;
	}

	public void setChuTypePrelevement(ChuTypePrelevement chuTypePrelevement) {
		this.chuTypePrelevement = chuTypePrelevement;
	}

	@Column(name = "type_histologique", length = 500)
	public String getTypeHistologique() {
		return this.typeHistologique;
	}

	public void setTypeHistologique(String typeHistologique) {
		this.typeHistologique = typeHistologique;
	}

	@Column(name = "association_cis")
	public Boolean getAssociationCis() {
		return this.associationCis;
	}

	public void setAssociationCis(Boolean associationCis) {
		this.associationCis = associationCis;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_prelevement", length = 13)
	public Date getDatePrelevement() {
		return this.datePrelevement;
	}

	public void setDatePrelevement(Date datePrelevement) {
		this.datePrelevement = datePrelevement;
	}

	@Column(name = "site_prelevement")
	public String getSitePrelevement() {
		return this.sitePrelevement;
	}

	public void setSitePrelevement(String sitePrelevement) {
		this.sitePrelevement = sitePrelevement;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuPrelevement")
	public List<ChuPrelevementBiomarqueur> getChuPrelevementBiomarqueurs() {
		return this.chuPrelevementBiomarqueurs;
	}

	public void setChuPrelevementBiomarqueurs(List<ChuPrelevementBiomarqueur> chuPrelevementBiomarqueurs) {
		this.chuPrelevementBiomarqueurs = chuPrelevementBiomarqueurs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuPrelevement")
	public List<ChuRessourceBiologique> getChuRessourceBiologiques() {
		return this.chuRessourceBiologiques;
	}

	public void setChuRessourceBiologiques(List<ChuRessourceBiologique> chuRessourceBiologiques) {
		this.chuRessourceBiologiques = chuRessourceBiologiques;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPrelevement == null) ? 0 : idPrelevement.hashCode());
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
		ChuPrelevement other = (ChuPrelevement) obj;
		if (idPrelevement == null) {
			if (other.idPrelevement != null)
				return false;
		} else if (!idPrelevement.equals(other.idPrelevement))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuPrelevement [idPrelevement=" + idPrelevement + ", typeHistologique=" + typeHistologique
				+ ", associationCis=" + associationCis + ", datePrelevement=" + datePrelevement + ", sitePrelevement="
				+ sitePrelevement + "]";
	}


}
