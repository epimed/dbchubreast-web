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

import dbchubreast_web.entity.ChuMorphologie;

@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuMorphologieDaoImpl extends BaseDao implements ChuMorphologieDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuMorphologie find(String idMorphologie) {
		ChuMorphologie result = (ChuMorphologie) sessionFactory.getCurrentSession().createCriteria(ChuMorphologie.class)
				.add(Restrictions.eq("idMorphologie", idMorphologie)).uniqueResult();
		return result;
	}

	/** ================================================= */
	public List<ChuMorphologie> list() {
		List<ChuMorphologie> result = sessionFactory.getCurrentSession().createCriteria(ChuMorphologie.class)
				.addOrder(Order.asc("idMorphologie")).list();
		return result;
	}

	/** ================================================= */
}
