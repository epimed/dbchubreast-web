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

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuComposantTraitement;
import dbchubreast_web.entity.ChuMethodeTraitement;
import dbchubreast_web.entity.ChuProtocoleTraitement;


@Transactional
@Repository
public class ChuComposantTraitementDaoImpl extends BaseDao implements ChuComposantTraitementDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuComposantTraitement find(Integer idComposant) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuComposantTraitement> criteria = builder.createQuery(ChuComposantTraitement.class);
			Root<ChuComposantTraitement> root = criteria.from(ChuComposantTraitement.class);
			this.fetchDependencies(root);
			criteria.select(root).where(builder.equal(root.get("idComposant"), idComposant));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
	
	/** ================================================= */

	public List<ChuComposantTraitement> list() {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuComposantTraitement> criteria = builder.createQuery(ChuComposantTraitement.class);
		Root<ChuComposantTraitement> root = criteria.from(ChuComposantTraitement.class);
		this.fetchDependencies(root);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idComposant")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

	/** ================================================= */
	
	public List<ChuComposantTraitement> listById(List<Integer> listIdComposants) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuComposantTraitement> criteria = builder.createQuery(ChuComposantTraitement.class);
		Root<ChuComposantTraitement> root = criteria.from(ChuComposantTraitement.class);
		criteria.select(root).where(
				root.get("idComposant").in(listIdComposants)
				);
		criteria.orderBy(builder.asc(root.get("idComposant")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}
	
	/** ================================================= */

	public List<ChuComposantTraitement> listByMethode(Integer idMethode) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuComposantTraitement> criteria = builder.createQuery(ChuComposantTraitement.class);
		Root<ChuComposantTraitement> root = criteria.from(ChuComposantTraitement.class);
		Join<ChuComposantTraitement, ChuMethodeTraitement> methode = root.join("chuMethodeTraitement");
		criteria.select(root).where(
				builder.equal(methode.get("idMethode"), idMethode)
				);
		criteria.orderBy(builder.asc(root.get("idComposant")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}
	
	/** ================================================= */

	public List<ChuComposantTraitement> listByProtocole(Integer idProtocole) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuComposantTraitement> criteria = builder.createQuery(ChuComposantTraitement.class);
		Root<ChuComposantTraitement> root = criteria.from(ChuComposantTraitement.class);
		Join<ChuComposantTraitement, ChuProtocoleTraitement> protocoles = root.join("chuProtocoleTraitements");
		criteria.select(root).where(
				builder.equal(protocoles.get("idProtocole"), idProtocole)
				);
		criteria.orderBy(builder.asc(root.get("idComposant")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}
	
	/** ================================================= */
	
	public void save(ChuComposantTraitement composant) {
		sessionFactory.getCurrentSession().save(composant);
	}
	
	/** ================================================= */
	
	public void update (ChuComposantTraitement composant) {
		sessionFactory.getCurrentSession().update(composant);
	}
	
	/** ================================================= */

	public void saveOrUpdate (ChuComposantTraitement composant) {
		sessionFactory.getCurrentSession().saveOrUpdate(composant);
	}
	
	/** ================================================= */
	
	public void delete (ChuComposantTraitement composant) {
		sessionFactory.getCurrentSession().delete(composant);
	}
	
	/** ================================================= */

	private void fetchDependencies(Root<ChuComposantTraitement> root) {
		root.fetch("chuMethodeTraitement", JoinType.INNER);
	}
	
	/** ================================================= */
}
