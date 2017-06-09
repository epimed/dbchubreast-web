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

import dbchubreast_web.entity.ChuBiomarqueur;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;

@Transactional
@Repository

public class ChuPrelevementBiomarqueurDaoImpl extends BaseDao implements ChuPrelevementBiomarqueurDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuPrelevementBiomarqueur find(Integer idPrelevement, String idBiomarqueur) {
		if (idPrelevement == null || idBiomarqueur == null) {
			return null;
		}

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPrelevementBiomarqueur> criteria = builder.createQuery(ChuPrelevementBiomarqueur.class);
			Root<ChuPrelevementBiomarqueur> root = criteria.from(ChuPrelevementBiomarqueur.class);
			Join<ChuPrelevementBiomarqueur, ChuBiomarqueur> biomarqueur = root.join("chuBiomarqueur");
			Join<ChuPrelevementBiomarqueur, ChuPrelevement> prelevement = root.join("chuPrelevement");
			criteria.select(root).where(
					builder.and(
							builder.equal(biomarqueur.get("idBiomarqueur"), idBiomarqueur),
							builder.equal(prelevement.get("idPrelevement"), idPrelevement)
							)
					);
			ChuPrelevementBiomarqueur result = sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
			this.populateDependencies(result);
			return result;
		}
		catch (NoResultException ex) {
			return null;
		}
		
	}

	/** ================================================= */

	public List<ChuPrelevementBiomarqueur> list(Integer idPrelevement) {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPrelevementBiomarqueur> criteria = builder.createQuery(ChuPrelevementBiomarqueur.class);
		Root<ChuPrelevementBiomarqueur> root = criteria.from(ChuPrelevementBiomarqueur.class);
		Join<ChuPrelevementBiomarqueur, ChuBiomarqueur> biomarqueur = root.join("chuBiomarqueur");
		Join<ChuPrelevementBiomarqueur, ChuPrelevement> prelevement = root.join("chuPrelevement");
		criteria.select(root).where(builder.equal(prelevement.get("idPrelevement"), idPrelevement));
		criteria.orderBy(builder.asc(biomarqueur.get("ordre")));
		List<ChuPrelevementBiomarqueur> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public List<ChuPrelevementBiomarqueur> list(Integer idPhaseTumeur, String idBiomarqueur) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPrelevementBiomarqueur> criteria = builder.createQuery(ChuPrelevementBiomarqueur.class);
		Root<ChuPrelevementBiomarqueur> root = criteria.from(ChuPrelevementBiomarqueur.class);
		Join<ChuPrelevementBiomarqueur, ChuBiomarqueur> biomarqueur = root.join("chuBiomarqueur");
		Join<ChuPrelevementBiomarqueur, ChuPrelevement> prelevement = root.join("chuPrelevement");
		Join<ChuPrelevement, ChuPhaseTumeur> phaseTumeur = prelevement.join("chuPhaseTumeur");
		criteria.select(root).where(
				builder.and(
						builder.equal(biomarqueur.get("idBiomarqueur"), idBiomarqueur),
						builder.equal(phaseTumeur.get("idPhase"), idPhaseTumeur)
						)
				);
		List<ChuPrelevementBiomarqueur> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public List<ChuPrelevementBiomarqueur> list(List<Integer> listIdPrelevements) {

		if (listIdPrelevements == null || listIdPrelevements.isEmpty()) {
			return null;
		}
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuPrelevementBiomarqueur> criteria = builder.createQuery(ChuPrelevementBiomarqueur.class);
		Root<ChuPrelevementBiomarqueur> root = criteria.from(ChuPrelevementBiomarqueur.class);
		Join<ChuPrelevementBiomarqueur, ChuBiomarqueur> biomarqueur = root.join("chuBiomarqueur");
		Join<ChuPrelevementBiomarqueur, ChuPrelevement> prelevement = root.join("chuPrelevement");
		
		criteria.select(root).where(
				builder.in(prelevement.get("idPrelevement")).value(listIdPrelevements)
				);
		
		criteria.orderBy(builder.asc(biomarqueur.get("ordre")));
		
		List<ChuPrelevementBiomarqueur> list = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

		this.populateDependencies(list);

		return list;
	}

	/** ================================================= */

	public void save(ChuPrelevementBiomarqueur prelBio) {
		sessionFactory.getCurrentSession().save(prelBio);
	}

	/** ================================================= */

	public void update(ChuPrelevementBiomarqueur prelBio) {
		sessionFactory.getCurrentSession().update(prelBio);
	}

	/** ================================================= */

	public void saveOrUpdate(ChuPrelevementBiomarqueur prelBio) {
		sessionFactory.getCurrentSession().saveOrUpdate(prelBio);
	}

	/** ================================================= */

	public void delete(ChuPrelevementBiomarqueur prelBio) {
		sessionFactory.getCurrentSession().delete(prelBio);
	}

	/** ================================================= */

	private void populateDependencies(List<ChuPrelevementBiomarqueur> list) {
		for (ChuPrelevementBiomarqueur prelBio : list) {
			this.populateDependencies(prelBio);
		}
	}

	/** ================================================= */

	private void populateDependencies(ChuPrelevementBiomarqueur prelBio) {
		if (prelBio != null) {
			Hibernate.initialize(prelBio.getChuPrelevement());
			Hibernate.initialize(prelBio.getChuPrelevement().getChuTypePrelevement());
			Hibernate.initialize(prelBio.getChuBiomarqueur());
		}
	}

	/** ================================================= */
}
