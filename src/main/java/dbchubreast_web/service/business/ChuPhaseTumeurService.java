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
package dbchubreast_web.service.business;

import java.util.List;

import dbchubreast_web.entity.ChuPhaseTumeur;

public interface ChuPhaseTumeurService {

	public ChuPhaseTumeur find(Integer idPhase);

	public ChuPhaseTumeur findWithDependencies(Integer idPhase);

	public ChuPhaseTumeur findFirstRelapse(Integer idTumeur);
	
	public List<ChuPhaseTumeur> findRelapses(Integer idTumeur);

	public ChuPhaseTumeur findPhaseInitiale(Integer idTumeur);

	public ChuPhaseTumeur findByIdPrelevementWithDependencies(Integer idPrelevement);

	public List<ChuPhaseTumeur> list();

	public List<ChuPhaseTumeur> listWithDependencies();

	public List<ChuPhaseTumeur> list(Integer idTumeur);

	public List<ChuPhaseTumeur> listWithDependencies(Integer idTumeur);

	public List<ChuPhaseTumeur> list(Integer idTumeur, Integer idTypePhase);
	
	public List<Object> listChronoPrelevementsTraitements(Integer idPhase); 

	public void save(ChuPhaseTumeur phaseTumeur);

	public void update(ChuPhaseTumeur phaseTumeur);

	public void saveOrUpdate(ChuPhaseTumeur phaseTumeur);
	
	public void deleteWithDependencies(ChuPhaseTumeur phaseTumeur);
}
