package com.tfswx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * ʱ�乤����
 * 
 * @author
 * 
 * @since
 * 
 */
public class DateUtils {

	/**
	 * Ĭ�����ڸ�ʽ
	 */
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd";

	public static String getNowTime() {
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
		String sDate = f.format(date);
		return sDate;
	}

	/**
	 * ��ʽ������
	 * 
	 * @param date ���ڶ���
	 * @return String �����ַ���
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
		String sDate = f.format(date);
		return sDate;
	}

	/**
	 * ��ʽ������
	 *
	 * @param date ���ڶ���
	 * @return String �����ַ���
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
	 * ��ȡ����ĵ�һ��
	 * 
	 * @return ��һ��
	 */
	public static String getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * ��ȡĳ���һ������
	 * 
	 * @param year ���
	 *
	 * @return ��һ��ʱ��
	 */
	public static String getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return formatDate(currYearFirst);
	}

	/**
	 * ��ȡĳ�����һ������
	 * 
	 * @param year ���
	 *
	 * @return ���һ������
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
	 * ��ȡ��ǰʱ��(��ʽ:2017-01-01)
	 * 
	 * @return ��ǰʱ��
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
	 * ��ȡһ��ǰ��ʱ��(��ʽ:2017-01-01)
	 * 
	 * @return һ��ǰ��ʱ��
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
     * ʱ����
	 * @param oldDate
     * @return
     */
	public static String periodTime(String oldDate) {
		//StringתDate
		Date od = formatDate(oldDate);

		//DateתLocalDate
		Instant instant = od.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		LocalDate localDate = localDateTime.toLocalDate();

		LocalDate today = LocalDate.now();

		Period p = Period.between(localDate, today);
		if (p.getYears() > 0) {
			return p.getYears() + "��ǰ";
		} else if (p.getYears() == 0 && p.getMonths() > 0) {
			return p.getMonths() + "����ǰ";
		} else if (p.getMonths() == 0 && p.getDays() > 0) {
			return p.getDays() + "��ǰ";
		} else {
			return "����";
		}
	}

	/**
	 * ʱ��Ƚ�
	 * @param d1 ��ʼʱ��
	 * @param d2 ����ʱ��
	 * @return �Ƿ����
	 */
	public static boolean sameDate(String d1, String d2) {
		Date date1 = DateUtils.formatDate(d1);
		Date date2 = DateUtils.formatDate(d2);
		LocalDate localDate1 = ZonedDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = ZonedDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault()).toLocalDate();
		return localDate1.isEqual(localDate2);
	}

	/**
	 * ���ȿ�ʼʱ��ͽ���ʱ��
	 * @param year ���
	 * @param season �ڼ�������
	 * @param lastyear ��һ���-����ͬ��
	 * @param lastseason �ϸ�����-���ڻ���
	 * @return
	 */
	public static String[] seasonTime(int year,int season,boolean lastyear, boolean lastseason){
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);

		String[] s = new String[2];
		String str = "";
		Calendar quarterCalendar = null;
		if(lastseason){//�Ƿ��ѯ�ϸ�����
			if(season==1){
				season = 4;
				lastyear = true;
			}else{
				season -= 1;
			}
		}
		switch (season) {
			case 1: // һ����
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
			case 2: // ������
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
			case 3://������
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
			case 4:// �ļ���
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
