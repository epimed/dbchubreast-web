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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuEvolution;


@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuEvolutionDaoImpl extends BaseDao implements ChuEvolutionDao {

	@Autowired
	private SessionFactory sessionFactory;


	/** =================================================*/

	public List<ChuEvolution> list(){

		List<ChuEvolution> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuEvolution.class)
				.addOrder(Order.asc("idEvolution"))
				.list();

		return result;
	}

	/** =================================================*/
}
