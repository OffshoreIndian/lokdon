package com.offshoreindian.lokdon.model;

import android.content.Context;

import com.offshoreindian.lokdon.framework.ApplicationController;
import com.offshoreindian.lokdon.framework.IFragment;


public interface  IModel {
    ApplicationController applicationController =ApplicationController.getInstance();
    Context context = ApplicationController.getInstance().getActivity();
    public void initilize();
    public void destroy();
    public void informView();
    public void informView(int requestId);

    public void registerView(IFragment fragment);
    public void unRegisterView(IFragment fragment);
    public void noMoreResult();
    public void networkProblem();
    public void displayBrokenScreen();
    public void onErrorComes();
    public void onErrorComes(String msg);
    public void updateProgressStatus(int progress);
}
