package com.offshoreindian.lokdon.utils;


public class DebugUtil {


    public static void printLog(String mes)
    {
        System.out.println(mes);
    }

    public static void printExceptionLog(String mes, Exception e)
    {

        System.out.println(mes +"::::::::::::: ----------"+e);
        e.printStackTrace();
    }
}
