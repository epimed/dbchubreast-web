package dbchubreast_web.entity;
// Generated 19 d�c. 2016 13:44:40 by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * ChuMetastase generated by hbm2java
 */
@Entity
@Table(name = "chu_metastase", schema = "db_chu_breast")
public class ChuMetastase implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idMetastase;
	private String nom;
	private List<ChuPhaseTumeur> chuPhaseTumeurs = new ArrayList<ChuPhaseTumeur>(0);

	public ChuMetastase() {
	}

	public ChuMetastase(Integer idMetastase, String nom) {
		this.idMetastase = idMetastase;
		this.nom = nom;
	}

	public ChuMetastase(Integer idMetastase, String nom, List<ChuPhaseTumeur> chuPhaseTumeurs) {
		this.idMetastase = idMetastase;
		this.nom = nom;
		this.chuPhaseTumeurs = chuPhaseTumeurs;
	}

	@Id

	@Column(name = "id_metastase", unique = true, nullable = false)
	public Integer getIdMetastase() {
		return this.idMetastase;
	}

	public void setIdMetastase(Integer idMetastase) {
		this.idMetastase = idMetastase;
	}

	@Column(name = "nom", nullable = false)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "chuMetastases")
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
		result = prime * result + ((idMetastase == null) ? 0 : idMetastase.hashCode());
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
		ChuMetastase other = (ChuMetastase) obj;
		if (idMetastase == null) {
			if (other.idMetastase != null)
				return false;
		} else if (!idMetastase.equals(other.idMetastase))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuMetastase [idMetastase=" + idMetastase + ", nom=" + nom + "]";
	}

}
