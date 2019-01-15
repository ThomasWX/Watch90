package com.example.common;

import android.text.format.Time;
/** Utility methods for processing dates. */
public class DateUtils {

    /**
     * Determine the difference, in days between two dates. Uses similar logic as the {@link
     * android.text.format.DateUtils.getRelativeTimeSpanString} method.
     *
     * @param time Instance of time object to use for calculations.
     * @param date1 First date to check.
     * @param date2 Second date to check.
     * @return The absolute difference in days between the two dates.
     */
    public static int getDayDifference(Time time, long date1, long date2) {
        time.set(date1);
        int startDay = Time.getJulianDay(date1, time.gmtoff);

        time.set(date2);
        int currentDay = Time.getJulianDay(date2, time.gmtoff);

        return Math.abs(currentDay - startDay);
    }
}
