package dbchubreast_web.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dbchubreast_web.entity.ChuBiomarqueur;
import dbchubreast_web.service.BaseService;

public class FormPrelevement extends BaseService implements IForm {

	private Integer idPrelevement;
	private String idPatient;
	private Integer idTumeur;
	private Integer idPhase;
	private Integer idTypePrelevement;
	private Date datePrelevement;
	private String sitePrelevement;
	private String idMorphologie;
	private String typeHistologique;
	private Boolean associationCis;

	private List<FormBiomarqueur> listFormBiomarqueurs;

	/** ========================================================= */

	public FormPrelevement() {
		super();
	}

	public FormPrelevement(String idPatient) {
		this.idPatient = idPatient;
	}

	/** ========================================================= */

	public void init(List<ChuBiomarqueur> listBiomarqueurs) {

		listFormBiomarqueurs = new ArrayList<FormBiomarqueur>();

		for (ChuBiomarqueur biomarqueur : listBiomarqueurs) {
			FormBiomarqueur formBio = new FormBiomarqueur();
			formBio.setIdBiomarqueur(biomarqueur.getIdBiomarqueur());
			formBio.setNom(biomarqueur.getNom());
			listFormBiomarqueurs.add(formBio);
		}
	}

	public Boolean getAssociationCis() {
		return associationCis;
	}

	public List<FormBiomarqueur> getListFormBiomarqueurs() {
		return listFormBiomarqueurs;
	}

	public void setListFormBiomarqueurs(List<FormBiomarqueur> listFormBiomarqueurs) {
		this.listFormBiomarqueurs = listFormBiomarqueurs;
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

	/** ====================================================================================== */

	public boolean isNew() {
		return this.idPrelevement == null;
	}

	/** ====================================================================================== */

	@Override
	public String toString() {
		return "FormPrelevement [idPrelevement=" + idPrelevement + ", idPatient=" + idPatient + ", idTumeur=" + idTumeur
				+ ", idPhase=" + idPhase + ", idTypePrelevement=" + idTypePrelevement + ", typeHistologique="
				+ typeHistologique + ", idMorphologie=" + idMorphologie + ", associationCis=" + associationCis
				+ ", datePrelevement=" + datePrelevement + ", sitePrelevement=" + sitePrelevement
				+ ", listFormBiomarqueurs=" + listFormBiomarqueurs + "]";
	}

}
