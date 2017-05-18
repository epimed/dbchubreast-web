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

import dbchubreast_web.entity.ChuProtocoleTraitement;
import dbchubreast_web.form.FormProtocole;

public interface FormProtocoleService {

	public void saveOrUpdateForm(FormProtocole form);
	public FormProtocole getForm(ChuProtocoleTraitement traitement);
}
