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
package dbchubreast_web.service.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.entity.ChuPatient;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTraitement;
import dbchubreast_web.form.FormTraitement;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuMethodeTraitementService;
import dbchubreast_web.service.business.ChuPatientService;
import dbchubreast_web.service.business.ChuPhaseTumeurService;
import dbchubreast_web.service.business.ChuProtocoleTraitementService;
import dbchubreast_web.service.business.ChuReponseService;
import dbchubreast_web.service.business.ChuTraitementService;
import dbchubreast_web.service.updater.UpdaterTypeTraitement;

@Service
public class FormTraitementServiceImpl extends BaseService implements FormTraitementService {

	@Autowired
	private ChuPatientService patientService;

	@Autowired
	private ChuPhaseTumeurService phaseTumeurService;

	@Autowired
	private ChuTraitementService traitementService;

	@Autowired
	private ChuMethodeTraitementService methodeTraitementService;

	@Autowired
	private ChuProtocoleTraitementService protocoleTraitementService;

	@Autowired
	private ChuReponseService reponseService;

	@Autowired
	private UpdaterTypeTraitement updaterTypeTraitement;



	/** ====================================================================== */

	public void saveOrUpdateForm(FormTraitement form) {

		logger.debug("=== " + this.getClass().getName() + ": saveOrUpdateForm" + " ===");


		ChuTraitement traitement = null;

		if (form.isNew()) {
			traitement = new ChuTraitement();
		}
		else {
			traitement = traitementService.find(form.getIdTraitement());

		}

		logger.debug("Traitement {}", traitement);



		ChuPhaseTumeur phase = phaseTumeurService.find(form.getIdPhase());
		traitement.setChuPhaseTumeur(phase);
		traitement.setChuMethodeTraitement(methodeTraitementService.find(form.getIdMethode()));

		if (form.getIdProtocole()!=null) {
			traitement.setChuProtocoleTraitement(protocoleTraitementService.find(form.getIdProtocole()));
		}

		if (form.getIdReponse()!=null) {
			traitement.setChuReponse(reponseService.find(form.getIdReponse()));
		}

		traitement.setDateDebut(form.getDateDebut());
		traitement.setDateFin(form.getDateFin());
		traitement.setNbCures(form.getNbCures());
		traitement.setGgSentinelle(form.getGgSentinelle());
		traitement.setRemarque(form.getRemarque());


		traitementService.saveOrUpdate(traitement);
		form.setIdTraitement(traitement.getIdTraitement());


		// === Type traitement ===
		updaterTypeTraitement.update(traitement);

	}

	/** ====================================================================== */

	public FormTraitement getForm(ChuTraitement traitement) {

		logger.debug("=== " + this.getClass().getName() + ": getForm" + " ===");

		FormTraitement form = new FormTraitement();

		if (traitement!=null) {
			form.setIdTraitement(traitement.getIdTraitement());
			form.setIdTumeur(traitement.getChuPhaseTumeur().getChuTumeur().getIdTumeur());
			form.setIdPhase(traitement.getChuPhaseTumeur().getIdPhase());
			form.setIdMethode(traitement.getChuMethodeTraitement().getIdMethode());

			if (traitement.getChuProtocoleTraitement()!=null) {
				form.setIdProtocole(traitement.getChuProtocoleTraitement().getIdProtocole());
			}

			form.setDateDebut(traitement.getDateDebut());
			form.setDateFin(traitement.getDateFin());
			form.setNbCures(traitement.getNbCures());
			form.setGgSentinelle(traitement.getGgSentinelle());
			form.setRemarque(traitement.getRemarque());

			// Patient
			ChuPatient patient = patientService.findByIdTraitement(traitement.getIdTraitement());
			form.setIdPatient(patient.getIdPatient());
		}

		return form;
	}

	/** ====================================================================== */

}
