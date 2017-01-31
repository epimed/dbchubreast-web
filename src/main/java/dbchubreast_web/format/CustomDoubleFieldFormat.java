package dbchubreast_web.format;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

public class CustomDoubleFieldFormat extends NumberFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final DecimalFormat FORMATTER = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
	
	@Override
	public Number parse(String source, ParsePosition parsePosition) {
		if (source != null) {
			source = source.replace(',', '.');
		}
		return FORMATTER.parse(source, parsePosition);
	}

	@Override
	public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
		return FORMATTER.format(number, toAppendTo, pos);
	}

	@Override
	public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
		return FORMATTER.format(number, toAppendTo, pos);
	}

}
