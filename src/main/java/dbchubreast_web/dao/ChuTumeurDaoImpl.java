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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.ChuTumeur;

@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuTumeurDaoImpl extends BaseDao implements ChuTumeurDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public ChuTumeur find(Integer idTumeur) {

		ChuTumeur tumeur = (ChuTumeur) sessionFactory.getCurrentSession().createCriteria(ChuTumeur.class)
				.add(Restrictions.eq("idTumeur", idTumeur)).uniqueResult();

		return tumeur;
	}

	/** ================================================= */

	public ChuTumeur findWithDependencies(Integer idTumeur) {

		ChuTumeur tumeur = (ChuTumeur) sessionFactory.getCurrentSession().createCriteria(ChuTumeur.class)
				.add(Restrictions.eq("idTumeur", idTumeur)).uniqueResult();

		Hibernate.initialize(tumeur.getChuEvolution());
		Hibernate.initialize(tumeur.getChuTopographie());
		Hibernate.initialize(tumeur.getChuPatient());

		return tumeur;
	}

	/** ================================================= */

	public ChuTumeur findByIdPhaseWithDependencies(Integer idPhase) {

		ChuTumeur tumeur = (ChuTumeur) sessionFactory.getCurrentSession().createCriteria(ChuTumeur.class)
				.createAlias("chuPhaseTumeurs", "chuPhaseTumeurs")
				.add(Restrictions.eq("chuPhaseTumeurs.idPhase", idPhase)).uniqueResult();

		this.populateDependencies(tumeur);

		return tumeur;
	}

	/** ================================================= */

	public List<ChuTumeur> findAsListWithDependencies(Integer idTumeur) {

		List<ChuTumeur> list = sessionFactory.getCurrentSession().createCriteria(ChuTumeur.class)
				.add(Restrictions.eq("idTumeur", idTumeur)).list();

		this.populateDependencies(list);

		return list;
	}

	/** ================================================= */

	public List<ChuTumeur> find(String idPatient) {

		List<ChuTumeur> list = sessionFactory.getCurrentSession().createCriteria(ChuTumeur.class)
				.createAlias("chuPatient", "chuPatient").add(Restrictions.eq("chuPatient.idPatient", idPatient))
				.addOrder(Order.asc("idTumeur")).list();

		return list;
	}

	/** ================================================= */

	public List<ChuTumeur> findWithDependencies(String idPatient) {

		List<ChuTumeur> list = sessionFactory.getCurrentSession().createCriteria(ChuTumeur.class)
				.createAlias("chuPatient", "chuPatient").add(Restrictions.eq("chuPatient.idPatient", idPatient))
				.addOrder(Order.asc("idTumeur")).list();

		this.populateDependencies(list);

		return list;
	}

	/** ================================================= */

	public List<ChuTumeur> findInAttributesWithDependencies(String text) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ChuTumeur.class)
				.createAlias("chuEvolution", "chuEvolution").createAlias("chuTopographie", "chuTopographie")
				.createAlias("chuPatient", "chuPatient");

		Criterion criterion = Restrictions.or(
				Restrictions.like("chuTopographie.idTopographie", "%" + text + "%").ignoreCase(),
				Restrictions.like("chuEvolution.code", "%" + text + "%").ignoreCase(),
				Restrictions.like("chuPatient.idPatient", "%" + text + "%").ignoreCase(),
				Restrictions.like("chuPatient.prenom", "%" + text + "%").ignoreCase(),
				Restrictions.like("chuPatient.nom", "%" + text + "%").ignoreCase(),
				Restrictions.like("chuPatient.rcp", "%" + text + "%").ignoreCase());

		criteria.add(criterion);

		criteria.addOrder(Order.asc("idTumeur"));

		List<ChuTumeur> list = criteria.list();

		this.populateDependencies(list);

		return list;
	}

	/** ================================================= */

	public List<ChuTumeur> list() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(ChuTumeur.class)
				.addOrder(Order.asc("idTumeur"));
		return crit.list();
	}

	/** ================================================= */

	public List<ChuTumeur> listWithDependencies() {
		List<ChuTumeur> list = this.list();
		this.populateDependencies(list);
		return list;
	}

	/** ================================================= */

	public void save(ChuTumeur tumeur) {
		sessionFactory.getCurrentSession().save(tumeur);
	}

	/** ================================================= */

	public void update(ChuTumeur tumeur) {
		sessionFactory.getCurrentSession().update(tumeur);
	}

	/** ================================================= */

	private void populateDependencies(ChuTumeur tumeur) {
		if (tumeur != null) {
			Hibernate.initialize(tumeur.getChuEvolution());
			Hibernate.initialize(tumeur.getChuTopographie());
			Hibernate.initialize(tumeur.getChuPatient());
		}
	}

	/** ================================================= */

	private void populateDependencies(List<ChuTumeur> list) {
		for (ChuTumeur tumeur : list) {
			this.populateDependencies(tumeur);
		}
	}

	/** ================================================= */
}
