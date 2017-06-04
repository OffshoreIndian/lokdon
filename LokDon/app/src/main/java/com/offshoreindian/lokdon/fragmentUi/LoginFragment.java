package com.offshoreindian.lokdon.fragmentUi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.encryption.CodeGeneratorController;
import com.offshoreindian.lokdon.encryption.MpinEncryption;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.httphandler.HttpHandlerAsyncTask;
import com.offshoreindian.lokdon.model.LoginModel;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginFragment extends BaseFragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private Button submitButton;
    private EditText username_textview, password_textview;
    private LinearLayout notRegisterTextView = null;
    private LinearLayout googleButton, hotmailButton, facebookButton, linkedInButton;
    private HttpHandlerAsyncTask httpHandlerAsyncTask;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions googleSignInOptions;
   // private CallbackManager callbackManager;
    private LoginModel loginModel;
    public LoginFragment() {
        loginModel = new LoginModel();
        loginModel.registerView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        applicationController.getActivity().hideToolBar();

        View view = inflater.inflate(R.layout.fragment_login, null);
        username_textview = (EditText) view.findViewById(R.id.input_username);
        password_textview = (EditText) view.findViewById(R.id.input_password);
        submitButton = (Button) view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        notRegisterTextView = (LinearLayout) view.findViewById(R.id.not_register_tv);
        notRegisterTextView.setOnClickListener(this);

        googleButton = (LinearLayout) view.findViewById(R.id.google_plus_button);
        googleButton.setOnClickListener(this);

        hotmailButton = (LinearLayout) view.findViewById(R.id.hotmail_button);
        hotmailButton.setOnClickListener(this);

        facebookButton = (LinearLayout) view.findViewById(R.id.facebook_button);
        facebookButton.setOnClickListener(this);

        linkedInButton = (LinearLayout) view.findViewById(R.id.linked_in_button);
        linkedInButton.setOnClickListener(this);

       // FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        progressBar =(ProgressBar) view.findViewById(R.id.loading_progressBar);


        return view;
    }

    private void loadDataFromServer() {


    }



    @Override
    public void onErrorComes() {
        super.onErrorComes();
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onErrorComes(String msg) {
        super.onErrorComes(msg);
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        applicationController.showToastMessages(msg);
    }

    @Override
    public void updateView(int requestID) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit_button: {
                loginUserInput();
            }
            break;
            case R.id.not_register_tv: {
                applicationController.handleEvent(AppEvents.EVENT_REGISTRATION_SCREEN);
            }
            break;
            case R.id.google_plus_button: {
                loginWithGoogle();
                // applicationController.showToastMessages(" Google Plus Clicked");
            }
            break;
            case R.id.hotmail_button: {
                applicationController.showToastMessages(" Hotmail Clicked");
                logOutFromGoogle();
            }
            break;
            case R.id.facebook_button: {
                //applicationController.showToastMessages(" Facebook Clicked");
                loginWithFacebook();
            }
            break;
            case R.id.linked_in_button: {
                applicationController.showToastMessages(" LinkedIn Clicked");
            }
            break;
        }

    }
    private void loginWithFacebook()
    {
//          callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_friends", "email", "public_profile","user_birthday"));
//
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        // App code
//
//                        getFacebookUserDetail(loginResult);
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                        applicationController.showToastMessages(" Facebook Cancel");
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                        applicationController.showToastMessages("Facebook Error");
//
//                    }
//                });
    }


