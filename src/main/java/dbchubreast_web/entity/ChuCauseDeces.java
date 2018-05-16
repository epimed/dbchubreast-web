package dbchubreast_web.entity;
// Generated 16 mai 2018 12:50:32 by Hibernate Tools 4.3.5.Final

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ChuCauseDeces generated by hbm2java
 */
@Entity
@Table(name = "chu_cause_deces", schema = "db_chu_breast")
public class ChuCauseDeces implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCauseDeces;
	private String nom;
	private List<ChuPatient> chuPatients = new ArrayList<ChuPatient>();

	public ChuCauseDeces() {
	}

	public ChuCauseDeces(Integer idCauseDeces, String nom) {
		this.idCauseDeces = idCauseDeces;
		this.nom = nom;
	}

	public ChuCauseDeces(Integer idCauseDeces, String nom, List<ChuPatient> chuPatients) {
		this.idCauseDeces = idCauseDeces;
		this.nom = nom;
		this.chuPatients = chuPatients;
	}

	@Id

	@Column(name = "id_cause_deces", unique = true, nullable = false)
	public Integer getIdCauseDeces() {
		return this.idCauseDeces;
	}

	public void setIdCauseDeces(Integer idCauseDeces) {
		this.idCauseDeces = idCauseDeces;
	}

	@Column(name = "nom", nullable = false)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuCauseDeces")
	public List<ChuPatient> getChuPatients() {
		return this.chuPatients;
	}

	public void setChuPatients(List<ChuPatient> chuPatients) {
		this.chuPatients = chuPatients;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCauseDeces == null) ? 0 : idCauseDeces.hashCode());
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
		ChuCauseDeces other = (ChuCauseDeces) obj;
		if (idCauseDeces == null) {
			if (other.idCauseDeces != null)
				return false;
		} else if (!idCauseDeces.equals(other.idCauseDeces))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuCauseDeces [idCauseDeces=" + idCauseDeces + ", nom=" + nom + "]";
	}
	
	

}
