package dbchubreast_web.service.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

public class PdfHeaderFooterEventHandler implements IEventHandler{


	protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Date today = new Date();

	protected Document document;

	public PdfHeaderFooterEventHandler (Document document) {
		this.document = document;
	}

	@Override
	public void handleEvent(Event event) {
		PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
		PdfCanvas canvas = new PdfCanvas(docEvent.getPage());
		Rectangle pageSize = docEvent.getPage().getPageSize();

		float yHeader = (pageSize.getTop() - document.getTopMargin()) + document.getTopMargin()/2;
		
		try {
			canvas.beginText();
			canvas.setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA_OBLIQUE), 9);

			canvas
			
			// === Header ===
			.moveText(450, yHeader)
			.showText("PROBREAST - " + dateFormat.format(today))

			.endText()
			.release();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
