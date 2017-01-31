package dbchubreast_web.dao;

import java.util.List;

import dbchubreast_web.entity.AppRole;

public interface AppRoleDao {

	public AppRole findById(String idRole);
	public List<AppRole> list();
	
}
