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
package dbchubreast_web.service.util;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;
import org.springframework.stereotype.Service;

@Service
public class FormatService {

	/** ================================================================================= */

	public String[] convertStringToArray(String list) {
		String[] array = null;

		if (list != null && !list.isEmpty()) {
			array = list.trim().replaceAll("['\"]", "").split("[,;:\\p{Space}][\\p{Space}]*");
		}

		return array;
	}

	/** ================================================================================= */

	public static String flattenToAscii(String string) {
		char[] out = new char[string.length()];
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		int j = 0;
		for (int i = 0, n = string.length(); i < n; ++i) {
			char c = string.charAt(i);
			if (c <= '\u007F')
				out[j++] = c;
		}
		return new String(out);
	}

	/** ================================================================================= */

	public String normalize(String entry) {
		return flattenToAscii(entry).replaceAll("[\\p{Punct}\\p{Space}*]", "_").toLowerCase();
	}

	/** ================================================================================= */

	public String formatFisrtName(String firstName) {

		if (firstName == null) {
			return null;
		}

		String result = "";
		String[] parts = firstName.split("[-\\p{Space}]");

		for (int i = 0; i < parts.length; i++) {
			String part = parts[i].trim();
			if (part != null && !part.isEmpty()) {
				part = part.toLowerCase();
				String formattedPart = Character.toUpperCase(part.charAt(0)) + "";
				if (part.length() > 1) {
					formattedPart = formattedPart + part.substring(1);
				}
				result = result + formattedPart;
				if (i < parts.length - 1) {
					result = result + "-";
				}
			}
		}

		return result;
	}

	/** ====================================================================================== */

	public Date recognizeDate(Object object) {

		Date date = null;
		try {
			date = (Date) object;
		} catch (ClassCastException e) {
			// nothing to do
		}

		return date;

	}

	/** ====================================================================================== */

	public Integer recognizeInteger(String text) {

		Integer result = null;
		try {
			Double resultDouble = Double.parseDouble(text);
			result = resultDouble.intValue();
		} catch (ClassCastException | NumberFormatException e) {
			// System.err.println("WARNING! Value " + text + " cannot be
			// recongnized as an integer." + " Error message: " +
			// e.getMessage());
		}

		return result;

	}

	/** ====================================================================================== */

	public Double recognizeDouble(String text) {

		Double result = null;
		try {
			text = text.replaceAll(",", ".");
			result = Double.parseDouble(text);
		} catch (ClassCastException | NumberFormatException e) {
			// System.err.println("WARNING! Value " + text + " cannot be
			// recongnized as a double." + " Error message: " + e.getMessage());
		}

		return result;

	}

	/** ====================================================================================== */

	public Integer recognizeInteger(Object object) {
		Integer result = null;
		try {
			String resultString = (String) object;
			result = this.recognizeInteger(resultString);
		} catch (ClassCastException e1) {
			try {
				Double resultDouble = (Double) object;
				result = resultDouble.intValue();
			} catch (ClassCastException e2) {
				result = null;
				System.err.println("WARNING! Value " + object + " cannot be recongnized as an integer.");
				e2.printStackTrace();
			}
		}
		return result;
	}

	/** ====================================================================================== */

	public String recognizeString(Object object) {
		String result = null;
		try {
			result = (String) object;
		} catch (ClassCastException e1) {
			try {
				Double resultDouble = (Double) object;
				Integer resultInteger = resultDouble.intValue();
				Double difference = Math.abs(resultDouble - resultInteger);
				if (difference > 0) {
					result = resultDouble.toString();
				} else {
					result = resultInteger.toString();
				}

			} catch (ClassCastException e2) {

				try {
					Integer resultInteger = (Integer) object;
					result = resultInteger.toString();
				} catch (ClassCastException e3) {

					try {
						Date date = (Date) object;
						result = date.toString();
					} catch (ClassCastException e4) {
						result = null;
						System.err.println("WARNING! Value " + object + " cannot be recongnized as a string.");
						e4.printStackTrace();
					}

				}
			}
		}
		return result;
	}

	/** ====================================================================================== */

	public List<Integer> getPow2Indexes(Double initialValue) {

		List<Integer> list = new ArrayList<Integer>();

		Double currentValue = initialValue;

		while (currentValue > 0) {

			Double log = this.log2(currentValue);
			Double floor = Math.floor(log);
			Double base = Math.pow(2, floor);
			list.add(base.intValue());
			currentValue = currentValue - base;
		}

		return list;
	}

	/** ====================================================================================== */

	public Double log2(Double value) {
		Double log = Math.log10(value) / Math.log10(2.);
		return log;
	}

	/** ====================================================================================== */

	public Double calculateAge(Date dateBegin, Date dateEnd) {

		DateTime begin = new DateTime(dateBegin);
		DateTime end = new DateTime(dateEnd);

		if (begin != null && end != null) {

			int months = Months.monthsBetween(begin, end).getMonths();
			Double age = months / 12.0;
			Double roundedAge = Math.round(age * 10d) / 10d;
			return roundedAge;
		}

		return null;

	}

	/** ====================================================================================== */

	public Integer getPeriodInYears(Date dateBegin, Date dateEnd) {

		DateTime begin = new DateTime(dateBegin);
		DateTime end = new DateTime(dateEnd);

		if (begin != null && end != null) {
			return Years.yearsBetween(begin, end).getYears();
		}

		return null;

	}
	
	/** ====================================================================================== */

	public Integer getPeriodInMonths(Date dateBegin, Date dateEnd) {

		DateTime begin = new DateTime(dateBegin);
		DateTime end = new DateTime(dateEnd);

		if (begin != null && end != null) {
			return Months.monthsBetween(begin, end).getMonths();
		}

		return null;

	}
	
	/** ====================================================================================== */

	public Integer getPeriodInDays(Date dateBegin, Date dateEnd) {

		DateTime begin = new DateTime(dateBegin);
		DateTime end = new DateTime(dateEnd);

		if (begin != null && end != null) {
			return Days.daysBetween(begin, end).getDays();
		}

		return null;

	}

	/** ====================================================================================== */

	public Double calculateImc(Double taille, Double poids) {

		if (taille!=null && poids!=null) {
			Double imc = poids / (taille * taille);
			Double roundedImc = Math.round(imc * 10d) / 10d;
			return roundedImc;
		}

		return null;

	}

	/** ====================================================================================== */

	public Date getDerniereDate(Date date1, Date date2) {

		if (date1==null && date2==null) {
			return null;
		}

		if (date1!=null && date2==null) {
			return date1;
		}

		if (date1==null && date2!=null) {
			return date2;
		}

		if (date1.after(date2)) {
			return date1;
		}
		else {
			return date2;
		}

	}

	/** ====================================================================================== */

}
