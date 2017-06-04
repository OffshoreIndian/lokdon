package com.offshoreindian.lokdon.fragmentUi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.DebugUtil;


public class WebViewFragment extends BaseFragment implements View.OnClickListener{

     private WebView webView;
    private ProgressBar progressBar;
    public WebViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String data = (String) getFragmentData();
        View view = inflater.inflate(R.layout.fragment_azure, null);
        applicationController.getActivity().showHomeScreenLogo();


        progressBar =(ProgressBar) view.findViewById(R.id.loading_progressBar);
        webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(data);

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
    }



    @Override
    public void updateView() {
        super.updateView();
    }




}
