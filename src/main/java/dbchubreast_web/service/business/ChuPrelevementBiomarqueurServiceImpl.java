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

import dbchubreast_web.dao.ChuPrelevementBiomarqueurDao;
import dbchubreast_web.entity.ChuPrelevementBiomarqueur;

@Service
public class ChuPrelevementBiomarqueurServiceImpl  implements ChuPrelevementBiomarqueurService {

	@Autowired
	private ChuPrelevementBiomarqueurDao prelevementBiomarqueurDao;

	public List<ChuPrelevementBiomarqueur> list(Integer idPrelevement) {
		return prelevementBiomarqueurDao.list(idPrelevement);
	}
	
	public List<ChuPrelevementBiomarqueur> list(List<Integer> listIdPrelevements) {
		return prelevementBiomarqueurDao.list(listIdPrelevements);
	}
	
}
