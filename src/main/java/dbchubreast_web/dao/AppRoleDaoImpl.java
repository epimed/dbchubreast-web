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

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.AppRole;


@Transactional
@Repository
@SuppressWarnings("unchecked")
public class AppRoleDaoImpl extends BaseDao implements AppRoleDao {


	@Autowired
	private SessionFactory sessionFactory;


	/** ================================================= */

	public AppRole findById(String idRole) {
		AppRole role = (AppRole) sessionFactory.getCurrentSession()
				.createCriteria(AppRole.class)
				.add(Restrictions.eq("idRole", idRole))
				.uniqueResult();

		return role;

	}

	/** ================================================= */

	public List<AppRole> list(){

		List<AppRole> list = sessionFactory.getCurrentSession()
				.createCriteria(AppRole.class)
				.addOrder(Order.asc("idRole"))
				.list();

		return list;
	}

	/** ================================================= */
}
