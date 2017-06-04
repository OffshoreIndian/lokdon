package com.offshoreindian.lokdon.httphandler;

import android.net.Uri;
import android.os.AsyncTask;

import com.offshoreindian.lokdon.framework.ApplicationController;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * Created by Pravesh Kumar Gupta on 01/03/16.
 */
public class HttpHandlerAsyncTask extends AsyncTask<String, String, String> {
    private String no_network = "no_network";
    private String takenExpired = "tokenExpired";

    //requested url
    private String requestUrl = "";
    String requestData = null;
    public int requestID = -1;
    private String requestType = HTTPConstant.HTTP_GET;
    private URL url;
    private HttpURLConnection connection = null;
    private IHttpHandler listener = null;
    private boolean isTaskComplete = false;
    private String errorMessage = null;

    public HttpHandlerAsyncTask(String reqType, IHttpHandler list) {
        this.requestType = reqType;
        this.listener = list;
    }


    public void initializeRequest(String url) {
        initializeRequest(url, -1);
    }


    public void initializeRequest(String url, int reqID) {
        this.requestUrl = url;
        this.requestID = reqID;
    }


    public void initializeRequestData(String[] key, String[] value) {
        if (key != null && value != null) {
            Uri.Builder builder = new Uri.Builder();
            for (int i = 0; i < key.length; i++) {
                builder.appendQueryParameter(key[i], value[i]);
            }
            requestData = builder.build().getEncodedQuery();
        }
    }

    public boolean isTaskComplete() {
        return isTaskComplete;
    }

    public void setTaskComplete(boolean isTaskComplete) {
        this.isTaskComplete = isTaskComplete;
    }


    public void initializeRequestData(String reqData) {
        requestData = reqData;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String[] values) {
        super.onProgressUpdate(values);
    }


    @Override
    protected String doInBackground(String[] objects) {


        if (ApplicationController.getInstance().isNetworkConnected()) {
            try {

                //Create connection

                url = new URL(requestUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(requestType);


                connection.setRequestProperty("Content-Type", "application/json");

                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setReadTimeout(HTTPConstant.HTTP_CONNECTION_READ_TIME_OUT);
                connection.setConnectTimeout(HTTPConstant.HTTP_CONNECTION_TIME_OUT);

                //Send request
                if (requestData != null && !requestData.equalsIgnoreCase("")) {
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(requestData.toString());
                    writer.flush();
                    writer.close();
                }


                int statusCode = connection.getResponseCode();


                DebugUtil.printLog(" Server status code " + statusCode);
                if (statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_CREATED)//|| statusCode == 400
                {
                    try {
                        InputStream is = connection.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        String line;
                        StringBuffer response = new StringBuffer();
                        while ((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();
                        if (response.toString().equalsIgnoreCase(""))
                            return Constants.SUCCESS;
                        else
                            return response.toString();
                    } catch (Exception e) {
                        return Constants.SUCCESS;

                    }
                } else if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED)// 401 token is expired now need to refresh the token
                {
//                    ApplicationController.getInstance().handleEvent(AppEvents.EVENT_TOKEN_EXPIRE);
                    return takenExpired;

                } else {

                    Map<String, List<String>> map = connection.getHeaderFields();

                    DebugUtil.printLog("Printing Response Header...\n");
                    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                        DebugUtil.printLog(" Header Key Key : " + entry.getKey()
                                + " ,Value : " + entry.getValue());


//                        if(entry != null && entry.getKey() != null && entry.getKey().equalsIgnoreCase("X-error"))
//                        {
//                            errorMessage = entry.getValue().get(0);
//                        }
                    }
                    if (errorMessage == null) {
                        try {
                            InputStream is = connection.getErrorStream();
                            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                            String line;
                            StringBuffer response = new StringBuffer();
                            while ((line = rd.readLine()) != null) {
                                response.append(line);
                                response.append('\r');
                            }
                            rd.close();
                            try {
                                JSONObject temp = new JSONObject(response.toString());
                                if (temp.has("error_description")) {
                                    errorMessage = temp.getString("error_description");
                                }
                            } catch (Exception e) {
                                {
                                    errorMessage = response.toString();
                                }
                            }

                            // errorMessage = response.toString();
                        } catch (Exception e) {
                            //return Constants.SUCCESS;
                            DebugUtil.printExceptionLog("Error comes ", e);

                        }
                    }
                    return null;
                }


            } catch (SocketTimeoutException e) {
                //ApplicationController.getInstance().handleEvent(AppEvents.ERROR_SERVER_NOT_RESPONSE);
                e.printStackTrace();
                return null;

            } catch (IOException e) {
                //  ApplicationController.getInstance().handleEvent(AppEvents.ERROR_SERVER_NOT_RESPONSE);
                e.printStackTrace();
                return null;

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            }
        } else {
            return no_network;
        }

    }


    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        DebugUtil.printLog("  Server Response " + str);
        if (str != null && !str.equalsIgnoreCase(no_network)) {
            if (listener != null) {
                listener.onComplete(str);
                if (requestID != -1)
                    listener.onComplete(str, requestID);
            }
        } else if (str != null && str.equalsIgnoreCase(takenExpired)) {

        } else {
            if (str != null && str.equalsIgnoreCase(no_network)) {
                if (listener != null) {
                    listener.noNetworkAvailable();
                }
                ApplicationController.getInstance().showToastMessages("No Network Avaliable");
            } else {

                if (errorMessage != null) {
                    if (listener != null) {
                        listener.onError(errorMessage, -1);
                    }

                } else {
                    if (listener != null) {
                        listener.onError();
                    }
                    ApplicationController.getInstance().showToastMessages("Internal Server Error. Please Try again after some time");


                }

            }
        }
        setTaskComplete(true);

    }
}
