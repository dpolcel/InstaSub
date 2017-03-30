package br.com.polcel.instasub.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by polcel on 26/03/17.
 */

public class Tools {

    public static final String LOG_TAG = "INSTA_SUB_LOG";
    public static final String LINE_BREAK_CHAR = "⠀⠀⠀";
    public static final String INSTAGRAM_PACKAGE_NAME = "com.instagram.android";
    public static final String CLIPBOARD_PARAMETER_NAME = "InstaSubText";
    public static final String SUBTITLE_INTENT_PARAMETER = "subtitleId";

    public static String formatDateFromMillis(String dateFormat, long dateInMillis) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());

        return formatter.format(new Date(dateInMillis));
    }

    public static String formatStringWithSeeMore(String subtitle) {
        String sub = "";

        if (subtitle.contains(LINE_BREAK_CHAR)) {
            sub = subtitle.substring(0, subtitle.indexOf(LINE_BREAK_CHAR));
        } else {
            sub = subtitle.substring(0, Math.min(subtitle.length(), 10));
        }

        sub += "...";

        return sub;
    }
}
