package dbchubreast_web.form;

import dbchubreast_web.service.BaseService;

public class FormComposant extends BaseService implements IForm {

	private Integer idComposant;
	private Integer idMethode;
	private String nomMethode;
	private String nomInternational;
	private String nomCommercial;
	private String classe;
	private String action;

	
	public FormComposant() {
		super();
	}


	public FormComposant(Integer idMethode, String nomMethode) {
		super();
		this.idMethode = idMethode;
		this.nomMethode = nomMethode;
	}


	public FormComposant(Integer idComposant, Integer idMethode, String nomMethode, String nomInternational,
			String nomCommercial, String classe, String action) {
		super();
		this.idComposant = idComposant;
		this.idMethode = idMethode;
		this.nomMethode = nomMethode;
		this.nomInternational = nomInternational;
		this.nomCommercial = nomCommercial;
		this.classe = classe;
		this.action = action;
	}


	
	
	public Integer getIdComposant() {
		return idComposant;
	}


	public void setIdComposant(Integer idComposant) {
		this.idComposant = idComposant;
	}


	public Integer getIdMethode() {
		return idMethode;
	}


	public void setIdMethode(Integer idMethode) {
		this.idMethode = idMethode;
	}


	public String getNomMethode() {
		return nomMethode;
	}


	public void setNomMethode(String nomMethode) {
		this.nomMethode = nomMethode;
	}


	public String getNomInternational() {
		return nomInternational;
	}


	public void setNomInternational(String nomInternational) {
		this.nomInternational = nomInternational;
	}


	public String getNomCommercial() {
		return nomCommercial;
	}


	public void setNomCommercial(String nomCommercial) {
		this.nomCommercial = nomCommercial;
	}


	public String getClasse() {
		return classe;
	}


	public void setClasse(String classe) {
		this.classe = classe;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public boolean isNew() {
		return this.idComposant==null;
	}


	@Override
	public String toString() {
		return "FormComposant [idComposant=" + idComposant + ", idMethode=" + idMethode + ", nomMethode=" + nomMethode
				+ ", nomInternational=" + nomInternational + ", nomCommercial=" + nomCommercial + ", classe=" + classe
				+ ", action=" + action + "]";
	}
	
	
	
	
	/** ====================================================================================== */

}
