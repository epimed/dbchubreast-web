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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.AppUserDao;
import dbchubreast_web.entity.AppUser;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	AppUserDao userDao;
	

	public AppUser findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
