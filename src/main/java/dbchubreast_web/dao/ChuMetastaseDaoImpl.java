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
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuMetastase;;

@Transactional
@Repository
public class ChuMetastaseDaoImpl extends BaseDao implements ChuMetastaseDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuMetastase find(Integer idMetastase) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuMetastase> criteria = builder.createQuery(ChuMetastase.class);
			Root<ChuMetastase> root = criteria.from(ChuMetastase.class);
			criteria.select(root).where(builder.equal(root.get("idMetastase"), idMetastase));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public List<ChuMetastase> list() {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuMetastase> criteria = builder.createQuery(ChuMetastase.class);
		Root<ChuMetastase> root = criteria.from(ChuMetastase.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idMetastase")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

	/** ================================================= */

	public List<ChuMetastase> list(List<Integer> listIdMetastases) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuMetastase> criteria = builder.createQuery(ChuMetastase.class);
		Root<ChuMetastase> root = criteria.from(ChuMetastase.class);
		criteria.select(root).where(builder.in(root.get("idMetastase")).value(listIdMetastases));
		criteria.orderBy(builder.asc(root.get("idMetastase")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */

	public List<ChuMetastase> list(Integer idPhaseTumeur) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuMetastase> criteria = builder.createQuery(ChuMetastase.class);
		Root<ChuMetastase> root = criteria.from(ChuMetastase.class);
		criteria.select(root).where(
				builder.equal(
						root.get("chuPhaseTumeurs").get("idPhase"),idPhaseTumeur)
				);
		criteria.orderBy(builder.asc(root.get("idMetastase")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */
}
