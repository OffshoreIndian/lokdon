package com.offshoreindian.lokdon.httphandler;


public class HTTPConstant {

    public static final String HTTP_POST = "POST";
    public static final String HTTP_GET = "GET";
    public static final String HTTP_DELETE = "DELETE";



    public static final String JSON_KEY_MESSAGE ="message";
    public static final String JSON_KEY_RESONSE_CODE ="response_code";
    public static final String JSON_KEY_DATA = "data";
//    http://ec2-54-202-137-142.us-west-2.compute.amazonaws.com:9999/api/v1/auth/login/

    public static final int HTTP_CONNECTION_READ_TIME_OUT = 3000;
    public static final int HTTP_CONNECTION_TIME_OUT = 3000;
     private static final String BASE_URL ="http://ec2-54-202-137-142.us-west-2.compute.amazonaws.com:9999/api/v1/auth/";
    public static final String REGISTRATION_URL =BASE_URL+"register/";
    public static final String LOGIN_URL =BASE_URL+"login/";



}
