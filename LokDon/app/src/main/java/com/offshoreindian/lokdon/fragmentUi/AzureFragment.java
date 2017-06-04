package com.offshoreindian.lokdon.fragmentUi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.encryption.CodeGeneratorController;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.Date;


public class AzureFragment extends BaseFragment implements View.OnClickListener {

    private WebView webView;
    private ProgressBar progressBar;
    public AzureFragment() {
    }

    // orignal on ::: PraveshĽĲďó¼¥@
    //                lokdonĽĲďó¼¥@
    // my key  &½xü³«s~ñþ'qz0^201,195,190,165,232
    //key I¯íĒ¯ëK#ÐRÄ H^82,115,64,43,36,
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_azure, null);
        applicationController.getActivity().showToolBar();
//        applicationController.getActivity().showHomeScreenLogo();
      //  generateOrignalKey(LokdonActivity.generatedKey);
        //lokdonĽĲďó¼¥@
//        String key =   PassKeyController.getInstance().generatePassKey("Hello".toCharArray());
        System.out.println("Start Time   "+new Date(System.currentTimeMillis()).toString());
       // String val = "Thanks for the advice! This does work, but how do you get the icon to go to the left side of the menu bar? Also,, Thanks for the advice! This does work, but how do you get the icon to go to the left side of the menu bar? Also,, have to keep track of whether the drawer is open to know if you should call openDrawer or closeDrawer, correct Thanks for the advice! This does work, but how do you get the icon to go to the left side of the menu bar? Also, have to keep track of whether the drawer is open to know if you should call openDrawer or closeDrawer, correct Thanks for the advice! This does work, but how do you get the icon to go to the left side of the menu bar? Also, have to keep track of whether the drawer is open to know if you should call openDrawer or closeDrawer, correct";
//        String key =   PassKeyController.getInstance().generatePassKey("lokdonĽĲďó¼¥@".toCharArray());

        String val ="The Congress questioned RBI governor Urjit Patel's role in the ban on Rs. 500 and 1,000 notes."
                +
                " New Delhi: Urjit Patel, who took charge of the Reserve Bank of India less than three months ago," +
                " has found himself in the crosshairs of the Congress party's The Congress questioned RBI governor" +
                " Urjit Patel's role in the ban on Rs. 500 and 1,000 notes. New Delhi: Urjit Patel, who took charge " +
                "of the Reserve Bank of India less than three months ago, has found himself in the crosshairs of the" +
                " Congress party's The Congress questioned RBI governor Urjit Patel's role in the ban on Rs. 500 and " +
                "1,000 notes. New Delhi: Urjit Patel, who took charge of the Reserve Bank of India less than three " +
                "months ago, has found himself in the crosshairs of the Congress party's The Congress questioned RBI " +
                "governor Urjit Patel's role in the ban on Rs. 500 and 1,000 notes. New Delhi: Urjit Patel," +
                " who took charge of the Reserve Bank of India less than three months ago, has found himself in " +
                "the crosshairs of the Congress party's The Congress questioned RBI governor Urjit Patel's role in the" +
                " ban on Rs. 500 and 1,000 notes. New Delhi: Urjit Patel, who took charge of the Reserve Bank of India " +
                "less than three months ago, has found himself in the crosshairs of the Congress party's The Congress" +
                " questioned RBI governor Urjit Patel's role in the ban on Rs. 500 and 1,000 notes. New Delhi: Urjit " +
                "Patel, who took charge of the Reserve Bank of India less than three months ago, has found himself in " +
                "the crosshairs of the Congress party's The Congress questioned RBI governor Urjit Patel's role in the ban on Rs." +
                " 500 and 1,000 notes. New Delhi: Urjit Patel, who took charge of the Reserve Bank of India less than three " +
                "months ago, has found himself in the crosshairs of the Congress party's ";

//        String key =   PassKeyController.getInstance().generatePassKey(val.toCharArray());
        String key =   CodeGeneratorController.getInstance().generatePassKey("Hello World".toCharArray(),5);
        DebugUtil.printLog(" Generated Key :- "+key);
        String password =   CodeGeneratorController.getInstance().decryptSilentPassKey(key);
        DebugUtil.printLog(" Pawword "+password);
       // String key = "¡¸NNo^226,63,220,5,234";
//        String key = NewCodeGeneratorController.getInstance().generatePassKey("Hello".toCharArray());
//    String orignalKey =  NewCodeGeneratorController.getInstance().decryptSilentPassKey(key);
//        DebugUtil.printLog("orignalKey ::  "+orignalKey);
//        System.out.println("END  Time   "+new Date(System.currentTimeMillis()).toString());



        progressBar =(ProgressBar) view.findViewById(R.id.loading_progressBar);
        webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://azure.microsoft.com");
//        webView.loadUrl("https://azure.microsoft.com/en-in/free/?&wt.mc_id=AID529447_SEM_rrCx92BA");

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                DebugUtil.printLog("request.toString() "+url.toString());
                view.loadUrl(url.toString());
                return true;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }




    @Override
    public void updateView() {
        super.updateView();
    }


}
