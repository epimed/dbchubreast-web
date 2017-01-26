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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.AppMenu;
import dbchubreast_web.entity.AppRole;
import dbchubreast_web.entity.AppUser;


@Transactional
@Repository
@SuppressWarnings("unchecked")
public class AppMenuDaoImpl extends BaseDao implements AppMenuDao {


	@Autowired
	private SessionFactory sessionFactory;

	String dbname = "db_chu_breast";


	/** ================================================= */


	public List<AppMenu> loadMenuForUser(AppUser user) {


		logger.debug("User {}", user);

		List<String> listIdRoles = new ArrayList<String>();
		for (AppRole role : user.getAppRoles()) {
			listIdRoles.add(role.getIdRole());
		}
		
		Criteria crit = sessionFactory.getCurrentSession()
				.createCriteria(AppMenu.class)
				.createAlias("subMenus", "subMenus")
				.createAlias("subMenus.appRoles", "subRoles")
				.add(Restrictions.in("subRoles.idRole", listIdRoles))
				.addOrder(Order.asc("level"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				;
		
		List<AppMenu> listRootMenus = crit.list();
		
		for (AppMenu rootMenu : listRootMenus) {
			List<AppMenu> subMenus = this.getAthorizedSubMenus(listIdRoles, rootMenu.getIdMenu());
			rootMenu.setSubMenus(subMenus);
		}

		logger.debug("listRootMenus {}", listRootMenus);

		return listRootMenus;
	}

	/** ================================================= */
	
	
	private List<AppMenu> getAthorizedSubMenus (List<String> listIdRoles, Integer idRootMenu) {
		Criteria crit = sessionFactory.getCurrentSession()
				.createCriteria(AppMenu.class)
				.createAlias("rootMenu", "rootMenu")
				.createAlias("appRoles", "appRoles")
				.add(Restrictions.in("appRoles.idRole", listIdRoles))
				.add(Restrictions.eq("rootMenu.idMenu", idRootMenu))
				.addOrder(Order.asc("level"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				;
		return crit.list();
	}
	
	/** ================================================= */
}
