package com.offshoreindian.lokdon.httphandler;


public interface IHttpHandler {
    public void onComplete(String data);
    public void onComplete(String data, int requestId);
    public void onError();
    public void onError(String errorMsg, int errorCode);
    
    public void onProgressUpdate(int progress);
    public void noNetworkAvailable();

}
