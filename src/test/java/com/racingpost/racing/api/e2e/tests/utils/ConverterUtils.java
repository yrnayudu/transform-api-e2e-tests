package com.racingpost.racing.api.e2e.tests.utils;

/**
 * Helper class containing calculation/formatting type methods that can be used on various fields to do with racing.
 */
public class ConverterUtils {

    /**
     * Given distance in yards format into furlong string e.g. 4½f
     *
     * @param yards double
     * @return String
     */
    public static String yardsToFurlongs(double yards) {
        double miles = yards / 1760;
        double remainingFurlongs = (miles % 1) * 7.99998;

        int displayMiles = (int) Math.floor(miles);
        String displayFurlongs = formatFraction(roundToClosest(remainingFurlongs, 0.5));

        if (displayMiles > 0) {
            return String.format("%dm%s", displayMiles, displayFurlongs);
        } else {
            return String.format("%s", displayFurlongs);
        }
    }

    /**
     * Given a double value format it to a fraction.
     *
     * @param value double
     * @return double
     */
    public static String formatFraction(double value) {
        int d = (int) value;
        double fraction = roundToClosest(value, 0.25) % 1;

        if (d == 0 && fraction == 0) {
            return "";
        }

        String displayValue = (d > 0 ? d + "" : "");
        String displayFraction;

        switch (fraction + "") {
            case "0.25":
                displayFraction = "¼";
                break;
            case "0.5":
                displayFraction = "½";
                break;
            case "0.75":
                displayFraction = "¾";
                break;
            default:
                displayFraction = "";
                break;
        }

        return String.format("%s%sf", displayValue, displayFraction);
    }

    /**
     * Give weight in lbs format into a string with weight in stone + lbs e.g. 12st 13lb
     * @param lbs double
     * @return String
     */
    public static String lbsToStone(double lbs) {
        // convert lbs to stones as a decimal e.g. 8.67
        double stoneDecimal = lbs / 14;
        // grab the full stone value e.g. 8.67 -> 8
        int stoneValue = (int) stoneDecimal;
        // work out the remaining pounds e.g. 8.67 -> .67
        int remainingLbs = (int) Math.round((stoneDecimal - stoneValue) * 14);

        if (stoneValue > 0 && remainingLbs > 0) {
            return String.format("%dst %dlb", stoneValue, remainingLbs);
        }

        if (remainingLbs > 0) {
            return String.format("%dlb", remainingLbs);
        }

        return String.format("%dst", stoneValue);
    }

    /**
     * Given a double round it to the closest value to a certain step e.g. round to the closest in steps of 0.5.
     *
     * @param value double
     * @param step  double
     * @return double
     */
    public static double roundToClosest(double value, double step) {
        double inv = 1.0 / step;
        return Math.round(value * inv) / inv;
    }

    public static void main(String[] args) {
        System.out.println(lbsToStone(2000));
        System.out.println(yardsToFurlongs(2100));
        System.out.println(yardsToFurlongs(2200));
        System.out.println(yardsToFurlongs(2300));
        System.out.println(yardsToFurlongs(3560));
    }
}
