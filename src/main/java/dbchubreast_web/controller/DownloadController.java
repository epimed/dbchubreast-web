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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dbchubreast_web.service.FileService;
import dbchubreast_web.service.exporter.ExporterPatient;
import dbchubreast_web.service.exporter.Table;


@Controller
public class DownloadController extends BaseController {

	@Autowired
	private FileService fileService;

	@Autowired
	private ExporterPatient exporterPatient;


	/** ====================================================================================== */

	@RequestMapping(value = "/download/patients", method = RequestMethod.GET)
	public void downloadAllPatients(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.debug("===== value = " + request.getRequestURI() + ", method = " + request.getMethod() + " =====");


		// ====== Create workbook =====
		XSSFWorkbook workbook = fileService.createWorkbook();

		// ====== Data =====
		Table table = exporterPatient.export();
		fileService.addSheet(workbook, "Patients", table.getHeader(), table.getData());

		// ===== File generation =====
		String fileName =  fileService.generateFileName("DB_CHU_BREAST_patients", "xlsx");
		logger.debug("Generated file name {}", fileName);
		response.setContentType("application/msexcel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		fileService.writeWorkbook(response.getOutputStream(), workbook);

	}

	/** ====================================================================================== */
}
