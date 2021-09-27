package fr.univ_amu.utils;

import java.text.DecimalFormat;

public class TextTransformation {
    public static String intTwoDigits(int i) {
        DecimalFormat formatter = new DecimalFormat("00");
        return formatter.format(i);
    }
}
