package com.offshoreindian.lokdon.model;



import com.offshoreindian.lokdon.bean.RegistrationBean;
import com.offshoreindian.lokdon.httphandler.HTTPConstant;
import com.offshoreindian.lokdon.httphandler.HttpHandlerAsyncTask;
import com.offshoreindian.lokdon.httphandler.IHttpHandler;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import org.json.JSONObject;

/**
 * Created by praveshkumar on 06/10/16.
 */

public class LoginModel extends BaseModel implements IHttpHandler {

    public LoginModel()
    {

    }

    public void sendLoginRequest(String userName,String pass)
    {
            try
            {



                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email",userName);
                jsonObject.put("password",pass);



                httpHandlerAsyncTask = new HttpHandlerAsyncTask(HTTPConstant.HTTP_POST,this);
                httpHandlerAsyncTask.initializeRequest(HTTPConstant.LOGIN_URL);
                httpHandlerAsyncTask.initializeRequestData(jsonObject.toString());
                 DebugUtil.printLog(" Registration "+jsonObject.toString());
                httpHandlerAsyncTask.execute();

            }catch (Exception e)
            {

            }
    }

    @Override
    public void initilize() {
        super.initilize();
    }

    @Override
    public void destroy() {
        super.destroy();
    }



    @Override
    public void onComplete(String data) {


        try {

            if(data != null)
            {
                JSONObject jsonObject = new JSONObject(data);
                DebugUtil.printLog(" Register Data "+jsonObject.toString());
                if(jsonObject.has(HTTPConstant.JSON_KEY_RESONSE_CODE))
                {
                    int errorCode = jsonObject.getInt(HTTPConstant.JSON_KEY_RESONSE_CODE);
                    if(errorCode == 0)
                    {

                        if(jsonObject.has(HTTPConstant.JSON_KEY_DATA)) {
                            JSONObject obj = jsonObject.getJSONObject(HTTPConstant.JSON_KEY_DATA);
                            applicationController.putValueInPerference(Constants.KEY_USER_NAME, obj.getString("username"));
                            applicationController.putValueInPerference(Constants.KEY_PHONE_NO, obj.getString("phone"));
                            applicationController.putValueInPerference(Constants.KEY_EMAIL_ID, obj.getString("email"));

                            applicationController.setUserName(applicationController.getValueFromPerference(Constants.KEY_USER_NAME));
                            applicationController.getActivity().getDrawer_contact().setText(applicationController.getValueFromPerference(Constants.KEY_PHONE_NO));
                            applicationController.getActivity().getDrawer_username().setText(applicationController.getValueFromPerference(Constants.KEY_USER_NAME));
                            applicationController.getActivity().getDrawer_email().setText(applicationController.getValueFromPerference(Constants.KEY_EMAIL_ID));
                        }
                        informView();
                        applicationController.showToastMessages(jsonObject.getString(HTTPConstant.JSON_KEY_MESSAGE));
                    }
                    else {

                        onErrorComes(jsonObject.getString(HTTPConstant.JSON_KEY_MESSAGE));
                    }
                }
            }

        }catch (Exception e)
        {

        }

     }

    @Override
    public void onComplete(String data, int requestId) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onError(String errorMsg, int errorCode) {

    }

    @Override
    public void onProgressUpdate(int progress) {

    }

    @Override
    public void noNetworkAvailable() {

    }
}
