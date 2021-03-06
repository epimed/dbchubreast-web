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
package dbchubreast_web.controller.tumeur;

import java.util.ArrayList;
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

import dbchubreast_web.controller.BaseController;
import dbchubreast_web.entity.ChuEvolution;
import dbchubreast_web.entity.ChuMetastase;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPerformanceStatus;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTopographie;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.tumeur.FormTumeurInitiale;
import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.business.ChuEvolutionService;
import dbchubreast_web.service.business.ChuMetastaseService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPerformanceStatusService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;
import dbchubreast_web.service.business.ChuTopographieService;
import dbchubreast_web.service.business.ChuTumeurService;
import dbchubreast_web.service.form.tumeur.FormPhaseTumeurInitialeService;
import dbchubreast_web.validator.FormTumeurInitialeValidator;

@Controller
public class PhaseTumeurInitialeController extends BaseController {

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

	@Autowired
	private ChuPerformanceStatusService performanceStatusService;

	@Autowired
	private FormPhaseTumeurInitialeService formPhaseTumeurInitialeService;

	@Autowired
	private FormTumeurInitialeValidator formTumeurInitialeValidator;

	@Autowired
	private AppLogService logService;

	
	/** ====================================================================================== */

	@RequestMapping(value = "/patient/{idPatient}/tumeurs", method = RequestMethod.GET)
	public String listPatientTumors(Model model, @PathVariable String idPatient, HttpServletRequest request) {

		logService.log("Affichage de tumeurs du patient " + idPatient);

		ChuPatient patient = patientService.find(idPatient);
		List<ChuTumeur> listTumeurs = tumeurService.listByIdPatientWithDependencies(patient.getIdPatient(), "phases");

		model.addAttribute("patient", patient);
		model.addAttribute("listTumeurs", listTumeurs);

		return "tumeur/list";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/tumeur", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchTumor(Model model, @RequestParam(value = "idPatient", required = false) String idPatient,
			HttpServletRequest request) {

		if (idPatient != null) {
			logService.log("Recherche de tumeurs pour le patient " + idPatient);
			ChuPatient patient = patientService.find(idPatient);
			List<ChuTumeur> listTumeurs = tumeurService.listByIdPatientWithDependencies(patient.getIdPatient(), "phases");
			model.addAttribute("patient", patient);
			model.addAttribute("listTumeurs", listTumeurs);
		}

		List<ChuPatient> listPatients = patientService.list();
		;
		model.addAttribute("idPatient", idPatient);
		model.addAttribute("listPatients", listPatients);

		return "tumeur/search";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/tumeur/{idTumeur}", method = RequestMethod.GET)
	public String showTumor(Model model, @PathVariable Integer idTumeur, HttpServletRequest request) {

		logService.log("Affichage de la tumeur " + idTumeur);

		ChuPatient patient = patientService.find(idTumeur);
		ChuTumeur tumeur = tumeurService.findWithDependencies(idTumeur);
		List<ChuTumeur> listTumeurs = tumeurService.listByIdPatient(patient.getIdPatient());
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
	public String showAddTumorForm(Model model, @PathVariable String idPatient, HttpServletRequest request) {


		ChuPatient patient = patientService.find(idPatient);

		logService.log("Affichage d'un formulaire pour ajouter une tumeur en phase initiale au patient " + idPatient);

		FormTumeurInitiale formTumeurInitiale = new FormTumeurInitiale(patient.getIdPatient(), patient.getDateDeces());

		model.addAttribute("formTumeurInitiale", formTumeurInitiale);
		this.populateAddTumorForm(patient, model);

		return "tumeur/formTumeurInitiale";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/tumeur/{idTumeur}/update", method = RequestMethod.GET)
	public String showUpdateTumorForm(Model model, @PathVariable Integer idTumeur, HttpServletRequest request) {

		logService.log("Affichage d'un formulaire pour modifier la tumeur en phase initiale " + idTumeur);

		ChuTumeur tumeur = tumeurService.findWithDependencies(idTumeur);
		ChuPatient patient = tumeur.getChuPatient();
		FormTumeurInitiale formTumeurInitiale = formPhaseTumeurInitialeService.getFormTumeurInitiale(tumeur);

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
			@ModelAttribute("formTumeurInitiale") @Valid FormTumeurInitiale formTumeurInitiale, BindingResult result,
			@RequestParam(value = "button", required = false) String button,
			final RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {


		// === Bouton "reinitialiser" ===

		if (button != null && button.equals("reset")) {
			if (formTumeurInitiale!=null && formTumeurInitiale.getIdTumeur()!=null) {
				return "redirect:/tumeur/" + formTumeurInitiale.getIdTumeur() + "/update";
			}
			else {
				return "redirect:/patient/" + formTumeurInitiale.getIdPatient() + "/tumeur/add";
			}
		}


		// === Bouton "enregistrer" ===

		if (button != null && button.equals("save")) {

			formTumeurInitialeValidator.validate(formTumeurInitiale, result);

			if (result.hasErrors()) {
				logService.log("Modification échouée de la phase initiale");
				ChuPatient patient = patientService.find(formTumeurInitiale.getIdPatient());
				this.populateAddTumorForm(patient, model);
				model.addAttribute("formTumeurInitiale", formTumeurInitiale);
				return "tumeur/formTumeurInitiale";
			} 

			else {

				logService.log("Modification validée de la phase initiale");

				redirectAttributes.addFlashAttribute("css", "success");
				if (formTumeurInitiale.isNew()) {
					redirectAttributes.addFlashAttribute("msg", "Une nouvelle tumeur a été ajoutée avec succès !");
				} else {
					redirectAttributes.addFlashAttribute("msg",
							"La modification de la tumeur a été effectuée avec succès !");
				}

				formPhaseTumeurInitialeService.saveOrUpdateForm(formTumeurInitiale);
			}
		}

		// === Bouton "annuler" ===

		if (button != null && button.equals("cancel")) {
			return "redirect:/patient/" +  formTumeurInitiale.getIdPatient() + "/tumeurs";
		}


		// POST/REDIRECT/GET
		return "redirect:/tumeur/" + formTumeurInitiale.getIdTumeur();
	}

	
	/** ====================================================================================== */

	public void populateAddTumorForm(ChuPatient patient, Model model) {

		List<String> listIdGroupeTopo = new ArrayList<String>();
		listIdGroupeTopo.add("C50");
		// listIdGroupeTopo.add("C51");

		List<ChuTopographie> listTopographies = topographieService.list(listIdGroupeTopo);
		List<ChuTumeur> listTumeurs = tumeurService.listByIdPatient(patient.getIdPatient());
		List<ChuEvolution> listEvolutions = evolutionService.list();
		List<ChuMetastase> listMetastases = metastaseService.list();
		List<ChuPerformanceStatus> listPerformanceStatus = performanceStatusService.list();

		model.addAttribute("patient", patient);
		model.addAttribute("listTumeurs", listTumeurs);
		model.addAttribute("listTopographies", listTopographies);
		model.addAttribute("listEvolutions", listEvolutions);
		model.addAttribute("listMetastases", listMetastases);
		model.addAttribute("listPerformanceStatus", listPerformanceStatus);
	}

	/** ====================================================================================== */
}