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

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTypePhase;

@Transactional
@Repository

public class ChuTypePhaseDaoImpl extends BaseDao implements ChuTypePhaseDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuTypePhase find(Integer idTypePhase) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTypePhase> criteria = builder.createQuery(ChuTypePhase.class);
			Root<ChuTypePhase> root = criteria.from(ChuTypePhase.class);
			criteria.select(root).where(builder.equal(root.get("idTypePhase"), idTypePhase));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
		
	}

	/** ================================================= */

	public ChuTypePhase findByIdPhase(Integer idPhase) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTypePhase> criteria = builder.createQuery(ChuTypePhase.class);
			Root<ChuTypePhase> root = criteria.from(ChuTypePhase.class);
			Join<ChuTypePhase, ChuPhaseTumeur> phases = root.join("chuPhaseTumeurs");
			criteria.select(root).where(builder.equal(phases.get("idPhase"), idPhase));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
		
	}

	/** ================================================= */

}
