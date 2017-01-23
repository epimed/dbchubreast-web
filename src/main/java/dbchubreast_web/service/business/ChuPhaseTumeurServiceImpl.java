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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuPhaseTumeurDao;
import dbchubreast_web.entity.ChuPhaseTumeur;
import dbchubreast_web.service.BaseService;


@Service
public class ChuPhaseTumeurServiceImpl extends BaseService implements ChuPhaseTumeurService {

	@Autowired
	private ChuPhaseTumeurDao phaseTumeurDao;

	/** =================================================================== */

	public ChuPhaseTumeur find(Integer idPhase) {
		return phaseTumeurDao.find(idPhase);
	}

	/** =================================================================== */

	public ChuPhaseTumeur findWithDependencies(Integer idPhase) {
		return phaseTumeurDao.findWithDependencies(idPhase);
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> list() {
		return phaseTumeurDao.list();
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> listWithDependencies() {
		return phaseTumeurDao.listWithDependencies();
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> list(Integer idTumeur) {
		return phaseTumeurDao.list(idTumeur);
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> listWithDependencies(Integer idTumeur) {
		return phaseTumeurDao.listWithDependencies(idTumeur);
	}

	/** =================================================================== */

	public List<ChuPhaseTumeur> list(Integer idTumeur, Integer idTypePhase) {
		return phaseTumeurDao.list(idTumeur, idTypePhase);
	}

	/** =================================================================== */

	public ChuPhaseTumeur findFirstRelapse(Integer idTumeur) {
		return phaseTumeurDao.findFirstRelapse(idTumeur);
	}
	
	/** =================================================================== */

	public ChuPhaseTumeur findByIdPrelevementWithDependencies(Integer idPrelevement) {
		return phaseTumeurDao.findByIdPrelevementWithDependencies(idPrelevement);
	}
	
	/** =================================================================== */

	public void save(ChuPhaseTumeur phaseTumeur) {
		phaseTumeurDao.save(phaseTumeur);	
	}

	/** =================================================================== */
	
	public void update(ChuPhaseTumeur phaseTumeur) {
		phaseTumeurDao.update(phaseTumeur);
	}

	/** =================================================================== */
	
	public void saveOrUpdate(ChuPhaseTumeur phaseTumeur) {
		phaseTumeurDao.saveOrUpdate(phaseTumeur);
	}

	/** =================================================================== */
	
	public ChuPhaseTumeur findPhaseInitiale(Integer idTumeur) {
		return phaseTumeurDao.findPhaseInitiale(idTumeur);
	}

	/** =================================================================== */

}
