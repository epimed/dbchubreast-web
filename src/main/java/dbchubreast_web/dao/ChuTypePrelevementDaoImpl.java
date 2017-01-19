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

import dbchubreast_web.entity.ChuTypePrelevement;


@Transactional
@Repository
@SuppressWarnings("unchecked")
public class ChuTypePrelevementDaoImpl extends BaseDao implements ChuTypePrelevementDao {

	@Autowired
	private SessionFactory sessionFactory;



	/** ================================================= */
	
	public ChuTypePrelevement find(Integer idTypePrelevement) {
		ChuTypePrelevement result = (ChuTypePrelevement) sessionFactory.getCurrentSession()
				.createCriteria(ChuTypePrelevement.class)
				.add(Restrictions.eq("idTypePrelevement", idTypePrelevement))
				.uniqueResult();
		return result;
	}

	/** ================================================= */
	
	
	public List<ChuTypePrelevement> list(Integer[] listIdTypePrelevement) {
		List<ChuTypePrelevement> result =  sessionFactory.getCurrentSession()
				.createCriteria(ChuTypePrelevement.class)
				.add(Restrictions.in("idTypePrelevement", (Object[]) listIdTypePrelevement))
				.list();
		return result;
	}

	/** ================================================= */
	
	public List<ChuTypePrelevement> listPhaseInitiale() {
		List<ChuTypePrelevement> result =  sessionFactory.getCurrentSession()
				.createCriteria(ChuTypePrelevement.class)
				.add(Restrictions.not(Restrictions.like("nom", "rechute").ignoreCase()))
				.list();
		return result;
	}

	/** ================================================= */
	
	public List<ChuTypePrelevement> listPhaseRechute() {
		List<ChuTypePrelevement> result =  sessionFactory.getCurrentSession()
				.createCriteria(ChuTypePrelevement.class)
				.add(Restrictions.like("nom", "rechute").ignoreCase())
				.list();
		return result;
	}
	
	/** ================================================= */

	/** ================================================= */

}
