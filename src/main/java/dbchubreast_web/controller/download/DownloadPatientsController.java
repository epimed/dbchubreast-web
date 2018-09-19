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
package dbchubreast_web.controller.download;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dbchubreast_web.controller.BaseController;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.exporter.PatientExporter;
import dbchubreast_web.service.util.FileService;

@Controller
public class DownloadPatientsController extends BaseController {

	@Autowired
	private ChuPatientService patientService;
	
	@Autowired
	private PatientExporter patientExporter;
	
	@Autowired 
	private FileService fileService;

	/** ====================================================================================== */

	@RequestMapping(value = "/download/patients", method = RequestMethod.GET)
	public String formDownloadPatient(Model model) throws IOException {
		
		Long nbPatients = patientService.count();
		model.addAttribute("nbPatients", nbPatients);
		return "download/patients/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/download/patients", method = RequestMethod.POST)
	public void downloadPatients(Model model, HttpServletResponse response) throws IOException {
		
		patientExporter.generate();
		String fileName =  fileService.generateFileName("PROBREAST_patients", "csv");
		logger.debug("Generated file name {}", fileName);
		response.setContentType( "text/csv" );
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		fileService.writeCsvFile(response, patientExporter.getHeader(), patientExporter.getData());

	}
	
	/** ====================================================================================== */
	
}
