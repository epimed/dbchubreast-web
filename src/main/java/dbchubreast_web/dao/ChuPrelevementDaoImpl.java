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

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuTumeur;

@Transactional
@Repository

public class ChuPrelevementDaoImpl extends BaseDao implements ChuPrelevementDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuPrelevement find(Integer idPrelevement) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPrelevement> criteria = builder.createQuery(ChuPrelevement.class);
			Root<ChuPrelevement> root = criteria.from(ChuPrelevement.class);
			criteria.select(root).where(builder.equal(root.get("idPrelevement"), idPrelevement));
			ChuPrelevement prelevement =  sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
			this.populateDependencies(prelevement);
			return prelevement;
		}
		catch (NoResultException ex) {
			return null;
		}

	}
	

	/** ================================================= */

	public List<ChuPrelevement> list() {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPrelevement> criteria = builder.createQuery(ChuPrelevement.class);
		Root<ChuPrelevement> root = criteria.from(ChuPrelevement.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idPrelevement")));
		List<ChuPrelevement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public List<ChuPrelevement> listByIdPhase(Integer idPhase) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPrelevement> criteria = builder.createQuery(ChuPrelevement.class);
		Root<ChuPrelevement> root = criteria.from(ChuPrelevement.class);
		Join<ChuPrelevement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
		criteria.select(root).where(builder.equal(phase.get("idPhase"), idPhase));
		criteria.orderBy(builder.asc(root.get("datePrelevement")), builder.asc(root.get("idPrelevement")));
		List<ChuPrelevement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(list);
		return list;

	}

	/** ================================================= */

	public List<ChuPrelevement> listByIdTumeur(Integer idTumeur) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPrelevement> criteria = builder.createQuery(ChuPrelevement.class);
		Root<ChuPrelevement> root = criteria.from(ChuPrelevement.class);
		Join<ChuPrelevement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
		Join<ChuPhaseTumeur, ChuTumeur> tumeur = phase.join("chuTumeur");
		criteria.select(root).where(builder.equal(tumeur.get("idTumeur"), idTumeur));
		criteria.orderBy(builder.asc(root.get("datePrelevement")), builder.asc(root.get("idPrelevement")));
		List<ChuPrelevement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(list);
		return list;

	}

	/** ================================================= */
	public ChuPrelevement findDernierPrelevement(Integer idTumeur) {
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPrelevement> criteria = builder.createQuery(ChuPrelevement.class);
			Root<ChuPrelevement> root = criteria.from(ChuPrelevement.class);
			Join<ChuPrelevement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
			Join<ChuPhaseTumeur, ChuTumeur> tumeur = phase.join("chuTumeur");
			criteria.select(root).where(builder.equal(tumeur.get("idTumeur"), idTumeur));
			criteria.orderBy(builder.desc(root.get("datePrelevement")), builder.desc(root.get("idPrelevement")));
			return sessionFactory.getCurrentSession().createQuery(criteria).setMaxResults(1).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}
	
	/** ================================================= */

	public List<ChuPrelevement> listByIdPatient(String idPatient) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPrelevement> criteria = builder.createQuery(ChuPrelevement.class);

		Root<ChuPrelevement> root = criteria.from(ChuPrelevement.class);
		Join<ChuPrelevement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
		Join<ChuPhaseTumeur, ChuTumeur> tumeur = phase.join("chuTumeur");
		Join<ChuTumeur, ChuPatient> patient = tumeur.join("chuPatient");

		criteria.select(root).where(builder.equal(patient.get("idPatient"), idPatient));
		criteria.orderBy(builder.asc(root.get("datePrelevement")), builder.asc(root.get("idPrelevement")));

		List<ChuPrelevement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

		this.populateDependencies(list);
		return list;

	}

	/** ================================================= */

	public void save(ChuPrelevement prelevement) {
		sessionFactory.getCurrentSession().save(prelevement);
	}

	/** ================================================= */

	public void update(ChuPrelevement prelevement) {
		sessionFactory.getCurrentSession().update(prelevement);
	}

	/** ================================================= */

	public void saveOrUpdate(ChuPrelevement prelevement) {
		sessionFactory.getCurrentSession().saveOrUpdate(prelevement);
	}

	/** ================================================= */

	public void delete(ChuPrelevement prelevement) {
		sessionFactory.getCurrentSession().delete(prelevement);
	}


	/** ================================================= */

	public void populateDependencies(List<ChuPrelevement> list) {

		for (ChuPrelevement prel : list) {
			this.populateDependencies(prel);
		}
	}

	/** ================================================= */

	public void populateDependencies(ChuPrelevement prel) {
		if (prel != null) {
			Hibernate.initialize(prel.getChuMorphologie());
			Hibernate.initialize(prel.getChuTypePrelevement());
			Hibernate.initialize(prel.getChuPhaseTumeur());
			Hibernate.initialize(prel.getChuPhaseTumeur().getChuTypePhase());
			Hibernate.initialize(prel.getChuPhaseTumeur().getChuTumeur());
		}
	}

	/** ================================================= */

}
