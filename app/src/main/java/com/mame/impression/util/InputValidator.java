package com.mame.impression.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kosukeEndo on 2015/12/12.
 */
public abstract class InputValidator {

    public abstract boolean isMoreThanMinLength(String input, int minLength);

    public abstract boolean isLessThanMaxLength(String input, int maxLength);

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";

//    private static final String TEXT_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";

    private static final String TEXT_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";

    private static Pattern mTextPattern = Pattern.compile(TEXT_PATTERN);
    private Matcher matcher;

    public static boolean isValidText(String text) {
        Matcher matcher  = mTextPattern.matcher(text);
        return matcher.matches();
//        if (isLessThanMaxLength(input, min) && isMoreThanMinLength(input, max)) {
//
//        }
    }



}
