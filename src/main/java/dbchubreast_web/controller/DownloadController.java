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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.service.business.AppLogService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.consistency.ConsistencyService;
import dbchubreast_web.service.exporter.ExporterBiomarqueur;
import dbchubreast_web.service.exporter.ExporterPatient;
import dbchubreast_web.service.exporter.ExporterPrelevement;
import dbchubreast_web.service.exporter.Table;
import dbchubreast_web.service.util.FileService;
import dbchubreast_web.service.util.PdfService;

@Controller
public class DownloadController extends BaseController {

	@Autowired
	private FileService fileService;

	@Autowired
	private PdfService pdfService;

	@Autowired
	private ExporterPatient exporterPatient;

	@Autowired
	private ExporterBiomarqueur exporterBiomarqueur;

	@Autowired
	private ExporterPrelevement exporterPrelevement;

	@Autowired
	private AppLogService logService;

	@Autowired
	private ConsistencyService consistencyService;
	
	@Autowired
	private ChuPatientService patientService;

	/** ====================================================================================== */

	@RequestMapping(value = "/download/{key}", method = RequestMethod.GET)
	public void downloadAllPatients(Model model, @PathVariable String key, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

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

		logService.log("Téléchargement de " + key + ", fichier généré " + fileName);

		this.generateResponse(response, table, fileName, key);
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/pdf/patient/{idPatient}", method = RequestMethod.GET)
	public void downloadPdf(Model model, @PathVariable String idPatient, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String fileName = "fiche_patient_"  + idPatient + ".pdf";
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setContentType("application/pdf");

		PdfWriter writer = new PdfWriter(response.getOutputStream());
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document document = new Document(pdfDoc);
		
		
		pdfService.createHeaderFooter(pdfDoc, document);
		
		List<String> listIdPatients = new ArrayList<String>();
		listIdPatients.add(idPatient);
		
		List<ChuPatient> listPatients = patientService.findAsList(idPatient);
		
		consistencyService.clearMessages();
		consistencyService.checkConsistency(listPatients);
		
		pdfService.createPdf(listIdPatients, document, consistencyService.getMapMessages(), "confidentielle");
		// pdfService.createPdf(listIdPatients, document, "publique");
		document.close();
		response.flushBuffer();

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

		response.setContentType("application/msexcel");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		fileService.writeWorkbook(response.getOutputStream(), workbook);

	}

}
