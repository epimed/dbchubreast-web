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
package dbchubreast_web.controller;

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

import dbchubreast_web.entity.ChuMethodeTraitement;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuProtocoleTraitement;
import dbchubreast_web.entity.ChuReponse;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.FormTraitement;
import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.business.ChuMethodeTraitementService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;
import dbchubreast_web.service.business.ChuProtocoleTraitementService;
import dbchubreast_web.service.business.ChuReponseService;
import dbchubreast_web.service.business.ChuTraitementService;
import dbchubreast_web.service.business.ChuTumeurService;
import dbchubreast_web.service.form.FormTraitementService;
import dbchubreast_web.validator.FormTraitementValidator;

@Controller
public class TraitementController extends BaseController {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuTumeurService tumeurService;

	@Autowired
	private ChuPhaseTumeurService phaseTumeurService;

	@Autowired
	private ChuTraitementService traitementService;

	@Autowired
	private ChuMethodeTraitementService methodeTraitementService;

	@Autowired
	private ChuProtocoleTraitementService protocoleTraitementService;

	@Autowired
	private ChuReponseService reponseService;

	@Autowired
	private FormTraitementService formTraitementService;

	@Autowired
	private FormTraitementValidator formTraitementValidator;

	@Autowired
	private AppLogService logService;

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/{idPatient}/traitements", method = RequestMethod.GET)
	public String listPatientTraitements(Model model, @PathVariable String idPatient, HttpServletRequest request) {

		logService.log("Affichage de traitements du patient " + idPatient);

		ChuPatient patient = patientService.find(idPatient);
		List<ChuTraitement> listTraitements = traitementService.listByIdPatient(idPatient);
		List<ChuTumeur> listTumeurs = tumeurService.find(idPatient);

		model.addAttribute("patient", patient);
		model.addAttribute("listTraitements", listTraitements);
		model.addAttribute("listTumeurs", listTumeurs);

		return "traitement/list";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/traitement" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String searchTraitement(Model model, @RequestParam(value = "idPatient", required = false) String idPatient,
			HttpServletRequest request) {

		logService.log("Affichage de traitement du patient " + idPatient);

		if (idPatient != null) {
			ChuPatient patient = patientService.find(idPatient);
			model.addAttribute("patient", patient);
			List<ChuTraitement> listTraitements = traitementService.listByIdPatient(idPatient);
			model.addAttribute("listTraitements", listTraitements);
			List<ChuTumeur> listTumeurs = tumeurService.find(idPatient);
			model.addAttribute("listTumeurs", listTumeurs);
		}

		List<ChuPatient> listPatients = patientService.list();
		model.addAttribute("idPatient", idPatient);
		model.addAttribute("listPatients", listPatients);

		return "traitement/search";
	}


	/** ====================================================================================== */

