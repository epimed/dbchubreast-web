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

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuPhaseTumeur;

@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuPhaseTumeurDaoImpl extends BaseDao implements ChuPhaseTumeurDao {

	@Autowired
	private SessionFactory sessionFactory;


	/** =================================================*/

	public List<ChuPhaseTumeur> list(){

		List<ChuPhaseTumeur> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuPhaseTumeur.class)
				.addOrder(Order.asc("idPhase"))
				.list();

		return result;
	}
	
	/** =================================================*/

	public List<ChuPhaseTumeur> listWithDependencies() {
		List<ChuPhaseTumeur> list = this.list();
		this.populateDependencies(list);
		return list;
	}

	/** =================================================*/

	public List<ChuPhaseTumeur> list(Integer idTumeur){

		List<ChuPhaseTumeur> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuPhaseTumeur.class)
				.createAlias("chuTumeur", "chuTumeur")
				.add(Restrictions.eq("chuTumeur.idTumeur", idTumeur))
				.addOrder(Order.asc("dateDiagnostic"))
				.addOrder(Order.asc("idPhase"))
				.list();

		return result;
	}
	
	/** =================================================*/

	public List<ChuPhaseTumeur> listWithDependencies(Integer idTumeur) {
		List<ChuPhaseTumeur> list = this.list(idTumeur);
		this.populateDependencies(list);
		return list;
	}
	
	/** =================================================*/

	public ChuPhaseTumeur find(Integer idPhase) {
		ChuPhaseTumeur result = (ChuPhaseTumeur) sessionFactory.getCurrentSession()
				.createCriteria(ChuPhaseTumeur.class)
				.add(Restrictions.eq("idPhase", idPhase))
				.uniqueResult();
		return result;
	}

	/** =================================================*/

	public ChuPhaseTumeur findWithDependencies(Integer idPhase) {
		ChuPhaseTumeur phase = (ChuPhaseTumeur) sessionFactory.getCurrentSession()
				.createCriteria(ChuPhaseTumeur.class)
				.add(Restrictions.eq("idPhase", idPhase))
				.uniqueResult();

		Hibernate.initialize(phase.getChuMetastases());
		Hibernate.initialize(phase.getChuTnms());
		Hibernate.initialize(phase.getChuTypePhase());
		Hibernate.initialize(phase.getChuPerformanceStatus());

		return phase;
	}
	/** =================================================*/

	public List<ChuPhaseTumeur> list(Integer idTumeur, Integer idTypePhase) {

		List<ChuPhaseTumeur> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuPhaseTumeur.class)
				.createAlias("chuTumeur", "chuTumeur")
				.createAlias("chuTypePhase", "chuTypePhase")
				.add(Restrictions.eq("chuTumeur.idTumeur", idTumeur))
				.add(Restrictions.eq("chuTypePhase.idTypePhase", idTypePhase))
				.addOrder(Order.asc("dateDiagnostic"))
				.addOrder(Order.asc("idPhase"))
				.list();

		this.populateDependencies(result);
		return result; 
	}

	/** =================================================*/

	public void save (ChuPhaseTumeur phaseTumeur) {
		sessionFactory.getCurrentSession().save(phaseTumeur);
	}

	/** =================================================*/

	public void update (ChuPhaseTumeur phaseTumeur) {
		sessionFactory.getCurrentSession().update(phaseTumeur);
	}

	/** =================================================*/

	public void saveOrUpdate (ChuPhaseTumeur phaseTumeur) {
		sessionFactory.getCurrentSession().saveOrUpdate(phaseTumeur);
	}

	/** =================================================*/

	private void populateDependencies(List<ChuPhaseTumeur> list) {
		for (ChuPhaseTumeur phase : list) {
			Hibernate.initialize(phase.getChuMetastases());
			Hibernate.initialize(phase.getChuTnms());
			Hibernate.initialize(phase.getChuTypePhase());
			Hibernate.initialize(phase.getChuPerformanceStatus());
		}
	}

	/** =================================================*/

	public ChuPhaseTumeur findFirstRelapse(Integer idTumeur) {

		Criteria crit =  sessionFactory.getCurrentSession()
				.createCriteria(ChuPhaseTumeur.class)
				.createAlias("chuTumeur", "chuTumeur") 
				.createAlias("chuTypePhase", "chuTypePhase") 
				.add(Restrictions.eq("chuTumeur.idTumeur", idTumeur))
				.add(Restrictions.eq("chuTypePhase.idTypePhase", 2)) // rechute
				.addOrder(Order.asc("dateDiagnostic"))
				.setMaxResults(1)
				;

		return (ChuPhaseTumeur) crit.uniqueResult();

	}

	/** =================================================*/

}
