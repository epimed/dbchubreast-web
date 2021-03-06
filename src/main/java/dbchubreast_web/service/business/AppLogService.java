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
package dbchubreast_web.service.business;

import org.springframework.web.servlet.ModelAndView;

public interface AppLogService {
	public void log();
	public void log(String comment);
	public void log(ModelAndView modelAndView);
	public void log(ModelAndView modelAndView, String comment);
	public void logComment(String username, String comment);
}
