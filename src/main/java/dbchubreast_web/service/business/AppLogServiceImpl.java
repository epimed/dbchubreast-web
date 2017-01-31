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

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import dbchubreast_web.dao.AppLogDao;
import dbchubreast_web.entity.AppLog;

@Service
@SuppressWarnings("rawtypes")
public class AppLogServiceImpl implements AppLogService {

	@Autowired
	AppLogDao logDao;

	@Autowired
	private HttpServletRequest request;

	/** ============================================================================================ */
	public void logComment(String username, String comment) {

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

	/** ============================================================================================ */

	public void log(ModelAndView modelAndView) {

		String comment = null;
		this.log(modelAndView, comment);
	}


	/** ============================================================================================ */

	public void log(ModelAndView modelAndView, String comment) {

		if (modelAndView!=null) {

			String parameter = this.mapToString(modelAndView.getModelMap());

			this.saveLog(
					this.getUsername(),
					this.getRole(),
					this.getIpAddress(),  // ip
					request==null ? null : request.getMethod(),  // method
							request==null ? null : request.getContextPath(),  // route
									request==null ? null : request.getRequestURI(), // url
											parameter,  // parameter
											comment // comment
					); 
		}
	}

	/** ============================================================================================ */

	public void log() {
		String comment = null;
		this.log(comment);
	}

	/** ============================================================================================ */

	public void log(String comment) {

		String parameter = this.mapToString(request.getParameterMap());

		this.saveLog(
				this.getUsername(),
				this.getRole(),
				this.getIpAddress(),  // ip
				request==null ? null : request.getMethod(),  // method
						request==null ? null : request.getContextPath(),  // route
								request==null ? null : request.getRequestURI(), // url
										parameter,  // parameter
										comment // comment
				); 

	}


	/** ============================================================================================ */

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

	public void save(AppLog appLog) {
		logDao.save(appLog);
	}

	/** =======================================================================*/

	private String getUsername() {

		String username = null;

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


			if (principal!=null) {

				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} 
				else {
					username = principal.toString();
				}
			}
		}
		catch (Exception ex) {
			username = "UNKNOWN";
		}

		return username;
	}


	/** =======================================================================*/

	private String getRole() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String role = null;

		if (principal!=null) {

			if (principal instanceof UserDetails) {
				role = ((UserDetails) principal).getAuthorities().toString();
			}
		}

		return role;
	}

	/** =======================================================================*/


	private String getIpAddress() {
		String ipAddress = null;
		ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	/** =======================================================================*/


	private String mapToString(Map<String, ?> map) {

		String text = "";

		if (map!=null) {
			for (Map.Entry<String, ?> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof Collection) {
					value = "list of " + ((Collection) value).size() + " values";
				}
				if (value instanceof String[]) {
					value = "array of " + ((String []) value).length + " values";
				}
				text = text + key + ": " + value + " ";
			}
		}
		return text;
	}

	/** =======================================================================*/
}

