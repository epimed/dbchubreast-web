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

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dbchubreast_web.form.FormUpload;

public interface FormUploadService {
	public File saveFileOnDisk(FormUpload formUpload, HttpServletRequest request) throws Exception;
	public void saveOrUpdate(List<String> header, List<Object> data);
}
