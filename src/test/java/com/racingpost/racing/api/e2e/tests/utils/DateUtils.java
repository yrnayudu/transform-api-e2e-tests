package com.racingpost.racing.api.e2e.tests.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static String dateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String todayDateToString(String format){
        return dateToString(Calendar.getInstance().getTime(), format);
    }

    public static Date stringToDate(String dateString)  {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {
        System.out.println(stringToDate("2020-01-22T15:33:00+00:00"));
        System.out.println(stringToDate("2020-01-22T15:33:00Z"));
    }


}
