package com.tfswx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author
 * 
 * @since
 * 
 */
public class DateUtils {

	/**
	 * 默认日期格式
	 */
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

	public static String getNowTime() {
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
		String sDate = f.format(date);
		return sDate;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date 日期对象
	 * @return String 日期字符串
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
		String sDate = f.format(date);
		return sDate;
	}

	/**
	 * 格式化日期
	 *
	 * @param date 日期对象
	 * @return String 日期字符串
	 */
	public static Date formatDate(String date) {
		SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
		Date sDate = null ;
		try {
			sDate = f.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sDate;
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @return 第一天
	 */
	public static String getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year 年份
	 *
	 * @return 第一天时间
	 */
	public static String getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return formatDate(currYearFirst);
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year 年份
	 *
	 * @return 最后一天日期
	 */
	public static String getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return formatDate(currYearLast);
	}

	/**
	 * 获取当前时间(格式:2017-01-01)
	 * 
	 * @return 当前时间
	 */
	public static String getNowDate() {

		Calendar calendar = Calendar.getInstance();
		calendar.get(Calendar.YEAR);
		calendar.get(Calendar.MONTH + 1);
		calendar.get(Calendar.DAY_OF_MONTH);

		Date now = calendar.getTime();

		return formatDate(now);
	}
	
	/**
	 * 获取一年前的时间(格式:2017-01-01)
	 * 
	 * @return 一年前的时间
	 */
	public static String getNowJianOneYear() {
		Date dt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.YEAR, -1);
		calendar.get(Calendar.YEAR);
		calendar.get(Calendar.MONTH + 1);
		calendar.get(Calendar.DAY_OF_MONTH);

		Date now = calendar.getTime();

		return formatDate(now);
	}

    /**
     * 时间跨度
	 * @param oldDate
     * @return
     */
	public static String periodTime(String oldDate) {
		//String转Date
		Date od = formatDate(oldDate);

		//Date转LocalDate
		Instant instant = od.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		LocalDate localDate = localDateTime.toLocalDate();

		LocalDate today = LocalDate.now();

		Period p = Period.between(localDate, today);
		if (p.getYears() > 0) {
			return p.getYears() + "年前";
		} else if (p.getYears() == 0 && p.getMonths() > 0) {
			return p.getMonths() + "个月前";
		} else if (p.getMonths() == 0 && p.getDays() > 0) {
			return p.getDays() + "天前";
		} else {
			return "今天";
		}
	}

	/**
	 * 时间比较
	 * @param d1 开始时间
	 * @param d2 结束时间
	 * @return 是否相等
	 */
	public static boolean sameDate(String d1, String d2) {
		Date date1 = DateUtils.formatDate(d1);
		Date date2 = DateUtils.formatDate(d2);
		LocalDate localDate1 = ZonedDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = ZonedDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault()).toLocalDate();
		return localDate1.isEqual(localDate2);
	}

	/**
	 * 季度开始时间和结束时间
	 * @param year 年份
	 * @param season 第几个季度
	 * @param lastyear 上一年度-用于同比
	 * @param lastseason 上个季度-用于环比
	 * @return
	 */
	public static String[] seasonTime(int year,int season,boolean lastyear, boolean lastseason){
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);

		String[] s = new String[2];
		String str = "";
		Calendar quarterCalendar = null;
		if(lastseason){//是否查询上个季度
			if(season==1){
				season = 4;
				lastyear = true;
			}else{
				season -= 1;
			}
		}
		switch (season) {
			case 1: // 一季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.YEAR,year);
				if(lastyear){
					quarterCalendar.set(Calendar.YEAR,year-1);
				}
				quarterCalendar.set(Calendar.MONTH, 3);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = format.format(quarterCalendar.getTime());
				s[0] = str.substring(0, str.length() - 5) + "01-01";
				s[1] = str;
				break;
			case 2: // 二季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.YEAR,year);
				if(lastyear){
					quarterCalendar.set(Calendar.YEAR,year-1);
				}
				quarterCalendar.set(Calendar.MONTH, 6);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = format.format(quarterCalendar.getTime());
				s[0] = str.substring(0, str.length() - 5) + "04-01";
				s[1] = str;
				break;
			case 3://三季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.YEAR,year);
				if(lastyear){
					quarterCalendar.set(Calendar.YEAR,year-1);
				}
				quarterCalendar.set(Calendar.MONTH, 9);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = format.format(quarterCalendar.getTime());
				s[0] = str.substring(0, str.length() - 5) + "07-01";
				s[1] = str;
				break;
			case 4:// 四季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.YEAR,year);
				if(lastyear){
					quarterCalendar.set(Calendar.YEAR,year-1);
				}
				str = format.format(quarterCalendar.getTime());
				s[0] = str.substring(0, str.length() - 5) + "10-01";
				s[1] = str.substring(0, str.length() - 5) + "12-31";
				break;
		}
		return s;
	}

}
