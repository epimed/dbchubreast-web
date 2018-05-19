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

import dbchubreast_web.entity.ChuParameter;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPatientParameter;


@Transactional
@Repository

public class ChuPatientParameterDaoImpl extends BaseDao implements ChuPatientParameterDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuPatientParameter find(String idPatient, Integer idParameter) {
		if (idPatient == null || idParameter == null) {
			return null;
		}

		try {
			CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ChuPatientParameter> criteria = builder.createQuery(ChuPatientParameter.class);
			Root<ChuPatientParameter> root = criteria.from(ChuPatientParameter.class);
			Join<ChuPatientParameter, ChuParameter> parameter = root.join("chuParameter");
			Join<ChuPatientParameter, ChuPatient> patient = root.join("chuPatient");
			criteria.select(root).where(
					builder.and(
							builder.equal(parameter.get("idParameter"), idParameter),
							builder.equal(patient.get("idPatient"), idPatient)
							)
					);
			ChuPatientParameter result = sessionFactory.getCurrentSession().createQuery(criteria).getSingleResult();
			this.populateDependencies(result);
			return result;
		}
		catch (NoResultException ex) {
			return null;
		}
		
	}

	/** ================================================= */

	public void save(ChuPatientParameter pp) {
		sessionFactory.getCurrentSession().save(pp);
	}

	/** ================================================= */

	public void update(ChuPatientParameter pp) {
		sessionFactory.getCurrentSession().update(pp);
	}

	/** ================================================= */

	public void saveOrUpdate(ChuPatientParameter pp) {
		sessionFactory.getCurrentSession().saveOrUpdate(pp);
	}

	/** ================================================= */

	public void delete(ChuPatientParameter pp) {
		sessionFactory.getCurrentSession().delete(pp);
	}

	
	/** ================================================= */
	/*
	private void populateDependencies(List<ChuPatientParameter> list) {
		for (ChuPatientParameter patientParameter : list) {
			this.populateDependencies(patientParameter);
		}
	}
	*/
	/** ================================================= */

	private void populateDependencies(ChuPatientParameter patientParameter) {
		if (patientParameter != null) {
			Hibernate.initialize(patientParameter.getChuPatient());
			Hibernate.initialize(patientParameter.getChuParameter());
		}
	}

	/** ================================================= */
}
