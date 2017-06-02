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

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.FormPatient;
import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuTumeurService;
import dbchubreast_web.service.form.FormPatientService;
import dbchubreast_web.validator.FormPatientValidator;

@Controller
public class PatientController extends BaseController {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private FormPatientService formPatientService;

	@Autowired
	private ChuTumeurService tumeurService;

	@Autowired
	private AppLogService logService;

	@Autowired
	FormPatientValidator formPatientValidator;

	/** ====================================================================================== */

	@RequestMapping(value = {"/patients", "patient"},  method = { RequestMethod.GET, RequestMethod.POST})
	public String showAllPatients(Model model, 
			@RequestParam(value = "text", required = false) String text,
			HttpServletRequest request) {	

		List<ChuPatient> listPatients;
		
		if (text!=null && !text.isEmpty()) {
			listPatients = patientService.findInAttributes(text);
			model.addAttribute("text", text);
		}
		else {
			listPatients = patientService.list();
		}

		logService.log("Affichage d'une liste de patients " + listPatients.size());

		model.addAttribute("listPatients", listPatients);

		return "patient/list";
	}


	/** ====================================================================================== */

	@RequestMapping(value = "/patient/{idPatient}", method = RequestMethod.GET)
	public String showPatientGet(Model model, @PathVariable String idPatient, HttpServletRequest request) {



		if (idPatient == null) {
			return "redirect:/patient";
		}

		logService.log("Affichage du patient " + idPatient);

		ChuPatient patient = patientService.find(idPatient);


		if (patient == null) {
			return "redirect:/patient";
		}

		model.addAttribute("patient", patient);

		return "patient/show";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/add", method = RequestMethod.GET)
	public String showAddPatientForm(Model model, HttpServletRequest request) {

		logService.log("Affichage d'un formulaire pour ajouter un patient");

		model.addAttribute("formPatient", new FormPatient());

		return "patient/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/{idPatient}/update", method = RequestMethod.GET)
	public String showUpdatePatientForm(Model model, @PathVariable String idPatient, HttpServletRequest request) {

		logService.log("Affichage d'un formulaire pour modifier le patient " + idPatient);

		ChuPatient patient = patientService.find(idPatient);

		if (patient == null) {
			return "redirect:/patient";
		}

		model.addAttribute("formPatient", formPatientService.getForm(patient));

		return "patient/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/patient/update", "/patient/{idPatient}/update" }, method = RequestMethod.POST)
	public String saveOrUpdatePatient(Model model, 
			@ModelAttribute("formPatient") @Valid FormPatient formPatient, BindingResult result,
			@RequestParam(value = "button", required = false) String button,
			final RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {


		// === Bouton "reinitialiser" ===
		
		if (button != null && button.equals("reset")) {
			return "redirect:/patient/" + formPatient.getIdPatient() + "/update";
		}


		// === Bouton "enregistrer" ===

		if (button != null && button.equals("save")) {

			formPatientValidator.validate(formPatient, result);

			if (result.hasErrors()) {
				logService.log("Modification échouée de patient");
				return "patient/form";
			}

			logService.log("Mise à jour du patient " + formPatient);

			redirectAttributes.addFlashAttribute("css", "success");
			if (formPatient.isNew()) {
				redirectAttributes.addFlashAttribute("msg", "Le nouveau patient a été ajouté avec succès !");
			} else {
				redirectAttributes.addFlashAttribute("msg", "La modification a été effectuée avec succès !");
			}

			formPatientService.saveOrUpdateForm(formPatient);

		}

		// POST/REDIRECT/GET
		return "redirect:/patient/" + formPatient.getIdPatient();
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/patient/{idPatient}/delete" }, method = RequestMethod.GET)
	public String showDeleteForm(Model model, 
			@PathVariable String idPatient, 
			@RequestParam(value = "view", required = false) String view,
			@RequestParam(value = "button", required = false) String button,
			final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		
		ChuPatient patient = patientService.find(idPatient);
		model.addAttribute("patient", patient);

		List<ChuTumeur> listTumeurs = tumeurService.listByIdPatientWithDependencies(patient.getIdPatient(), "tumeurs");
		model.addAttribute("listTumeurs", listTumeurs);

		String redirectPath = "redirect:/patients";
		if (view!=null && view.contains("show.jsp")) {
			redirectPath = "redirect:/patient/" + patient.getIdPatient();
		}
		model.addAttribute("view", view);
		

		// ===== Bouton supprimer =====

		if (button!=null && button.equals("delete")) {


			if (listTumeurs==null || listTumeurs.isEmpty()) {
				// OK
				boolean success = patientService.delete(patient);
				if (success) {
					redirectAttributes.addFlashAttribute("css", "success");
					redirectAttributes.addFlashAttribute("msg", "Le patient " + patient.getPrenom() + " " + patient.getNom() + " a été supprimé !");
					logService.log("Suppression du patient " + patient.getIdPatient() + " " + patient.getPrenom() + " " + patient.getNom());
					return  "redirect:/patients";
				}
				else {
					redirectAttributes.addFlashAttribute("css", "danger");
					redirectAttributes.addFlashAttribute("msg", "Le patient " + patient.getPrenom() + " " + patient.getNom() + " n'a pas pu être supprimé !");
					logService.log("Suppression du patient " + patient.getIdPatient() + "  échouée : la commande DELETE a été rejetée par la BD" );
					return redirectPath;
				}

				
			}
			else {
				// KO
				redirectAttributes.addFlashAttribute("css", "danger");
				redirectAttributes.addFlashAttribute("msg", "Le patient " + patient.getPrenom() + " " + patient.getNom() + " n'a pas pu être supprimé !");
				logService.log("Suppression du patient " + patient.getIdPatient() + "  échouée : la liste des tumeurs n'est pas vide" );
				return redirectPath;
			}	
		}
		
		

		// ===== Bouton annuler =====

		if (button!=null && button.equals("cancel")) {
			return redirectPath;
		}

		return "patient/delete";
	}


	/** ====================================================================================== */

}
