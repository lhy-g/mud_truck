package com.tc.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

 

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	public static int Guid=100;
	
	public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyMMdd"); 
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static List<String> DATE_LIST = null;
	
	 
	
    public static Date now(){
        return new Date();
    }
    
    /**
     * 
     * @return
     */
    public static String getTimeStamp() {
    	return String.valueOf(System.currentTimeMillis()/1000);
    }
   
    /**
     * 生成16位ID
     * [6位年月日+7位时间戳+3位随机数]
	 *  
	 */
	public static String getGuid() {
		DateUtils.Guid+=1;
		long now = System.currentTimeMillis();  
		String time=dateFormat.format(now);
		String info=String.valueOf(now);
		//获取三位随机数  
		//int ran=(int) ((Math.random()*9+1)*100); 
		//要是一段时间内的数据连过大会有重复的情况，所以做以下修改
		int ran=0;
		if(DateUtils.Guid>999){
			DateUtils.Guid=100;    	
		}
		ran=DateUtils.Guid;
		return time+info.substring(6, info.length())+ran;  
	}
	
	
	
	/** 
	 * 获取最近7天日期集合 
	 * @return
	 */
	public static List<String> getDateList(){
		if (null == DATE_LIST) {
			DATE_LIST = getDate();
		}
		if (!DATE_LIST.get(0).equals(getDateString(0,null))) {
			DATE_LIST = getDate();
		}
		return DATE_LIST;
	}
	
	/**
	 * 获取指定日期最近10天集合
	 * @param dateStr
	 * @return
	 */
	public static List<String> getDateListByDateStr(String dateStr){
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add(getDateString(i,dateStr));
		}
		return list;
	}
	
	/**
	 * 获取最近12月份集合
	 * @return
	 */
	public static List<String> getMonthsList() {
		LocalDate today = LocalDate.now();
		List<String> list = new ArrayList<String>();
		for (long i = 11L; i >=0; i--) {
			LocalDate localDate = today.minusMonths(i);
			String ss = localDate.toString().substring(0, 7);
			list.add(ss);

		}
		return list;
	}
	
	public static List<String> getDate(){
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			list.add(getDateString(i,null));
		}
		return list;
	}
	
	public static String getDateString(int i,String dateStr) {
		Date date = null;
		if(StringUtils.isEmpty(dateStr)) {
			 date=new Date();
		}else {
			try {
				date = DateUtils.parseDate(dateStr, DateUtils.DATE_FORMAT);
			} catch (ParseException e) {
				 
				 
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, i-6);
		date = calendar.getTime();
		String dateString = DateFormatUtils.format(date, DATE_FORMAT);
		return dateString;
	}
	
	public static int getWeekString() {
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE,0);
		date = calendar.getTime();
		int weekOfYear = calendar.get(Calendar.MONTH);
		String dateString = DateFormatUtils.format(date, DATE_FORMAT);
		//String dateString = format.format(date);
		System.out.println(dateString);
		return weekOfYear;
	}
	
	
		
	public static void main(String[] args) throws ParseException {
		Date date = DateUtils.parseDate("2019-09-02", "yyyy-MM-dd");
		System.out.println(date);
		System.out.println(getDateListByDateStr("2020-08-07"));
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
//        Date date = simpleDateFormat.parse("2019-09-02");
//        Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.add(calendar.DATE, 0-6);
//		date = calendar.getTime();
//		String dateString = DateFormatUtils.format(date, DATE_FORMAT);
		//String dateString = format.format(date);
		//System.out.println(dateString);
        
		
		//	System.out.println(getMonthsList());
	}
		 
		
		
	 
	 
	
	
	
	public static String getDate(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}
}
