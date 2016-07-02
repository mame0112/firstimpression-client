package com.mame.impression.util;

import com.mame.impression.constant.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kosukeEndo on 2016/01/16.
 */
public class SecurityUtil {

    private final static String TAG = Constants.TAG + SecurityUtil.class.getSimpleName();

    private static final String ALG = "SHA-256";

    public static String getPasswordHash(String userName, String password){
        return getHash(password, getSalt(userName));
    }

    private static String getHash(String target){
        String hash = null;

        try {
            MessageDigest md = MessageDigest.getInstance(ALG);
            md.update(target.getBytes());
            byte[] digest = md.digest();
            //byte[] -> String
            hash = new String(digest, "UTF-8");

        } catch (NoSuchAlgorithmException e) {
            LogUtil.d(TAG, "NoSuchAlgorithmException: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LogUtil.d(TAG, "UnsupportedEncodingException: " + e.getMessage());
        }
        return hash;

    }

    private static String getHash(String target, String salt){
        return getHash(target + salt);
    }

    private static String getSalt(String userName){
        //TODO Add fixed salt if necessary
        return userName;
    }
}
