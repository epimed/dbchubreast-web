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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dbchubreast_web.controller.BaseController;
import dbchubreast_web.form.download.FormDownloadDonneesBiocliniques;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.exporter.IExporter;
import dbchubreast_web.service.exporter.NullExporter;
import dbchubreast_web.service.exporter.PrelevementExporter;
import dbchubreast_web.service.exporter.TraitementExporter;
import dbchubreast_web.service.util.FileService;

@Controller
public class DownloadDonneesBiocliniquesController extends BaseController {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private PrelevementExporter prelevementExporter;

	@Autowired
	private TraitementExporter traitementExporter;

	@Autowired
	private NullExporter nullExporter;
	
	@Autowired 
	private FileService fileService;


	/** ====================================================================================== */

	@RequestMapping(value = "/download/donnees", method = RequestMethod.GET)
	public String formDownloadPatient(Model model) throws IOException {

		Long nbPatients = patientService.count();
		model.addAttribute("nbPatients", nbPatients);
		model.addAttribute("form", new FormDownloadDonneesBiocliniques());
		return "download/donnees/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/download/donnees", method = RequestMethod.POST)
	public void downloadPatients(Model model, @ModelAttribute("form") FormDownloadDonneesBiocliniques form, 
			HttpServletResponse response) throws IOException {

		String typeDonnees = form.getTypeDonnees();
		IExporter exporter = this.getExporter(typeDonnees); 

		exporter.generate();
		String fileName =  fileService.generateFileName("PROBREAST_" + typeDonnees, "csv");
		logger.debug("Generated file name {}", fileName);
		response.setContentType( "text/csv" );
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		fileService.writeCsvFile(response, exporter.getHeader(), exporter.getData());

	}

	/** ====================================================================================== */

	private IExporter getExporter(String typeDonnees) {
		
		IExporter exporter = nullExporter;

		if (typeDonnees.equals("prelevements")) {
			exporter = prelevementExporter;
		}

		if (typeDonnees.equals("traitements")) {
			exporter = traitementExporter;
		}
		
		return exporter;
	}


	/** ====================================================================================== */

}
