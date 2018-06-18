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
package dbchubreast_web.service.form.tumeur;

import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.entity.ChuTumeur;
import dbchubreast_web.form.tumeur.FormPhaseRechute;

public interface FormPhaseRechuteService {
	public void saveOrUpdateForm(FormPhaseRechute form);
	public FormPhaseRechute getFormPhaseRechute(ChuPhaseTumeur phase);
	public FormPhaseRechute createNewFormPhaseRechute(ChuTumeur tumeur);
}
