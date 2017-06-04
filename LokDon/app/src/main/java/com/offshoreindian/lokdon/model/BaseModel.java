package com.offshoreindian.lokdon.model;

import com.offshoreindian.lokdon.framework.IFragment;
import com.offshoreindian.lokdon.httphandler.HttpHandlerAsyncTask;

import java.util.ArrayList;


public class BaseModel implements IModel{

    ArrayList<IFragment> fragments = new ArrayList<IFragment>();
    HttpHandlerAsyncTask httpHandlerAsyncTask;
    @Override
    public void initilize() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void informView() {
        for (int i = 0; i< fragments.size();i++)
            fragments.get(i).updateView();

    }
    @Override
    public void informView(int requestId) {
        for (int i = 0; i< fragments.size();i++)
            fragments.get(i).updateView(requestId);

    }
    @Override
    public void noMoreResult() {
        for (int i = 0; i< fragments.size();i++)
            fragments.get(i).noMoreResult();

    }

    @Override
    public void networkProblem() {
        for (int i = 0; i< fragments.size();i++)
            fragments.get(i).networkProblem();

    }

    @Override
    public void displayBrokenScreen() {
        for (int i = 0; i< fragments.size();i++)
            fragments.get(i).displayBrokenScreen();

    }

    @Override
    public void registerView(IFragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public void unRegisterView(IFragment fragment) {

        if(fragments.contains(fragment))
            fragments.remove(fragment);

    }


    @Override
    public void onErrorComes() {
        for (int i = 0; i< fragments.size();i++)
            fragments.get(i).onErrorComes();
    }

    @Override
    public void onErrorComes(String msg) {
        for (int i = 0; i< fragments.size();i++)
            fragments.get(i).onErrorComes(msg);
    }

    @Override
    public void updateProgressStatus(int progress) {
        for (int i = 0; i< fragments.size();i++)
            fragments.get(i).updateProgress(progress);
    }
}
