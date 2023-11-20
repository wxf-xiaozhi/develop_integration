package com.ifyou.skypivot.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 获取某个时间的当月有多少天
	 * @param date
	 * @return
	 */
	public static Integer getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前时间为当月第多少天
	 * @return
	 */
	public static Integer getDaysOfCurMonth() {
		return getDaysOfTheMonth(new Date());
	}
	
	/**
	 * 获取某个时间为当月第多少天
	 * @param date
	 * @return
	 */
	public static Integer getDaysOfTheMonth(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取某个时间MM_DD字符串
	 * @param date
	 * @return
	 */
	public static String getDateMM_DDString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

		return sdf.format(date);
	}
	public static String getName(String subareaName,String fileName,String startDate,String endDate){
//		，如矿区001-单车接管数据明细-2023.01.05-2023.01.15
		return subareaName+ "-" + fileName + "-" + startDate +"-" +endDate;
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
		Date now = new Date();

		System.out.println(getDateMM_DDString(now) + "公有" + getDaysOfMonth(now) + "天");

		now = sdf.parse("2015_02_2");
		System.out.println(sdf.format(now) + "公有" + getDaysOfMonth(now) + "天");

		Calendar calendar = Calendar.getInstance();
        //时间，可以为具体的某一时间
        Date nowDate = new Date();
        calendar.setTime(nowDate);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
        int yearDay = calendar.get(Calendar.DAY_OF_YEAR);
        weekDay = (weekDay == 1 ? 7 : weekDay - 1);
        System.out.println("当前时间是，本周的第" + weekDay  + "天");
        System.out.println("当前时间是，本月的第" + monthDay + "天");
        System.out.println("当前时间是，本年的第" + yearDay  + "天");
	}
}
