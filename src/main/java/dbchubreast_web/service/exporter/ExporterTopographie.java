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
package dbchubreast_web.service.exporter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.ChuTopographieDao;
import dbchubreast_web.entity.ChuTopographie;

@Service
public class ExporterTopographie extends AbstractExporter {

	@Autowired
	private ChuTopographieDao topographieDao;

	@Override
	public Table export() {

		List<ChuTopographie> list = topographieDao.list();

		Table table = new Table(list.size());

		for (int i = 0; i < list.size(); i++) {

			ChuTopographie topo = list.get(i);

			// ===== Topographie =====

			table.addToTable(i, "id_topographie", topo.getIdTopographie());
			table.addToTable(i, "nom_fr", topo.getNomFr());
			table.addToTable(i, "nom_en", topo.getNomEn());

			// ===== Groupe de topographie =====

			table.addToTable(i, "id_groupe_topo",
					topo.getChuGroupeTopographie() == null ? null : topo.getChuGroupeTopographie().getIdGroupeTopo());
			table.addToTable(i, "nom_groupe_topo",
					topo.getChuGroupeTopographie() == null ? null : topo.getChuGroupeTopographie().getNom());

		}

		return table;

	}
}
