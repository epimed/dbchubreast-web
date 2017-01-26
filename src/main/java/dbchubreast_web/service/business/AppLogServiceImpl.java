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
package dbchubreast_web.service.business;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import dbchubreast_web.dao.AppLogDao;
import dbchubreast_web.entity.AppLog;

@Service
public class AppLogServiceImpl implements AppLogService {

	@Autowired
	AppLogDao logDao;

	@Autowired
	private HttpServletRequest request;

	public void saveLog(String comment) {

		String parameterText = "";
		String ipAddress = null;

		if (request!=null) {

			for (Map.Entry<String,String[]> entry : request.getParameterMap().entrySet()) {
				String key = entry.getKey();
				String[] value = entry.getValue();
				parameterText = parameterText + key + ": " + Arrays.toString(value) + " ";
			}


			ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}	

			if (parameterText.isEmpty()) {
				parameterText = null;
			}

		}
		// === Logged user ===

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		String role = null;

		if (principal!=null) {

			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
				role = ((UserDetails) principal).getAuthorities().toString();
			} else {
				username = principal.toString();
			}

		}

		this.saveLog(
				username,
				role,
				ipAddress,  // ip
				request==null ? null : request.getMethod(),  // method
				request==null ? null : request.getContextPath(),  // route
				request==null ? null : request.getRequestURI(), // url
				parameterText,  // parameter
				comment // comment
				); 

	}


	/** =======================================================================*/

	public void saveLog(String username, String role, String ip, String method, String route, String url, String parameter, String comment) {

		AppLog appLog = new AppLog();
		appLog.setLastActivity(new Date());

		if (username!=null) {
			appLog.setUsername(username);
		}

		if (role!=null) {
			appLog.setRole(role);
		}

		if (ip!=null) {
			appLog.setIp(ip);
		}
		if (method!=null) {
			appLog.setMethod(method);
		}
		if (route!=null) {
			appLog.setRoute(route);
		}
		if (url!=null) {
			if (url.length()>500) {
				url = url.substring(0, 499);
			}
			appLog.setUrl(url);
		}
		if (parameter!=null) {
			appLog.setParameter(parameter);
		}

		if (comment!=null) {
			appLog.setComment(comment);
		}

		logDao.save(appLog);

	}

	
	/** =======================================================================*/

	public void saveComment(String username, String comment) {

		AppLog appLog = new AppLog();
		appLog.setLastActivity(new Date());

		if (username!=null) {
			appLog.setUsername(username);
		}
		
		if (comment!=null) {
			appLog.setComment(comment);
		}

		logDao.save(appLog);

	}

	

	/** =======================================================================*/

	public void save(AppLog appLog) {
		logDao.save(appLog);
	}

	/** =======================================================================*/


}
