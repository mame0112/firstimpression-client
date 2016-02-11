package com.mame.impression.ui;

import com.mame.impression.constant.Constants;

import java.util.regex.Pattern;

/**
 * Created by kosukeEndo on 2016/01/03.
 */
public abstract class TextValidator {

    public enum VALIDATION_RESULT{
        RESULT_OK, INPUT_NULL, INPUT_SHORT, INPUT_LONG, INVALIDED_INPUT_CHAR_TYPE
    }

    public VALIDATION_RESULT isValidInput(String input){
        if(input == null){
            return VALIDATION_RESULT.INPUT_NULL;
        }

        if(input.length() < getMinimumInputength()){
            return VALIDATION_RESULT.INPUT_SHORT;
        }

        if(input.length() > getMaximumInputength()){
            return VALIDATION_RESULT.INPUT_LONG;
        }

        String acceptableString = getAcceptedInputType();

        if(acceptableString != null){
            Pattern pattern = Pattern.compile(acceptableString);

            if(pattern.matcher(input).matches()){
                return VALIDATION_RESULT.INVALIDED_INPUT_CHAR_TYPE;
            }
        }

        return VALIDATION_RESULT.RESULT_OK;

    }

    public boolean isValideInput(String input){
        if(input != null){
            return true;
        }

        if(input.length() >= getMinimumInputength() && input.length() <= getMaximumInputength()){
            String acceptableString = getAcceptedInputType();

            if(acceptableString != null){
                Pattern pattern = Pattern.compile(acceptableString);

                if(pattern.matcher(input).matches()){
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }

        return false;

    }

    public abstract int getMinimumInputength();

    public abstract int getMaximumInputength();

    public abstract String getAcceptedInputType();

}
