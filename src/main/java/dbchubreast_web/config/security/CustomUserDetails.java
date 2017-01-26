package dbchubreast_web.config.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dbchubreast_web.entity.AppMenu;
import dbchubreast_web.entity.AppRole;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	// === Mandatory === 

	private String password;
	private String username;
	private List<GrantedAuthority> authorities;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled;

	// === Custom ===

	private String firstName;
	private String lastName;
	private String email;
	private List<AppMenu> listMenus;
	private List<AppRole> listRoles;


	/** ==================================================== */


	public CustomUserDetails() {
		super();
	}
	
	public CustomUserDetails(String username, String password, boolean enabled, String firstName, String lastName,
			String email) {
		super();
		this.password = password;
		this.username = username;
		this.enabled = enabled;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}


	public CustomUserDetails(String password, String username, List<GrantedAuthority> authorities, boolean enabled,
			String firstName, String lastName, String email, List<AppMenu> listMenus, List<AppRole> listRoles) {
		super();
		this.password = password;
		this.username = username;
		this.authorities = authorities;
		this.enabled = enabled;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.listMenus = listMenus;
		this.listRoles = listRoles;
	}



	/** ==================================================== */

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AppMenu> getListMenus() {
		return listMenus;
	}
	public void setListMenus(List<AppMenu> listMenus) {
		this.listMenus = listMenus;
	}
	public List<AppRole> getListRoles() {
		return listRoles;
	}
	public void setListRoles(List<AppRole> listRoles) {
		this.listRoles = listRoles;
	}

	@Override
	public String toString() {
		return "CustomUserDetails [password=" + password + ", username=" + username + ", authorities=" + authorities
				+ ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", listMenus=" + listMenus
				+ ", listRoles=" + listRoles + "]";
	}

}
