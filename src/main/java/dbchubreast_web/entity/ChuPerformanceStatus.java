package dbchubreast_web.entity;
// Generated 20 d�c. 2016 16:19:18 by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ChuPerformanceStatus generated by hbm2java
 */
@Entity
@Table(name = "chu_performance_status", schema = "db_chu_breast")
public class ChuPerformanceStatus implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idPs;
	private String description;
	private List<ChuPhaseTumeur> chuPhaseTumeurs = new ArrayList<ChuPhaseTumeur>(0);

	public ChuPerformanceStatus() {
	}

	public ChuPerformanceStatus(Integer idPs, String description) {
		this.idPs = idPs;
		this.description = description;
	}

	public ChuPerformanceStatus(Integer idPs, String description, List<ChuPhaseTumeur> chuPhaseTumeurs) {
		this.idPs = idPs;
		this.description = description;
		this.chuPhaseTumeurs = chuPhaseTumeurs;
	}

	@Id

	@Column(name = "id_ps", unique = true, nullable = false)
	public Integer getIdPs() {
		return this.idPs;
	}

	public void setIdPs(Integer idPs) {
		this.idPs = idPs;
	}

	@Column(name = "description", nullable = false, length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chuPerformanceStatus")
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
		result = prime * result + ((idPs == null) ? 0 : idPs.hashCode());
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
		ChuPerformanceStatus other = (ChuPerformanceStatus) obj;
		if (idPs == null) {
			if (other.idPs != null)
				return false;
		} else if (!idPs.equals(other.idPs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChuPerformanceStatus [idPs=" + idPs + ", description=" + description + "]";
	}

}
