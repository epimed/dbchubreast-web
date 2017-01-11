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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Table {

	/** ======================= ATTRIBUTES ====================================================*/

	// Table : header (String) + data (String [])
	private Map<String, String []> table = new LinkedHashMap<String, String []>();

	// Length of data
	private int dataLength;

	// Header
	private List<String> header = new ArrayList<String>();

	// Data
	private List<Object> data = new ArrayList<Object>();


	/** ======================= CONSTRUCTOR ====================================================*/

	public Table(int dataLength) {
		super();
		this.dataLength = dataLength;
	}

	/** ======================================================================================*/
	
	public void clear() {
		table.clear();
		header.clear();
		data.clear();
	}
	
	
	/** ======================================================================================*/

	public void displayTable () {

		for (Map.Entry<String, String []> entry : table.entrySet()) {
			String key = entry.getKey();
			String [] value = entry.getValue();
			System.out.println(key + ":\t" + Arrays.toString(value));
		}
	}

	/** ======================================================================================*/

	public void displayData(List<Object> data) {

		for (int i=0; i<data.size(); i++) {
			String [] dataLine = (String[]) data.get(i);
			System.out.println(Arrays.toString(dataLine));
		}

	}

	/** ======================================================================================*/

	public List<Object> getData() {
		data.clear();
		List<String> header = this.getHeader();
		for (int i=0; i<this.dataLength; i++) {
			String [] dataLine = new String [header.size()];
			for (int j=0; j<header.size(); j++) {
				dataLine[j] = table.get(header.get(j))[i];
			}
			data.add(dataLine);
		}
		return data;
	}

	/** ======================================================================================*/

	public List<String> getHeader() {
		header.clear();
		header.addAll(table.keySet());
		return header;
	}


	/** ======================================================================================*/

	public void addToTable(int index, String key, Object value) {

		// System.out.println(index + " " + key + " " + value);
		
		if (table.get(key)==null) {
			table.put(key, new String [this.dataLength]);
		}
		table.get(key)[index] = value==null ? null : value.toString();

	}

	/** ======================================================================================*/

}
