package dbchubreast_web.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "chu_morphologie", schema = "db_chu_breast")
public class ChuMorphologie implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idMorphologie;
	private ChuGroupeMorphologie chuGroupeMorphologie;
	private String nomFr;
	private String nomEn;
	private List<ChuPrelevement> chuPrelevements = new ArrayList<ChuPrelevement>(0);

	public ChuMorphologie() {
	}

	public ChuMorphologie(String idMorphologie) {
		this.idMorphologie = idMorphologie;
	}

	public ChuMorphologie(String idMorphologie, ChuGroupeMorphologie chuGroupeMorphologie, String nomFr, String nomEn,
			List<ChuPrelevement> chuPrelevements) {
		this.idMorphologie = idMorphologie;
		this.chuGroupeMorphologie = chuGroupeMorphologie;
		this.nomFr = nomFr;
		this.nomEn = nomEn;
		this.chuPrelevements = chuPrelevements;
	}

	@Id

	@Column(name = "id_morphologie", unique = true, nullable = false, length = 20)
	public String getIdMorphologie() {
		return this.idMorphologie;
	}

	public void setIdMorphologie(String idMorphologie) {
		this.idMorphologie = idMorphologie;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_groupe_morpho")
	public ChuGroupeMorphologie getChuGroupeMorphologie() {
		return this.chuGroupeMorphologie;
	}

	public void setChuGroupeMorphologie(ChuGroupeMorphologie chuGroupeMorphologie) {
		this.chuGroupeMorphologie = chuGroupeMorphologie;
	}

	@Column(name = "nom_fr")
	public String getNomFr() {
		return this.nomFr;
	}

	public void setNomFr(String nomFr) {
		this.nomFr = nomFr;
	}

	@Column(name = "nom_en")
	public String getNomEn() {
		return this.nomEn;
	}

	public void setNomEn(String nomEn) {
		this.nomEn = nomEn;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuMorphologie")
	public List<ChuPrelevement> getChuPrelevements() {
		return this.chuPrelevements;
	}

	public void setChuPrelevements(List<ChuPrelevement> chuPrelevements) {
		this.chuPrelevements = chuPrelevements;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMorphologie == null) ? 0 : idMorphologie.hashCode());
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
		ChuMorphologie other = (ChuMorphologie) obj;
		if (idMorphologie == null) {
			if (other.idMorphologie != null)
				return false;
		} else if (!idMorphologie.equals(other.idMorphologie))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuMorphologie [idMorphologie=" + idMorphologie + ", nomFr=" + nomFr + ", nomEn=" + nomEn + "]";
	}
	
	

}
