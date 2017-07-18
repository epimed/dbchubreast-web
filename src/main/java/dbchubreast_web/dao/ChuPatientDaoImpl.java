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
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;

@Transactional
@Repository

public class ChuPatientDaoImpl extends BaseDao implements ChuPatientDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public List<ChuPatient> list() {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPatient> criteria = builder.createQuery(ChuPatient.class);
		Root<ChuPatient> root = criteria.from(ChuPatient.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("nom")), builder.asc(root.get("prenom")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */

	public String getLastIdPatient() {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<String> criteria = builder.createQuery(String.class);
			Root<ChuPatient> root = criteria.from(ChuPatient.class);
			CompoundSelection<String> projection = builder.construct(String.class, root.get("idPatient"));
			criteria.select(projection);
			criteria.orderBy(builder.desc(root.get("idPatient")));

			return sessionFactory.getCurrentSession().createQuery(criteria).setMaxResults(1).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public Long count() {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
			Root<ChuPatient> root = criteria.from(ChuPatient.class);
			criteria.select(builder.count(root));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public ChuPatient find(String idPatient) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPatient> criteria = builder.createQuery(ChuPatient.class);
			Root<ChuPatient> root = criteria.from(ChuPatient.class);
			criteria.select(root).where(builder.equal(root.get("idPatient"), idPatient));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}

	}

	/** ================================================= */

	public ChuPatient find(Integer idTumeur) {	
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPatient> criteria = builder.createQuery(ChuPatient.class);
			Root<ChuPatient> root = criteria.from(ChuPatient.class);
			Join<ChuPatient, ChuTumeur> tumeurs = root.join("chuTumeurs");
			criteria.select(root).where(builder.equal(tumeurs.get("idTumeur"), idTumeur));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}

	/** ================================================= */

	public ChuPatient findByIdPrelevement(Integer idPrelevement) {

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPatient> criteria = builder.createQuery(ChuPatient.class);
			Root<ChuPatient> root = criteria.from(ChuPatient.class);
			Join<ChuPatient, ChuTumeur> tumeurs = root.join("chuTumeurs");
			Join<ChuTumeur, ChuPhaseTumeur> phases = tumeurs.join("chuPhaseTumeurs");
			Join<ChuPhaseTumeur, ChuPrelevement> prelevements = phases.join("chuPrelevements");
			criteria.select(root).where(builder.equal(prelevements.get("idPrelevement"), idPrelevement));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}

	/** ================================================= */

	public ChuPatient findByIdTraitement(Integer idTraitement) {
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPatient> criteria = builder.createQuery(ChuPatient.class);
			Root<ChuPatient> root = criteria.from(ChuPatient.class);
			Join<ChuPatient, ChuTumeur> tumeurs = root.join("chuTumeurs");
			Join<ChuTumeur, ChuPhaseTumeur> phases = tumeurs.join("chuPhaseTumeurs");
			Join<ChuPhaseTumeur, ChuTraitement> traitements = phases.join("chuTraitements");
			criteria.select(root).where(builder.equal(traitements.get("idTraitement"), idTraitement));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}

	/** ================================================= */

	public List<ChuPatient> findInAttributes(String text) {

		text = text.toLowerCase();

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPatient> criteria = builder.createQuery(ChuPatient.class);
		Root<ChuPatient> root = criteria.from(ChuPatient.class);
		criteria.select(root)
		.where(
				builder.or(
						builder.like(builder.lower(root.get("nom")), "%" + text + "%"),
						builder.like(builder.lower(root.get("nomNaissance")), "%" + text + "%"),
						builder.like(builder.lower(root.get("prenom")), "%" + text + "%"),
						builder.like(builder.lower(root.get("rcp")), "%" + text + "%"),
						builder.like(builder.lower(root.get("tk")), "%" + text + "%"),
						builder.like(builder.lower(root.get("idPatient")), "%" + text + "%")
						)
				);

		criteria.orderBy(builder.asc(root.get("nom")), builder.asc(root.get("prenom")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */

	public void update(ChuPatient patient) {
		sessionFactory.getCurrentSession().update(patient);
	}

	/** ================================================= */

	public void save(ChuPatient patient) {

		String lastIdPatient = this.getLastIdPatient();

		Integer count = 0;
		if (lastIdPatient != null) {
			String idString = lastIdPatient.substring(3);
			Double idDouble = Double.parseDouble(idString);
			count = idDouble.intValue();
		}
		String idPatient = "EPT" + String.format("%04d", count + 1);
		patient.setIdPatient(idPatient);

		sessionFactory.getCurrentSession().save(patient);
	}

	/** ================================================= */

	public boolean delete(ChuPatient patient) {
		try {
			sessionFactory.getCurrentSession().delete(patient);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/** ================================================= */

}
