/**
 * EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * 
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package dbchubreast_web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dbchubreast_web.entity.AppRole;
import dbchubreast_web.entity.AppUser;
import dbchubreast_web.form.FormPasswordEncoder;
import dbchubreast_web.form.FormUtilisateur;
import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.business.AppRoleService;
import dbchubreast_web.service.business.AppUserService;
import dbchubreast_web.service.form.FormUtilisateurService;

@Controller
public class AdminController extends BaseController {

	@Autowired
	FormPasswordEncoder formPasswordEncoder;

	@Autowired
	AppUserService userService;

	@Autowired
	AppRoleService roleService;

	@Autowired
	FormUtilisateurService formUtilisateurService;

	@Autowired
	AppLogService logService;

	/** ====================================================================================== */

	@RequestMapping(value = { "/admin/users" }, method = RequestMethod.GET)
	public String listUsers(Model model) {

		List<AppUser> listUtilisateurs = userService.list();
		model.addAttribute("listUtilisateurs", listUtilisateurs);

		return "admin/list";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/admin/user/{idUser}" }, method = RequestMethod.GET)
	public String showUser(Model model, @PathVariable Integer idUser) {

		AppUser utilisateur = userService.findById(idUser);
		logger.debug("{}", utilisateur);
		model.addAttribute("utilisateur", utilisateur);

		return "admin/show";
	}


	/** ====================================================================================== */

	@RequestMapping(value = { "/admin/user/add" }, method = RequestMethod.GET)
	public String showAddUserForm(Model model) {

		FormUtilisateur formUtilisateur = new FormUtilisateur();
		List<AppRole> listRoles = roleService.list();

		model.addAttribute("formUtilisateur", formUtilisateur);
		model.addAttribute("listRoles", listRoles);

		return "admin/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/admin/user/{idUser}/delete" }, method = RequestMethod.GET)
	public String deleteUserForm(Model model, @PathVariable Integer idUser) {

		AppUser utilisateur = userService.findById(idUser);
		logger.debug("Supression {}", utilisateur);

		userService.delete(utilisateur);

		// POST/REDIRECT/GET
		return "redirect:/admin/users";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/admin/user/{idUser}/update" }, method = RequestMethod.GET)
	public String showUpdateUserForm(Model model, @PathVariable Integer idUser) {

		AppUser utilisateur = userService.findById(idUser);
		logger.debug("{}", utilisateur);

		FormUtilisateur formUtilisateur = formUtilisateurService.getForm(utilisateur);
		List<AppRole> listRoles = roleService.list();

		model.addAttribute("formUtilisateur", formUtilisateur);
		model.addAttribute("listRoles", listRoles);

		return "admin/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/admin/user/update" }, method = RequestMethod.GET)
	public String redirectListUsers() {
		// POST/REDIRECT/GET
		return "redirect:/admin/users";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/admin/user/update" }, method = RequestMethod.POST)
	public String updateUserForm(Model model, 
			@RequestParam(value = "button", required = false) String button,
			@ModelAttribute("formUtilisateur") @Valid FormUtilisateur formUtilisateur,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {


		// === Bouton "reinitialiser" ===

		if (button != null && button.equals("reset")) {
			if (formUtilisateur!=null && formUtilisateur.getIdUser()!=null) {
				return "/admin/user/" + formUtilisateur.getIdUser() + "/update";
			}
			return "redirect:/admin/user/add";
		}


		// === Bouton "enregistrer" ===

		if (button != null && button.equals("save")) {
			if (result.hasErrors()) {
				List<AppRole> listRoles = roleService.list();
				model.addAttribute("formUtilisateur", formUtilisateur);
				model.addAttribute("listRoles", listRoles);
				return "admin/form";
			}
			redirectAttributes.addFlashAttribute("css", "success");
			if (formUtilisateur.isNew()) {
				redirectAttributes.addFlashAttribute("msg", "Le nouvel utilisateur a été ajouté avec succès !");
			} 
			else {
				redirectAttributes.addFlashAttribute("msg", "La modification a été effectuée avec succès !");
			}
			formUtilisateurService.saveOrUpdateForm(formUtilisateur);
		}

		// === Bouton "annuler" ===

		if (button!=null && button.equals("cancel")) {
			return "redirect:/admin/users";
		}

		// POST/REDIRECT/GET
		return "redirect:/admin/user/" + formUtilisateur.getIdUser();
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/admin/password" }, method = RequestMethod.GET)
	public String showGenerateHashedPasswordForm(
			Model model,
			HttpServletRequest request
			) {

		model.addAttribute("formPasswordEncoder", formPasswordEncoder);

		return "admin/generatePassword";
	}

	/** ====================================================================================== */


	@RequestMapping(value = { "/admin/password" }, method = RequestMethod.POST)
	public String generateHashedPassword(
			Model model,
			@ModelAttribute("formPasswordEncoder") @Valid FormPasswordEncoder formPasswordEncoder,
			BindingResult result,
			HttpServletRequest request
			) {

		if (!result.hasErrors()) {
			formPasswordEncoder.generate(10);
		}
		else {
			formPasswordEncoder.clear();
		}
		model.addAttribute("formPasswordEncoder", formPasswordEncoder);

		return "admin/generatePassword";
	}
	/** ====================================================================================== */
}
