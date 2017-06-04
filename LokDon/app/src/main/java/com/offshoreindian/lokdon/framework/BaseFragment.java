package com.offshoreindian.lokdon.framework;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.offshoreindian.lokdon.utils.DebugUtil;


public class BaseFragment extends Fragment implements IFragment {
    protected ApplicationController applicationController;
    public Context context;
    protected Object fragmentData;
    protected TextView noResultTextView;
    protected ProgressBar progressBar;
    protected int fragmentViewId;

    public BaseFragment() {
        this.applicationController = ApplicationController.getInstance();
        context = applicationController.getActivity();
    }


    public int getFragmentViewId() {
        return fragmentViewId;
    }

    public void setFragmentViewId(int fragmentViewId) {
        this.fragmentViewId = fragmentViewId;
    }

    public Object getFragmentData() {
        return fragmentData;
    }

    public void setFragmentData(Object fragmentData) {
        this.fragmentData = fragmentData;
    }


    @Override
    public void initilize() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void updateView() {

    }

    @Override
    public void noMoreResult() {

    }

    @Override
    public void networkProblem() {

    }

    @Override
    public void displayBrokenScreen() {

    }

    @Override
    public boolean onBackKeyPressed() {


        return false;
    }

    @Override
    public void updateView(int requestID) {

    }

    @Override
    public void onErrorComes() {

    }

    @Override
    public void onErrorComes(String msg) {

    }

    @Override
    public void updateProgress(int status) {

    }

    @Override
    public boolean handleFloatingButtonClick() {
        return false;
    }
}
