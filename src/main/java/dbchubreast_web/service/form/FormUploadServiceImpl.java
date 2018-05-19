package dbchubreast_web.service.form;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.ChuParameter;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPatientParameter;
import dbchubreast_web.entity.ChuPatientParameterId;
import dbchubreast_web.form.FormUpload;
import dbchubreast_web.service.business.ChuParameterService;
import dbchubreast_web.service.business.ChuPatientParameterService;
import dbchubreast_web.service.business.ChuPatientService;

@Service
public class FormUploadServiceImpl implements FormUploadService {

	private SimpleDateFormat filenameDateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");

	private Date today = new Date();
	
	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuParameterService parameterService;

	@Autowired
	private ChuPatientParameterService patientParameterService;


	/** ========================================================================================================= */

	public void saveOrUpdate(List<String> header, List<Object> data) {

		List<String> excludeColumns = new ArrayList<String>();
		excludeColumns.add("id_patient");
		excludeColumns.add("nom");
		excludeColumns.add("prenom");
		excludeColumns.add("date_naissance");


		// === Init ===
		Map<String, ChuParameter> map = new HashMap<String, ChuParameter>();

		// === Find position of id_patient ===

		Integer indIdPatient = null;
		int k=0;
		boolean isFound = false;
		while (!isFound && k<header.size()) {
			String itemHeader = header.get(k);
			if (itemHeader!=null && itemHeader.trim().equals("id_patient")) {
				indIdPatient = k;
				isFound = true;
			}
			k++;
		}


		// === Create parameters and map to header ===

		for (String itemHeader: header) {
			if (itemHeader!=null && !excludeColumns.contains(itemHeader)) {
				ChuParameter parameter = parameterService.find(itemHeader);
				if (parameter==null) {
					parameter = new ChuParameter();
					parameter.setNom(itemHeader);
					parameterService.save(parameter);
				}
				map.put(itemHeader, parameter);
			}
		}


		if (indIdPatient!=null) {

			for (int i=0; i<data.size(); i++) {

				String[] line = (String[]) data.get(i);
				String idPatient = line[indIdPatient]==null ? null : line[indIdPatient].trim();

				ChuPatient patient = patientService.find(idPatient);

				if (patient!=null) {

					for (int j=0; j<header.size(); j++) {
						if (j<line.length) {
							String key = header.get(j);
							String value = line[j]==null ? null : line[j].trim();

							if (key!=null && !excludeColumns.contains(key) && value!=null && !value.isEmpty()) {
								// logger.debug("\t key={}, value={}", key, value);
								
								ChuParameter parameter = map.get(key);
								ChuPatientParameter pp = patientParameterService.find(idPatient, parameter.getIdParameter());
								
								if (pp==null) {
									pp = new ChuPatientParameter();
									ChuPatientParameterId id = new ChuPatientParameterId();
									id.setIdParameter(parameter.getIdParameter());
									id.setIdPatient(patient.getIdPatient());
									pp.setId(id);
									pp.setChuPatient(patient);
									pp.setChuParameter(parameter);
									pp.setValeur(value);
								}
								
								pp.setDateImport(today);
								pp.setEnabled(true);
								
								patientParameterService.saveOrUpdate(pp);
							}

						}
					}
				}

			}
		}

	}

	/** ========================================================================================================= */

	public void saveFileOnDisk(FormUpload formUpload, HttpServletRequest request)  throws Exception {
		if (formUpload!=null && formUpload.getFile()!=null) {

			// String uploadPathString = "E:\\WORK\\ECLIPSE_WORKSPACE\\dbchubreast-web\\src\\main\\webapp\\resources\\uploads\\";
			String uploadPathString = "/epistorage/database/";	
			File uploadPath = new File(uploadPathString);

			if (!uploadPath.exists()) {
				uploadPathString = request.getServletContext().getRealPath("/resources/uploads/");
				uploadPath = new File(uploadPathString);
				if (!uploadPath.exists()) {
					uploadPath.mkdir();
				}
			}

			String filePath = uploadPath + File.separator + filenameDateFormat.format(new Date()) + "_PROBREAST_clinical_supplement.csv";
			File dest = new File(filePath);
			formUpload.getFile().transferTo(dest);
			// logger.debug("FILE {}", dest.getAbsolutePath());
		}
	}

	/** ========================================================================================================= */

}
