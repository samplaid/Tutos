/**
 * 
 */
package lu.wealins.liability.services.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lu.wealins.liability.services.core.persistence.repository.HolidayRepository;
/**
 * @author xqv66
 *
 */
@Component
public class CalendarUtils {

	public static final String LISSIA_NULL_DATE = "1753-01-01";
	private final Logger logger = LoggerFactory.getLogger(CalendarUtils.class);

	private static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	private static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	private static final String YYYY_MM_DD_DOT = "yyyy.MM.dd";
	private static final String YYYY_MM_DD_SLASH = "yyyy/MM/dd";
	private static final String DD_MM_YYYY_SLASH = "dd/MM/yyyy";
	private static final String YYYYMMDD = "yyyyMMdd";
	private static final String MM_DD_YYYY_DOT = "MM.dd.yyyy";
	private static final String DD_MM_YYYY_DOT = "dd.MM.yyyy";

	private static final List<String> TIME_FORMATS = Arrays.asList(YYYY_MM_DD_DOT, YYYY_MM_DD, YYYY_MM_DD_SLASH, YYYY_MM_DD_HH_MM_SS_SSS, YYYY_MM_DD_HH_MM_SS, YYYY_MM_DD_HH_MM, YYYYMMDD,
			MM_DD_YYYY_DOT, DD_MM_YYYY_SLASH, DD_MM_YYYY_DOT);
	private static final String DATE_CANNOT_BE_NULL = "Date cannot be null.";

	private static final Set<String> QUARTER_DAYS = new HashSet<>();

	@Autowired
	private HolidayRepository holidayRepository;

	static {
		QUARTER_DAYS.add("3103");
		QUARTER_DAYS.add("3006");
		QUARTER_DAYS.add("3009");
		QUARTER_DAYS.add("3112");

	}

	/**
	 * This method will reset the time in the date to 00:00:000
	 * 
	 * @param date the date form which the time will be reseted.
	 * @return The date with reseted time
	 * 
	 * @throws IllegalArgumentException if the date is null.
	 */
	public static Date resetTime(Date date) {

		if (CalendarUtils.nonNull(date)) {
			Calendar instance = Calendar.getInstance();
			instance.setTime(date);
			instance.set(Calendar.HOUR, 0);
			instance.set(Calendar.MINUTE, 0);
			instance.set(Calendar.SECOND, 0);
			instance.set(Calendar.MILLISECOND, 0);
			return instance.getTime();
		}

		throw new IllegalArgumentException("Cannot reset the time of a null date.");
	}


	/**
	 * The date is considered as null if it is equal to null or it is equal to the {@value #LISSIA_NULL_DATE} as specified by lissia.
	 * 
	 * @param date the date to check
	 * @return true if the condition is met.
	 */
	public static boolean isNull(Date date) {
		return date == null || CalendarUtils.LISSIA_NULL_DATE.equals(new SimpleDateFormat(CalendarUtils.YYYY_MM_DD).format(date));
	}

	/**
	 * The date is considered as not null if it is not null and it is not equal to the {@value #LISSIA_NULL_DATE} as specified by lissia.
	 * 
	 * @param date the date to check
	 * @return true if the condition is met.
	 */
	public static boolean nonNull(Date date) {
		return date != null && !CalendarUtils.LISSIA_NULL_DATE.equals(new SimpleDateFormat(CalendarUtils.YYYY_MM_DD).format(date));
	}

	public boolean isAfter(Date date1, Date date2) {
		LocalDateTime lDateTime1 = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
		LocalDateTime lDateTime2 = LocalDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault());

