package com.mame.impression.util;

/**
 * Created by kosukeEndo on 2015/12/12.
 */
public abstract class AbstractInputValidator {

    public abstract boolean isMoreThanMinLength(String input, int minLength);

    public abstract boolean isLessThanMaxLength(String input, int maxLength);

    public boolean isValidText(String input, int min, int max){
        if(isLessThanMaxLength(input, min) && isMoreThanMinLength(input, max)){

        }
        return false;
    }

}
