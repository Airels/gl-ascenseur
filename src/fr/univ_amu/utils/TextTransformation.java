package fr.univ_amu.utils;

import java.text.DecimalFormat;

/**
 * Utility class to transform objects into specific strings
 */
public class TextTransformation {

    /**
     * Convert integer to string with two digits
     *
     * @param i positive integer to convert
     * @return String representation in two digits of the int
     */
    public static String intTwoDigits(int i) {
        if (i < 0) throw new IllegalArgumentException("Value can't be lower than 0");
        if (i > 99) throw new IllegalArgumentException("Value can't be higher than 99");

        DecimalFormat formatter = new DecimalFormat("00");
        return formatter.format(i);
    }
}
