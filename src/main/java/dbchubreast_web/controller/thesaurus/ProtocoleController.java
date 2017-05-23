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
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.form.FormProtocole;
import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.business.ChuComposantTraitementService;
import dbchubreast_web.service.business.ChuMethodeTraitementService;
import dbchubreast_web.service.business.ChuProtocoleTraitementService;
import dbchubreast_web.service.business.ChuTraitementService;
import dbchubreast_web.service.form.FormProtocoleService;
import dbchubreast_web.validator.FormProtocoleValidator;

@Controller
@RequestMapping(value = "/thesaurus")
public class ProtocoleController extends BaseController {


	@Autowired
	private ChuTraitementService traitementService;
	
	@Autowired
	private ChuMethodeTraitementService methodeTraitementService;

	@Autowired
	private ChuProtocoleTraitementService protocoleTraitementService;

	@Autowired
	private ChuComposantTraitementService composantTraitementService;

	@Autowired
	private FormProtocoleService formProtocoleService;

	@Autowired
	private FormProtocoleValidator formProtocoleValidator;
	
	@Autowired
	private AppLogService logService;

	/** ====================================================================================== */

	@RequestMapping(value = "/protocoles", method = RequestMethod.GET)
	public String listPatientTraitements(Model model, HttpServletRequest request) {

		List<ChuMethodeTraitement> listMethodes = methodeTraitementService.listWithDependencies();
		model.addAttribute("listMethodes", listMethodes);

		return "thesaurus/protocole/list";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/protocole/{idProtocole}/update" }, method = RequestMethod.GET)
	public String showUpdateForm(Model model, 
			@PathVariable Integer idProtocole, 
			HttpServletRequest request) {

		// Form 
		ChuProtocoleTraitement protocole = protocoleTraitementService.find(idProtocole);
		FormProtocole formProtocole = formProtocoleService.getForm(protocole);
		model.addAttribute("formProtocole", formProtocole);
		List<ChuComposantTraitement> listComposants = composantTraitementService.listByMethode(formProtocole.getIdMethode());
		model.addAttribute("listComposants", listComposants);

		return "thesaurus/protocole/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "methode/{idMethode}/protocoles/add" }, method = RequestMethod.GET)
	public String showAddForm(Model model, 
			@PathVariable Integer idMethode, 
			HttpServletRequest request) {

		// Form 
		ChuMethodeTraitement methode = methodeTraitementService.find(idMethode);
		FormProtocole formProtocole = new FormProtocole(methode.getIdMethode(), methode.getNom());
		model.addAttribute("formProtocole", formProtocole);
		List<ChuComposantTraitement> listComposants = composantTraitementService.listByMethode(formProtocole.getIdMethode());
		model.addAttribute("listComposants", listComposants);

		return "thesaurus/protocole/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/protocole/update" }, method = RequestMethod.GET)
	public String redirectForm() {

		// POST/REDIRECT/GET
		return "redirect:/thesaurus/protocoles";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/protocole/update" }, method = RequestMethod.POST)
	public String updateForm(Model model,
			@RequestParam(value = "button", required = false) String button,
			@ModelAttribute("formProtocole") FormProtocole formProtocole, BindingResult result,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		logger.debug("Protocole {}",formProtocole);

		if (button!=null && button.equals("save")) {
			formProtocoleValidator.validate(formProtocole, result);
			if (result.hasErrors()) {
				List<ChuComposantTraitement> listComposants = composantTraitementService.listByMethode(formProtocole.getIdMethode());
				model.addAttribute("listComposants", listComposants);
				return "thesaurus/protocole/form";
			}
			else {
				redirectAttributes.addFlashAttribute("css", "success");
				if (formProtocole.isNew()) {
					redirectAttributes.addFlashAttribute("msg", "Un nouveau protocole a été ajouté avec succès !");
				} else {
					redirectAttributes.addFlashAttribute("msg",
							"La modification du protocole a été effectuée avec succès !");
				}

				formProtocoleService.saveOrUpdateForm(formProtocole);
			}

		}

		// POST/REDIRECT/GET
		return "redirect:/thesaurus/protocoles";

	}
	
	/** ====================================================================================== */

	@RequestMapping(value = { "/protocole/{idProtocole}/delete" }, method = {RequestMethod.GET, RequestMethod.POST})
	public String showDeleteForm(Model model, 
			@PathVariable Integer idProtocole, 
			@RequestParam(value = "button", required = false) String button,
			final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		
		ChuProtocoleTraitement protocole = protocoleTraitementService.find(idProtocole);
		model.addAttribute("protocole", protocole);
		
		List<ChuTraitement> listTraitements = traitementService.listByIdProtocole(protocole.getIdProtocole());
		model.addAttribute("listTraitements", listTraitements);
		
		
		// ===== Bouton supprimer =====
		
		if (button!=null && button.equals("delete")) {
		
			
			if (listTraitements==null || listTraitements.isEmpty()) {
				// OK
				protocoleTraitementService.delete(protocole);
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Le protocole " + protocole.getNom() + " a été supprimé !");
				logService.log("Suppression du protocole " + idProtocole);
				return "redirect:/thesaurus/protocoles";
			}
			else {
				// KO
				redirectAttributes.addFlashAttribute("css", "danger");
				redirectAttributes.addFlashAttribute("msg", "Le protocole " + protocole.getNom() + " ne peut pas être supprimé !");
				logService.log("Le protocole " + idProtocole + " ne peut pas être supprimé");
				return "redirect:/thesaurus/protocoles";
			}	
		}
		
		// ===== Bouton annuler =====
		
		if (button!=null && button.equals("cancel")) {
			return "redirect:/thesaurus/protocoles";
		}
		
		return "thesaurus/protocole/delete";
	}

	/** ====================================================================================== */

}
