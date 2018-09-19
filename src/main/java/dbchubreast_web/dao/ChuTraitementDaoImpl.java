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

import dbchubreast_web.entity.ChuMethodeTraitement;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuProtocoleTraitement;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.entity.ChuTypePhase;


@Transactional
@Repository

public class ChuTraitementDaoImpl extends BaseDao implements ChuTraitementDao {

	@Autowired
	private SessionFactory sessionFactory;


	/** ================================================= */

	public ChuTraitement find(Integer idTraitement) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTraitement> criteria = builder.createQuery(ChuTraitement.class);
			Root<ChuTraitement> root = criteria.from(ChuTraitement.class);
			criteria.select(root).where(builder.equal(root.get("idTraitement"), idTraitement));
			ChuTraitement traitement =  sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
			this.populateDependencies(traitement);
			return traitement;
		}
		catch (NoResultException ex) {
			return null;
		}

	}
	
	/** ================================================= */
	
	public List<ChuTraitement> list() {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTraitement> criteria = builder.createQuery(ChuTraitement.class);

		Root<ChuTraitement> root = criteria.from(ChuTraitement.class);
		Join<ChuTraitement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
		Join<ChuPhaseTumeur, ChuTumeur> tumeur = phase.join("chuTumeur");
		Join<ChuTumeur, ChuPatient> patient = tumeur.join("chuPatient");

		criteria.select(root);
		criteria.orderBy(builder.asc(patient.get("idPatient")),  builder.asc(root.get("dateDebut")));

		List<ChuTraitement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

		this.populateDependencies(list);
		return list;
		
	}

	/** ================================================= */

	public List<ChuTraitement> listByIdPatient(String idPatient) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTraitement> criteria = builder.createQuery(ChuTraitement.class);

		Root<ChuTraitement> root = criteria.from(ChuTraitement.class);
		Join<ChuTraitement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
		Join<ChuPhaseTumeur, ChuTumeur> tumeur = phase.join("chuTumeur");
		Join<ChuTumeur, ChuPatient> patient = tumeur.join("chuPatient");

		criteria.select(root).where(builder.equal(patient.get("idPatient"), idPatient));
		criteria.orderBy(builder.asc(root.get("dateDebut")));

		List<ChuTraitement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

		this.populateDependencies(list);
		return list;

	}

	/** ================================================= */

	public List<ChuTraitement> listByIdProtocole(Integer idProtocole) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTraitement> criteria = builder.createQuery(ChuTraitement.class);

		Root<ChuTraitement> root = criteria.from(ChuTraitement.class);
		Join<ChuTraitement, ChuProtocoleTraitement> protocole = root.join("chuProtocoleTraitement");
		criteria.select(root).where(builder.equal(protocole.get("idProtocole"), idProtocole));
		criteria.orderBy(builder.asc(root.get("dateDebut")));

		List<ChuTraitement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

		this.populateDependencies(list);
		return list;

	}

	/** ================================================= */

	public List<ChuTraitement> listByIdTumeur(Integer idTumeur) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTraitement> criteria = builder.createQuery(ChuTraitement.class);

		Root<ChuTraitement> root = criteria.from(ChuTraitement.class);
		Join<ChuTraitement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
		Join<ChuPhaseTumeur, ChuTumeur> tumeur = phase.join("chuTumeur");

		criteria.select(root).where(builder.equal(tumeur.get("idTumeur"), idTumeur));
		criteria.orderBy(builder.asc(root.get("dateDebut")));

		List<ChuTraitement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */
	
	public ChuTraitement findDernierTraitement(Integer idTumeur) {
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTraitement> criteria = builder.createQuery(ChuTraitement.class);

			Root<ChuTraitement> root = criteria.from(ChuTraitement.class);
			Join<ChuTraitement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
			Join<ChuPhaseTumeur, ChuTumeur> tumeur = phase.join("chuTumeur");

			criteria.select(root).where(builder.equal(tumeur.get("idTumeur"), idTumeur));
			criteria.orderBy(builder.desc(root.get("dateDebut")));

			return sessionFactory.getCurrentSession().createQuery(criteria).setMaxResults(1).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}

	/** ================================================= */

	public List<ChuTraitement> listByIdPhase(Integer idPhase) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTraitement> criteria = builder.createQuery(ChuTraitement.class);

		Root<ChuTraitement> root = criteria.from(ChuTraitement.class);
		Join<ChuTraitement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");

		criteria.select(root).where(builder.equal(phase.get("idPhase"), idPhase));
		criteria.orderBy(builder.asc(root.get("dateDebut")));

		List<ChuTraitement> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public ChuTraitement findChirurgieReference(Integer idTumeur) {

		try {

			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTraitement> criteria = builder.createQuery(ChuTraitement.class);

			Root<ChuTraitement> root = criteria.from(ChuTraitement.class);
			Join<ChuTraitement, ChuMethodeTraitement> methode = root.join("chuMethodeTraitement");
			Join<ChuTraitement, ChuPhaseTumeur> phase = root.join("chuPhaseTumeur");
			Join<ChuPhaseTumeur, ChuTypePhase> typePhase = phase.join("chuTypePhase");
			Join<ChuPhaseTumeur, ChuTumeur> tumeur = phase.join("chuTumeur");

			criteria.select(root).where(
					builder.and(
							builder.equal(tumeur.get("idTumeur"), idTumeur),
							builder.equal(typePhase.get("idTypePhase"), 1),
							builder.equal(methode.get("idMethode"), 1)
							)
					);
			criteria.orderBy(builder.asc(root.get("dateDebut")));

			return sessionFactory.getCurrentSession().createQuery(criteria).setMaxResults(1).getSingleResult();

		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public void save(ChuTraitement traitement) {
		sessionFactory.getCurrentSession().save(traitement);
	}

	/** ================================================= */

	public void update(ChuTraitement traitement) {
		sessionFactory.getCurrentSession().update(traitement);
	}

	/** ================================================= */

	public void saveOrUpdate(ChuTraitement traitement) {
		sessionFactory.getCurrentSession().saveOrUpdate(traitement);
	}

	/** ================================================= */

	public void delete(ChuTraitement traitement) {
		sessionFactory.getCurrentSession().delete(traitement);
	}

	/** ================================================= */

	private void populateDependencies(List<ChuTraitement> list) {

		for (ChuTraitement traitement : list) {
			this.populateDependencies(traitement);
		}
	}

	/** ================================================= */

	private void populateDependencies(ChuTraitement traitement) {
		if (traitement != null) {
			Hibernate.initialize(traitement.getChuProtocoleTraitement());
			Hibernate.initialize(traitement.getChuMethodeTraitement());
			Hibernate.initialize(traitement.getChuTypeTraitement());
			Hibernate.initialize(traitement.getChuReponse());
			Hibernate.initialize(traitement.getChuPhaseTumeur());
			Hibernate.initialize(traitement.getChuPhaseTumeur().getChuTypePhase());
			Hibernate.initialize(traitement.getChuPhaseTumeur().getChuTumeur());
			Hibernate.initialize(traitement.getChuPhaseTumeur().getChuTumeur().getChuPatient());
		}
	}

	/** ================================================= */

}
