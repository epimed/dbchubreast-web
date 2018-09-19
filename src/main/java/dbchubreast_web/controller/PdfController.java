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
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.consistency.ConsistencyService;
import dbchubreast_web.service.util.PdfService;

@Controller
public class PdfController extends BaseController {

	@Autowired
	private PdfService pdfService;

	@Autowired
	private ConsistencyService consistencyService;
	
	@Autowired
	private ChuPatientService patientService;


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

}
