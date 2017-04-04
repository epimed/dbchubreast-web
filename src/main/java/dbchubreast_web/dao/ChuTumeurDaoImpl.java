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

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuEvolution;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTopographie;
import dbchubreast_web.entity.ChuTumeur;

@Transactional
@Repository
public class ChuTumeurDaoImpl extends BaseDao implements ChuTumeurDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuTumeur find(Integer idTumeur) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTumeur> criteria = builder.createQuery(ChuTumeur.class);
			Root<ChuTumeur> root = criteria.from(ChuTumeur.class);
			criteria.select(root).where(builder.equal(root.get("idTumeur"), idTumeur));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public ChuTumeur findWithDependencies(Integer idTumeur) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTumeur> criteria = builder.createQuery(ChuTumeur.class);
			Root<ChuTumeur> root = criteria.from(ChuTumeur.class);
			criteria.select(root).where(builder.equal(root.get("idTumeur"), idTumeur));
			ChuTumeur tumeur =  sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
			this.populateDependencies(tumeur);
			return tumeur;
		}
		catch (NoResultException ex) {
			return null;
		}
	}

	/** ================================================= */

	public ChuTumeur findByIdPhaseWithDependencies(Integer idPhase) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTumeur> criteria = builder.createQuery(ChuTumeur.class);
			Root<ChuTumeur> root = criteria.from(ChuTumeur.class);
			Join<ChuTumeur, ChuPhaseTumeur> phases = root.join("chuPhaseTumeurs");
			criteria.select(root).where(builder.equal(phases.get("idPhase"), idPhase));
			ChuTumeur tumeur =  sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
			this.populateDependencies(tumeur);
			return tumeur;
		}
		catch (NoResultException ex) {
			return null;
		}
		
	}

	/** ================================================= */

	public List<ChuTumeur> findAsListWithDependencies(Integer idTumeur) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTumeur> criteria = builder.createQuery(ChuTumeur.class);
		Root<ChuTumeur> root = criteria.from(ChuTumeur.class);
		criteria.select(root).where(builder.equal(root.get("idTumeur"), idTumeur));
		List<ChuTumeur> tumeurs =  sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(tumeurs);
		return tumeurs;

	}

	/** ================================================= */

	public List<ChuTumeur> find(String idPatient) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTumeur> criteria = builder.createQuery(ChuTumeur.class);
		Root<ChuTumeur> root = criteria.from(ChuTumeur.class);
		Join<ChuTumeur, ChuPatient> patient = root.join("chuPatient");
		criteria.select(root).where(builder.equal(patient.get("idPatient"), idPatient));
		criteria.orderBy(builder.asc(root.get("idTumeur")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */

	public List<ChuTumeur> findWithDependencies(String idPatient) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTumeur> criteria = builder.createQuery(ChuTumeur.class);
		Root<ChuTumeur> root = criteria.from(ChuTumeur.class);
		Join<ChuTumeur, ChuPatient> patient = root.join("chuPatient");
		criteria.select(root).where(builder.equal(patient.get("idPatient"), idPatient));
		criteria.orderBy(builder.asc(root.get("idTumeur")));
		List<ChuTumeur> list =  sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public List<ChuTumeur> findInAttributesWithDependencies(String text) {

		text = text.toLowerCase();
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTumeur> criteria = builder.createQuery(ChuTumeur.class);
		Root<ChuTumeur> root = criteria.from(ChuTumeur.class);
		Join<ChuTumeur, ChuPatient> patient = root.join("chuPatient");
		Join<ChuTumeur, ChuEvolution> evolution = root.join("chuEvolution");
		Join<ChuTumeur, ChuTopographie> topographie = root.join("chuTopographie");
		
		criteria.select(root).where(
				builder.or(
						builder.like(builder.lower(topographie.get("idTopographie")), "%" + text + "%"),
						builder.like(builder.lower(evolution.get("code")), "%" + text + "%"),
						builder.like(builder.lower(patient.get("idPatient")), "%" + text + "%"),
						builder.like(builder.lower(patient.get("prenom")), "%" + text + "%"),
						builder.like(builder.lower(patient.get("nom")), "%" + text + "%"),
						builder.like(builder.lower(patient.get("rcp")), "%" + text + "%")
						)
				);
		
		criteria.orderBy(builder.asc(root.get("idTumeur")));
		List<ChuTumeur> list =  sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public List<ChuTumeur> list() {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTumeur> criteria = builder.createQuery(ChuTumeur.class);
		Root<ChuTumeur> root = criteria.from(ChuTumeur.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idTumeur")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

	/** ================================================= */

	public List<ChuTumeur> listWithDependencies() {
		List<ChuTumeur> list = this.list();
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public void save(ChuTumeur tumeur) {

		logger.debug("DAO save {}", tumeur);

		sessionFactory.getCurrentSession().save(tumeur);
	}

	/** ================================================= */

	public void update(ChuTumeur tumeur) {
		sessionFactory.getCurrentSession().update(tumeur);
	}

	/** ================================================= */

	private void populateDependencies(ChuTumeur tumeur) {
		if (tumeur != null) {
			Hibernate.initialize(tumeur.getChuEvolution());
			Hibernate.initialize(tumeur.getChuTopographie());
			Hibernate.initialize(tumeur.getChuPatient());
		}
	}

	/** ================================================= */

	private void populateDependencies(List<ChuTumeur> list) {
		for (ChuTumeur tumeur : list) {
			this.populateDependencies(tumeur);
		}
	}

	/** ================================================= */
}
