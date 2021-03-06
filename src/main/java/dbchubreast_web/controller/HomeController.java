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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuTumeurService;

@Controller
public class HomeController extends BaseController {

	@Autowired
	private ChuPatientService patientService;
	
	@Autowired
	private ChuTumeurService tumeurService;

	/** ====================================================================================== */

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		
		Long nbPatients = patientService.count();
		model.addAttribute("nbPatients", nbPatients);
		
		Long nbTumeurs = tumeurService.count();
		model.addAttribute("nbTumeurs", nbTumeurs);
		
		return "index";
	}

	/** ====================================================================================== */
}
