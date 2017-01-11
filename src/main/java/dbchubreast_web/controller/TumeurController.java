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

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.service.ChuPatientService;
import dbchubreast_web.service.ChuPrelevementService;
import dbchubreast_web.service.ChuTumeurService;


@Controller
public class TumeurController extends BaseController {

	@Autowired
	private ChuPatientService patientService;
	
	@Autowired
	private ChuTumeurService tumeurService;
	
	@Autowired
	private ChuPrelevementService prelevementService;


	/** ====================================================================================== */

	@RequestMapping(value ="/tumeur/{idTumeur}", method = RequestMethod.GET)
	public String showPatientGet(Model model,
			@PathVariable Integer idTumeur,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		
		logger.debug("Parameter idTumeur {}", idTumeur);
		
		List<ChuPrelevement> listPrelevements = prelevementService.find(idTumeur);
		ChuPatient patient = patientService.find(idTumeur);
		ChuTumeur tumeur = tumeurService.find(idTumeur);
		
		model.addAttribute("patient", patient);
		model.addAttribute("tumeur", tumeur);
		model.addAttribute("listPrelevements", listPrelevements);

		return "prelevement/show";
	}

	/** ====================================================================================== */
}
