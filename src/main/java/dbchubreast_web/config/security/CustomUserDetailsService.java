package dbchubreast_web.config.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.AppRole;
import dbchubreast_web.entity.AppUser;
import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.business.AppMenuService;
import dbchubreast_web.service.business.AppUserService;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private AppUserService userService;
	
	@Autowired
	private AppMenuService menuService;
	
	@Autowired
	AppLogService logService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser user = userService.findByUsername(username);

		logService.logComment(username, "Login " + user);
		
		if (user==null) {
			logger.debug("User with username {} not found", username);
			throw new UsernameNotFoundException("Username not found");
		}
		
		CustomUserDetails userDetails = new CustomUserDetails(user.getLogin(), user.getPassword(), 
				user.isEnabled(), user.getFirstName(), user.getLastName(), user.getEmail());
		userDetails.setAuthorities(getGrantedAuthorities(user));
		userDetails.setListRoles(user.getAppRoles());
		userDetails.setListMenus(menuService.loadMenuForUser(user));
		
		return userDetails;
	}

	
	private List<GrantedAuthority> getGrantedAuthorities(AppUser user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(AppRole role : user.getAppRoles()){
			logger.info("User role : {}", role);
			authorities.add(new SimpleGrantedAuthority(role.getIdRole()));
		}
		logger.info("authorities : {}", authorities);
		return authorities;
	}
	
}
