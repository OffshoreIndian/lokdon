package com.offshoreindian.lokdon.fragmentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.adapter.WelcomePagerAdapter;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;


public class WelcomeReadyFragment extends BaseFragment implements View.OnClickListener {


    private Button start_lokdon;
    public WelcomeReadyFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_ready_wel, null);
        start_lokdon =(Button) view.findViewById(R.id.start_lokdon);
        start_lokdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationController.putValueInPerference(Constants.WELCOME_KEY,"save");
                applicationController.handleEvent(AppEvents.EVENT_LOGIN_SCREEN,null,true);
            }
        });


        String text = "<font color=#0faac3><b><a href='http://www.lokdon.com/'>Why use Lokdon?</a><</b></font>";
        TextView textView = (TextView) view.findViewById(R.id.why_lokdon);
        textView.setText(Html.fromHtml(text));



        String text1 = "<font color=#0faac3><b><a href='http://www.lokdon.com/'>The knowledge base</a><</b></font>";
        TextView textView1 = (TextView) view.findViewById(R.id.knowledge);
        textView1.setText(Html.fromHtml(text1));


        String text2 = "<font color=#0faac3><b><a href='http://www.lokdon.com/'>Make an investment today</a><</b></font>";
        TextView textView2 = (TextView) view.findViewById(R.id.make_invest);
        textView2.setText(Html.fromHtml(text2));

        String text3 = "<font color=#0faac3><b><a href='http://www.lokdon.com/'>Send us an email</a><</b></font>";
        TextView textView3 = (TextView) view.findViewById(R.id.send_mail);
        textView3.setText(Html.fromHtml(text3));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void updateView() {
        super.updateView();


    }


}
