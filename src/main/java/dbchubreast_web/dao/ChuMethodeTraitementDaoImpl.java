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

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuMethodeTraitement;
import dbchubreast_web.entity.ChuProtocoleTraitement;


@Transactional
@Repository
public class ChuMethodeTraitementDaoImpl extends BaseDao implements ChuMethodeTraitementDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuMethodeTraitement find(Integer idMethode) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuMethodeTraitement> criteria = builder.createQuery(ChuMethodeTraitement.class);
			Root<ChuMethodeTraitement> root = criteria.from(ChuMethodeTraitement.class);
			criteria.select(root).where(builder.equal(root.get("idMethode"), idMethode));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public List<ChuMethodeTraitement> list() {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuMethodeTraitement> criteria = builder.createQuery(ChuMethodeTraitement.class);
		Root<ChuMethodeTraitement> root = criteria.from(ChuMethodeTraitement.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idMethode")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

	/** ================================================= */

	public List<ChuMethodeTraitement> listWithDependencies() {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuMethodeTraitement> criteria = builder.createQuery(ChuMethodeTraitement.class);
		Root<ChuMethodeTraitement> root = criteria.from(ChuMethodeTraitement.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idMethode")));
		List<ChuMethodeTraitement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	private void populateDependencies(ChuMethodeTraitement methode) {
		if (methode != null) {
			Hibernate.initialize(methode.getChuComposantTraitements());
			Hibernate.initialize(methode.getChuProtocoleTraitements());
			for (ChuProtocoleTraitement protocole : methode.getChuProtocoleTraitements()) {
				Hibernate.initialize(protocole.getChuComposantTraitements());
			}
		}
	}

	/** ================================================= */

	private void populateDependencies(List<ChuMethodeTraitement> list) {
		for (ChuMethodeTraitement methode : list) {
			this.populateDependencies(methode);
		}
	}

	/** ================================================= */
}
