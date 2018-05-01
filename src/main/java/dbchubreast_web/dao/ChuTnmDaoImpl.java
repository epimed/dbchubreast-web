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
import dbchubreast_web.entity.ChuTnm;

@Transactional
@Repository

public class ChuTnmDaoImpl extends BaseDao implements ChuTnmDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuTnm find(Integer idPhase, String type) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTnm> criteria = builder.createQuery(ChuTnm.class);
			Root<ChuTnm> root = criteria.from(ChuTnm.class);
			Join<ChuTnm, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
			criteria.select(root).where(
					builder.and(
							builder.equal(phase.get("idPhase"), idPhase),
							builder.equal(root.get("type"), type)
							)
					);
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public List<ChuTnm> find(Integer idPhase) {

			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTnm> criteria = builder.createQuery(ChuTnm.class);
			Root<ChuTnm> root = criteria.from(ChuTnm.class);
			Join<ChuTnm, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
			criteria.select(root).where(builder.equal(phase.get("idPhase"), idPhase));
			List<ChuTnm> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
			return list;
		
	}

	/** ================================================= */

	public void save(ChuTnm tnm) {
		sessionFactory.getCurrentSession().save(tnm);
	}

	/** ================================================= */

	public void update(ChuTnm tnm) {
		sessionFactory.getCurrentSession().update(tnm);
	}

	/** ================================================= */

	public void saveOrUpdate(ChuTnm tnm) {
		sessionFactory.getCurrentSession().saveOrUpdate(tnm);
	}

	/** ================================================= */

	public void delete(ChuTnm tnm) {
		sessionFactory.getCurrentSession().delete(tnm);
	}

	/** ===============================================*/

}
