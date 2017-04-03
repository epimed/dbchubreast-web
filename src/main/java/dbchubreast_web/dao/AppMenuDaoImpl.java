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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dbchubreast_web.entity.AppMenu;
import dbchubreast_web.entity.AppRole;
import dbchubreast_web.entity.AppUser;


@Transactional
@Repository
public class AppMenuDaoImpl extends BaseDao implements AppMenuDao {

	@Autowired
	private SessionFactory sessionFactory;


	/** ================================================= */


	public List<AppMenu> loadMenuForUser(AppUser user) {

		logger.debug("loadMenuForUser:  user={}", user);

		List<String> listIdRoles = new ArrayList<String>();
		for (AppRole role : user.getAppRoles()) {
			listIdRoles.add(role.getIdRole());
		}


		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<AppMenu> criteria = builder.createQuery(AppMenu.class);
		Root<AppMenu> root = criteria.from(AppMenu.class);
		Join<AppMenu, AppMenu> submenus = root.join("subMenus");
		Join<AppMenu, AppRole> roles = submenus.join("appRoles");

		criteria.select(root).distinct(true).where(
				builder.in(roles.get("idRole")).value(listIdRoles)
				);

		criteria.orderBy(builder.asc(root.get("level")));

		List<AppMenu> listRootMenus =  sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

		for (AppMenu rootMenu : listRootMenus) {
			List<AppMenu> subMenus = this.getAthorizedSubMenus(listIdRoles, rootMenu.getIdMenu());
			rootMenu.setSubMenus(subMenus);
		}

		return listRootMenus;
	}

	/** ================================================= */

	private List<AppMenu> getAthorizedSubMenus (List<String> listIdRoles, Integer idRootMenu) {

		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<AppMenu> criteria = builder.createQuery(AppMenu.class);
		Root<AppMenu> root = criteria.from(AppMenu.class);
		Join<AppMenu, AppRole> roles = root.join("appRoles");
		Join<AppMenu, AppMenu> rootMenu = root.join("rootMenu");
		criteria.select(root).distinct(true).where(
				builder.and(
						builder.in(roles.get("idRole")).value(listIdRoles),
						builder.equal(rootMenu.get("idMenu"), idRootMenu)
						)
				);

		criteria.orderBy(builder.asc(root.get("level")));

		return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

	}

	/** ================================================= */
}
