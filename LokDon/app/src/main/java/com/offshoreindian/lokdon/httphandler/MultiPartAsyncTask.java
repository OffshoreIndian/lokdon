package com.offshoreindian.lokdon.httphandler;

import android.os.AsyncTask;

import com.offshoreindian.lokdon.utils.DebugUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

public class MultiPartAsyncTask extends AsyncTask<String, Integer, String> {


    //requested url
    private String requestUrl = "";
    String requestData = null;
    private int requestID = -1;
    private String requestType = HTTPConstant.HTTP_POST;
    private URL url;
    private HttpURLConnection connection = null;
    private IHttpHandler listener = null;
    //
    private boolean isTaskComplete = false;
    private Vector<String> addFormFieldKey;
    private Vector<String> addFormFieldValue;
    private Vector<String> addFilePath;
    private String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private String charset = "UTF-8";
    private OutputStream outputStream;
    private PrintWriter writer;
    private File uploadFile;
    private String uploadFileName;

    public MultiPartAsyncTask(String reqType, IHttpHandler list) {
        this.requestType = reqType;
        this.listener = list;
        addFormFieldKey = new Vector<String>();
        addFormFieldValue = new Vector<String>();
        addFilePath = new Vector<String>();
    }

    /**
     * Initialize Request
     *
     * @param url - Request URL
     */
    public void initializeRequest(String url) {
        initialize(url, -1);
    }

    /**
     * Initialize Request
     *
     * @param url   - Request URL
     * @param reqID - Request ID
     */
    public void initialize(String url, int reqID) {
        this.requestUrl = url;
        this.requestID = reqID;
    }

    /**
     * For add the multipart key and value field
     *
     * @param key
     * @param value
     */
    public void addFromField(String key, String value) {
        if (key != null && value != null) {
            addFormFieldKey.add(key);
            addFormFieldValue.add(value);

        }
    }


    /**
     * Path of the file which we need to send to server
     *
     * @param fieldName
     * @param uploadFile
     */
    public void addFilePath(String fieldName, String uploadFile) {
        this.uploadFileName = fieldName;
        this.uploadFile = new File(uploadFile);
    }


    public boolean isTaskComplete() {
        return isTaskComplete;
    }

    public void setTaskComplete(boolean isTaskComplete) {
        this.isTaskComplete = isTaskComplete;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (listener != null) {
            listener.onComplete(s);
            if (requestID != -1) {
                listener.onComplete(s, requestID);
            }

        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // Debug.printLog(" onProgressUpdate "+values[0]);
        if (listener != null)
            listener.onProgressUpdate(values[0]);
    }

    @Override
    protected String doInBackground(String... strings) {
        boundary = "===" + System.currentTimeMillis() + "===";
        int totalByteRead = 0;

        try {

            URL url = new URL(requestUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);    // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setRequestProperty("Connection", "Keep-Alive");

            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);

            if (addFormFieldValue.size() > 0 && addFormFieldKey.size() > 0 && addFormFieldKey.size() == addFormFieldValue.size()) {
                for (int i = 0; i < addFormFieldValue.size(); i++) {
                    writer.append("--" + boundary).append(LINE_FEED);
                    writer.append("Content-Disposition: form-data; name=\"" + addFormFieldKey.get(i) + "\"")
                            .append(LINE_FEED);
                    writer.append("Content-Type: text/plain; charset=" + charset).append(
                            LINE_FEED);
                    writer.append(LINE_FEED);
                    writer.append(addFormFieldValue.get(i)).append(LINE_FEED);
                    writer.flush();

                }
            }

            if (uploadFile != null) {
                {
                    String fileName = uploadFile.getName();
                    writer.append("--" + boundary).append(LINE_FEED);
                    writer.append(
                            "Content-Disposition: form-data; name=\"" + uploadFileName
                                    + "\"; filename=\"" + uploadFile + "\"")
                            .append(LINE_FEED);
                    writer.append(
                            "Content-Type: "
                                    + URLConnection.guessContentTypeFromName(fileName))
                            .append(LINE_FEED);
                    writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
                    writer.append(LINE_FEED);
                    writer.flush();
                    FileInputStream inputStream = new FileInputStream(uploadFile);

                    //  int totalSize = httpConn.getContentLength();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    int totalSize = (int) (uploadFile).length();
                    while ((bytesRead = inputStream.read(buffer)) != -1) {

                        totalByteRead += bytesRead;
                        int progressValue = ((int) (((totalByteRead * 100) / (int) totalSize)));
                        if (progressValue < 85) {
                            publishProgress(progressValue);
                            try {
                                Thread.sleep(10);
                            } catch (Exception ex) {
                            }
                        }
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                    inputStream.close();

                    writer.append(LINE_FEED);
                    writer.flush();
                }

                writer.append(LINE_FEED).flush();
                writer.append("--" + boundary + "--").append(LINE_FEED);
                writer.close();

                StringBuffer responseData = new StringBuffer();
                // checks server's status code first
                publishProgress(90);
                int status = httpConn.getResponseCode();
                publishProgress(95);
                if (status == HttpURLConnection.HTTP_OK || status ==201) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            httpConn.getInputStream()));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        responseData.append(line);
                    }
                    publishProgress(100);
                    reader.close();
                    httpConn.disconnect();
                    return responseData.toString();
                } else {
                    return null;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
            if (listener != null) {
                listener.onError();

            }

        }

        return null;
    }
}
