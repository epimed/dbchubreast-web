package dbchubreast_web.service.exporter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.ChuComposantTraitement;
import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.service.business.ChuComposantTraitementService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;
import dbchubreast_web.service.business.ChuTraitementService;
import dbchubreast_web.service.business.ChuTumeurService;
import dbchubreast_web.service.exporter.header.IHeader;
import dbchubreast_web.service.exporter.header.PhaseHeaderDecorator;
import dbchubreast_web.service.exporter.header.TraitementHeaderDecorator;
import dbchubreast_web.service.exporter.header.TumorHeader;
import dbchubreast_web.service.util.FormatService;

@Service
public class TraitementExporter implements IExporter {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuTumeurService tumeurService;


	@Autowired
	private ChuPhaseTumeurService phaseService;

	@Autowired
	private ChuTraitementService traitementService;
	
	@Autowired
	private ChuComposantTraitementService composantService;

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
		IHeader traitementHeader = new TraitementHeaderDecorator(phaseHeader);
		header = traitementHeader.getListOfItems();
	}

	private void generateData() {
		data.clear();
		List<ChuTraitement> traitements = traitementService.list();
		List<ChuComposantTraitement> composants;

		for (int l=0; l<traitements.size(); l++) {

			Object[] line = new Object[header.size()];

			ChuTraitement traitement = traitements.get(l);
			Integer idPhase = traitement.getChuPhaseTumeur().getIdPhase();
			ChuPhaseTumeur phase = phaseService.findWithDependencies(idPhase);
			ChuTumeur tumeur = tumeurService.findByIdPhaseWithDependencies(idPhase);
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

			line[++i] = traitement.getIdTraitement();
			line[++i] = traitement.getChuTypeTraitement() == null ? "" : traitement.getChuTypeTraitement().getNom();
			line[++i] = traitement.getChuMethodeTraitement() == null ? "" : traitement.getChuMethodeTraitement().getNom();
			line[++i] = traitement.getChuProtocoleTraitement() == null ? "" : traitement.getChuProtocoleTraitement().getCode();
			line[++i] = traitement.getChuProtocoleTraitement() == null ? "" : traitement.getChuProtocoleTraitement().getNom();

			// === Composants ===
			String textComposants = "";
			if (traitement.getChuProtocoleTraitement()!=null) {
				composants = composantService.listByProtocole(traitement.getChuProtocoleTraitement().getIdProtocole());
				for (ChuComposantTraitement composant : composants ) {
					textComposants = textComposants + " " + composant.getNomInternational();
				}
			}
			line[++i] = textComposants.trim();

			line[++i] = traitement.getChuReponse() == null ? "" :  traitement.getChuReponse().getNom();
			line[++i] = formatService.convertDateToString(traitement.getDateDebut(), "dd/MM/yyyy");
			line[++i] = formatService.convertDateToString(traitement.getDateFin(), "dd/MM/yyyy");
			line[++i] = traitement.getDose();
			line[++i] = traitement.getNbCures();
			line[++i] = traitement.getGgSentinelle();
			line[++i] = traitement.getRemarque();

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
