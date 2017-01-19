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


import java.util.ArrayList;
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

import dbchubreast_web.entity.ChuMorphologie;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.entity.ChuTypePhase;
import dbchubreast_web.entity.ChuTypePrelevement;
import dbchubreast_web.form.FormPrelevement;
import dbchubreast_web.service.business.ChuMorphologieService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;
import dbchubreast_web.service.business.ChuPrelevementBiomarqueurService;
import dbchubreast_web.service.business.ChuPrelevementService;
import dbchubreast_web.service.business.ChuTumeurService;
import dbchubreast_web.service.business.ChuTypePhaseService;
import dbchubreast_web.service.business.ChuTypePrelevementService;


@Controller
public class PrelevementController extends BaseController {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuTumeurService tumeurService;

	@Autowired
	private ChuPhaseTumeurService phaseTumeurService;

	@Autowired
	private ChuTypePhaseService typePhaseService;

	@Autowired
	private ChuPrelevementService prelevementService;

	@Autowired
	private ChuMorphologieService morphologieService;
	
	@Autowired
	private ChuTypePrelevementService typePrelevementService;

	@Autowired
	private ChuPrelevementBiomarqueurService prelevementBiomarqueurService;
	
	@Autowired
	private FormPrelevement formPrelevement;

	/** ====================================================================================== */

	@RequestMapping(value = {"/patient/{idPatient}/prelevements"}, method = RequestMethod.GET)
	public String listPrelevements(Model model,
			@PathVariable String idPatient,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");
		logger.debug("idPatient {}", idPatient);

		ChuPatient patient = patientService.find(idPatient);
		model.addAttribute("patient", patient);

		List<ChuPrelevement> listPrelevements = prelevementService.listByIdPatient(idPatient);
		this.populatePrelevementBiomarqueurs(model, listPrelevements);
		return "prelevement/list";
	}


	/** ====================================================================================== */

