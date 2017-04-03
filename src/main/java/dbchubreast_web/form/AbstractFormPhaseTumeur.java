package dbchubreast_web.form;
// Generated 26 d�c. 2016 14:27:40 by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dbchubreast_web.service.BaseService;

public abstract class AbstractFormPhaseTumeur extends BaseService implements IForm {

	protected Integer idTumeur;
	protected String idPatient;
	protected Date dateDiagnostic;

	protected Integer idPhase;
	protected Integer idTypePhase;

	protected String remarque;

	protected List<Integer> listIdMetastases = new ArrayList<Integer>(0);

	public Integer getIdTumeur() {
		return idTumeur;
	}

	public void setIdTumeur(Integer idTumeur) {
		this.idTumeur = idTumeur;
	}

	public String getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	public Date getDateDiagnostic() {
		return dateDiagnostic;
	}

	public void setDateDiagnostic(Date dateDiagnostic) {
		this.dateDiagnostic = dateDiagnostic;
	}

	public Integer getIdPhase() {
		return idPhase;
	}

	public void setIdPhase(Integer idPhase) {
		this.idPhase = idPhase;
	}

	public Integer getIdTypePhase() {
		return idTypePhase;
	}

	public void setIdTypePhase(Integer idTypePhase) {
		this.idTypePhase = idTypePhase;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public List<Integer> getListIdMetastases() {
		return listIdMetastases;
	}

	public void setListIdMetastases(List<Integer> listIdMetastases) {
		this.listIdMetastases = listIdMetastases;
	}

	/** ====================================================================================== */

	public abstract boolean isNew();

	/** ====================================================================================== */

}
