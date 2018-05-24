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
package dbchubreast_web.validator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dbchubreast_web.form.FormUpload;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.util.FileService;
import dbchubreast_web.service.util.FormatService;

@Component
public class FormUploadValidator extends BaseService implements Validator {

	private File file;
	private List<String> header = new ArrayList<String>();
	private List<Object> data = new ArrayList<Object>();
	private List<String> normalizedHeader = new ArrayList<String>();
	private Map<String, String> mapHeaders = new HashMap<String, String>();
	private String separator = ";";

	@Autowired
	FileService fileService;

	@Autowired
	ChuPatientService patientService;

	@Autowired
	FormatService formatService;

	/** =========================================================================================  */

	public boolean supports(Class<?> clazz) {
		return FormUpload.class.isAssignableFrom(clazz);
	}


	/** ========================================================================================= */

	public void validate(Object target, Errors errors) {

		FormUpload formUpload = (FormUpload) target;

		try {

			// === Check if the file exist === 
			checkFileExist(formUpload, errors); 

			// === Check the file format ===
			checkFileFormat (formUpload, errors);

			// === Check header ===
			checkFileHeader(formUpload, errors, fileService);

			// === Check data ===
			checkFileData(formUpload, errors, fileService, formatService);

		} 
		catch (FileUploadException fue) {
			// Errors found, nothing to do
		}
		catch (Exception e) {
			errors.rejectValue("file", "error.loadFile", "Error: " + e.toString());
			e.printStackTrace();
		} 

	}

	/** =========================================================================================  */

	public void checkFileData(FormUpload formUpload, Errors errors, FileService fileService, FormatService formatService) throws FileUploadException, Exception {

		this.normalizedHeader.clear();
		this.mapHeaders.clear();

		for (String key : header) {
			String normalizedKey = formatService.normalize(key);
			this.normalizedHeader.add(normalizedKey);
			if (!normalizedKey.equals("id_patient")) {
				this.mapHeaders.put(normalizedKey, key);
			}
		}

		Integer columnIndex = header.indexOf("id_patient");
		this.data = fileService.getCsvData(file, this.separator);
		List<String> listIdPatients = fileService.extractColumnCsv(columnIndex, data);
		
		List<String> listIdPatientsInDatabase = patientService.findAllIdPatients();
		
		// logger.debug("File header {}", header);
		// logger.debug("Normalized header {}", normalizedHeader);
		// logger.debug("List id_patient {}", listIdPatients);
		// logger.debug("List id_patient in DB {}", listIdPatientsInDatabase);
		
		listIdPatients.removeAll(listIdPatientsInDatabase);
		
		if (listIdPatients!=null && !listIdPatients.isEmpty()) {
			errors.rejectValue("file", "error.patientDoesntExist", 
					"Le fichier contient des patients non enregistrés dans la base de données : " + listIdPatients 
					+ ". Il faut que tous les patients contenus dans le fichier existent déjà dans la base de données."
					+ " Sinon, le fichier ne peut pas être traité.");
			throw new FileUploadException();
		}
	
	}

	/** =========================================================================================  */

	public void checkFileHeader(FormUpload formUpload, Errors errors, FileService fileService) throws FileUploadException, Exception {

		if (this.file==null) {
			errors.rejectValue("file", "error.convert", "Le fichier ne peut pas être lu.");
			throw new FileUploadException();
		}
		
		// === Define separator ===

		String sep = fileService.guessSeparator(file);
		if (sep!=null) {
			this.separator = sep;
		}

		this.header = fileService.getCsvHeader(file, this.separator);

		logger.debug("Header {}", header);
		
		if (header==null) {
			errors.rejectValue("file", "error.empty", "Le fichier semble être vide. Vérifiez le contenu et les séparateurs de colonnes.");
			throw new FileUploadException();
		}

		if (!header.contains("id_patient")) {
			errors.rejectValue("file", "error.idPatientNotDetected", "Le fichier ne contient pas la colonne obligatoire \"id_patient\".");
			throw new FileUploadException();
		}

		if (header!=null && header.size()<2) {
			errors.rejectValue("file", "error.nbOfColumnsLessThanTwo", "Le fichier doit contenir au moins deux colonnes "
					+ "Nombre de colonnes détectés : " 
					+ header.size() + " " + header 
					+ ". Vérifiez le contenu et les séparateurs de colonnes.");
			throw new FileUploadException();
		}
	}


	/** ========================================================================================= 
	 * @throws FileUploadException */

	public void checkFileExist (FormUpload formUpload, Errors errors) throws FileUploadException {

		if (formUpload.getFile()==null || formUpload.getFile().getSize()==0) {
			errors.rejectValue("file", "error.fileNotSelected", "Veuillez sélectionner un fichier.");
			throw new FileUploadException();
		}

	}

	/** ========================================================================================= 
	 * @throws FileUploadException */

	public void checkFileFormat (FormUpload formUpload, Errors errors) throws FileUploadException {

		if (!formUpload.getFile().getContentType().equals("application/vnd.ms-excel")) {
			errors.rejectValue("file", "error.badFormat", "Veuillez importer votre fichier au format CSV.");
			throw new FileUploadException();
		}

	}

	/** ========================================================================================= */

	public File getFile() {
		return file;
	}
	

	public void setFile(File file) {
		this.file = file;
	}


	public List<Object> getData() {
		return data;
	}

	public Map<String, String> getMapHeaders() {
		return mapHeaders;
	}

	public String getSeparator() {
		return separator;
	}


	public List<String> getHeader() {
		return header;
	}


	public List<String> getNormalizedHeader() {
		return normalizedHeader;
	}

	/** ========================================================================================= */

}
