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

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuGroupeTopographie;
import dbchubreast_web.entity.ChuTopographie;

@Transactional
@Repository
public class ChuTopographieDaoImpl extends BaseDao implements ChuTopographieDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuTopographie find(String idTopographie) {
		
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuTopographie> criteria = builder.createQuery(ChuTopographie.class);
			Root<ChuTopographie> root = criteria.from(ChuTopographie.class);
			criteria.select(root).where(builder.equal(root.get("idTopographie"), idTopographie));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}

	/** ================================================= */

	public List<ChuTopographie> list(List<String> listIdGroupeTopo) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTopographie> criteria = builder.createQuery(ChuTopographie.class);
		Root<ChuTopographie> root = criteria.from(ChuTopographie.class);
		Join<ChuTopographie, ChuGroupeTopographie> groupe = root.join("chuGroupeTopographie");
		criteria.select(root).where(builder.in(groupe.get("idGroupeTopo")).value(listIdGroupeTopo));
		criteria.orderBy(builder.asc(root.get("idTopographie")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		
	}

	/** ================================================= */

	public List<ChuTopographie> list() {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuTopographie> criteria = builder.createQuery(ChuTopographie.class);
		Root<ChuTopographie> root = criteria.from(ChuTopographie.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idTopographie")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
		
	}

	/** ================================================= */
}
