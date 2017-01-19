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

import dbchubreast_web.entity.ChuPrelevementBiomarqueur;





@Transactional
@Repository

@SuppressWarnings("unchecked")
public class ChuPrelevementBiomarqueurDaoImpl extends BaseDao implements ChuPrelevementBiomarqueurDao {

	@Autowired
	private SessionFactory sessionFactory;

	/** ================================================= */

	public List<ChuPrelevementBiomarqueur> list(Integer idPrelevement) {
		List<ChuPrelevementBiomarqueur> list = sessionFactory.getCurrentSession()
				.createCriteria(ChuPrelevementBiomarqueur.class)
				.createAlias("chuPrelevement", "chuPrelevement")
				.createAlias("chuBiomarqueur", "chuBiomarqueur")
				.add(Restrictions.eq("chuPrelevement.idPrelevement", idPrelevement))
				.addOrder(Order.asc("chuBiomarqueur.ordreAffichage"))
				.list();

		this.populateDependencies(list);

		return list;
	}

	/** ================================================= */


	public List<ChuPrelevementBiomarqueur> list(List<Integer> listIdPrelevements) {

		if (listIdPrelevements==null || listIdPrelevements.isEmpty()) {
			return null;
		}
		
		List<ChuPrelevementBiomarqueur> list = sessionFactory.getCurrentSession()
				.createCriteria(ChuPrelevementBiomarqueur.class)
				.createAlias("chuPrelevement", "chuPrelevement")
				.createAlias("chuBiomarqueur", "chuBiomarqueur")
				.add(Restrictions.in("chuPrelevement.idPrelevement", listIdPrelevements))
				.addOrder(Order.asc("chuBiomarqueur.ordreAffichage"))
				.list();

		this.populateDependencies(list);

		return list;
	}

	/** ================================================= */

	private void populateDependencies(List<ChuPrelevementBiomarqueur> list) {
		for (ChuPrelevementBiomarqueur prelBio : list) {
			this.populateDependencies(prelBio);
		}
	}

	/** ================================================= */

	private void populateDependencies(ChuPrelevementBiomarqueur prelBio) {
		Hibernate.initialize(prelBio.getChuPrelevement());
		Hibernate.initialize(prelBio.getChuBiomarqueur());
	}

	/** ================================================= */
}
