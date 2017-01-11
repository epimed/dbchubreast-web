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

import dbchubreast_web.entity.ChuTumeur;


@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuTumeurDao extends BaseDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** =================================================*/
	
	public ChuTumeur find(Integer idTumeur) {

		return (ChuTumeur) sessionFactory.getCurrentSession()
				.createCriteria(ChuTumeur.class)	
				.add(Restrictions.eq("idTumeur", idTumeur))
				.uniqueResult();
	}
	
	/** =================================================*/

	public List<ChuTumeur> find(String idPatient) {

		return sessionFactory.getCurrentSession()
				.createCriteria(ChuTumeur.class)
				.createAlias("chuPatient", "chuPatient")
				.add(Restrictions.eq("chuPatient.idPatient", idPatient))
				.addOrder(Order.asc("idTumeur"))
				.list();
	}
	
	/** =================================================*/
}
