package dbchubreast_web.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "app_user", schema = "db_chu_breast")
public class AppUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idUser;
	private String firstName;
	private String lastName;
	private String email;
	private String login;
	private String password;
	private boolean enabled;
	private List<AppRole> appRoles = new ArrayList<AppRole>(0);

	public AppUser() {
	}

	public AppUser(Integer idUser, String firstName, String lastName, String email, String login, String password, boolean enabled) {
		this.idUser = idUser;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.login = login;
		this.password = password;
		this.enabled = enabled;
	}

	public AppUser(Integer idUser, String firstName, String lastName, String email, String login, String password,
			boolean enabled, List<AppRole> appRoles) {
		this.idUser = idUser;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.login = login;
		this.password = password;
		this.enabled = enabled;
		this.appRoles = appRoles;
	}

	@Id

	@Column(name = "id_user", unique = true, nullable = false)
	public Integer getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "email", nullable = false, length = 500)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "login", nullable = false, length = 500)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "password", nullable = false, length = 500)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "app_user_role", schema = "db_chu_breast", joinColumns = {
			@JoinColumn(name = "id_user", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_role", nullable = false, updatable = false) })
	public List<AppRole> getAppRoles() {
		return this.appRoles;
	}

	public void setAppRoles(List<AppRole> appRoles) {
		this.appRoles = appRoles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
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
		AppUser other = (AppUser) obj;
		if (idUser == null) {
			if (other.idUser != null)
				return false;
		} else if (!idUser.equals(other.idUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AppUser [idUser=" + idUser + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", login=" + login + ", password=" + password + ", enabled=" + enabled + "]";
	}



	
	
}
