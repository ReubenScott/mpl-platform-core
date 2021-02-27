package com.soak.common.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarUtil {
	/**
	 * 获取虚拟的第一天
	 */
	static public Date getVirtualStartOfFirstDay() {
		Calendar calendar = new GregorianCalendar(1900, 11, 30);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	/**
	 * 获取今天的结束
	 */
	static public Date getEndOfToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	};

	/**
	 * 获取一天的结束
	 * 
	 * @param date
	 * @return 23:59:59.999
	 */
	static public Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	};

	/**
	 * 获取一天的开始
	 * 
	 * @param date
	 * @return 00:00:00.000
	 */
	static public Date getBeginOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 * @return
	 */
	static public Date getMondayOfWeekWithMonDayIsFirstDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 * @return
	 */
	static public Date getSundayOfWeekWithMonDayIsFirstDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	static public boolean isWeekend(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return (day == Calendar.SATURDAY || day == Calendar.SUNDAY);
	}

	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 * @return
	 */
	static public Date getMondayOfWeekWithMonDayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 * @return
	 */
	static public Date getSundayOfWeekWithMonDayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	static public int getWeekOfYearWithMondayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	};

	static public int getYearWithMondayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.YEAR);
	};

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 * @return 第一天 00:00:00.000
	 */
	static public Date getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	static public Date getMonDayOfFirstWeekInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 0);
		return calendar.getTime();
	};

	static public Date getSunDayOfLastWeekInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	};

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 * @return 最后一天 23:59:59.999
	 */
	static public Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	};

	/**
	 * 获取一天的开始
	 * 
	 * @param date
	 * @return 00:00:00.000
	 */
	static public Date getMiddleOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	static public int getDayOfYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	static public int getYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * @param date
	 * @return 周一是 1
	 */
	static public int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (i == 0) {
			return 7;
		} else {
			return i;
		}
	};

	/**
	 * @param date
	 */
	static public int getDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	};

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 * @return
	 */
	static public int getDayCountOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	};

	static public int getDayCountOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	};

	static public List<Date> getNormalDaysWithMonDayIsFirstDay(int year,
			int week) {
		List<Date> days = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		for (int d = 2; d < 7; d++) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.DAY_OF_WEEK, d);
			days.add(calendar.getTime());
		}
		return days;
	}

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 * 
	 * @return
	 */
	static public Date getDay(int year, int month, int Day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Day);
		return calendar.getTime();
	};

	static public List<Date> getWeekendDaysWithMonDayIsFirstDay(int year,
			int week) {
		List<Date> days = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		days.add(calendar.getTime());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		days.add(calendar.getTime());
		return days;
	}


	static public Date getTime(int hour, int min, int sec) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, sec);
		return calendar.getTime();
	}

	static public Date getDateTime(Date date, Date time) {
		Calendar calendarD = Calendar.getInstance();
		calendarD.setTime(date);
		Calendar calendarT = Calendar.getInstance();
		calendarT.setTime(time);
		calendarD
				.set(Calendar.HOUR_OF_DAY, calendarT.get(Calendar.HOUR_OF_DAY));
		calendarD.set(Calendar.MINUTE, calendarT.get(Calendar.MINUTE));
		calendarD.set(Calendar.SECOND, calendarT.get(Calendar.SECOND));

		return calendarD.getTime();
	}

	static public int getDaysBetweenDates(Date date1, Date date2) {
		long d = date2.getTime() - date1.getTime();
		return Long.valueOf(d / (24 * 60 * 60 * 1000)).intValue();
	}

	static public Date getDayAfterDate(Date date, int days) {
		Calendar calendarD = Calendar.getInstance();
		calendarD.setTime(date);
		calendarD.set(Calendar.DATE, calendarD.get(Calendar.DATE) + days);
		return calendarD.getTime();

	}

	static public Boolean isDayBetweenDates(Date date, Date sd, Date md) {
		return date.before(md) && date.after(sd);
	}

	public static void main(String[] args) {
		// Calendar calendar=Calendar.getInstance();
		// calendar.setFirstDayOfWeek(Calendar.MONDAY);
		// calendar.set(Calendar.YEAR, 2009);
		// calendar.set(Calendar.WEEK_OF_YEAR, 49);
		// calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		// SimpleDateFormat df=new SimpleDateFormat("EEE d/MMM");
		// System.out.println(df.format(calendar.getTime()));
		// week= Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		// year= Calendar.getInstance().get(Calendar.YEAR);
		// /Calendar calendar = new GregorianCalendar(2010,6, 23);
		// Calendar calendar1 = new GregorianCalendar(2010,0, 7);
		// System.out.println(calendar.getTime());
		System.out.println(CalendarUtil.getMonDayOfFirstWeekInMonth(2010, 7));
		System.out.println(CalendarUtil.getSunDayOfLastWeekInMonth(2010, 7));
		// System.out.println(calendar.getTime());
		// System.out.print(CalendarUtil.getWeekOfYearWithMonDayIsFirstDay(calendar.getTime()));
		// System.out.print(CalendarUtil.getFirstDayOfMonth(2009, 11));

		// System.out.println(CalendarUtil
		// .getSundayOfWeekWithMonDayIsFirstDay(calendar.getTime()));
		// SimpleDateFormat timeDf = new SimpleDateFormat("yyyy-MM-dd K:mm a",
		// new Locale("en"));
		// Date date = null;
		// try {
		// date = timeDf.parse("2010-02-10 12:30 AM");
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println(date);

		// calendar.setFirstDayOfWeek(Calendar.MONDAY);
		// // n.setFirstDayOfWeek(Calendar.MONDAY);
		// SimpleDateFormat df = new SimpleDateFormat("E d/MMM yyyy");
		// System.out.println("ddd" + df.format(calendar.getTime()));
		// System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
		// int year = calendar.get(Calendar.YEAR);
		// int week = calendar.get(Calendar.WEEK_OF_YEAR);
		// int day = calendar.get(Calendar.DAY_OF_YEAR);
		// int DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);
		// // int DAY_OF_WEEK_IN_MONTH =n.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		// if (day > 7 && week == 1) {
		// year++;
		// }
		// System.out.println(year);
		// System.out.println(day);
		// System.out.println(DAY_OF_WEEK);
		// // System.out.println(DAY_OF_WEEK_IN_MONTH);
		// // Calendar calendar=Calendar.getInstance();
		//
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.WEEK_OF_YEAR, week);
		// // SimpleDateFormat df=new SimpleDateFormat("EEE d/MMM yyyy");
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 2);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 3);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 4);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 5);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 6);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 7);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 1);
		// System.out.println(df.format(calendar.getTime()));
		// // for (int i=Calendar.MONDAY;i<8;i++){
		// calendar.set(Calendar.DAY_OF_WEEK,i);
		//        	
		// //System.out.println(df.format(calendar.getTime()));
		// }
	}

}
