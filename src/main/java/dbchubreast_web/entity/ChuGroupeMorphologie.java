package dbchubreast_web.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "chu_groupe_morphologie", schema = "db_chu_breast")
public class ChuGroupeMorphologie implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idGroupeMorpho;
	private String nom;
	private List<ChuMorphologie> chuMorphologies = new ArrayList<ChuMorphologie>(0);

	public ChuGroupeMorphologie() {
	}

	public ChuGroupeMorphologie(String idGroupeMorpho, String nom) {
		this.idGroupeMorpho = idGroupeMorpho;
		this.nom = nom;
	}

	public ChuGroupeMorphologie(String idGroupeMorpho, String nom, List<ChuMorphologie> chuMorphologies) {
		this.idGroupeMorpho = idGroupeMorpho;
		this.nom = nom;
		this.chuMorphologies = chuMorphologies;
	}

	@Id

	@Column(name = "id_groupe_morpho", unique = true, nullable = false, length = 20)
	public String getIdGroupeMorpho() {
		return this.idGroupeMorpho;
	}

	public void setIdGroupeMorpho(String idGroupeMorpho) {
		this.idGroupeMorpho = idGroupeMorpho;
	}

	@Column(name = "nom", nullable = false)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuGroupeMorphologie")
	public List<ChuMorphologie> getChuMorphologies() {
		return this.chuMorphologies;
	}

	public void setChuMorphologies(List<ChuMorphologie> chuMorphologies) {
		this.chuMorphologies = chuMorphologies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGroupeMorpho == null) ? 0 : idGroupeMorpho.hashCode());
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
		ChuGroupeMorphologie other = (ChuGroupeMorphologie) obj;
		if (idGroupeMorpho == null) {
			if (other.idGroupeMorpho != null)
				return false;
		} else if (!idGroupeMorpho.equals(other.idGroupeMorpho))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuGroupeMorphologie [idGroupeMorpho=" + idGroupeMorpho + ", nom=" + nom + "]";
	}
	
	
}
