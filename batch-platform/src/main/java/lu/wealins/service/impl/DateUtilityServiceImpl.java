package lu.wealins.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import lu.wealins.service.DateUtilityService;

@Service
public class DateUtilityServiceImpl implements DateUtilityService {

	private static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	private static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	private static final String YYYY_MM_DD = "yyyy-MM-dd";
	private static final String YYYY_MM_DD_DOT = "yyyy.MM.dd";
	private static final String YYYY_MM_DD_SLASH = "yyyy/MM/dd";

	private static final List<String> TIME_FORMATS = Arrays.asList(YYYY_MM_DD_DOT, YYYY_MM_DD, YYYY_MM_DD_SLASH, YYYY_MM_DD_HH_MM_SS_SSS, YYYY_MM_DD_HH_MM_SS, YYYY_MM_DD_HH_MM);

	/*
	 * (non-Javadoc)
	 * 
	 * @see lu.wealins.service.DateUtilityService#createDate(java.lang.String)
	 */
	@Override
	public Date createDate(String date) {

		for (String timeFormat : TIME_FORMATS) {
			try {
				return new SimpleDateFormat(timeFormat).parse(date);
			} catch (ParseException e) {
				// no exception to catch
			}
		}

		throw new IllegalArgumentException("Cannot convert " + date + " to the date format.");
	}

}
