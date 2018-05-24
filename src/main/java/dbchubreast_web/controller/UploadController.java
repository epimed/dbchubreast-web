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

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dbchubreast_web.form.FormUpload;
import dbchubreast_web.service.form.FormUploadService;
import dbchubreast_web.validator.FormUploadValidator;



@Controller
public class UploadController extends BaseController {

	@Autowired
	private FormUploadValidator formUploadValidator;

	@Autowired
	private FormUploadService formUploadService;


	/** ========================================================================================= */

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String UploadDataGet(Model model,  HttpServletRequest request) throws Exception {

		return "upload/form";
	}

	/** ========================================================================================= */

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadDataPost(
			Model model,
			@ModelAttribute("formUpload") FormUpload formUpload,  
			BindingResult result, 
			HttpServletRequest request
			) throws Exception {

		
		File file = formUploadService.saveFileOnDisk(formUpload, request);
		formUploadValidator.setFile(file);
		formUploadValidator.validate(formUpload, result);

		if (!result.hasErrors()) {
			List<String> normalizedHeader = formUploadValidator.getNormalizedHeader();
			List<Object> data = formUploadValidator.getData();
			formUploadService.saveOrUpdate(normalizedHeader, data);
			
			model.addAttribute("message", "Le fichier " + formUpload.getFile().getOriginalFilename() + " a été importé avec succes !");
			model.addAttribute("success", true);
			
			return "upload/result";
			
		}

		return "upload/form";
	}

	/** ========================================================================================= */

}


