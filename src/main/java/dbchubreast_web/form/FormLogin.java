package dbchubreast_web.form;

import javax.validation.constraints.NotNull;

import dbchubreast_web.service.BaseService;

public class FormLogin extends BaseService implements IForm {

	@NotNull(message = "Ne peut pas être vide")
	private String username;

	@NotNull(message = "Ne peut pas être vide")
	private String password;

	public FormLogin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	@Override
	public String toString() {
		return "FormLogin [username=" + username + ", password=" + password + "]";
	}
	
	
	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

}
