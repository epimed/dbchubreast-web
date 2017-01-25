package dbchubreast_web.dao;

import dbchubreast_web.entity.AppUser;

public interface AppUserDao {

	public AppUser findByUsername(String username);
	
}
