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

import dbchubreast_web.entity.ChuBiomarqueur;

@Transactional
@Repository
public class ChuBiomarqueurDaoImpl extends BaseDao implements ChuBiomarqueurDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuBiomarqueur find(String idBiomarqueur) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuBiomarqueur> criteria = builder.createQuery(ChuBiomarqueur.class);
			Root<ChuBiomarqueur> root = criteria.from(ChuBiomarqueur.class);
			criteria.select(root).where(builder.equal(root.get("idBiomarqueur"), idBiomarqueur));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	
	}

	/** ================================================= */

	public List<ChuBiomarqueur> list() {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuBiomarqueur> criteria = builder.createQuery(ChuBiomarqueur.class);
		Root<ChuBiomarqueur> root = criteria.from(ChuBiomarqueur.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("ordre")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */

	public List<ChuBiomarqueur> list(Object[] noms) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuBiomarqueur> criteria = builder.createQuery(ChuBiomarqueur.class);
		Root<ChuBiomarqueur> root = criteria.from(ChuBiomarqueur.class);
		criteria.select(root).where(builder.in(root.get("idBiomarqueur")).value(noms));
		criteria.orderBy(builder.asc(root.get("ordre")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	}

	/** ================================================= */

}
