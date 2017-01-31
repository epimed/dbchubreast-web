package dbchubreast_web.dao;

import java.util.List;

import dbchubreast_web.entity.AppUser;

public interface AppUserDao {

	public AppUser findByUsername(String username);
	public AppUser findById(Integer idUser);
	public List<AppUser> list();
	public void save(AppUser user);
	public void update(AppUser user);
	public void delete(AppUser user);
}
