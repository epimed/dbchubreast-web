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
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuTopographie;

@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuTopographieDaoImpl extends BaseDao implements ChuTopographieDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuTopographie find(String idTopographie) {
		ChuTopographie result = (ChuTopographie) sessionFactory.getCurrentSession().createCriteria(ChuTopographie.class)
				.add(Restrictions.eq("idTopographie", idTopographie)).uniqueResult();
		return result;
	}

	/** ================================================= */

	public List<ChuTopographie> list(String idGroupeTopo) {

		List<ChuTopographie> result = sessionFactory.getCurrentSession().createCriteria(ChuTopographie.class)
				.createAlias("chuGroupeTopographie", "chuGroupeTopographie")
				.add(Restrictions.eq("chuGroupeTopographie.idGroupeTopo", idGroupeTopo))
				.addOrder(Order.asc("idTopographie")).list();

		return result;
	}

	/** ================================================= */

	public List<ChuTopographie> list() {

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(ChuTopographie.class)
				.addOrder(Order.asc("idTopographie"));

		return crit.list();
	}

	/** ================================================= */
}
