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
import dbchubreast_web.form.FormPatient;
import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.form.FormPatientService;

@Controller
public class PatientController extends BaseController {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private FormPatientService formPatientService;

	@Autowired
	private AppLogService logService;
	
	/** ====================================================================================== */

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public String showAllPatients(Model model, HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");
		
		List<ChuPatient> listPatients = patientService.list();

		logger.debug("listPatients {}", listPatients.size());
		logService.saveLog("Affichage d'une liste de patients " + listPatients.size());
		
		model.addAttribute("listPatients", listPatients);

		return "patient/list";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchPatient(Model model, @RequestParam(value = "text", required = false) String text,
			HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");
		
		logger.debug("Text {}", text);

		if (text != null) {
			logService.saveLog("Recherche de patients par texte " + text);
			List<ChuPatient> listPatients = patientService.findInAttributes(text);
			model.addAttribute("listPatients", listPatients);
		}
		model.addAttribute("text", text);

		return "patient/search";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/{idPatient}", method = RequestMethod.GET)
	public String showPatientGet(Model model, @PathVariable String idPatient, HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");
		
		if (idPatient == null) {
			return "redirect:/patient";
		}
		
		logService.saveLog("Affichage du patient " + idPatient);

		ChuPatient patient = patientService.find(idPatient);
		logger.debug("Patient {}", patient);

		if (patient == null) {
			return "redirect:/patient";
		}

		model.addAttribute("patient", patient);

		return "patient/show";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/add", method = RequestMethod.GET)
	public String showAddPatientForm(Model model, HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		logService.saveLog("Affichage d'un formulaire pour ajouter un patient");
		
		model.addAttribute("formPatient", new FormPatient());

		return "patient/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/{idPatient}/update", method = RequestMethod.GET)
	public String showUpdatePatientForm(Model model, @PathVariable String idPatient, HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");
		logger.debug("idPatient {}", idPatient);

		logService.saveLog("Affichage d'un formulaire pour modifier le patient " + idPatient);
		
		ChuPatient patient = patientService.find(idPatient);

		if (patient == null) {
			return "redirect:/patient";
		}

		model.addAttribute("formPatient", formPatientService.getForm(patient));

		return "patient/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = { "/patient/update", "/patient/{idPatient}/update" }, method = RequestMethod.POST)
	public String saveOrUpdatePatient(Model model, @ModelAttribute("formPatient") @Valid FormPatient formPatient,
			BindingResult result, final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");
		logger.debug("Form Patient {}", formPatient);

		
		if (result.hasErrors()) {
			return "patient/form";
		}

		logService.saveLog("Mise à jour du patient " + formPatient);
		
		redirectAttributes.addFlashAttribute("css", "success");
		if (formPatient.isNew()) {
			redirectAttributes.addFlashAttribute("msg", "Le nouveau patient a été ajouté avec succès !");
		} else {
			redirectAttributes.addFlashAttribute("msg", "La modification a été effectuée avec succès !");
		}

		formPatientService.saveOrUpdateForm(formPatient);

		// POST/REDIRECT/GET
		return "redirect:/patient/" + formPatient.getIdPatient();
	}

	/** ====================================================================================== */
}
