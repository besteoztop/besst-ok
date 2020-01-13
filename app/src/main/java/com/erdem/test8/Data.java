package com.erdem.test8;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Data {

    static long LAST_TIME_DATA_INSTERTED;
    static String DATA_STRING;
    static int COUNT;
    static String SAFE_DATA_STRING;
    static int SAFE_COUNT;

    //12000 format data(100 bayt) per sharedpref about 1.1 MB
    //max rate is 300 second per data -> 5min/data
    //it will take 41 days to get a memory error
    //FORMAT _--->  #latitude-longitude-[day-time]# MONDAY is 1 bıdılamışım
    private static final String SHARED_PREF="FIRST_41_DAY";
    private static final String KEY = "KEY_FIRST_41_DAY";
    private static final String SHARED_PREF_COUNTER="FIRST_41_DAY_COUNTER";
    private static final String KEY_COUNTER = "KEY_FIRST_41_DAY";
    // USE WHEN APP START
    public static void setupData(Context context){
        LAST_TIME_DATA_INSTERTED=System.currentTimeMillis();
        COUNT=loadCounter(context);
        DATA_STRING =loadData(context);
        SAFE_DATA_STRING=DATA_STRING;
        SAFE_COUNT=COUNT;
    }

    //INSERTS TEXT TO DATA_STRING //TEMPORARLY
    static void expand_DATA_STRING(String LANGITUDE,String LONGITUDE,String MINUTE_OF_THE_DAY,long THRESHOLD_TO_ACCEPT_NEW_DATA,Context context){
        if((System.currentTimeMillis()-LAST_TIME_DATA_INSTERTED)<THRESHOLD_TO_ACCEPT_NEW_DATA)return;
        LAST_TIME_DATA_INSTERTED=System.currentTimeMillis();
        DATA_STRING+="#"+LANGITUDE+"-"+LONGITUDE+"-"+MINUTE_OF_THE_DAY;
        COUNT++;
    }

    // WRITE DATA_STRING & COUNT() TO MEMORY
    static void saveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY, DATA_STRING);
        editor.apply();
        changeDataCount(COUNT,context);
        SAFE_DATA_STRING=DATA_STRING;
        SAFE_COUNT=COUNT;
    }
        //WRITE TOTAL COUNT TO MEMORY
        static int changeDataCount(int newVal,Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_COUNTER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt(KEY_COUNTER,newVal);
        editor.apply();
        return 1;
    }

    //READ STRING DATA AND RETURN IT
    static String loadData(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
            String text = sharedPreferences.getString(KEY, "");
            return text;
    }

    //READ COUNT DATA AND RETURN IT
    static int loadCounter(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_COUNTER, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_COUNTER,0);
    }


}















