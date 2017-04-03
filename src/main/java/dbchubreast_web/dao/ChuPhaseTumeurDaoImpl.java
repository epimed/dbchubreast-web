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

import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.entity.ChuTypePhase;

@Transactional
@Repository
public class ChuPhaseTumeurDaoImpl extends BaseDao implements ChuPhaseTumeurDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public List<ChuPhaseTumeur> list() {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPhaseTumeur> criteria = builder.createQuery(ChuPhaseTumeur.class);
		Root<ChuPhaseTumeur> root = criteria.from(ChuPhaseTumeur.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idPhase")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		
	}

	/** ================================================= */

	public List<ChuPhaseTumeur> listWithDependencies() {
		List<ChuPhaseTumeur> list = this.list();
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public List<ChuPhaseTumeur> list(Integer idTumeur) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPhaseTumeur> criteria = builder.createQuery(ChuPhaseTumeur.class);
		Root<ChuPhaseTumeur> root = criteria.from(ChuPhaseTumeur.class);
		criteria.select(root).where(builder.equal(root.get("chuTumeur").get("idTumeur"), idTumeur));
		criteria.orderBy(builder.asc(root.get("dateDiagnostic")), builder.asc(root.get("idPhase")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		
	}

	/** ================================================= */

	public List<ChuPhaseTumeur> listWithDependencies(Integer idTumeur) {
		List<ChuPhaseTumeur> list = this.list(idTumeur);
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public ChuPhaseTumeur find(Integer idPhase) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPhaseTumeur> criteria = builder.createQuery(ChuPhaseTumeur.class);
			Root<ChuPhaseTumeur> root = criteria.from(ChuPhaseTumeur.class);
			criteria.select(root).where(builder.equal(root.get("idPhase"), idPhase));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
		
	}

	
	/** ================================================= */

	public ChuPhaseTumeur findWithDependencies(Integer idPhase) {

			ChuPhaseTumeur result = this.find(idPhase);
			this.populateDependencies(result);
			return result;
	}

	
	/** ================================================= */

	public ChuPhaseTumeur findByIdPrelevementWithDependencies(Integer idPrelevement) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPhaseTumeur> criteria = builder.createQuery(ChuPhaseTumeur.class);
			Root<ChuPhaseTumeur> root = criteria.from(ChuPhaseTumeur.class);
			criteria.select(root).where(builder.equal(root.get("chuPrelevements").get("idPrelevement"), idPrelevement));
			ChuPhaseTumeur result =  sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
			this.populateDependencies(result);
			return result;
		}
		catch (NoResultException ex) {
			return null;
		}

	}


	/** ================================================= */

	public List<ChuPhaseTumeur> list(Integer idTumeur, Integer idTypePhase) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPhaseTumeur> criteria = builder.createQuery(ChuPhaseTumeur.class);
		Root<ChuPhaseTumeur> root = criteria.from(ChuPhaseTumeur.class);
		Join<ChuPhaseTumeur, ChuTumeur> tumeur = root.join("chuTumeur");
		Join<ChuPhaseTumeur, ChuTypePhase> typePhase = root.join("chuTypePhase");
		criteria.select(root)
		.where(
				builder.and(
						builder.equal(tumeur.get("idTumeur"), idTumeur),
						builder.equal(typePhase.get("idTypePhase"), idTypePhase)
				));
		
		criteria.orderBy(builder.asc(root.get("dateDiagnostic")), builder.asc(root.get("idPhase")));
		List<ChuPhaseTumeur> result =  sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(result);
		return result;
	}

	
	/** ================================================= */

	public ChuPhaseTumeur findFirstRelapse(Integer idTumeur) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPhaseTumeur> criteria = builder.createQuery(ChuPhaseTumeur.class);
			Root<ChuPhaseTumeur> root = criteria.from(ChuPhaseTumeur.class);
			Join<ChuPhaseTumeur, ChuTumeur> tumeur = root.join("chuTumeur");
			Join<ChuPhaseTumeur, ChuTypePhase> typePhase = root.join("chuTypePhase");
			
			criteria.select(root)
			.where(
					builder.and(
							builder.equal(tumeur.get("idTumeur"), idTumeur),
							builder.equal(typePhase.get("idTypePhase"), 2)
					));
			criteria.orderBy(builder.asc(root.get("dateDiagnostic")));
			
			ChuPhaseTumeur result =  sessionFactory.getCurrentSession().createQuery(criteria).setMaxResults(1).getSingleResult();
			this.populateDependencies(result);
			return result;
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public ChuPhaseTumeur findPhaseInitiale(Integer idTumeur) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPhaseTumeur> criteria = builder.createQuery(ChuPhaseTumeur.class);
			Root<ChuPhaseTumeur> root = criteria.from(ChuPhaseTumeur.class);
			Join<ChuPhaseTumeur, ChuTumeur> tumeur = root.join("chuTumeur");
			Join<ChuPhaseTumeur, ChuTypePhase> typePhase = root.join("chuTypePhase");
			
			criteria.select(root)
			.where(
					builder.and(
							builder.equal(tumeur.get("idTumeur"), idTumeur),
							builder.equal(typePhase.get("idTypePhase"), 1)
					));
			criteria.orderBy(builder.asc(root.get("dateDiagnostic")));
			
			return sessionFactory.getCurrentSession().createQuery(criteria).setMaxResults(1).getSingleResult();
		
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public void save(ChuPhaseTumeur phaseTumeur) {
		sessionFactory.getCurrentSession().save(phaseTumeur);
	}

	/** ================================================= */

	public void update(ChuPhaseTumeur phaseTumeur) {
		sessionFactory.getCurrentSession().update(phaseTumeur);
	}

	/** ================================================= */

	public void saveOrUpdate(ChuPhaseTumeur phaseTumeur) {
		sessionFactory.getCurrentSession().saveOrUpdate(phaseTumeur);
	}

	
	/** ================================================= */

	private void populateDependencies(List<ChuPhaseTumeur> list) {
		for (ChuPhaseTumeur phase : list) {
			this.populateDependencies(phase);
		}
	}

	/** ================================================= */

	private void populateDependencies(ChuPhaseTumeur phase) {
		if (phase != null) {
			Hibernate.initialize(phase.getChuMetastases());
			Hibernate.initialize(phase.getChuTnms());
			Hibernate.initialize(phase.getChuTypePhase());
			Hibernate.initialize(phase.getChuPerformanceStatus());
		}
	}

	/** ================================================= */

}
