package com.nandi.coffeeapp.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nandi_000 on 10-11-2015.
 */
public class Constants {
    public static final String API_KEY = "WuVbkuUsCXHPx3hsQzus4SE";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String checkUpdateStatus(String updateDate) {
        updateDate = updateDate.substring(0, updateDate.length()-4);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        long updateTime = 0;
        String elapsed = null;
        try {
            updateTime = simpleDateFormat.parse(updateDate).getTime();
            Date currentDate = new Date();
            long diffInSeconds = (currentDate.getTime() - updateTime) / 1000;
            long seconds = diffInSeconds;
            long mins = diffInSeconds / 60;
            long hours = diffInSeconds / 3600;
            long days = diffInSeconds / 86400;
            long weeks = diffInSeconds / 604800;
            long months = diffInSeconds / 2592000;

            if (seconds < 120) {
                elapsed = "Updated a min ago";
            } else if (mins < 60) {
                elapsed = "Updated " + mins + " mins ago";
            } else if (hours < 24) {
                elapsed = "Updated " + hours + " "+ (hours > 1 ? "hrs" : "hr")+ " ago";
            } else if (hours < 48) {
                elapsed = "Updated " + "a day ago";
            } else if (days < 7) {
                elapsed = "Updated " + days + " days ago";
            } else if (weeks < 5) {
                elapsed = "Updated " + weeks + " " + (weeks > 1 ? "weeks" : "week") + " ago";
            } else if (months < 12) {
                elapsed = "Updated " + months + " " + (months > 1 ? "months" : "months")+ " ago";
            } else {
                elapsed = "Updated more than a year ago";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return elapsed;
    }
}
