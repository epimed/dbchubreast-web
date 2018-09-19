package dbchubreast_web.service.exporter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuPrelevement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;
import dbchubreast_web.service.business.ChuPrelevementBiomarqueurService;
import dbchubreast_web.service.business.ChuPrelevementService;
import dbchubreast_web.service.business.ChuTumeurService;
import dbchubreast_web.service.exporter.header.IHeader;
import dbchubreast_web.service.exporter.header.PhaseHeaderDecorator;
import dbchubreast_web.service.exporter.header.PrelevementHeaderDecorator;
import dbchubreast_web.service.exporter.header.TumorHeader;
import dbchubreast_web.service.util.FormatService;

@Service
public class PrelevementExporter implements IExporter {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuTumeurService tumeurService;


	@Autowired
	private ChuPhaseTumeurService phaseService;

	@Autowired
	private ChuPrelevementService prelevementService;

	@Autowired
	private ChuPrelevementBiomarqueurService prelevementBiomarqueurService;

	@Autowired
	private FormatService formatService;


	private List<String> header;
	private List<Object> data = new ArrayList<Object>();


	public void generate() {
		this.generateHeader();
		this.generateData();
	}

	private void generateHeader() {
		IHeader tumorHeader = new TumorHeader();
		IHeader phaseHeader = new PhaseHeaderDecorator(tumorHeader);
		IHeader prelevementHeader = new PrelevementHeaderDecorator(phaseHeader);
		header = prelevementHeader.getListOfItems();
	}

	private void generateData() {
		data.clear();
		List<ChuPrelevement> prelevements = prelevementService.list();

		String [] biomarqueursSansStatut = {"ganglions", "rupture", "recoupe", "necrose", "emboles", "sbr"};
		String [] biomarqueursAvecStatut = {"re", "rp", "her2", "ki67", "upa", "pai1"};

		for (int l=0; l<prelevements.size(); l++) {

			Object[] line = new Object[header.size()];

			ChuPrelevement prelevement = prelevements.get(l);
			Integer idPrelevement = prelevement.getIdPrelevement();
			ChuPhaseTumeur phase = phaseService.findByIdPrelevementWithDependencies(idPrelevement);
			ChuTumeur tumeur = tumeurService.findByIdPhaseWithDependencies(phase.getIdPhase());
			ChuPatient patient = patientService.find(tumeur.getIdTumeur());

			int i = -1;
			line[++i] = patient.getIdPatient();

			line[++i] = tumeur.getIdTumeur();
			line[++i] = formatService.convertDateToString(tumeur.getDateDiagnostic(), "dd/MM/yyyy");
			line[++i] = tumeur.getAgeDiagnostic();
			line[++i] = tumeur.getCote();
			line[++i] = tumeur.getDfsMonths();
			line[++i] = tumeur.getOsMonths();
			line[++i] = tumeur.getRelapsed();
			line[++i] = tumeur.getDead();
			line[++i] = tumeur.getCategorie();
			line[++i] = tumeur.getChuEvolution() == null ? "" : tumeur.getChuEvolution().getNom();

			line[++i] = phase.getIdPhase();
			line[++i] = phase.getChuTypePhase()==null ? "" : phase.getChuTypePhase().getNom();
			line[++i] = formatService.convertDateToString(phase.getDateDiagnostic(), "dd/MM/yyyy");
			line[++i] = phase.getNatureDiagnostic();
			line[++i] = phase.getMetastases(); 		
			line[++i] = phase.getChuTopographie() == null ? "" : phase.getChuTopographie().getIdTopographie();
			line[++i] = phase.getChuTopographie() == null ? "" : phase.getChuTopographie().getNomFr();

			line[++i] = prelevement.getIdPrelevement();
			line[++i] = prelevement.getTk();
			line[++i] = prelevement.getChuTypePrelevement() == null ? "" : prelevement.getChuTypePrelevement().getNom();
			line[++i] = prelevement.getSitePrelevement();
			line[++i] = formatService.convertDateToString(prelevement.getDatePrelevement(), "dd/MM/yyyy");
			line[++i] = prelevement.getChuMorphologie() == null ? "" : prelevement.getChuMorphologie().getIdMorphologie();
			line[++i] = prelevement.getChuMorphologie() == null ? "" : prelevement.getChuMorphologie().getNomFr();
			line[++i] = prelevement.getTypeHistologique();
			line[++i] = prelevement.getAssociationCis();

			for (String idBiomarqueur: biomarqueursSansStatut) {
				line[++i] = prelevementBiomarqueurService.find(idPrelevement, idBiomarqueur) == null ? "" : prelevementBiomarqueurService.find(idPrelevement, idBiomarqueur).getValeur();
			}

			for (String idBiomarqueur: biomarqueursAvecStatut) {
				line[++i] = prelevementBiomarqueurService.find(idPrelevement, idBiomarqueur) == null ? "" : prelevementBiomarqueurService.find(idPrelevement, idBiomarqueur).getValeur();
				line[++i] = prelevementBiomarqueurService.find(idPrelevement, idBiomarqueur) == null ? "" : prelevementBiomarqueurService.find(idPrelevement, idBiomarqueur).getStatut();
			}

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
