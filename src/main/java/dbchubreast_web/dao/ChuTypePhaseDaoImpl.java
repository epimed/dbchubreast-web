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

import dbchubreast_web.entity.ChuTypePhase;


@Transactional
@Repository

public class ChuTypePhaseDaoImpl extends BaseDao implements ChuTypePhaseDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** =================================================*/
	public ChuTypePhase find(Integer idTypePhase) {
		ChuTypePhase result = (ChuTypePhase) sessionFactory.getCurrentSession()
				.createCriteria(ChuTypePhase.class)
				.add(Restrictions.eq("idTypePhase", idTypePhase))
				.uniqueResult();
		return result;
	}

	/** =================================================*/

}
