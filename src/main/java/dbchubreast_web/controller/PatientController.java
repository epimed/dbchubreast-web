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
import dbchubreast_web.service.ChuPatientService;
import dbchubreast_web.service.ChuTumeurService;


@Controller
public class PatientController extends BaseController {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuTumeurService tumeurService;


	/** ====================================================================================== */

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public String showAllPatients(Model model, HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		List<ChuPatient> listPatients = patientService.list();;

		logger.debug("listPatients {}", listPatients.size());
		model.addAttribute("listPatients", listPatients);


		return "patient/list";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient", method = {RequestMethod.GET, RequestMethod.POST})
	public String searchPatient(Model model,
			@RequestParam(value = "text", required = false) String text,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		logger.debug("Text {}", text);

		if (text!=null) {

			List<ChuPatient> listPatients = patientService.findInAttributes(text);
			model.addAttribute("listPatients", listPatients);
		}
		model.addAttribute("text", text);

		return "patient/search";
	}

	/** ====================================================================================== */

	@RequestMapping(value ="/patient/{idPatient}", method = RequestMethod.GET)
	public String showPatientGet(Model model,
			@PathVariable String idPatient,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		if (idPatient==null) {
			return "redirect:/patient";
		}

		ChuPatient patient = patientService.find(idPatient);
		logger.debug("Patient {}", patient);

		if (patient==null) {
			return "redirect:/patient";
		}

		List<ChuTumeur> listTumeurs = tumeurService.find(idPatient);

		model.addAttribute("patient", patient);
		model.addAttribute("listTumeurs", listTumeurs);

		return "patient/show";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/add", method = RequestMethod.GET)
	public String showAddPatientForm(Model model, 
			HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		model.addAttribute("patient", new ChuPatient());

		return "patient/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/{idPatient}/update", method = RequestMethod.GET)
	public String showUpdatePatientForm(Model model, 
			@PathVariable String idPatient,
			HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");
		logger.debug("idPatient {}", idPatient);

		ChuPatient patient = patientService.find(idPatient);

		if (patient==null) {
			return "redirect:/patient";
		}

		model.addAttribute("patient", patient);

		return "patient/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/patient/update", "/patient/{idPatient}/update"}, method = RequestMethod.POST)
	public String saveOrUpdatePatient(Model model, 
			@ModelAttribute("patient") @Valid ChuPatient patient, 
			BindingResult result,
			final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");
		logger.debug("Patient {}", patient);

		if (result.hasErrors()) {
			return "patient/form";
		}

		redirectAttributes.addFlashAttribute("css", "success");
		if(patient.getIdPatient()==null) {
			redirectAttributes.addFlashAttribute("msg", "Le nouveau patient a été ajouté avec succès !");
		}
		else {
			redirectAttributes.addFlashAttribute("msg", "La modification a été effectuée avec succès !");
		}

		patientService.saveOrUpdate(patient);

		// POST/REDIRECT/GET
		return "redirect:/patient/" + patient.getIdPatient();
	}

	/** ====================================================================================== */
}