	@RequestMapping(value = {"/prelevement"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String searchPrelevement(Model model,
			@RequestParam(value = "idPatient", required = false) String idPatient,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		logger.debug("idPatient {}", idPatient);

		if (idPatient!=null) {
			ChuPatient patient = patientService.find(idPatient);
			model.addAttribute("patient", patient);
			List<ChuPrelevement> listPrelevements = prelevementService.listByIdPatient(idPatient);
			this.populatePrelevementBiomarqueurs(model, listPrelevements);
		}

		List<ChuPatient> listPatients = patientService.list();;
		model.addAttribute("idPatient", idPatient);
		model.addAttribute("listPatients", listPatients);

		return "prelevement/search";
	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/prelevement/{idPrelevement}"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String showPrelevement(Model model,
			@PathVariable Integer idPrelevement,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		logger.debug("idPrelevement {}", idPrelevement);

		// Patient
		ChuPatient patient = patientService.findByIdPrelevement(idPrelevement);
		model.addAttribute("patient", patient);

		// Prelevements
		List<ChuPrelevement> listPrelevements = prelevementService.listByIdPatient(patient.getIdPatient());
		model.addAttribute("listPrelevements", listPrelevements);


		// Prelevement
		ChuPrelevement prelevement = prelevementService.find(idPrelevement);
		model.addAttribute("prelevement", prelevement);
		this.populatePrelevementBiomarqueurs(model, prelevement);

		return "prelevement/show";
	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/patient/{idPatient}/prelevement/add"}, method = RequestMethod.GET)
	public String showAddSampleForm(Model model,
			@PathVariable String idPatient,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		// Form prelevement
		// FormPrelevement	formPrelevement = new FormPrelevement(idPatient);
		formPrelevement.setIdPatient(idPatient);
		
		this.populateAddSampleForm(formPrelevement, model);
		formPrelevement.populateBiomarqueurs();
		
		return "prelevement/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/patient/{idPatient}/prelevement/add/detail"}, method = RequestMethod.GET)
	public String showDetailAddSampleForm(
			@PathVariable String idPatient
			) {
		// POST/REDIRECT/GET
		return "redirect:/patient/" + idPatient + "/prelevement/add";
	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/patient/{idPatient}/prelevement/add/detail"}, method = RequestMethod.POST)
	public String showDetailAddSampleForm(Model model,
			@PathVariable String idPatient,
			@ModelAttribute("formPrelevement") FormPrelevement formPrelevement, 
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		this.populateAddSampleForm(formPrelevement, model);

		return "prelevement/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/prelevement/update"}, method = RequestMethod.GET)
	public String redirectSampleForm() {
		// POST/REDIRECT/GET
		return "redirect:/prelevement";
	}

	/** ====================================================================================== */

	@RequestMapping(value = {"/prelevement/update"}, method = RequestMethod.POST)
	public String saveOrUpdateSampleForm(Model model,
			@ModelAttribute("formPrelevement") FormPrelevement formPrelevement, 
			BindingResult result,
			final RedirectAttributes redirectAttributes,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		logger.debug("List bio {}", formPrelevement.getListBiomarqueurs());
		
		this.populateAddSampleForm(formPrelevement, model);

		return "prelevement/form";
	}


	/** ====================================================================================== */

	private void populatePrelevementBiomarqueurs(Model model, ChuPrelevement prelevement) {
		List<ChuPrelevementBiomarqueur> listPrelevementBiomarqueurs = prelevementBiomarqueurService.list(prelevement.getIdPrelevement());
		model.addAttribute("listPrelevementBiomarqueurs", listPrelevementBiomarqueurs);
		logger.debug("listPrelevementBiomarqueurs {}", listPrelevementBiomarqueurs);
	}

	/** ====================================================================================== */

	private void populatePrelevementBiomarqueurs(Model model, List<ChuPrelevement> listPrelevements) {

		model.addAttribute("listPrelevements", listPrelevements);

		List<Integer> listIdPrelevements = new ArrayList<Integer>();
		for (ChuPrelevement prel : listPrelevements) {
			listIdPrelevements.add(prel.getIdPrelevement());
		}
		List<ChuPrelevementBiomarqueur> listPrelevementBiomarqueurs = prelevementBiomarqueurService.list(listIdPrelevements);
		model.addAttribute("listPrelevementBiomarqueurs", listPrelevementBiomarqueurs);
		logger.debug("listPrelevementBiomarqueurs {}", listPrelevementBiomarqueurs);
	}

	/** ====================================================================================== */


	public void populateAddSampleForm(FormPrelevement formPrelevement, Model model) {

		// Patient
		ChuPatient patient = patientService.find(formPrelevement.getIdPatient());
		model.addAttribute("patient", patient);

		// Tumeurs
		List<ChuTumeur> listTumeurs = tumeurService.find(formPrelevement.getIdPatient());
		model.addAttribute("listTumeurs", listTumeurs);

		// Tumeur pre-selectionnee si unique
		if (formPrelevement.getIdTumeur()==null && listTumeurs!=null && listTumeurs.size()==1) {
			formPrelevement.setIdTumeur(listTumeurs.get(0).getIdTumeur());
		}

		// Phases tumeur
		if (formPrelevement.getIdTumeur()!=null) {
			List<ChuPhaseTumeur> listPhases = phaseTumeurService.listWithDependencies(formPrelevement.getIdTumeur());
			model.addAttribute("listPhases", listPhases);

			// Phase pre-selectionnee si unique
			if (formPrelevement.getIdPhase()==null && listPhases!=null && listPhases.size()==1) {
				formPrelevement.setIdPhase(listPhases.get(0).getIdPhase());
			}
		}

		// Types de prelevements
		if (formPrelevement.getIdPhase()!=null) {
			ChuTypePhase typePhase = typePhaseService.findByIdPhase(formPrelevement.getIdPhase());
			List<ChuTypePrelevement> listTypePrelevements = null;
			if (typePhase!=null && typePhase.getIdTypePhase()==1) {
				listTypePrelevements = typePrelevementService.listPhaseInitiale();
			}
			if (typePhase!=null && typePhase.getIdTypePhase()==2) {
				listTypePrelevements = typePrelevementService.listPhaseRechute();
			}
			model.addAttribute("listTypePrelevements", listTypePrelevements);
			
			// Type de prelevement pre-selectionne si unique
			
			if (formPrelevement.getIdTypePrelevement()==null && listTypePrelevements!=null && listTypePrelevements.size()==1) {
				formPrelevement.setIdTypePrelevement(listTypePrelevements.get(0).getIdTypePrelevement());
			}
		}

		// Prelevements
		List<ChuPrelevement> listPrelevements = prelevementService.listByIdPatient(patient.getIdPatient());
		model.addAttribute("listPrelevements", listPrelevements);
		
		// Morphologies
		List<ChuMorphologie> listMorphologies = morphologieService.list();
		model.addAttribute("listMorphologies", listMorphologies);

		// Form
		model.addAttribute("formPrelevement", formPrelevement);
		
		logger.debug("formPrelevement {}", formPrelevement);
	}

	/** ====================================================================================== */

}
