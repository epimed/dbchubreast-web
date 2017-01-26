package dbchubreast_web.dao;

import java.util.List;

import dbchubreast_web.entity.AppMenu;
import dbchubreast_web.entity.AppUser;

public interface AppMenuDao {

	public List<AppMenu> loadMenuForUser(AppUser user);
	
}
