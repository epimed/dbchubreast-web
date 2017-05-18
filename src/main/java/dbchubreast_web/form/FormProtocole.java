package dbchubreast_web.form;

import java.util.ArrayList;
import java.util.List;

import dbchubreast_web.service.BaseService;

public class FormProtocole extends BaseService implements IForm {

	private Integer idProtocole;
	private Integer idMethode;
	private String nomMethode;
	private String code;
	private String nom;
	private List<Integer> listIdComposants = new ArrayList<Integer>();

	
	public FormProtocole() {
		super();
	}
	
	
	public FormProtocole(Integer idMethode, String nomMethode) {
		super();
		this.idMethode = idMethode;
		this.nomMethode = nomMethode;
	}


	public boolean isNew() {
		return this.idProtocole==null;
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


	public Integer getIdProtocole() {
		return idProtocole;
	}

	public void setIdProtocole(Integer idProtocole) {
		this.idProtocole = idProtocole;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Integer> getListIdComposants() {
		return listIdComposants;
	}

	public void setListIdComposants(List<Integer> listIdComposants) {
		this.listIdComposants = listIdComposants;
	}


	@Override
	public String toString() {
		return "FormProtocole [idProtocole=" + idProtocole + ", idMethode=" + idMethode + ", nomMethode=" + nomMethode
				+ ", code=" + code + ", nom=" + nom + ", listIdComposants=" + listIdComposants + "]";
	}

	
	
	/** ====================================================================================== */

}
