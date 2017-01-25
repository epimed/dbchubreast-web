package dbchubreast_web.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "app_role", schema = "db_chu_breast")
public class AppRole implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idRole;
	private String name;
	private List<AppUser> appUsers = new ArrayList<AppUser>(0);

	public AppRole() {
	}

	public AppRole(Integer idRole, String name) {
		this.idRole = idRole;
		this.name = name;
	}

	public AppRole(Integer idRole, String name, List<AppUser> appUsers) {
		this.idRole = idRole;
		this.name = name;
		this.appUsers = appUsers;
	}

	@Id

	@Column(name = "id_role", unique = true, nullable = false)
	public Integer getIdRole() {
		return this.idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "appRoles")
	public List<AppUser> getAppUsers() {
		return this.appUsers;
	}

	public void setAppUsers(List<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRole == null) ? 0 : idRole.hashCode());
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
		AppRole other = (AppRole) obj;
		if (idRole == null) {
			if (other.idRole != null)
				return false;
		} else if (!idRole.equals(other.idRole))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppRole [idRole=" + idRole + ", name=" + name + "]";
	}
	
	

}
