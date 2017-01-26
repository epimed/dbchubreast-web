/**
 * EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * 
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package dbchubreast_web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dbchubreast_web.form.FormPasswordEncoder;
import dbchubreast_web.service.business.AppLogService;

@Controller
public class AdminController extends BaseController {

	@Autowired
	FormPasswordEncoder formPasswordEncoder;

	@Autowired
	AppLogService logService;


	/** ====================================================================================== */

	@RequestMapping(value = { "/admin/password" }, method = RequestMethod.GET)
	public String showGenerateHashedPasswordForm(
			Model model,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		model.addAttribute("formPasswordEncoder", formPasswordEncoder);
		
		return "admin/generatePassword";
	}

	/** ====================================================================================== */


	@RequestMapping(value = { "/admin/password" }, method = RequestMethod.POST)
	public String generateHashedPassword(
			Model model,
			@ModelAttribute("formPasswordEncoder") @Valid FormPasswordEncoder formPasswordEncoder,
			BindingResult result,
			HttpServletRequest request
			) {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		if (!result.hasErrors()) {
			formPasswordEncoder.generate(10);
		}
		else {
			formPasswordEncoder.clear();
		}
		model.addAttribute("formPasswordEncoder", formPasswordEncoder);
		
		return "admin/generatePassword";
	}
	/** ====================================================================================== */
}
