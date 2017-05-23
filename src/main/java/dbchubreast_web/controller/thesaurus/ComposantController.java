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
package dbchubreast_web.controller.thesaurus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import dbchubreast_web.controller.BaseController;
import dbchubreast_web.entity.ChuComposantTraitement;
import dbchubreast_web.entity.ChuMethodeTraitement;
import dbchubreast_web.entity.ChuProtocoleTraitement;
import dbchubreast_web.form.FormComposant;
import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.business.ChuComposantTraitementService;
import dbchubreast_web.service.business.ChuMethodeTraitementService;
import dbchubreast_web.service.business.ChuProtocoleTraitementService;
import dbchubreast_web.service.form.FormComposantService;
import dbchubreast_web.validator.FormComposantValidator;

@Controller
@RequestMapping(value = "/thesaurus")
public class ComposantController extends BaseController {

	@Autowired
	private ChuMethodeTraitementService methodeTraitementService;

	@Autowired
	private ChuProtocoleTraitementService protocoleTraitementService;
	
	@Autowired
	private ChuComposantTraitementService composantTraitementService;

	@Autowired
	private AppLogService logService;

	@Autowired
	private FormComposantService formComposantService;

	@Autowired
	private FormComposantValidator formComposantValidator;
	
	/** ====================================================================================== */

	@RequestMapping(value = "/composants", method = RequestMethod.GET)
	public String listPatientTraitements(Model model, HttpServletRequest request) {

		List<ChuMethodeTraitement> listMethodes = methodeTraitementService.listWithDependencies();
		model.addAttribute("listMethodes", listMethodes);

		return "thesaurus/composant/list";
	}
	/** ====================================================================================== */

	@RequestMapping(value = { "/composant/{idComposant}/update" }, method = RequestMethod.GET)
	public String showUpdateForm(Model model, 
			@PathVariable Integer idComposant, 
			HttpServletRequest request) {

		// Form 
		ChuComposantTraitement composant = composantTraitementService.find(idComposant);
		FormComposant formComposant = formComposantService.getForm(composant);
		model.addAttribute("formComposant", formComposant);

		return "thesaurus/composant/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "methode/{idMethode}/composants/add" }, method = RequestMethod.GET)
	public String showAddForm(Model model, 
			@PathVariable Integer idMethode, 
			HttpServletRequest request) {

		// Form 
		ChuMethodeTraitement methode = methodeTraitementService.find(idMethode);
		FormComposant formComposant  = new FormComposant(methode.getIdMethode(), methode.getNom());
		model.addAttribute("formComposant", formComposant);

		return "thesaurus/composant/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/composant/update" }, method = RequestMethod.GET)
	public String redirectForm() {

		// POST/REDIRECT/GET
		return "redirect:/thesaurus/composants";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/composant/update" }, method = RequestMethod.POST)
	public String updateForm(Model model,
			@RequestParam(value = "button", required = false) String button,
			@ModelAttribute("formComposant") FormComposant formComposant, BindingResult result,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		logger.debug("{}", formComposant);

		if (button!=null && button.equals("save")) {
			formComposantValidator.validate(formComposant, result);
			if (result.hasErrors()) {
				return "thesaurus/composant/form";
			}
			else {
				redirectAttributes.addFlashAttribute("css", "success");
				if (formComposant.isNew()) {
					redirectAttributes.addFlashAttribute("msg", "Un nouveau composant a été ajouté avec succès !");
				} else {
					redirectAttributes.addFlashAttribute("msg",
							"La modification du composant a été effectuée avec succès !");
				}

				formComposantService.saveOrUpdateForm(formComposant);
			}

		}

		// POST/REDIRECT/GET
		return "redirect:/thesaurus/composants";

	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/composant/{idComposant}/delete" }, method = {RequestMethod.GET, RequestMethod.POST})
	public String showDeleteForm(Model model, 
			@PathVariable Integer idComposant, 
			@RequestParam(value = "button", required = false) String button,
			final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		ChuComposantTraitement composant = composantTraitementService.find(idComposant);
		model.addAttribute("composant", composant);
		List<ChuProtocoleTraitement> listProtocoles = protocoleTraitementService.listByComposant(idComposant);
		model.addAttribute("listProtocoles", listProtocoles);
		
		// ===== Bouton supprimer =====

		if (button!=null && button.equals("delete")) {

				composantTraitementService.delete(composant);
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Le protocole " + composant.getNomInternational() + " a été supprimé !");
				logService.log("Suppression du protocole " + idComposant);
				return "redirect:/thesaurus/composants";
	
		}

		// ===== Bouton annuler =====

		if (button!=null && button.equals("cancel")) {
			return "redirect:/thesaurus/composants";
		}

		return "thesaurus/composant/delete";
	}

	/** ====================================================================================== */

}