		return lDateTime1.isAfter(lDateTime2);
	}

	public Date getFirstDayOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		
		return toDate(toLocalDate(date).withDayOfMonth(1));
	}

	public Date getFirstDayOfNextMonth(Date date) {
		if (date == null) {
			return null;
		}

		return toDate(toLocalDate(date).plusMonths(1).withDayOfMonth(1));
	}

	private Date toDate(LocalDate date) {
		if (date == null) {
			return null;
		}

		return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	private LocalDate toLocalDate(Date date) {
		if (date == null) {
			return null;
		}

		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public Date getFirstDayOfQuarter(Date date) {
		if (date == null) {
			return null;
		}

		LocalDate inputDate = toLocalDate(date);

		return getFirstDayOfQuarter(inputDate);
	}

	private Date getFirstDayOfQuarter(LocalDate inputDate) {
		if (inputDate == null) {
			return null;
		}
		return toDate(inputDate.withMonth(inputDate.get(IsoFields.QUARTER_OF_YEAR) * 3 - 2).with(TemporalAdjusters.firstDayOfMonth()));
	}

	public Date getFirstDayOfNextQuarter(Date date) {
		if (date == null) {
			return null;
		}

		LocalDate inputDate = toLocalDate(date).plusMonths(3);

		return getFirstDayOfQuarter(inputDate);
	}

	public boolean isAfterOrEquals(Date date1, Date date2) {
		LocalDateTime lDateTime1 = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
		LocalDateTime lDateTime2 = LocalDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault());

		return lDateTime1.equals(lDateTime2) || lDateTime1.isAfter(lDateTime2);
	}

	public boolean isBefore(Date date1, Date date2) {
		LocalDateTime lDateTime1 = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
		LocalDateTime lDateTime2 = LocalDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault());

		return lDateTime1.isBefore(lDateTime2);
	}

	/**
	 * Returns the day of week of the given <code>date</code>.
	 * 
	 * @param date the date to work on
	 * @return the day of week
	 */
	public int getDayOfWeek(Date date) {

		if (date == null) {
			throw new IllegalArgumentException(DATE_CANNOT_BE_NULL);
		}

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(GregorianCalendar.DAY_OF_WEEK);
	}

	public static boolean isInFuture(Date date) {
		if (date == null) {
			return false;
		}

		LocalDateTime lDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return lDate.isAfter(LocalDateTime.now());
	}

	public int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public boolean isAnHoliday(Date date) {
		return holidayRepository.findOne(date) != null;
	}

	public Date createDate(int year, int month, int day) {
		Assert.isTrue(day >= 1 && day <= 31, "Day must be between 1 and 31.");
		Assert.isTrue(month >= 1 && month <= 12, "Month must be between 1 and 12.");
		Calendar calendar = GregorianCalendar.getInstance();

		calendar.set(year, month - 1, day);

		return calendar.getTime();
	}

	public Date createDefaultDate() {
		return createDate(LISSIA_NULL_DATE);
	}

	public Date createDate(Date date, int hourOfDay, int minute, int second) {
		try {
			return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(new SimpleDateFormat(CalendarUtils.YYYY_MM_DD).format(date) + " " + hourOfDay + ":" + minute + ":" + second);
		} catch (ParseException e) {
			logger.error("Cannot create date", e);
			throw new IllegalStateException("Cannot create date", e);
		}
	}


	public Date createTime(int hourOfDay, int minute, int second) {
		try {
			return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(LISSIA_NULL_DATE + " " + hourOfDay + ":" + minute + ":" + second);
		} catch (ParseException e) {
			logger.error("Cannot create date", e);
			throw new IllegalStateException("Cannot create date", e);
		}
	}


	public Date addDays(Date date, int numberOfDays) {
		Calendar calendar = GregorianCalendar.getInstance();

		calendar.setTime(date);
		calendar.add(Calendar.DATE, numberOfDays);

		return calendar.getTime();
	}

	public boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(date1).equals(fmt.format(date2));
	}

	public boolean isSameDayOrAfter(Date date1, Date date2) {
		return isSameDay(date1, date2) || date1.after(date2);
	}

	public boolean isEndOfQuarter(Date date1) {
		if (date1 == null) {
			return false;
		}
		SimpleDateFormat fmt = new SimpleDateFormat("ddMM");
		String day = fmt.format(date1);
		return QUARTER_DAYS.contains(day);
	}

	public Date getCurrentDate() {
		return new Date();
	}

	public boolean isBeforeOrEqualsNow(Date endDate) {

		if (isNull(endDate)) {
			return false;
		}

		LocalDateTime lDate = LocalDateTime.ofInstant(new Date(endDate.getTime()).toInstant(), ZoneId.systemDefault());

		return lDate.isBefore(LocalDateTime.now());
	}

	public Date createDate(String date) {

		for (String timeFormat : TIME_FORMATS) {
			try {
				if (isValidFormat(timeFormat, date))
					return new SimpleDateFormat(timeFormat).parse(date);
			} catch (ParseException e) {
				// no exception to catch
				// logger.error("Cannot convert " + date + ".", e);
			}
		}

		throw new IllegalArgumentException("Cannot convert " + date + " to the date format.");
	}

	public boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException e) {
			// logger.error("Cannot convert " + date + ".", e);
		}
		return date != null;
	}

}
