/**
 * EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * GNU GENERAL PUBLIC LICENSE
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package dbchubreast_web.service.form;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.AppRoleDao;
import dbchubreast_web.dao.AppUserDao;
import dbchubreast_web.entity.AppRole;
import dbchubreast_web.entity.AppUser;
import dbchubreast_web.form.FormPasswordEncoder;
import dbchubreast_web.form.FormUtilisateur;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.util.FormatService;

@Service
public class FormUtilisateurServiceImpl extends BaseService implements FormUtilisateurService {

	@Autowired
	private AppUserDao userDao;

	@Autowired
	private AppRoleDao roleDao;

	@Autowired
	FormPasswordEncoder formPasswordEncoder;

	@Autowired
	private FormatService formatService;
	
	/** =========================================================================================== */

	@Override
	public void saveOrUpdateForm(FormUtilisateur form) {

		AppUser u = null;
		if (form.isNew()) {
			u = new AppUser();
		}
		else {
			u = userDao.findById(form.getIdUser());
		}

		u.setLastName(form.getLastName().toUpperCase());
		
		if (form.getFirstName() != null) {
			u.setFirstName(formatService.formatFisrtName(form.getFirstName()));
		}

		u.setEmail(form.getEmail());
		u.setLogin(form.getLogin());
		u.setEnabled(form.isEnabled());

		// Password
		if (form.getPassword()!=null && !form.getPassword().isEmpty()) {
			formPasswordEncoder.clear();
			formPasswordEncoder.setClearpass(form.getPassword());
			formPasswordEncoder.generate(1);
			u.setPassword(formPasswordEncoder.getHashedpass().get(0));
		}

		// Roles

		if (u.getAppRoles()==null) {
			u.setAppRoles(new ArrayList<AppRole>());
		}
		else {
			u.getAppRoles().clear();
		}

		for(String idRole : form.getRoles()) {
			AppRole role = roleDao.findById(idRole);
			u.getAppRoles().add(role);
		}


		// === Save or update ===

		if (form.isNew()) {
			userDao.save(u);
			form.setIdUser(u.getIdUser());
		}
		else {
			userDao.update(u);
		}

	}

	/** =========================================================================================== */

	@Override
	public FormUtilisateur getForm(AppUser u) {

		FormUtilisateur form =new FormUtilisateur();

		form.setIdUser(u.getIdUser());
		form.setLastName(u.getLastName());
		form.setFirstName(u.getFirstName());
		form.setEmail(u.getEmail());
		form.setLogin(u.getLogin());
		form.setEnabled(u.isEnabled());

		form.setRoles(new ArrayList<String>());
		for (AppRole role : u.getAppRoles()) {
			form.getRoles().add(role.getIdRole());
		}

		return form;
	}


	/** =========================================================================================== */

}
