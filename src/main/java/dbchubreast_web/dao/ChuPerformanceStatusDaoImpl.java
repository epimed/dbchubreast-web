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

import dbchubreast_web.entity.ChuPerformanceStatus;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class ChuPerformanceStatusDaoImpl extends BaseDao implements ChuPerformanceStatusDao {

	@Autowired
	private SessionFactory sessionFactory;

	public ChuPerformanceStatus find(Integer idPs) {
		ChuPerformanceStatus result = (ChuPerformanceStatus) sessionFactory.getCurrentSession()
				.createCriteria(ChuPerformanceStatus.class).add(Restrictions.eq("idPs", idPs)).uniqueResult();
		return result;
	}

	public List<ChuPerformanceStatus> list() {
		List<ChuPerformanceStatus> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuPerformanceStatus.class).addOrder(Order.asc("idPs")).list();
		return result;
	}
}
