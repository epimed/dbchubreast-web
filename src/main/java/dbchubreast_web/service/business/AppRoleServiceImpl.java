/**
 * EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * 
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package dbchubreast_web.service.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.AppRoleDao;
import dbchubreast_web.entity.AppRole;

@Service
public class AppRoleServiceImpl implements AppRoleService {

	@Autowired
	AppRoleDao roleDao;

	@Override
	public AppRole findById(String idRole) {
		return roleDao.findById(idRole);
	}

	@Override
	public List<AppRole> list() {
		return roleDao.list();
	}
	


}
