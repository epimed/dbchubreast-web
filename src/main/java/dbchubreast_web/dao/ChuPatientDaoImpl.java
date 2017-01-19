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


import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuPatient;

@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuPatientDaoImpl extends BaseDao implements ChuPatientDao {

	@Autowired
	private SessionFactory sessionFactory;


	/** ===============================================================================================================================*/

	public List<ChuPatient> list() {

		Criteria crit = sessionFactory.getCurrentSession()
				.createCriteria(ChuPatient.class)
				.addOrder(Order.asc("nom"))
				.addOrder(Order.asc("prenom"))
				;

		return crit.list();
	}

	/** =================================================*/

	public String getLastIdPatient() {

		Criteria crit = sessionFactory.getCurrentSession()
				.createCriteria(ChuPatient.class)
				.setProjection(Projections.projectionList()
						.add( Projections.property("idPatient"))
						)
				.addOrder( Order.desc("idPatient") )
				.setMaxResults(1);

		return (String) crit.uniqueResult();

	}


	/** =================================================*/

	public ChuPatient find(String nom, String prenom, Date dateNaissance) {

		ChuPatient result = null;
		Criteria crit = sessionFactory.getCurrentSession()
				.createCriteria(ChuPatient.class);

		crit.add(Restrictions.eq("nom", nom).ignoreCase());

		if (prenom!=null) {
			crit.add(Restrictions.eq("prenom", prenom).ignoreCase());
		}

		if (dateNaissance!=null) {
			crit.add(Restrictions.eq("dateNaissance", dateNaissance));
		}

		result = (ChuPatient) crit.uniqueResult();

		return result;

	}

	/** =================================================*/

	public Long count() {

		return (Long) sessionFactory.getCurrentSession()
				.createCriteria(ChuPatient.class)
				.setProjection( Projections.projectionList()
						.add( Projections.rowCount() )
						)
				.uniqueResult();
	}


	/** =================================================*/

	public ChuPatient find(String idPatient) {

		ChuPatient patient =  (ChuPatient) sessionFactory.getCurrentSession()
				.createCriteria(ChuPatient.class)
				.add(Restrictions.eq("idPatient", idPatient))
				.uniqueResult();

		return patient;
	}

	/** =================================================*/

	public ChuPatient find(Integer idTumeur) {

		ChuPatient patient =  (ChuPatient) sessionFactory.getCurrentSession()
				.createCriteria(ChuPatient.class)
				.createAlias("chuTumeurs", "chuTumeurs")
				.add(Restrictions.eq("chuTumeurs.idTumeur", idTumeur))
				.uniqueResult();

		return patient;
	}

	/** =================================================*/
	

	public ChuPatient findByIdPrelevement(Integer idPrelevement) {
		ChuPatient patient =  (ChuPatient) sessionFactory.getCurrentSession()
				.createCriteria(ChuPatient.class)
				.createAlias("chuTumeurs", "chuTumeurs")
				.createAlias("chuTumeurs.chuPhaseTumeurs", "chuPhaseTumeurs")
				.createAlias("chuPhaseTumeurs.chuPrelevements", "chuPrelevements")
				.add(Restrictions.eq("chuPrelevements.idPrelevement", idPrelevement))
				.uniqueResult();
		return patient;
	}

	
	/** =================================================*/

	public List<ChuPatient> findInAttributes(String text) {


		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(ChuPatient.class);

		Criterion criterion = Restrictions.or(
				Restrictions.like( "nom", "%" + text + "%").ignoreCase(),
				Restrictions.like( "prenom", "%" + text + "%").ignoreCase(),
				Restrictions.like( "rcp", "%" + text + "%").ignoreCase(),
				Restrictions.like( "idPatient", "%" + text + "%").ignoreCase()
				);

		criteria.add(criterion);

		criteria.addOrder(Order.asc("nom")).addOrder(Order.asc("prenom"));

		return criteria.list();
	}

	/** =================================================*/

	public void update(ChuPatient patient) {
		sessionFactory.getCurrentSession().update(patient);
	}

	/** =================================================*/

	public void save(ChuPatient patient) {

			String lastIdPatient = this.getLastIdPatient();

			Integer count = 0;
			if (lastIdPatient!=null) {
				String idString = lastIdPatient.substring(3);
				Double idDouble = Double.parseDouble(idString);
				count = idDouble.intValue();
			}
			String idPatient = "EPT" + String.format("%04d", count+1);
			patient.setIdPatient(idPatient);

			sessionFactory.getCurrentSession().save(patient);		
	}

	/** =================================================*/
}
