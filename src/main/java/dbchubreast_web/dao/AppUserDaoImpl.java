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

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
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
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<AppUser> criteria = builder.createQuery(AppUser.class);
			Root<AppUser> root = criteria.from(AppUser.class);
			root.fetch("appRoles");
			criteria.select(root).where(
					builder.and(
							builder.or(
									builder.equal(root.get("login"), username),
									builder.equal(root.get("email"), username)
									),
							builder.isTrue(root.get("enabled"))
							)
					);
			AppUser user = sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
			
			return user;

		}
		catch (NoResultException ex) {
			return null;
		}
	}

	/** ================================================= */

	public AppUser findById(Integer idUser) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<AppUser> criteria = builder.createQuery(AppUser.class);
			Root<AppUser> root = criteria.from(AppUser.class);
			root.fetch("appRoles");
			criteria.select(root).where(builder.equal(root.get("idUser"), idUser));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();

		}
		catch (NoResultException ex) {
			return null;
		}
		
	}

	/** ================================================= */

	public List<AppUser> list(){
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<AppUser> criteria = builder.createQuery(AppUser.class);
		Root<AppUser> root = criteria.from(AppUser.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idUser")));
		List<AppUser> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		
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
