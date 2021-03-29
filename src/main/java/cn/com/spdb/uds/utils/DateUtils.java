package cn.com.spdb.uds.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.CronExpression;

import com.baidu.jprotobuf.pbrpc.utils.StringUtils;

public class DateUtils {
	// ------------------时间单位-----------------
	public static final int TIME_MILLSECOND_OF_SECOND = 1000;
	public static final int TIME_MILLSECOND_OF_MINUTE = 60 * TIME_MILLSECOND_OF_SECOND;
	public static final int TIME_MILLSECOND_OF_HORE = 60 * TIME_MILLSECOND_OF_MINUTE;
	public static final int TIME_MILLSECOND_OF_DAY = 24 * TIME_MILLSECOND_OF_HORE;

	// ------------------时间格式-------------------
	public static final String PATTERN_HHMMSS = "HH:mm:ss";
	public static final String PATTERN_HHMMSS_CONS = "HHmmss";
	public static final String PATTERN_YYYYMMDD = "yyyy-MM-dd";
	public static final String PATTERN_YYYYMMDD_CONS = "yyyyMMdd";
	public static final String PATTERN_NORMAL = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_NORMAL_CONS = "yyyyMMddHHmmss";

	private static Calendar CALENDAR = Calendar.getInstance();

	/**
	 * 获取当前字符串时间
	 * 
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String getDateTime(Date date, String pattern) {
		if (!StringUtils.isBlank(pattern)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(date);
		}
		return null;
	}

	/**
	 * 获取当前字符串时间
	 * 
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String getDateTime(String pattern) {
		if (!StringUtils.isBlank(pattern)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(new Date());
		}
		return null;
	}

	/**
	 * 根据字符时间获取当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static Date parserDate(String dateStr, String pattern) {
		if (!StringUtils.isBlank(pattern) && !StringUtils.isBlank(dateStr)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			try {
				return dateFormat.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 增加天数
	 */
	public static Date add(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 获取0点时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getZeroTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static boolean isSameDay(Date date0, Date date1) {
		return isSameDay(getCalendar(date0), getCalendar(date1));
	}

	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 是否同一天
	 * 
	 * @param calendar0
	 * @param calendar1
	 * @return
	 */
	public static boolean isSameDay(Calendar calendar0, Calendar calendar1) {
		if (calendar0 == null || calendar1 == null) {
			return false;
		}
		int era0 = calendar0.get(Calendar.ERA);
		int era1 = calendar1.get(Calendar.ERA);
		int year0 = calendar0.get(Calendar.YEAR);
		int year1 = calendar1.get(Calendar.YEAR);
		int date0 = calendar0.get(Calendar.DAY_OF_YEAR);
		int date1 = calendar1.get(Calendar.DAY_OF_YEAR);
		return era0 == era1 && year0 == year1 && date0 == date1;
	}

	/**
	 * Calendar对应时间获取
	 * 
	 * @param field
	 * @return
	 */
	public static int get(int field) {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(field);

	}

	/**
	 * second minute hour day month week year
	 * 
	 * @param cron
	 * @return
	 * @throws ParseException
	 */
	public static Date getNextValidTimeAfter(String cron) throws ParseException {
		return getNextValidTimeAfter(cron, new Date());
	}

	/**
	 * second minute hour day month week year
	 * 
	 * @param cron
	 * @return
	 * @throws ParseException
	 */
	public static Date getNextValidTimeAfter(String cron, Date date) throws ParseException {
		cron = cron.trim();
		String[] tmps = cron.split("\\ ");
		StringBuffer cronBuffer = new StringBuffer();
		String tmpCron = cron;
		// 包含1到7
		if (tmps[5].matches(".*[1-7]+.*")) {
			char[] chs = tmps[5].toCharArray();
			for (int j = 0; j < chs.length; j++) {
				if (chs[j] >= '0' && chs[j] <= '9') {
					if (chs[j] == 55) {
						chs[j] = 49;
					} else {
						chs[j] += 1;
					}
				}
			}
			tmps[5] = new String(chs);

			cronBuffer.setLength(0);
			for (int i = 0; i < tmps.length; i++) {
				cronBuffer.append(tmps[i]);
				cronBuffer.append(" ");
			}
			tmpCron = cronBuffer.toString().trim();
		}

		if (tmps[3].contains(",L")) {
			cronBuffer.setLength(0);
			for (int i = 0; i < tmps.length; i++) {
				if (i != 3) {
					cronBuffer.append(tmps[i]);
				} else {
					cronBuffer.append("L");
				}
				cronBuffer.append(" ");
			}
			tmpCron = cronBuffer.toString().trim();
			CronExpression tmpCronExpression1 = new CronExpression(tmpCron);
			Date tmpDate1 = tmpCronExpression1.getNextValidTimeAfter(date);

			cronBuffer.setLength(0);
			for (int i = 0; i < tmps.length; i++) {
				if (i != 3) {
					cronBuffer.append(tmps[i]);
				} else {
					cronBuffer.append(tmps[i].replace(",L", ""));
				}
				cronBuffer.append(" ");
			}
			tmpCron = cronBuffer.toString().trim();
			CronExpression tmpCronExpression2 = new CronExpression(tmpCron);
			Date tmpDate2 = tmpCronExpression2.getNextValidTimeAfter(date);
			return tmpDate1.compareTo(tmpDate2) > 0 ? tmpDate2 : tmpDate1;
		}
		CronExpression tmpCronExpression2 = new CronExpression(tmpCron);
		Date tmpDate = tmpCronExpression2.getNextValidTimeAfter(date);
		return tmpDate;
	}

	public static boolean isDate(String strDate, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(strDate);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) throws ParseException {
		String cron = "0 0 0 3,6,9,12,15,18,21,24,27,30,L * ? *";
		int i = 20;
		Date date = new Date();
		while (i-- > 0) {
			try {
				date = DateUtils.getNextValidTimeAfter(cron, date);
				System.out.println(new Timestamp(date.getTime()).toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
