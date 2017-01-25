package dbchubreast_web.entity;
// Generated 19 d�c. 2016 13:44:40 by Hibernate Tools 4.3.1.Final

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

import org.hibernate.annotations.GenericGenerator;

/**
 * ChuProtocoleTraitement generated by hbm2java
 */
@Entity
@Table(name = "chu_protocole_traitement", schema = "db_chu_breast")
public class ChuProtocoleTraitement implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idProtocole;
	private ChuMethodeTraitement chuMethodeTraitement;
	private String code;
	private String nom;
	private List<ChuTraitement> chuTraitements = new ArrayList<ChuTraitement>(0);

	public ChuProtocoleTraitement() {
	}

	public ChuProtocoleTraitement(Integer idProtocole, String nom) {
		this.idProtocole = idProtocole;
		this.nom = nom;
	}

	public ChuProtocoleTraitement(Integer idProtocole, ChuMethodeTraitement chuMethodeTraitement, String code,
			String nom, List<ChuTraitement> chuTraitements) {
		this.idProtocole = idProtocole;
		this.chuMethodeTraitement = chuMethodeTraitement;
		this.code = code;
		this.nom = nom;
		this.chuTraitements = chuTraitements;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id_protocole", unique = true, nullable = false)
	public Integer getIdProtocole() {
		return this.idProtocole;
	}

	public void setIdProtocole(Integer idProtocole) {
		this.idProtocole = idProtocole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_methode")
	public ChuMethodeTraitement getChuMethodeTraitement() {
		return this.chuMethodeTraitement;
	}

	public void setChuMethodeTraitement(ChuMethodeTraitement chuMethodeTraitement) {
		this.chuMethodeTraitement = chuMethodeTraitement;
	}

	@Column(name = "code", length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "nom", nullable = false, length = 500)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuProtocoleTraitement")
	public List<ChuTraitement> getChuTraitements() {
		return this.chuTraitements;
	}

	public void setChuTraitements(List<ChuTraitement> chuTraitements) {
		this.chuTraitements = chuTraitements;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProtocole == null) ? 0 : idProtocole.hashCode());
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
		ChuProtocoleTraitement other = (ChuProtocoleTraitement) obj;
		if (idProtocole == null) {
			if (other.idProtocole != null)
				return false;
		} else if (!idProtocole.equals(other.idProtocole))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuProtocoleTraitement [idProtocole=" + idProtocole + ", chuMethodeTraitement=" + chuMethodeTraitement
				+ ", code=" + code + ", nom=" + nom + "]";
	}

}
