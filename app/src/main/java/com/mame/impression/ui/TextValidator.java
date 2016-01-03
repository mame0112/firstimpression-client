package com.mame.impression.ui;

import com.mame.impression.constant.Constants;

/**
 * Created by kosukeEndo on 2016/01/03.
 */
public class TextValidator {

    public static boolean isValidUsername(String username){
        if(username != null){
            if(Constants.USERNAME_MAX_LENGTH >= username.length() && Constants.USERNAMET_MIN_LENGTH <= username.length()){
                return true;
            }
        }

        return false;
    }

    public static boolean isValidPassword(String password){
        if(password != null){
            if(Constants.USERNAME_MAX_LENGTH >= password.length() && Constants.USERNAMET_MIN_LENGTH <= password.length()){
                return true;
            }
        }

        return false;
    }

}
