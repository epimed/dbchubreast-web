package dbchubreast_web.form;
// Generated 26 d�c. 2016 14:27:40 by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import dbchubreast_web.service.BaseService;

public abstract class AbstractFormPhaseTumeur extends BaseService implements IForm {

	protected Integer idTumeur;
	protected String idPatient;
	protected Date dateDiagnostic;

	protected Integer idPhase;
	protected Integer idTypePhase;
	
	protected String idTopographie;
	
	@Length(max = 50, message = "50 caractères au maximum")
	private String cT;
	
	@Length(max = 50, message = "50 caractères au maximum")
	private String cN;
	
	@Length(max = 50, message = "50 caractères au maximum")
	private String cM;
	
	@Length(max = 50, message = "50 caractères au maximum")
	private String cTaille;

	@Length(max = 50, message = "50 caractères au maximum")
	private String pT;
	
	@Length(max = 50, message = "50 caractères au maximum")
	private String pN;
	
	@Length(max = 50, message = "50 caractères au maximum")
	private String pM;
	
	@Length(max = 50, message = "50 caractères au maximum")
	private String pTaille;

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

	public String getcT() {
		return cT;
	}

	public void setcT(String cT) {
		this.cT = cT;
	}

	public String getcN() {
		return cN;
	}

	public void setcN(String cN) {
		this.cN = cN;
	}

	public String getcM() {
		return cM;
	}

	public void setcM(String cM) {
		this.cM = cM;
	}

	public String getcTaille() {
		return cTaille;
	}

	public void setcTaille(String cTaille) {
		this.cTaille = cTaille;
	}

	public String getpT() {
		return pT;
	}

	public void setpT(String pT) {
		this.pT = pT;
	}

	public String getpN() {
		return pN;
	}

	public void setpN(String pN) {
		this.pN = pN;
	}

	public String getpM() {
		return pM;
	}

	public void setpM(String pM) {
		this.pM = pM;
	}

	public String getpTaille() {
		return pTaille;
	}

	public void setpTaille(String pTaille) {
		this.pTaille = pTaille;
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
	

	public String getIdTopographie() {
		return idTopographie;
	}

	public void setIdTopographie(String idTopographie) {
		this.idTopographie = idTopographie;
	}

	/** ====================================================================================== */

	public abstract boolean isNew();

	/** ====================================================================================== */

}
