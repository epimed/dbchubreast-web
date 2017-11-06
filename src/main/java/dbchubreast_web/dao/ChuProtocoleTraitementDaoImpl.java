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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuComposantTraitement;
import dbchubreast_web.entity.ChuMethodeTraitement;
import dbchubreast_web.entity.ChuProtocoleTraitement;


@Transactional
@Repository
public class ChuProtocoleTraitementDaoImpl extends BaseDao implements ChuProtocoleTraitementDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuProtocoleTraitement find(Integer idProtocole) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuProtocoleTraitement> criteria = builder.createQuery(ChuProtocoleTraitement.class);
			Root<ChuProtocoleTraitement> root = criteria.from(ChuProtocoleTraitement.class);
			this.fetchDependencies(root);
			criteria.select(root).where(builder.equal(root.get("idProtocole"), idProtocole));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public List<ChuProtocoleTraitement> list() {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuProtocoleTraitement> criteria = builder.createQuery(ChuProtocoleTraitement.class);
		Root<ChuProtocoleTraitement> root = criteria.from(ChuProtocoleTraitement.class);
		Join<ChuProtocoleTraitement, ChuMethodeTraitement> methode = root.join("chuMethodeTraitement");
		this.fetchDependencies(root);
		criteria.select(root);
		criteria.orderBy(builder.asc(methode.get("idMethode")), builder.asc(root.get("code")));
		List<ChuProtocoleTraitement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		return list;
	}

	/** ================================================= */

	public List<ChuProtocoleTraitement> listByMethode(Integer idMethode) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuProtocoleTraitement> criteria = builder.createQuery(ChuProtocoleTraitement.class);
		Root<ChuProtocoleTraitement> root = criteria.from(ChuProtocoleTraitement.class);
		Join<ChuProtocoleTraitement, ChuMethodeTraitement> methode = root.join("chuMethodeTraitement");
		criteria.select(root).where(
				builder.equal(methode.get("idMethode"), idMethode)
				);
		criteria.orderBy(builder.asc(root.get("code")));
		List<ChuProtocoleTraitement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		populateDependencies(list);
		return list;
	}
	
	/** ================================================= */
	
	public List<ChuProtocoleTraitement> listByComposant(Integer idComposant) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuProtocoleTraitement> criteria = builder.createQuery(ChuProtocoleTraitement.class);
		Root<ChuComposantTraitement> composant = criteria.from(ChuComposantTraitement.class);
		Join<ChuComposantTraitement, ChuProtocoleTraitement> protocoles = composant.join("chuProtocoleTraitements");
		criteria.select(protocoles).where(
				builder.equal(composant.get("idComposant"), idComposant)
				);
		criteria.orderBy(builder.asc(protocoles.get("code")));
		List<ChuProtocoleTraitement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		return list;
	}

	/** ================================================= */

	public void save(ChuProtocoleTraitement protocole) {
		sessionFactory.getCurrentSession().save(protocole);
	}

	/** ================================================= */

	public void update(ChuProtocoleTraitement protocole) {
		sessionFactory.getCurrentSession().update(protocole);
	}

	/** ================================================= */

	public void saveOrUpdate(ChuProtocoleTraitement protocole) {
		sessionFactory.getCurrentSession().saveOrUpdate(protocole);
	}
	
	/** ================================================= */

	public void delete(ChuProtocoleTraitement protocole) {
		sessionFactory.getCurrentSession().delete(protocole);
	}

	
	/** ================================================= */

	private void fetchDependencies(Root<ChuProtocoleTraitement> root) {
		root.fetch("chuMethodeTraitement", JoinType.INNER);
		root.fetch("chuComposantTraitements", JoinType.LEFT);
	}

	/** ================================================= */
	

	private void populateDependencies(List<ChuProtocoleTraitement> list) {
		for (ChuProtocoleTraitement protocole : list) {
			this.populateDependencies(protocole);
		}
	}

	/** ================================================= */

	private void populateDependencies(ChuProtocoleTraitement protocole) {
		if (protocole != null) {
			Hibernate.initialize(protocole.getChuMethodeTraitement());
			Hibernate.initialize(protocole.getChuComposantTraitements());
		}
	}

	/** ================================================= */
}
