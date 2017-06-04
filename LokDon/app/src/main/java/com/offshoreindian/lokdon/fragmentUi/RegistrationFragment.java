package com.offshoreindian.lokdon.fragmentUi;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.bean.ContactNoBean;
import com.offshoreindian.lokdon.bean.RegistrationBean;
import com.offshoreindian.lokdon.encryption.CodeGeneratorController;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.model.RegistrationModel;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import org.json.JSONArray;
import org.json.JSONObject;


public class RegistrationFragment extends BaseFragment implements View.OnClickListener {

    private EditText emailEditText, passwordEditText, confirmPassEditText, userNameEditText, contactNoEditText, firstNameEditText, lastNameEditText;
    private Button submitButton;
    private RegistrationModel registrationModel;

    public RegistrationFragment() {
        registrationModel = new RegistrationModel();
        registrationModel.registerView(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, null);

        firstNameEditText = (EditText) view.findViewById(R.id.input_firstname);

        lastNameEditText = (EditText) view.findViewById(R.id.input_lastname);

        emailEditText = (EditText) view.findViewById(R.id.input_email);

        passwordEditText = (EditText) view.findViewById(R.id.input_password);

        confirmPassEditText = (EditText) view.findViewById(R.id.input_con_password);

        contactNoEditText = (EditText) view.findViewById(R.id.input_contact);

        submitButton = (Button) view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);
        progressBar =(ProgressBar) view.findViewById(R.id.loading_progressBar);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button: {
                validateAllCompulsoryField();
            }
            break;
        }
    }


    @Override
    public void updateView() {
        super.updateView();

        progressBar.setVisibility(View.INVISIBLE);
//        applicationController.showToastMessages(" Register Successfully");
        applicationController.handleEvent(AppEvents.EVENT_LOGIN_SCREEN);

     }

    private void validateAllCompulsoryField() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String conPassword = confirmPassEditText.getText().toString();
        String contactNo = contactNoEditText.getText().toString();

        if (firstName != null && !firstName.equalsIgnoreCase("")) {
            if (lastName != null && !lastName.equalsIgnoreCase("")) {
                if (email != null && !email.equalsIgnoreCase("")) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        if (password != null && !password.equalsIgnoreCase("")) {
                            if (conPassword != null && !conPassword.equalsIgnoreCase("")) {
                                if (password.equalsIgnoreCase(conPassword)) {
                                    if (contactNo != null && !contactNo.equalsIgnoreCase("")) {
                                        //applicationController.showToastMessages("All Field are vaild.");

                                        progressBar.setVisibility(View.VISIBLE);
                                        RegistrationBean bean = new RegistrationBean();
                                        bean.setEmailID(email);
                                        bean.setContactNo(contactNo);
                                        bean.setFirstName(firstName);
                                        bean.setLastNamer(lastName);
                                        bean.setPassword(CodeGeneratorController.getInstance().generatePassKey(password.toCharArray(),5));
                                        bean.setUsername(email);
                                        registrationModel.sendRegistrationRequest(bean);
                                       // applicationController.handleEvent(AppEvents.EVENT_PROVIDER_FRAGMENT);
                                    } else {
                                        applicationController.showToastMessages("Please enter Contact No");

                                    }
                                } else {
                                    applicationController.showToastMessages("Password Mismatch !");

                                }
                            } else {
                                applicationController.showToastMessages("Please enter Confirm Password");

                            }
                        } else {
                            applicationController.showToastMessages("Please enter Password");

                        }
                    } else {
                        applicationController.showToastMessages("Email Address is not valid");

                    }
                } else {
                    applicationController.showToastMessages("Please enter Email address");

                }
            } else {
                applicationController.showToastMessages("Please enter Last Name");

            }
        } else {
            applicationController.showToastMessages("Please enter First Name");
        }


    }


    @Override
    public void onErrorComes(String msg) {
        super.onErrorComes(msg);
        if(progressBar!=null)
            progressBar.setVisibility(View.INVISIBLE);
     //   "password":["This password is too short. It must contain at least 8 characters.","This password is too common.","This password is entirely numeric."]
        if(msg!=null)
        {
            applicationController.showToastMessages(msg);

        }
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
                        dialog.dismiss();
                    }
                    else {
                        applicationController.showToastMessages("Enter 4 number for your MPIN");
                    }

                }
            });


        }

        dialog.show();
    }
}
