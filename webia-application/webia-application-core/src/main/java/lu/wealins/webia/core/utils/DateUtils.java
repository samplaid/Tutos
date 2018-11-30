package lu.wealins.webia.core.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.util.Assert;

public class DateUtils {

	// Compare two dates without taking the time in consideration.
	public static int compareDayDates(Date firstDate, Date secondDate) {
		Assert.notNull(firstDate, "The first date to compare can't be null");
		Assert.notNull(secondDate, "The second date to compare can't be null");

		DateTime firstDateTime = new DateTime(firstDate.getTime());
		DateTime secondDateTime = new DateTime(secondDate.getTime());

		LocalDate firstLocalDate = firstDateTime.toLocalDate();
		LocalDate secondLocalDate = secondDateTime.toLocalDate();

		return firstLocalDate.compareTo(secondLocalDate);
	}
}
