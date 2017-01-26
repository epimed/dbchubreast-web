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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.exporter.ExporterBiomarqueur;
import dbchubreast_web.service.exporter.ExporterPatient;
import dbchubreast_web.service.exporter.ExporterPrelevement;
import dbchubreast_web.service.exporter.Table;
import dbchubreast_web.service.util.FileService;

@Controller
public class DownloadController extends BaseController {

	@Autowired
	private FileService fileService;

	@Autowired
	private ExporterPatient exporterPatient;

	@Autowired
	private ExporterBiomarqueur exporterBiomarqueur;

	@Autowired
	private ExporterPrelevement exporterPrelevement;
	
	@Autowired
	private AppLogService logService;
	
	
	/** ====================================================================================== */

	@RequestMapping(value = "/download/{key}", method = RequestMethod.GET)
	public void downloadAllPatients(Model model, @PathVariable String key, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");

		logger.debug("key {}", key);
		
		Table table = null;

		if (key != null && key.equals("biomarqueurs")) {
			
			table = exporterBiomarqueur.export();
		}
		
		if (key != null && key.equals("prelevements")) {
			table = exporterPrelevement.export();
		} 
		
		if (key != null && key.equals("patients")) {
			table = exporterPatient.export();
		}

		if (table==null) {
			table = new Table(1);
		}
		
		String fileName = fileService.generateFileName("DB_CHU_BREAST_" + key, "xlsx");

		logService.saveLog("Téléchargement de " + key + ", fichier généré " + fileName);
		
		this.generateResponse(response, table, fileName, key);
	}

	/**
	 * ======================================================================================
	 * 
	 * @throws IOException
	 */

	private void generateResponse(HttpServletResponse response, Table table, String fileName, String sheetName)
			throws IOException {

		// ====== Create workbook =====
		XSSFWorkbook workbook = fileService.createWorkbook();

		// ====== Data =====
		fileService.addSheet(workbook, sheetName, table.getHeader(), table.getData());

		// ===== File generation =====
		logger.debug("Generated file name {}", fileName);
		response.setContentType("application/msexcel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		fileService.writeWorkbook(response.getOutputStream(), workbook);

	}

}
