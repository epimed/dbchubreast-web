/**
* EpiMed - Information system for bioinformatics developments in the field of epigenetics
 * 
 * This software is a computer program which performs the data management 
 * for EpiMed platform of the Institute for Advances Biosciences (IAB)
 *
 * Copyright University of Grenoble Alps (UGA)
 * 
 * Please check LICENSE file
 *
 * Author: Ekaterina Bourova-Flin 
 *
 */
package dbchubreast_web.form;

public class FormBiomarqueur {

	private String idBiomarqueur;
	private String nom;
	private String valeur;
	private String statut;

	public String getIdBiomarqueur() {
		return idBiomarqueur;
	}

	public void setIdBiomarqueur(String idBiomarqueur) {
		this.idBiomarqueur = idBiomarqueur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	@Override
	public String toString() {
		return "FormBiomarqueur [idBiomarqueur=" + idBiomarqueur + ", nom=" + nom + ", valeur=" + valeur + ", statut="
				+ statut + "]";
	}

}
