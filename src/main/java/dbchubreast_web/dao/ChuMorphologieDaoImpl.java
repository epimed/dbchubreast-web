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

import dbchubreast_web.entity.ChuMorphologie;

@Transactional
@Repository
public class ChuMorphologieDaoImpl extends BaseDao implements ChuMorphologieDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuMorphologie find(String idMorphologie) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuMorphologie> criteria = builder.createQuery(ChuMorphologie.class);
			Root<ChuMorphologie> root = criteria.from(ChuMorphologie.class);
			criteria.select(root).where(builder.equal(root.get("idMorphologie"), idMorphologie));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
		
	}

	/** ================================================= */
	
	public List<ChuMorphologie> list() {
		
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuMorphologie> criteria = builder.createQuery(ChuMorphologie.class);
		Root<ChuMorphologie> root = criteria.from(ChuMorphologie.class);
		criteria.select(root).where(
				builder.equal(root.get("chuGroupeMorphologie").get("idGroupeMorpho"), "MG1")
				);
		criteria.orderBy(builder.asc(root.get("idMorphologie")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
	
	}

	/** ================================================= */
}
