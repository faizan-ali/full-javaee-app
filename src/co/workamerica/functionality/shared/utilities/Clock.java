package co.workamerica.functionality.shared.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Clock {

	public static String getCurrentTime() {
		Date time = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a zzz");
		timeFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return timeFormat.format(time);
	}

	public static String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
		dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return dateFormat.format(date);
	}

	public static String getCurrentDateDashes () {
		return getCurrentDate().replaceAll("/", "-");
	}

	public static Date getCurrentDateAsDate()  {
		return new Date();
	}

	public static String unixTimestampToDate (long timestamp) {
		Date date = new Date(timestamp * 1000L);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
		dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return dateFormat.format(date);
	}

	public static String unixTimestampToTime (long timestamp) {
		Date date = new Date(timestamp * 1000L);
		Date time = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a zzz");
		timeFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return timeFormat.format(time);
	}
}
