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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.AppUser;


@Transactional
@Repository
@SuppressWarnings("unchecked")
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

		crit.add(Restrictions.eq("enabled", true));

		AppUser user = (AppUser) crit.uniqueResult();
		if(user!=null){
			Hibernate.initialize(user.getAppRoles());
		}
		return user;
	}

	/** ================================================= */

	public AppUser findById(Integer idUser) {
		AppUser user = (AppUser) sessionFactory.getCurrentSession()
				.createCriteria(AppUser.class)
				.add(Restrictions.eq("idUser", idUser))
				.uniqueResult();

		if(user!=null){
			Hibernate.initialize(user.getAppRoles());
		}
		return user;

	}

	/** ================================================= */

	public List<AppUser> list(){

		List<AppUser> list = sessionFactory.getCurrentSession()
				.createCriteria(AppUser.class)
				.addOrder(Order.asc("lastName"))
				.list();

		for (AppUser user : list) {
			Hibernate.initialize(user.getAppRoles());
		}

		return list;
	}

	/** ================================================= */

	public void save(AppUser user) {
		sessionFactory.getCurrentSession().save(user);
	}

	/** ================================================= */

	public void update(AppUser user) {
		sessionFactory.getCurrentSession().update(user);
	}

	/** ================================================= */
	
	public void delete(AppUser user) {
		
		user.getAppRoles().clear();
		sessionFactory.getCurrentSession().delete(user);
	}

	/** ================================================= */
}
