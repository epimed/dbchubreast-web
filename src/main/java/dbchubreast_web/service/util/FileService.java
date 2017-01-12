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
package dbchubreast_web.service.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;

@Service
public class FileService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	private static String columnSeparator = ";";
	private static String lineSeparator = "\n";


	/** ====================================================================================== */

	/**
	 * Generate a file name depending on parameters
	 * 
	 * @param prefix - prefix of the filename
	 * @param list - list of elements in the file which could appear in the filename
	 * @param maxListNb - max number of elements in the list that can be displayed in the filename
	 * @param suffix - suffux that appear if the list is longer than maxListNb
	 * @param fileExtension - file extension
	 * @return complete filename
	 * 
	 * Examples:
	 * 
	 *  fileService.generateFileName("experimental_grouping", listIdSeries, 3, "SEVERAL_STUDIES", "xlsx");
	 *  
	 *  listIdSeries = [GSE11092, GSE13309, GSE15431]
	 *  Generated file name = experimental_grouping_2016.07.28_GSE11092_GSE13309_GSE15431.xlsx
	 *  
	 *  listIdSeries = [GSE11092, GSE12662, GSE13309, GSE15431]
	 *  Generated file name = experimental_grouping_2016.07.28_SEVERAL_STUDIES.xlsx
	 *  
	 */
	

	public String generateFileName(String prefix, String[] list, Integer maxListNb, String suffix, String fileExtension) {

		String text="";

		if (suffix!=null && ((list==null ) || 
				(list!=null && list.length>maxListNb))){
			text = text + "_" + suffix;
		}

		if (list!=null && list.length<=maxListNb) {
			text = text + "_";
			for (int i=0; i<list.length; i++) {
				text = text + list[i];
				if (i<list.length-1) {
					text = text + "_";
				}
			}
		}

		String fileName =  prefix + "_" + dateFormat.format(new Date()) + text + "." + fileExtension;
		return fileName;
	}

	/** ================================================================================= */
	
	public String generateFileName(String prefix, String fileExtension) {

		String fileName =  prefix + "_" + dateFormat.format(new Date()) + "." + fileExtension;
		return fileName;
	}
	
	
	/** ================================================================================= */

	public File convertToFile(MultipartFile multipartFile) throws Exception {

		String originalFileName = multipartFile.getOriginalFilename();
		
		// String processedFileName = dateFormat.format(new Date()) + "_" + flattenToAscii(originalFileName).toLowerCase().replaceAll("[\\p{Space}]","_");
		
		File file = new File(originalFileName);

		if (file.exists()) {
			file.delete();
		}
		multipartFile.transferTo(file);

		/*
		String path = request.getServletContext().getRealPath("/");
		logger.debug("Context path {}" , path);
		logger.debug("File saved to {}", file.getAbsolutePath());
		 */

		return file;
	}


	/** ================================================================================= */

	public List<String> getCsvHeader (File file) throws IOException  {

		List<String> header = new ArrayList<String>();
		CSVReader reader = new CSVReader(new FileReader(file), columnSeparator.toCharArray()[0]);
		header.addAll(Arrays.asList(reader.readNext()));		
		reader.close();
		return header;
	}

	/** ================================================================================= */

	public List<Object> getCsvData (File file) throws IOException  {

		List<Object> data = new ArrayList<Object>();

		CSVReader reader = new CSVReader(new FileReader(file), columnSeparator.toCharArray()[0]);

		// === Skip Header ===
		reader.readNext();

		// === Load data ===
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			data.add(nextLine);
		}
		reader.close();
		return data;
	}

	/** ================================================================================= */
	
	public String [] extractColumnCsv (Integer columnIndex, List<Object> data) {

		List<String> column = new ArrayList<String> ();
		for (int i=0; i<data.size(); i++) {
			String [] line = (String[]) data.get(i);
			if (line.length>columnIndex && line[columnIndex]!=null) {
				column.add(line[columnIndex].trim());
			}
		}
		String [] array = new String [column.size()];
		
		return column.toArray(array);
	}

	/** ================================================================================= */

	public void writeCsvFile(HttpServletResponse response, List<String> header, List<Object> listData) {


		try {

			PrintWriter writer = response.getWriter();

			// === Header ===
			if (header!=null) {
				for (int i=0; i<header.size(); i++) {
					writer.append(header.get(i)!=null ? header.get(i).replaceAll("[\\p{Punct}\\p{Space}*]", "_").toLowerCase() : "");
					if (i<(header.size()-1)) {
						writer.append(columnSeparator);
					}
				}
				writer.append(lineSeparator);
				writer.flush();
			}

			// === Data ===
			if (listData!=null) {
				for ( Iterator<Object> iterator = listData.iterator(); iterator.hasNext(); ) {
					Object data[] = (Object[]) iterator.next();
					for (int j=0; j<data.length; j++) {
						writer.append(data[j]!=null ? data[j].toString().replaceAll(columnSeparator, "_") : "");
						if (j<(data.length-1)) {
							writer.append(columnSeparator);
						}
					}
					writer.append(lineSeparator);
				}
				writer.flush();
			}

			// ===== Close file =====
			writer.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/** ====================================================================================== */

	public void writeExcelFile(OutputStream outputStream, List<String> header, List<Object> listData) {

		// === Blank workbook ===
		XSSFWorkbook workbook = new XSSFWorkbook(); 

		// === Create a blank sheet ===
		XSSFSheet sheet = workbook.createSheet("EpiMed data "  + dateFormat.format(new Date()));

		// === Nb of rows and cells ===
		int rownum = 0;


		// === Header ===
		if (header!=null && !header.isEmpty()) {
			Row row = sheet.createRow(rownum++);
			int cellnum = 0;
			for (int i=0; i<header.size(); i++) {
				Cell cell = row.createCell(cellnum++);
				cell.setCellValue(header.get(i));
			}
		}

		// === Data ===
		if (listData!=null) {
			for ( Iterator<Object> iterator = listData.iterator(); iterator.hasNext(); ) {
				Object data[] = (Object[]) iterator.next();

				logger.trace(rownum + " " + Arrays.toString(data));

				Row row = sheet.createRow(rownum++);

				int cellnum = 0;
				for (int j=0; j<data.length; j++) {

					Cell cell = row.createCell(cellnum++);
					cell.setCellType(Cell.CELL_TYPE_STRING);

					boolean isNull = (data[j]==null);
					if (!isNull) {
						cell.setCellValue(data[j].toString());
					}
				}
			}
		}

		try {
			workbook.write(outputStream);
			workbook.close();
			outputStream.flush();
			outputStream.close();

		} catch (IOException e) {
			logger.debug("XLS error");
			e.printStackTrace();
		} 
	}

	/** ================================================================================= */

	public void addSheet(XSSFWorkbook workbook, String sheetName, List<String> header, List<Object> listData) {


		// === Create a blank sheet ===
		XSSFSheet sheet = workbook.createSheet(sheetName);

		// === Nb of rows and cells ===
		int rownum = 0;


		// === Header ===
		if (header!=null) {
			Row row = sheet.createRow(rownum++);
			int cellnum = 0;
			for (int i=0; i<header.size(); i++) {
				Cell cell = row.createCell(cellnum++);
				cell.setCellValue(header.get(i));
			}
		}

		// === Data ===
		if (listData!=null) {
			for ( Iterator<Object> iterator = listData.iterator(); iterator.hasNext(); ) {
				Object data[] = (Object[]) iterator.next();

				Row row = sheet.createRow(rownum++);

				int cellnum = 0;
				for (int j=0; j<data.length; j++) {

					Cell cell = row.createCell(cellnum++);
					cell.setCellType(Cell.CELL_TYPE_STRING);

					boolean isNull = (data[j]==null);
					if (!isNull) {
						cell.setCellValue(data[j].toString());
					}
				}
			}
		}

	}

	/** ================================================================================= */

	public XSSFWorkbook createWorkbook(){
		return new XSSFWorkbook(); 
	}


	/** ================================================================================= */

	public void writeWorkbook(OutputStream outputStream, XSSFWorkbook workbook) {

		try {
			workbook.write(outputStream);
			workbook.close();
			outputStream.flush();
			outputStream.close();

		} 
		catch (IOException e) {
			logger.debug("XLS error");
			e.printStackTrace();
		} 
	}

	/** ================================================================================= */
}