//    private void getFacebookUserDetail(LoginResult loginResult)
//    {
//        GraphRequest request = GraphRequest.newMeRequest(
//                loginResult.getAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        // Application code
//                        try {
//                            Log.i("Response",response.toString());
//
//                            String email = response.getJSONObject().getString("email");
//                            String firstName = response.getJSONObject().getString("first_name");
//                            String lastName = response.getJSONObject().getString("last_name");
//                            String gender = response.getJSONObject().getString("gender");
//                            String bday= response.getJSONObject().getString("birthday");
//
//                            Profile profile = Profile.getCurrentProfile();
//                            String id = profile.getId();
//                            String link = profile.getLinkUri().toString();
//
////                            applicationController.showToastMessages(" Your Email Id  ::  " + email);
////                            applicationController.putValueInPerference(Constants.KEY_USER_NAME, email);
////                            applicationController.putValueInPerference(Constants.KEY_PASSWORD, firstName);
//////                            applicationController.handleEvent(AppEvents.EVENT_HOME_SCREEN);
////                            applicationController.handleAppLaunchEvent(applicationController.getActivity().drawerProviderBeanArrayList.get(0).getTagId());
//
//                           // Log.i("Link",link);
////                            if (Profile.getCurrentProfile()!=null)
////                            {
////                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
////                            }
//
////                            Log.i("Login" + "Email", email);
////                            Log.i("Login"+ "FirstName", firstName);
////                            Log.i("Login" + "LastName", lastName);
////                            Log.i("Login" + "Gender", gender);
////                            Log.i("Login" + "Bday", bday);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,email,first_name,last_name,gender, birthday");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }
    private void loginWithGoogle() {
        if (googleSignInOptions == null)
        {
            googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage(getActivity(), this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                    .build();

             Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, 11);
        }
        else {

            if(mGoogleApiClient!=null)
            {
                if(mGoogleApiClient.isConnected())
                {
                    applicationController.showToastMessages("You already login with Google");

                }
                else
                {
                    mGoogleApiClient.reconnect();
                }
            }
        }
    }


    private void logOutFromGoogle() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        applicationController.showToastMessages(" Sucessfully Logout!!!");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode == 11){
                if (data != null) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (result != null) {
                        GoogleSignInAccount acct = result.getSignInAccount();
                        if (acct != null) {
//                            applicationController.showToastMessages(" Your Email Id  ::  " + acct.getEmail());
//                            applicationController.putValueInPerference(Constants.KEY_USER_NAME, acct.getEmail());
//                            applicationController.putValueInPerference(Constants.KEY_PASSWORD, acct.getId());
//                            applicationController.handleAppLaunchEvent(applicationController.getActivity().drawerProviderBeanArrayList.get(0).getTagId());

                        }
                    }
                }
            }
           else
            {
               // callbackManager.onActivityResult(requestCode, resultCode, data);

            }




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void loginUserInput() {

        if (username_textview != null && password_textview != null) {
            String userName = username_textview.getText().toString();
            String password = password_textview.getText().toString();
            if (userName!= null && !userName.equalsIgnoreCase("") && password!= null && !password.equalsIgnoreCase(""))
            {
                progressBar.setVisibility(View.VISIBLE);
               // applicationController.showToastMessages("Successfully Login !");

               // applicationController.handleEvent(AppEvents.EVENT_HOME_SCREEN);
                loginModel.sendLoginRequest(userName, CodeGeneratorController.getInstance().generatePassKey(password.toCharArray(),5));
                //   applicationController.handleAppLaunchEvent(applicationController.getActivity().drawerProviderBeanArrayList.get(0).getTagId());
            } else {
                applicationController.showToastMessages("Check your Username and Password.");
                showMpinPopup();
            }
        }
    }


    @Override
    public void updateView() {
        super.updateView();
        if(progressBar!=null)
        progressBar.setVisibility(View.INVISIBLE);
        showMpinPopup();
        //
    }

    Dialog dialog;
    EditText input_mpin;
    EditText input_confirm_mpin;
    public void showMpinPopup( ) {
        dialog = null;
        if (dialog == null) {
            dialog = new Dialog(context, R.style.Dialog_theme);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.register_mpin_pop_up);
            //dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Drawable d = new ColorDrawable(Color.BLACK);
            d.setAlpha(130);
            dialog.getWindow().setBackgroundDrawable(d);

            input_mpin = (EditText) dialog.findViewById(R.id.input_mpin);
            input_confirm_mpin = (EditText) dialog.findViewById(R.id.input_confirm_mpin);

            ImageView crossImage = (ImageView) dialog.findViewById(R.id.cross_button);
            crossImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                }
            });

            Button    okButton = (Button) dialog.findViewById(R.id.ok_button);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mpin = input_mpin.getText().toString();
                    String conMpin = input_mpin.getText().toString();

                    if(mpin!=null && !mpin.equalsIgnoreCase("") && mpin.length() == 4 && conMpin!=null && !conMpin.equalsIgnoreCase("") && conMpin.length() == 4) {
                        if(checkForUnique(mpin)) {
                            dialog.dismiss();
                            String mPinValue =  MpinEncryption.getInstance().getMpinEncrypetData(mpin);
                            System.out.println(" mPinValue  "+mPinValue);
                            // applicationController.handleEvent(AppEvents.EVENT_HOME_SCREEN);
                        }
                        else
                        {
                            applicationController.showToastMessages("All nubmer should be different");
                        }


                    }
                    else {
                        applicationController.showToastMessages("Enter 4 number for your MPIN");
                    }

                }
            });


        }

        dialog.show();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        applicationController.showToastMessages("Some problem to connect with Google. Please try again later");
    }

    public   boolean checkForUnique(String str){
        boolean containsUnique = false;

        for(char c : str.toCharArray()){
            if(str.indexOf(c) == str.lastIndexOf(c)){
                containsUnique = true;
            } else {
                containsUnique = false;
            }
        }

        return containsUnique;
    }
}
