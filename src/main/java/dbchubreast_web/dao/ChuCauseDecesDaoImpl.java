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

import dbchubreast_web.entity.ChuCauseDeces;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuTumeur;


@Transactional
@Repository

public class ChuCauseDecesDaoImpl extends BaseDao implements ChuCauseDecesDao {

	@Autowired
	private SessionFactory sessionFactory;


	/** ================================================= */

	@Override
	public ChuCauseDeces find(Integer idCauseDeces) {
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuCauseDeces> criteria = builder.createQuery(ChuCauseDeces.class);
			Root<ChuCauseDeces> root = criteria.from(ChuCauseDeces.class);
			criteria.select(root).where(builder.equal(root.get("idCauseDeces"), idCauseDeces));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}

	/** ================================================= */

	@Override
	public ChuCauseDeces findByIdPatient(String idPatient) {
		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuCauseDeces> criteria = builder.createQuery(ChuCauseDeces.class);
			Root<ChuCauseDeces> root = criteria.from(ChuCauseDeces.class);
			Join<ChuTumeur, ChuPatient> patient = root.join("chuPatients");
			criteria.select(root).where(builder.equal(patient.get("idPatient"), idPatient));
			return sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}


	/** ================================================= */

	public List<ChuCauseDeces> list() {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<ChuCauseDeces> criteria = builder.createQuery(ChuCauseDeces.class);
		Root<ChuCauseDeces> root = criteria.from(ChuCauseDeces.class);
		criteria.select(root);
		criteria.orderBy(builder.asc(root.get("idCauseDeces")));
		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */



}
