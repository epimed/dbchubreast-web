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

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuTnm;

@Transactional
@Repository

public class ChuTnmDaoImpl extends BaseDao implements ChuTnmDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** =================================================*/
	public ChuTnm find(Integer idPhase, String type) {
		ChuTnm result = (ChuTnm) sessionFactory.getCurrentSession()
				.createCriteria(ChuTnm.class)
				.createAlias("chuPhaseTumeur", "chuPhaseTumeur")
				.add(Restrictions.eq("chuPhaseTumeur.idPhase", idPhase))
				.add(Restrictions.eq("type", type))
				.uniqueResult();
		return result;
	}

	/** =================================================*/

	public void save(ChuTnm tnm) {
		sessionFactory.getCurrentSession().save(tnm);
	}
	
	/** =================================================*/

	public void update(ChuTnm tnm) {
		sessionFactory.getCurrentSession().update(tnm);
	}

	/** =================================================*/

	public void saveOrUpdate(ChuTnm tnm) {
		sessionFactory.getCurrentSession().saveOrUpdate(tnm);
	}

	/** =================================================*/

}
