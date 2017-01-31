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

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import dbchubreast_web.format.CustomDoubleFieldFormat;

@ControllerAdvice
public class GlobalFormatController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		CustomDoubleFieldFormat df = new CustomDoubleFieldFormat ();
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, df, true));

	}
}
