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

import dbchubreast_web.entity.ChuBiomarqueur;

@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuBiomarqueurDaoImpl extends BaseDao implements ChuBiomarqueurDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuBiomarqueur find(String idBiomarqueur) {
		ChuBiomarqueur result = (ChuBiomarqueur) sessionFactory.getCurrentSession().createCriteria(ChuBiomarqueur.class)
				.add(Restrictions.eq("idBiomarqueur", idBiomarqueur)).uniqueResult();
		return result;
	}

	/** ================================================= */

	public List<ChuBiomarqueur> list() {
		List<ChuBiomarqueur> result = sessionFactory.getCurrentSession().createCriteria(ChuBiomarqueur.class)
				.addOrder(Order.asc("ordre")).list();
		return result;
	}

	/** ================================================= */

	public List<ChuBiomarqueur> list(Object[] noms) {

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(ChuBiomarqueur.class)
				.add(Restrictions.in("idBiomarqueur", noms)).addOrder(Order.asc("ordre"));

		return crit.list();
	}

	/** ================================================= */

}
