package dbchubreast_web.form;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dbchubreast_web.entity.ChuBiomarqueur;
import dbchubreast_web.service.BaseService;
import dbchubreast_web.service.business.ChuBiomarqueurService;

@Component
public class FormPrelevement extends BaseService implements IForm {

	@Autowired
	ChuBiomarqueurService biomarqueurService;
	
	private Integer idPrelevement;
	private String idPatient;
	private Integer idTumeur;
	private Integer idPhase;
	private Integer idTypePrelevement;
	
	private String typeHistologique;
	private String idMorphologie;
	private Boolean associationCis;
	private Date datePrelevement;
	private String sitePrelevement;
	
	private List<ChuBiomarqueur> listBiomarqueurs;
	private String [] listValeurs;

	/** ========================================================= */
	
	public FormPrelevement() {
		super();
	}
	
	/** ========================================================= */
	
	public void populateBiomarqueurs() {
		this.listBiomarqueurs = biomarqueurService.list();
		this.listValeurs = new String[this.listBiomarqueurs.size()];
		logger.debug("Biomarqueurs {}", this.listBiomarqueurs);
	}
	
	/** ========================================================= */
	
	
	
	public List<ChuBiomarqueur> getListBiomarqueurs() {
		return listBiomarqueurs;
	}

	public String[] getListValeurs() {
		return listValeurs;
	}

	public void setListValeurs(String[] listValeurs) {
		this.listValeurs = listValeurs;
	}

	public void setListBiomarqueurs(List<ChuBiomarqueur> listBiomarqueurs) {
		this.listBiomarqueurs = listBiomarqueurs;
	}

	public Boolean getAssociationCis() {
		return associationCis;
	}

	public void setAssociationCis(Boolean associationCis) {
		this.associationCis = associationCis;
	}

	public Date getDatePrelevement() {
		return datePrelevement;
	}

	public void setDatePrelevement(Date datePrelevement) {
		this.datePrelevement = datePrelevement;
	}

	public String getSitePrelevement() {
		return sitePrelevement;
	}

	public void setSitePrelevement(String sitePrelevement) {
		this.sitePrelevement = sitePrelevement;
	}

	public Integer getIdTypePrelevement() {
		return idTypePrelevement;
	}

	public void setIdTypePrelevement(Integer idTypePrelevement) {
		this.idTypePrelevement = idTypePrelevement;
	}

	public String getIdMorphologie() {
		return idMorphologie;
	}

	public void setIdMorphologie(String idMorphologie) {
		this.idMorphologie = idMorphologie;
	}

	public String getTypeHistologique() {
		return typeHistologique;
	}

	public void setTypeHistologique(String typeHistologique) {
		this.typeHistologique = typeHistologique;
	}

	public Integer getIdPhase() {
		return idPhase;
	}

	public void setIdPhase(Integer idPhase) {
		this.idPhase = idPhase;
	}

	public Integer getIdTumeur() {
		return idTumeur;
	}

	public void setIdTumeur(Integer idTumeur) {
		this.idTumeur = idTumeur;
	}

	public Integer getIdPrelevement() {
		return idPrelevement;
	}

	public void setIdPrelevement(Integer idPrelevement) {
		this.idPrelevement = idPrelevement;
	}

	public String getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}


	public String toString() {
		return "FormPrelevement [idPrelevement=" + idPrelevement + ", idPatient=" + idPatient + ", idTumeur=" + idTumeur
				+ ", idPhase=" + idPhase + ", idTypePrelevement=" + idTypePrelevement + ", typeHistologique="
				+ typeHistologique + ", idMorphologie=" + idMorphologie + ", associationCis=" + associationCis
				+ ", datePrelevement=" + datePrelevement + ", sitePrelevement=" + sitePrelevement + "]";
	}


	/** ====================================================================================== */
	
	public boolean isNew() {
		return this.idPrelevement==null;
	}
	
	/** ====================================================================================== */

}
