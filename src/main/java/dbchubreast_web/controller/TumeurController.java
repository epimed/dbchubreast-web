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

import dbchubreast_web.entity.ChuEvolution;
import dbchubreast_web.entity.ChuMetastase;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTopographie;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.FormTumeurInitiale;
import dbchubreast_web.service.business.ChuEvolutionService;
import dbchubreast_web.service.business.ChuMetastaseService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;
import dbchubreast_web.service.business.ChuTopographieService;
import dbchubreast_web.service.business.ChuTumeurService;


@Controller
public class TumeurController extends BaseController {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuTumeurService tumeurService;

	@Autowired
	private ChuPhaseTumeurService phaseTumeurService;

	@Autowired
	private ChuTopographieService topographieService;

	@Autowired
	private ChuEvolutionService evolutionService;

	@Autowired
	private ChuMetastaseService metastaseService;


	/** ====================================================================================== */

	@RequestMapping(value ="/patient/{idPatient}/tumeurs", method = RequestMethod.GET)
	public String listPatientTumors(Model model,
			@PathVariable String idPatient,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		ChuPatient patient = patientService.find(idPatient);
		List<ChuTumeur> listTumeurs = tumeurService.findWithDependencies(idPatient);

		model.addAttribute("patient", patient);
		model.addAttribute("listTumeurs", listTumeurs);

		return "tumeur/list";
	}


	/** ====================================================================================== */

	@RequestMapping(value ="/tumeur", method = {RequestMethod.GET, RequestMethod.POST})
	public String searchTumor(Model model,
			@RequestParam(value = "idPatient", required = false) String idPatient,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		logger.debug("idPatient {}", idPatient);

		if (idPatient!=null) {
			ChuPatient patient = patientService.find(idPatient);
			List<ChuTumeur> listTumeurs = tumeurService.findWithDependencies(idPatient);
			model.addAttribute("patient", patient);
			model.addAttribute("listTumeurs", listTumeurs);
		}

		List<ChuPatient> listPatients = patientService.list();;
		model.addAttribute("idPatient", idPatient);
		model.addAttribute("listPatients", listPatients);

		return "tumeur/search";
	}

	/** ====================================================================================== */

	@RequestMapping(value ="/tumeur/{idTumeur}", method = RequestMethod.GET)
	public String showTumor(Model model,
			@PathVariable Integer idTumeur,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");


		logger.debug("Parameter idTumeur {}", idTumeur);


		ChuPatient patient = patientService.find(idTumeur);
		ChuTumeur tumeur = tumeurService.findWithDependencies(idTumeur);
		List<ChuTumeur> listTumeurs = tumeurService.find(patient.getIdPatient());
		List<ChuPhaseTumeur> listPhasesInitiales = phaseTumeurService.list(idTumeur, 1);
		List<ChuPhaseTumeur> listPhasesRechutes = phaseTumeurService.list(idTumeur, 2);

		model.addAttribute("patient", patient);
		model.addAttribute("tumeur", tumeur);
		model.addAttribute("listTumeurs", listTumeurs);
		model.addAttribute("listPhasesInitiales", listPhasesInitiales);
		model.addAttribute("listPhasesRechutes", listPhasesRechutes);

		return "tumeur/show";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/{idPatient}/tumeur/add", method = RequestMethod.GET)
	public String showAddTumorForm(Model model, 
			@PathVariable String idPatient,
			HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		ChuPatient patient = patientService.find(idPatient);

		FormTumeurInitiale formTumeurInitiale = new FormTumeurInitiale(patient.getIdPatient());
		model.addAttribute("formTumeurInitiale", formTumeurInitiale);
		this.populateAddTumorForm(patient, model);

		return "tumeur/formTumeurInitiale";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/tumeur/{idTumeur}/update", method = RequestMethod.GET)
	public String showUpdateTumorForm(Model model, 
			@PathVariable Integer idTumeur,
			HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		ChuTumeur tumeur = tumeurService.findWithDependencies(idTumeur);
		ChuPatient patient = tumeur.getChuPatient();

		FormTumeurInitiale formTumeurInitiale = phaseTumeurService.getFormTumeurInitiale(tumeur);
		model.addAttribute("formTumeurInitiale", formTumeurInitiale);

		this.populateAddTumorForm(patient, model);

		return "tumeur/formTumeurInitiale";
	}


	/** ====================================================================================== */

	@RequestMapping(value = "/tumeur/update", method = RequestMethod.GET)
	public String redirectTumeur() {
		// POST/REDIRECT/GET
		return "redirect:/tumeur";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/tumeur/update", method = RequestMethod.POST)
	public String saveOrUpdateTumorForm(Model model, 
			@ModelAttribute("formTumeurInitiale") FormTumeurInitiale formTumeurInitiale, 
			BindingResult result,
			final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		logger.debug("formTumeurInitiale {}", formTumeurInitiale);

		phaseTumeurService.saveOrUpdateForm(formTumeurInitiale);

		if (result.hasErrors()) {
			ChuPatient patient = patientService.find(formTumeurInitiale.getIdPatient());
			this.populateAddTumorForm(patient, model);
			model.addAttribute("formTumeurInitiale", formTumeurInitiale);
			return "tumeur/formTumeurInitiale";
		}

		// POST/REDIRECT/GET
		return "redirect:/tumeur/" + formTumeurInitiale.getIdTumeur();
	}

	/** ====================================================================================== */

	public void populateAddTumorForm(ChuPatient patient, Model model) {

		List<ChuTumeur> listTumeurs = tumeurService.find(patient.getIdPatient());
		List<ChuTopographie> listTopographies = topographieService.list("C50");
		List<ChuEvolution> listEvolutions = evolutionService.list();
		List<ChuMetastase> listMetastases = metastaseService.list();

		model.addAttribute("patient", patient);
		model.addAttribute("listTumeurs", listTumeurs);
		model.addAttribute("listTopographies", listTopographies);
		model.addAttribute("listEvolutions", listEvolutions);
		model.addAttribute("listMetastases", listMetastases);
	}

	/** ====================================================================================== */
}