	@RequestMapping(value = { "/traitement/{idTraitement}" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String showTraitement(Model model, @PathVariable Integer idTraitement, HttpServletRequest request) {

		logService.log("Affichage du traitement " + idTraitement);

		// Patient
		ChuPatient patient = patientService.findByIdTraitement(idTraitement);
		model.addAttribute("patient", patient);

		// Tumeurs
		List<ChuTumeur> listTumeurs = tumeurService.find(patient.getIdPatient());
		model.addAttribute("listTumeurs", listTumeurs);
		
		// Traitements
		List<ChuTraitement> listTraitements = traitementService.listByIdPatient(patient.getIdPatient());
		model.addAttribute("listTraitements", listTraitements);

		// Traitement
		ChuTraitement traitement = traitementService.find(idTraitement);
		model.addAttribute("traitement", traitement);

		return "traitement/show";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/traitement/{idTraitement}/update" }, method = RequestMethod.GET)
	public String showUpdateForm(Model model, @PathVariable Integer idTraitement, HttpServletRequest request) {

		logService.log("Affichage d'un formulaire pour modifier le traitement " + idTraitement);

		// Form traitement
		ChuTraitement traitement = traitementService.find(idTraitement);
		FormTraitement formTraitement = formTraitementService.getForm(traitement);

		populateFormTraitement(formTraitement, model);

		return "traitement/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/patient/{idPatient}/traitement/add" }, method = RequestMethod.GET)
	public String showAddForm(Model model, @PathVariable String idPatient, HttpServletRequest request) {

		logService.log("Affichage d'un formulaire pour ajouter un traitement au patient " + idPatient);

		// Form traitement
		FormTraitement formTraitement = new FormTraitement(idPatient);
		populateFormTraitement(formTraitement, model);

		return "traitement/form";
	}


	/** ====================================================================================== */


	@RequestMapping(value = { "/traitement/update" }, method = RequestMethod.GET)
	public String redirectForm() {

		// POST/REDIRECT/GET
		return "redirect:/traitement";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/traitement/update" }, method = RequestMethod.POST)
	public String showDetailForm(Model model,
			@RequestParam(value = "button", required = false) String button,
			@ModelAttribute("formTraitement") FormTraitement formTraitement, BindingResult result,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		// === Mise a jour de la forme sans bouton  ===

		if (button == null) {
			this.populateFormTraitement(formTraitement, model);
			return "traitement/form";
		}
		

		// === Bouton "reinitialiser" ===

		if (button != null && button.equals("reset")) {
			ChuTraitement traitement = traitementService.find(formTraitement.getIdTraitement());
			this.populateFormTraitement(formTraitementService.getForm(traitement), model);
			return "traitement/form";
		}


		// === Bouton "enregistrer" ===

		if (button != null && button.equals("save")) {

			formTraitementValidator.validate(formTraitement, result);

			if (result.hasErrors()) {
				logService.log("Modification échouée de traitement");
				this.populateFormTraitement(formTraitement, model);
				return "traitement/form";
			}

			else {
				logService.log("Modification validée de traitement");
				redirectAttributes.addFlashAttribute("css", "success");
				if (formTraitement.isNew()) {
					redirectAttributes.addFlashAttribute("msg", "Un nouveau traitement a été ajouté avec succès !");
				} else {
					redirectAttributes.addFlashAttribute("msg",
							"La modification du traitement a été effectuée avec succès !");
				}

				formTraitementService.saveOrUpdateForm(formTraitement);
			}

		}
		
		// === Bouton "annuler" ===
		
		if (button!=null && button.equals("cancel")) {
			return "redirect:/patient/" + formTraitement.getIdPatient() + "/traitements";
		}

		// POST/REDIRECT/GET
		return "redirect:/traitement/" + formTraitement.getIdTraitement();

	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/traitement/{idTraitement}/delete" }, method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteTraitement(Model model, 
			@PathVariable Integer idTraitement, 
			@RequestParam(value = "button", required = false) String button,
			final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		logService.log("Affichage d'un formulaire pour supprimer le traitement " + idTraitement);

		// Patient
		ChuPatient patient = patientService.findByIdTraitement(idTraitement);
		model.addAttribute("patient", patient);

		// Traitements
		List<ChuTraitement> listTraitements = traitementService.listByIdPatient(patient.getIdPatient());
		model.addAttribute("listTraitements", listTraitements);

		// Traitement
		ChuTraitement traitement = traitementService.find(idTraitement);
		model.addAttribute("traitement", traitement);

		if (button!=null && button.equals("delete")) {
			traitementService.delete(traitement);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Le traitement " + idTraitement + " a été supprimé !");
			logService.log("Suppression du traitement " + idTraitement);
			return "redirect:/patient/" + patient.getIdPatient() + "/traitements";
		}
		
		if (button!=null && button.equals("cancel")) {
			return "redirect:/patient/" + patient.getIdPatient() + "/traitements";
		}
		
		return "traitement/delete";
	}

	/** ====================================================================================== */

	private void populateFormTraitement(FormTraitement formTraitement, Model model) {

		// Patient
		ChuPatient patient = patientService.find(formTraitement.getIdPatient());
		model.addAttribute("patient", patient);

		// Traitements
		List<ChuTraitement> listTraitements = traitementService.listByIdPatient(patient.getIdPatient());
		model.addAttribute("listTraitements", listTraitements);

		// Tumeurs
		List<ChuTumeur> listTumeurs = tumeurService.find(formTraitement.getIdPatient());
		model.addAttribute("listTumeurs", listTumeurs);

		// Tumeur pre-selectionnee si unique
		if (formTraitement.getIdTumeur() == null && listTumeurs != null && listTumeurs.size() == 1) {
			formTraitement.setIdTumeur(listTumeurs.get(0).getIdTumeur());
		}

		// Phases tumeur
		if (formTraitement.getIdTumeur() != null) {
			List<ChuPhaseTumeur> listPhases = phaseTumeurService.listWithDependencies(formTraitement.getIdTumeur());
			model.addAttribute("listPhases", listPhases);

			// Phase pre-selectionnee si unique
			if (formTraitement.getIdPhase() == null && listPhases != null && listPhases.size() == 1) {
				formTraitement.setIdPhase(listPhases.get(0).getIdPhase());
			}
		}

		// Methode 
		List<ChuMethodeTraitement> listMethodes = methodeTraitementService.list();
		model.addAttribute("listMethodes", listMethodes);

		// Protocole
		if (formTraitement.getIdMethode() != null) {
			List<ChuProtocoleTraitement> listProtocoles = protocoleTraitementService.listByMethode(formTraitement.getIdMethode());
			model.addAttribute("listProtocoles", listProtocoles);

			// Protocole pre-selectionne si unique
			if (formTraitement.getIdProtocole() == null && listProtocoles != null && listProtocoles.size() == 1) {
				formTraitement.setIdProtocole(listProtocoles.get(0).getIdProtocole());
			}
		}

		// Reponse
		List<ChuReponse> listReponses = reponseService.list();
		model.addAttribute("listReponses", listReponses);

		// Form
		model.addAttribute("formTraitement", formTraitement);

	}
	
	/** ====================================================================================== */
	
}
