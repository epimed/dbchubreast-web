/**
 * EpiMed - Information system for bioinformatics developments in the field of epiOmGenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * GNU OmGeneRAL PUBLIC LICENSE
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */

package dbchubreast_web.dao;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.AppUser;


@Transactional
@Repository

public class AppUserDaoImpl extends BaseDao implements AppUserDao {


	@Autowired
	private SessionFactory sessionFactory;
	
	/** ================================================= */

	public AppUser findByUsername(String username) {

		Criteria crit = sessionFactory.getCurrentSession()
				.createCriteria(AppUser.class);
		
		crit.add(Restrictions.or(
				Restrictions.eq("login", username),
				Restrictions.eq("email", username)
				));
		
		AppUser user = (AppUser) crit.uniqueResult();
		if(user!=null){
			Hibernate.initialize(user.getAppRoles());
		}
		return user;
	}

	/** ================================================= */

}
