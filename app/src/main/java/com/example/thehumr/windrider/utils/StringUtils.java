package com.example.thehumr.windrider.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by ondraboura on 19/05/2018.
 */

public class StringUtils {

    public static final Locale CZ_LOCALE = new Locale("cs", "CZ");

    public static final DecimalFormat integerFormat = (DecimalFormat) NumberFormat.getIntegerInstance(CZ_LOCALE);
    public static DecimalFormat df1 = new DecimalFormat("#.#");
    public static DecimalFormat df2 = new DecimalFormat("#.##");

    public static String formatDistance(double distance) {
        return integerFormat.format(distance) + "m";
    }

    public static String formatDistanceKm(double distance) {
        double distanceKm = distance / 1000;
        return df1.format(distanceKm) + "km";
    }

    public static String formatGrade(double grade) {
        return df1.format(grade) + "%";
    }
}
