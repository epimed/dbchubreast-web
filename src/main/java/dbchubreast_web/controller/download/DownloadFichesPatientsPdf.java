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
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import dbchubreast_web.controller.BaseController;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.util.FileService;
import dbchubreast_web.service.util.FormatService;
import dbchubreast_web.service.util.PdfService;

@Controller
public class DownloadFichesPatientsPdf extends BaseController {

	@Autowired
	private ChuPatientService patientService;

	@Autowired 
	private FormatService formatService;

	@Autowired 
	private FileService fileService;

	@Autowired
	private PdfService pdfService;

	/** ====================================================================================== */

	@RequestMapping(value = "/download/fiches", method = RequestMethod.GET)
	public String formDownloadPatient(Model model) throws IOException {

		Long nbPatients = patientService.count();
		model.addAttribute("nbPatients", nbPatients);
		return "download/fiches/form";
	}

	/** ====================================================================================== */

	@RequestMapping(value = "/download/fiches", method = RequestMethod.POST)
	public void downloadPatients(Model model, 
			@RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "button", required = false) String button,
			HttpServletResponse response) throws IOException {

		logger.debug("Text : {}", text);

		if (button!=null && button.equals("saveall")) {
			List<String> listIdPatients = patientService.findAllIdPatients();
			this.generatePdf(response, listIdPatients);
		}

		else {
			List<String> listIdPatients = Arrays.asList(formatService.convertStringToArray(text));
			this.generatePdf(response, listIdPatients);
		}

	}

	/** ====================================================================================== */

	private void generatePdf(HttpServletResponse response, List<String> listIdPatients) throws IOException {

		String fileName =  fileService.generateFileName("PROBREAST_fiches_patients", "pdf");

		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setContentType("application/pdf");

		PdfWriter writer = new PdfWriter(response.getOutputStream());
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document document = new Document(pdfDoc);

		pdfService.createHeaderFooter(pdfDoc, document);		
		pdfService.createPdf(listIdPatients, document, null, "confidentielle");

		document.close();
		response.flushBuffer();
	}

	/** ====================================================================================== */

}
