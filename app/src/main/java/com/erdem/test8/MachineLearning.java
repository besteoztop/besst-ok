package com.erdem.test8;

import android.util.Log;

import java.util.Random;
//1 degree of Longitude 55.051 miles
public class MachineLearning {
    /*****
     * WE COULD NOT USE MACHINE LEARNING BECAUSE OF TIME LIMITATIONS
     * BUT TO DEMONSTRATE HOW IT WOULD WORK ,WE HARDCODED IT
     */

    static String DATA_SET[][];
    static double RATE=0;

    //comma seperated value
    static void extract_DATA_SET(){
        //splits and extracts data from Data.SAFE_DATA_STRING
        String intermediate[]=Data.SAFE_DATA_STRING.split("#");//first member is "" ignore intermediate[0]
        DATA_SET=new String[intermediate.length-1][4];
        for(int i=1;i<intermediate.length;i++){
            String temp[]=intermediate[i].split("-");
            for(int j=0;j<4;j++){
                DATA_SET[i-1][j]=temp[j];
            }
        }
    }
    ////////////////////////
    //FORMAT _--->  #latitude-longitude-[day-time]# MONDAY is 1
    static final double Dx=0.0003;/*about 30 m at ankara*/
    static double CLOSEST;
    static void determineClosest(){
        if(DATA_SET.length<1)return;
        double R=6371;
        for(int i=0;i<DATA_SET.length;i++) {
            double dLat=Double.valueOf(DATA_SET[i][0])-MainActivity.CORD[0];
            double dLon=Double.valueOf(DATA_SET[i][1])-MainActivity.CORD[1];
            Log.d("PR",String.valueOf(dLat));
        }
    }



}




















