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

import dbchubreast_web.entity.ChuMetastase;
;


@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuMetastaseDaoImpl extends BaseDao implements ChuMetastaseDao  {

	@Autowired
	private SessionFactory sessionFactory;


	/** =================================================*/

	public ChuMetastase find(Integer idMetastase) {
		ChuMetastase result = (ChuMetastase) sessionFactory.getCurrentSession()
				.createCriteria(ChuMetastase.class)
				.add(Restrictions.eq("idMetastase", idMetastase))
				.uniqueResult();
		return result;
	}


	/** =================================================*/

	public List<ChuMetastase> list(){

		List<ChuMetastase> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuMetastase.class)
				.addOrder(Order.asc("idMetastase"))
				.list();

		return result;
	}

	/** =================================================*/

	public List<ChuMetastase> list(List<Integer> listIdMetastases) {
		List<ChuMetastase> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuMetastase.class)
				.add(Restrictions.in("idMetastase", listIdMetastases))
				.addOrder(Order.asc("idMetastase"))
				.list();

		return result;
	}

	/** =================================================*/

	public List<ChuMetastase> list(Integer idPhaseTumeur) {
		List<ChuMetastase> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuMetastase.class)
				.createAlias("chuPhaseTumeurs", "chuPhaseTumeurs")
				.add(Restrictions.eq("chuPhaseTumeurs.idPhase", idPhaseTumeur))
				.addOrder(Order.asc("idMetastase"))
				.list();

		return result;
	}

	/** =================================================*/
}
