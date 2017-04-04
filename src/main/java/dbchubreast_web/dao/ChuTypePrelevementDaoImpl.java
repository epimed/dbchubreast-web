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

import dbchubreast_web.entity.ChuTypePrelevement;

@Transactional
@Repository
public class ChuTypePrelevementDaoImpl extends BaseDao implements ChuTypePrelevementDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuTypePrelevement find(Integer idTypePrelevement) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTypePrelevement> criteria = builder.createQuery(ChuTypePrelevement.class);
			Root<ChuTypePrelevement> root = criteria.from(ChuTypePrelevement.class);
			criteria.select(root).where(builder.equal(root.get("idTypePrelevement"), idTypePrelevement));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public List<ChuTypePrelevement> list(Integer[] listIdTypePrelevement) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTypePrelevement> criteria = builder.createQuery(ChuTypePrelevement.class);
		Root<ChuTypePrelevement> root = criteria.from(ChuTypePrelevement.class);
		criteria.select(root).where(builder.in(root.get("idTypePrelevement")).value(listIdTypePrelevement));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */

	public List<ChuTypePrelevement> listPhaseInitiale() {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTypePrelevement> criteria = builder.createQuery(ChuTypePrelevement.class);
		Root<ChuTypePrelevement> root = criteria.from(ChuTypePrelevement.class);
		criteria.select(root).where(
				builder.notLike(builder.lower(root.get("nom")), builder.lower(root.get("rechute")))
				);
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */

	public List<ChuTypePrelevement> listPhaseRechute() {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTypePrelevement> criteria = builder.createQuery(ChuTypePrelevement.class);
		Root<ChuTypePrelevement> root = criteria.from(ChuTypePrelevement.class);
		criteria.select(root).where(
				builder.like(builder.lower(root.get("nom")), builder.lower(root.get("rechute")))
				);
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */

}
