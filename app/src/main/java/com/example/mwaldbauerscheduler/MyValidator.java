package com.example.mwaldbauerscheduler;

import android.text.TextUtils;

public class MyValidator {
    private int day;
    private int month;
    private int year;

    public MyValidator(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public MyValidator(){}

    public boolean checkDate(int day, int month, int year){

        boolean result = true;

        //Check Start Date

        if (month < 1 || month > 12) {
            result = false;
        }

        switch (month){
            case 2:
                //all leap year rules

                int checkYear = year;
                if (checkYear % 400 == 0)
                {if (day < 1 || day > 29) {
                    result = false;}
                    break;}
                if (checkYear % 100 == 0) {
                    if (day < 1 || day > 28) {
                        result = false;}
                    break;}
                if (checkYear % 4 == 0) {
                    if (day < 1 || day > 29) {
                        result = false;}
                    break;}

                //non-leap year

                if (day < 1 || day > 28) {
                    result = false;
                    break;}

                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (day < 1 || day > 30) {
                    result = false;}
                break;
            default:
                if (day < 1 || day > 31) {
                    result = false;}


        }

        return result;
    }

    public boolean checkDate(String day, String month, String year) {
        boolean result = true;

        int parsedDay;
        int parsedMonth;
        int parsedYear;

        try{
            parsedDay = Integer.parseInt(day);
            parsedMonth = Integer.parseInt(month);
            parsedYear = Integer.parseInt(year);
        }

        catch (Exception e){
            return false;
        }

        result = checkDate(parsedDay, parsedMonth, parsedYear);

        return result;

    }

    //no getters and setts for now as you shouldn't need them.
}
