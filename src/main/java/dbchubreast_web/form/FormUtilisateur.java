package dbchubreast_web.form;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import dbchubreast_web.service.BaseService;

public class FormUtilisateur extends BaseService implements IForm {

	private Integer idUser;

	@NotNull(message = "Ne peut pas être vide")
	private String lastName;
	
	@NotNull(message = "Ne peut pas être vide")
	private String firstName;

	@NotNull(message = "Ne peut pas être vide")
	private String email;

	@NotNull(message = "Ne peut pas être vide")
	private String login;
	
	@Length(min = 8, message = "8 caractères au moins")
	private String password;
	
	@NotNull(message = "Ne peut pas être vide")
	private boolean enabled = true;

	List<String> roles;

	public FormUtilisateur() {
		super();
	}




	@Override
	public String toString() {
		return "FormUtilisateur [idUser=" + idUser + ", lastName=" + lastName + ", firstName=" + firstName + ", email="
				+ email + ", login=" + login + ", password=" + password + ", enabled=" + enabled + ", roles=" + roles
				+ "]";
	}



	public Integer getIdUser() {
		return idUser;
	}




	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}




	public String getLastName() {
		return lastName;
	}




	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getLogin() {
		return login;
	}




	public void setLogin(String login) {
		this.login = login;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public boolean isEnabled() {
		return enabled;
	}




	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}




	public List<String> getRoles() {
		return roles;
	}




	public void setRoles(List<String> roles) {
		this.roles = roles;
	}




	/** ====================================================================================== */

	public boolean isNew() {
		return this.idUser == null;
	}

	/** ====================================================================================== */

}
