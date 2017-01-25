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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import dbchubreast_web.service.business.AppLogService;

@ControllerAdvice
public class GlobalExceptionController {

	@Autowired
	private AppLogService logService;
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex, HttpServletRequest request) {

		ModelAndView model = new ModelAndView("error");
		model.addObject("errorMessage", ex.toString());

		logService.saveLog(request, ex.toString());
		
		ex.printStackTrace();

		return model;

	}

}
