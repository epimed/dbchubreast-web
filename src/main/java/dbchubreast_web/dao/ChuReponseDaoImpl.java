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

import dbchubreast_web.entity.ChuReponse;


@Transactional
@Repository
public class ChuReponseDaoImpl extends BaseDao implements ChuReponseDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuReponse find(Integer idReponse) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuReponse> criteria = builder.createQuery(ChuReponse.class);
			Root<ChuReponse> root = criteria.from(ChuReponse.class);
			criteria.select(root).where(builder.equal(root.get("idReponse"), idReponse));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public List<ChuReponse> list() {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuReponse> criteria = builder.createQuery(ChuReponse.class);
		Root<ChuReponse> root = criteria.from(ChuReponse.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idReponse")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

	/** ================================================= */
}
