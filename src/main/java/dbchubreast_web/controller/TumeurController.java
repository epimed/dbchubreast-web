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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuTopographie;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.FormTumeurInitiale;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuTopographieService;
import dbchubreast_web.service.business.ChuTumeurService;
import dbchubreast_web.service.util.FormatService;


@Controller
public class TumeurController extends BaseController {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuTumeurService tumeurService;
	
	@Autowired
	private ChuTopographieService topographieService;

	@Autowired
	private FormatService formatService;

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
			@RequestParam(value = "text", required = false) String text,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");


		logger.debug("text {}", text);


		if (text!=null) {
			Integer idTumeur = formatService.recognizeInteger(text);
			List<ChuTumeur> listTumeurs = null;
			if (idTumeur!=null) {
				listTumeurs = tumeurService.findAsListWithDependencies(idTumeur);
			}
			else {
				listTumeurs = tumeurService.findInAttributesWithDependencies(text);
			}
			model.addAttribute("listTumeurs", listTumeurs);
		}
		
		model.addAttribute("text", text);

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

		model.addAttribute("patient", patient);
		model.addAttribute("tumeur", tumeur);
		model.addAttribute("listTumeurs", listTumeurs);

		return "tumeur/show";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/patient/{idPatient}/tumeur/add", method = RequestMethod.GET)
	public String showAddTumorForm(Model model, 
			@PathVariable String idPatient,
			HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		ChuPatient patient = patientService.find(idPatient);
		List<ChuTumeur> listTumeurs = tumeurService.find(patient.getIdPatient());
		List<ChuTopographie> listTopographies = topographieService.list("C50");
		FormTumeurInitiale formTumeurInitiale = new FormTumeurInitiale(patient.getIdPatient());

		model.addAttribute("patient", patient);
		model.addAttribute("formTumeurInitiale", formTumeurInitiale);
		model.addAttribute("listTumeurs", listTumeurs);
		model.addAttribute("listTopographies", listTopographies);

		return "tumeur/formTumeurInitiale";
	}
	
	/** ====================================================================================== */
	
	@RequestMapping(value = "/tumeur/{idTumeur}/update", method = RequestMethod.GET)
	public String showUpdateTumorForm(Model model, 
			@PathVariable Integer idTumeur,
			HttpServletRequest request) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		ChuTumeur tumeur = tumeurService.findWithDependencies(idTumeur);
		List<ChuTumeur> listTumeurs = tumeurService.find(tumeur.getChuPatient().getIdPatient());
		
		model.addAttribute("patient", tumeur.getChuPatient());
		model.addAttribute("tumeur", tumeur);
		model.addAttribute("listTumeurs", listTumeurs);

		return "tumeur/show";
	}
	
	/** ====================================================================================== */
}
