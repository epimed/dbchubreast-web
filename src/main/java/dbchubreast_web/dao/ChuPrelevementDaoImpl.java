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
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuPrelevement;



@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuPrelevementDaoImpl extends BaseDao implements ChuPrelevementDao {

	@Autowired
	private SessionFactory sessionFactory;


	/** =================================================*/

	public List<ChuPrelevement> find(Integer idTumeur) {

		List<ChuPrelevement> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuPrelevement.class)
				.createAlias("chuPhaseTumeur", "chuPhaseTumeur")
				.createAlias("chuPhaseTumeur.chuTumeur", "chuTumeur")
				.add(Restrictions.eq("chuTumeur.idTumeur", idTumeur))
				.list();
		
		logger.debug("Prelevements {}", result);
		
		return result;
	}

	/** =================================================*/
}
