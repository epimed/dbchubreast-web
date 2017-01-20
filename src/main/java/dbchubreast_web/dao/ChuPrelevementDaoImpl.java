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

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuPrelevement;



@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuPrelevementDaoImpl extends BaseDao implements ChuPrelevementDao {

	@Autowired
	private SessionFactory sessionFactory;


	/** ================================================= */

	public ChuPrelevement find(Integer idPrelevement) {
		ChuPrelevement result = (ChuPrelevement) sessionFactory.getCurrentSession()
				.createCriteria(ChuPrelevement.class)
				.add(Restrictions.eq("idPrelevement", idPrelevement))
				.uniqueResult();
		this.populateDependencies(result);
		return result;
	}

	/** ================================================= */

	public List<ChuPrelevement> listByIdPhase(Integer idPhase) {

		List<ChuPrelevement> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuPrelevement.class)
				.createAlias("chuPhaseTumeur", "chuPhaseTumeur")
				.add(Restrictions.eq("chuPhaseTumeur.idPhase", idPhase))
				.addOrder(Order.asc("datePrelevement"))
				.addOrder(Order.asc("idPrelevement"))
				.list();

		this.populateDependencies(result);

		return result;
	}

	/** ================================================= */

	public List<ChuPrelevement> listByIdTumeur(Integer idTumeur) {

		List<ChuPrelevement> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuPrelevement.class)
				.createAlias("chuPhaseTumeur", "chuPhaseTumeur")
				.createAlias("chuPhaseTumeur.chuTumeur", "chuTumeur")
				.add(Restrictions.eq("chuTumeur.idTumeur", idTumeur))
				.addOrder(Order.asc("datePrelevement"))
				.addOrder(Order.asc("idPrelevement"))
				.list();

		this.populateDependencies(result);

		return result;
	}

	/** ================================================= */


	public List<ChuPrelevement> listByIdPatient(String idPatient) {
		List<ChuPrelevement> result = sessionFactory.getCurrentSession()
				.createCriteria(ChuPrelevement.class)
				.createAlias("chuPhaseTumeur", "chuPhaseTumeur")
				.createAlias("chuPhaseTumeur.chuTumeur", "chuTumeur")
				.createAlias("chuTumeur.chuPatient", "chuPatient")
				.add(Restrictions.eq("chuPatient.idPatient", idPatient))
				.addOrder(Order.asc("datePrelevement"))
				.addOrder(Order.asc("idPrelevement"))
				.list();
		this.populateDependencies(result);

		return result;
	}

	/** ================================================= */

	public void save(ChuPrelevement prelevement) {
		sessionFactory.getCurrentSession().save(prelevement);
	}

	/** ================================================= */

	public void update(ChuPrelevement prelevement) {
		sessionFactory.getCurrentSession().update(prelevement);
	}

	/** ================================================= */

	public void saveOrUpdate(ChuPrelevement prelevement) {
		sessionFactory.getCurrentSession().saveOrUpdate(prelevement);
	}

	/** ================================================= */

	private void populateDependencies(List<ChuPrelevement> list) {


		for (ChuPrelevement prel : list) {
			this.populateDependencies(prel);
		}
	}

	/** ================================================= */

	private void populateDependencies(ChuPrelevement prel) {
		if (prel!=null) {
			Hibernate.initialize(prel.getChuMorphologie());
			Hibernate.initialize(prel.getChuTypePrelevement());
			Hibernate.initialize(prel.getChuPhaseTumeur());
			Hibernate.initialize(prel.getChuPhaseTumeur().getChuTypePhase());
			Hibernate.initialize(prel.getChuPhaseTumeur().getChuTumeur());
		}
	}

	/** ================================================= */

}
