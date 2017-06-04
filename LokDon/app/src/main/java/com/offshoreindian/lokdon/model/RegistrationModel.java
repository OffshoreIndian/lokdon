package com.offshoreindian.lokdon.model;



import com.offshoreindian.lokdon.bean.RegistrationBean;
import com.offshoreindian.lokdon.httphandler.HTTPConstant;
import com.offshoreindian.lokdon.httphandler.HttpHandlerAsyncTask;
import com.offshoreindian.lokdon.httphandler.IHttpHandler;
import com.offshoreindian.lokdon.utils.DebugUtil;

import org.json.JSONObject;

/**
 * Created by praveshkumar on 06/10/16.
 */

public class RegistrationModel extends BaseModel implements IHttpHandler {

    public RegistrationModel()
    {

    }

    public void sendRegistrationRequest(RegistrationBean bean)
    {
            try
            {



                JSONObject jsonObject = new JSONObject();
                jsonObject.put("first_name",bean.getFirstName());
                jsonObject.put("last_name",bean.getLastNamer());
                jsonObject.put("email",bean.getEmailID());
                jsonObject.put("phone_number",bean.getContactNo());
                jsonObject.put("password",bean.getPassword());
                jsonObject.put("username",bean.getEmailID());
                jsonObject.put("user_type","AppUser");


                httpHandlerAsyncTask = new HttpHandlerAsyncTask(HTTPConstant.HTTP_POST,this);
                httpHandlerAsyncTask.initializeRequest(HTTPConstant.REGISTRATION_URL);
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
