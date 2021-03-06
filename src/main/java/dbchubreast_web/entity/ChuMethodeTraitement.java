package dbchubreast_web.entity;
// Generated 12 mai 2017 13:31:51 by Hibernate Tools 4.3.5.Final

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * ChuMethodeTraitement generated by hbm2java
 */
@Entity
@Table(name = "chu_methode_traitement", schema = "db_chu_breast")
public class ChuMethodeTraitement implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idMethode;
	private String nom;
	private List<ChuProtocoleTraitement> chuProtocoleTraitements = new ArrayList<ChuProtocoleTraitement>(0);
	private List<ChuTraitement> chuTraitements = new ArrayList<ChuTraitement>(0);
	private List<ChuComposantTraitement> chuComposantTraitements = new ArrayList<ChuComposantTraitement>(0);

	public ChuMethodeTraitement() {
	}

	public ChuMethodeTraitement(Integer idMethode, String nom) {
		this.idMethode = idMethode;
		this.nom = nom;
	}

	public ChuMethodeTraitement(Integer idMethode, String nom, List<ChuProtocoleTraitement> chuProtocoleTraitements,
			List<ChuTraitement> chuTraitements, List<ChuComposantTraitement> chuComposantTraitements) {
		this.idMethode = idMethode;
		this.nom = nom;
		this.chuProtocoleTraitements = chuProtocoleTraitements;
		this.chuTraitements = chuTraitements;
		this.chuComposantTraitements = chuComposantTraitements;
	}

	@Id

	@Column(name = "id_methode", unique = true, nullable = false)
	public Integer getIdMethode() {
		return this.idMethode;
	}

	public void setIdMethode(Integer idMethode) {
		this.idMethode = idMethode;
	}

	@Column(name = "nom", nullable = false)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuMethodeTraitement")
	@OrderBy("code")
	public List<ChuProtocoleTraitement> getChuProtocoleTraitements() {
		return this.chuProtocoleTraitements;
	}

	public void setChuProtocoleTraitements(List<ChuProtocoleTraitement> chuProtocoleTraitements) {
		this.chuProtocoleTraitements = chuProtocoleTraitements;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuMethodeTraitement")
	public List<ChuTraitement> getChuTraitements() {
		return this.chuTraitements;
	}

	public void setChuTraitements(List<ChuTraitement> chuTraitements) {
		this.chuTraitements = chuTraitements;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuMethodeTraitement")
	@OrderBy("nomInternational")
	public List<ChuComposantTraitement> getChuComposantTraitements() {
		return this.chuComposantTraitements;
	}

	public void setChuComposantTraitements(List<ChuComposantTraitement> chuComposantTraitements) {
		this.chuComposantTraitements = chuComposantTraitements;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMethode == null) ? 0 : idMethode.hashCode());
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
		ChuMethodeTraitement other = (ChuMethodeTraitement) obj;
		if (idMethode == null) {
			if (other.idMethode != null)
				return false;
		} else if (!idMethode.equals(other.idMethode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuMethodeTraitement [idMethode=" + idMethode + ", nom=" + nom + "]";
	}
	
	

}
