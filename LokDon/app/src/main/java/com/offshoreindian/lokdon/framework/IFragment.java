package com.offshoreindian.lokdon.framework;


public interface IFragment
{

    public void initilize();
    public void destroy();
    public void updateView();
    public void updateView(int requestID);

    public void noMoreResult();
    public void networkProblem();
    public void displayBrokenScreen();
    public  boolean onBackKeyPressed();
    public void onErrorComes();
    public void onErrorComes(String msg);
    public void updateProgress(int status);

    public boolean handleFloatingButtonClick();

}
