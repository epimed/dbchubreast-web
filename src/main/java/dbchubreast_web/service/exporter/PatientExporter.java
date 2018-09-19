package dbchubreast_web.service.exporter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.util.FormatService;

@Service
public class PatientExporter implements IExporter {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private FormatService formatService;


	private List<String> header = new ArrayList<String>();
	private List<Object> data = new ArrayList<Object>();


	public void generate() {
		this.generateHeader();
		this.generateData();
	}

	private void generateHeader() {
		header.clear();
		header.add("id_patient");
		header.add("tk");
		header.add("rcp");
		header.add("prenom");
		header.add("nom");
		header.add("nom_naissance");
		header.add("sexe");
		header.add("date_naissance");
		header.add("date_deces");
		header.add("cause_deces");
		header.add("remarque");
		header.add("statut_brca");
		header.add("nombre_tumeurs");
	}

	private void generateData() {
		data.clear();
		List<String> listIdPatients = patientService.findAllIdPatients();

		for (String idPatient : listIdPatients) {
			
			ChuPatient patient = patientService.findByIdPatientWithDependencies(idPatient);
			
			Object[] line = new Object[header.size()];
			
			int ind=-1;
			line[++ind] = patient.getIdPatient();
			line[++ind] = patient.getTk();
			line[++ind] = patient.getRcp();
			line[++ind] = patient.getPrenom();
			line[++ind] = patient.getNom();
			line[++ind] = patient.getNomNaissance();
			line[++ind] = patient.getSexe();
			line[++ind] = formatService.convertDateToString(patient.getDateNaissance(), "dd/MM/yyyy");
			line[++ind] = formatService.convertDateToString(patient.getDateDeces(), "dd/MM/yyyy");
			line[++ind] = patient.getChuCauseDeces()==null ? "" :  patient.getChuCauseDeces().getNom();
			line[++ind] = patient.getRemarque();
			line[++ind] = patient.getStatutBrca();
			line[++ind] = patient.getChuTumeurs()==null ? "" : patient.getChuTumeurs().size();

			data.add(line);

		}
	}


	@Override
	public List<String> getHeader() {
		return this.header;
	}

	@Override
	public List<Object> getData() {
		return this.data;
	}

}
