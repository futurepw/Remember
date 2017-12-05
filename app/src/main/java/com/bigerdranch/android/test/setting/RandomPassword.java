package com.bigerdranch.android.test.setting;

import java.util.Random;

/**
 * Created by Administrator on 2017/12/2.
 */

public class RandomPassword {
    private String upperCase;
    private String lowerCase;
    private String digital;
    private String specialChar;

    public String makeRandomPassword(int len, String string) {

//        char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*.?".toCharArray();
        //System.out.println("字符数组长度:" + charr.length);	//可以看到调用此方法多少次
        char charr[] = string.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < len; ++x) {
            sb.append(charr[r.nextInt(charr.length)]);
        }
        return sb.toString();
    }

    public String getUpperCase() {
        return upperCase;
    }

    public void setUpperCase(String upperCase) {
        this.upperCase = upperCase;
    }

    public String getLowerCase() {
        return lowerCase;
    }

    public void setLowerCase(String lowerCase) {
        this.lowerCase = lowerCase;
    }

    public String getDigital() {
        return digital;
    }

    public void setDigital(String digital) {
        this.digital = digital;
    }

    public String getSpecialChar() {
        return specialChar;
    }

    public void setSpecialChar(String specialChar) {
        this.specialChar = specialChar;
    }

    public RandomPassword() {
    }

    public RandomPassword(String upperCase, String lowerCase, String digital, String specialChar) {
        this.upperCase = upperCase;
        this.lowerCase = lowerCase;
        this.digital = digital;
        this.specialChar = specialChar;
    }
}
