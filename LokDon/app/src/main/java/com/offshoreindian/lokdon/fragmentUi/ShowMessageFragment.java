package com.offshoreindian.lokdon.fragmentUi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.adapter.InboxSmsListAdapter;
import com.offshoreindian.lokdon.adapter.ShowSmsListAdapter;
import com.offshoreindian.lokdon.bean.SmsBean;
import com.offshoreindian.lokdon.bean.SmsDataListBean;
import com.offshoreindian.lokdon.encryption.AESEncryption;
import com.offshoreindian.lokdon.encryption.SmsEncryption;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.AppEvents;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;
import java.util.Date;


public class ShowMessageFragment extends BaseFragment implements View.OnClickListener {

    private SmsDataListBean smsDataListBean;
    private ListView smsListView;
    private ShowSmsListAdapter showSmsListAdapter;
    private EditText messageBox;
    private Button sendMesButton;
//    String val ="The Congress questioned RBI governor Urjit Patel's role in the ban on Rs. 500 and 1,000 notes."
//            +
//            " New Delhi: Urjit Patel, who took charge of the Reserve Bank of India less than three months ago," +
//            " has found himself in the crosshairs of the Congress party's The Congress questioned RBI governor" +
//            " Urjit Patel's role in the ban on Rs. 500 and 1,000 notes. New Delhi: Urjit Patel, who took charge " +
//            "of the Reserve Bank of India less than three months ago, has found himself in the crosshairs of the" +
//            " Congress party's The Congress questioned RBI governor Urjit Patel's role in the ban on Rs. 500 and " +
//            "1,000 notes. New Delhi: Urjit Patel, who took charge of the Reserve Bank of India less than three " +
//            "months ago, has found himself in the crosshairs of the Congress party's The Congress questioned RBI " +
//            "governor Urjit Patel's role in the ban on Rs. 500 and 1,000 notes. New Delhi: Urjit Patel," +
//            " who took charge of the Reserve Bank of India less than three months ago, has found himself in " +
//            "the crosshairs of the Congress party's The Congress questioned RBI governor Urjit Patel's role in the" +
//            " ban on Rs. 500 and 1,000 notes. New Delhi: Urjit Patel, who took charge of the Reserve Bank of India " +
//            "less than three months ago, has found himself in the crosshairs of the Congress party's The Congress" +
//            " questioned RBI governor Urjit Patel's role in the ban on Rs. 500 and 1,000 notes. New Delhi: Urjit " +
//            "Patel, who took charge of the Reserve Bank of India less than three months ago, has found himself in " +
//            "the crosshairs of the Congress party's The Congress questioned RBI governor Urjit Patel's role in the ban on Rs." +
//            " 500 and 1,000 notes. New Delhi: Urjit Patel, who took charge of the Reserve Bank of India less than three " +
//            "months ago, has found himself in the crosshairs of the Congress party's ";
    public ShowMessageFragment() {

//        System.out.println("START  Time   "+new Date(System.currentTimeMillis()).toString());
//        AESEncryption.getInstance().setKey(Constants.ENCRYPTION_KEY);
//        String aes = AESEncryption.getInstance().encrypt(val);
//        System.out.println(" enc data "+AESEncryption.getInstance().getEncryptedString() );
//        String data = AESEncryption.getInstance().decrypt(AESEncryption.getInstance().getEncryptedString() );
//        System.out.println("des data "+AESEncryption.getInstance().getDecryptedString());
//        System.out.println("END  Time   "+new Date(System.currentTimeMillis()).toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_show_sms, null);
        applicationController.getActivity().showToolBar();
        if (getFragmentData() != null) {
            smsDataListBean = (SmsDataListBean) getFragmentData();
        }
        smsListView = (ListView) view.findViewById(R.id.show_sms_list_view);
        messageBox = (EditText) view.findViewById(R.id.sms_editbox);
        sendMesButton = (Button) view.findViewById(R.id.send_sms);
        sendMesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageBox.getText().toString();
                if (message == null)
                    message = "";

                SmsBean bean = new SmsBean();
                bean.setMsg(message);
                bean.setTime(System.currentTimeMillis() + "");
                bean.setFolderName("sent");
                bean.setReadState("0");
                bean.setAddress(smsDataListBean.getSmsBeanArrayList().get(0).getAddress());
                smsDataListBean.getSmsBeanArrayList().add(bean);
                showSmsListAdapter.notifyDataSetChanged();
                messageBox.setText("");
//
                AESEncryption.getInstance().setKey(Constants.ENCRYPTION_KEY);
                //AESEncryption.getInstance().encrypt(message);
                //sendSMSMessage(smsDataListBean.getSmsBeanArrayList().get(0).getAddress(), AESEncryption.getInstance().getEncryptedString() + Constants.MES_LOKDON_TEXT);
                String smsText = SmsEncryption.getInstance().getEncryptSms( message,smsDataListBean.getSmsBeanArrayList().get(0).getAddress());
                sendSMSMessage(smsDataListBean.getSmsBeanArrayList().get(0).getAddress(), smsText+ Constants.MES_LOKDON_TEXT);
                smsListView.setSelection(smsListView.getAdapter().getCount() - 1);

            }
        });

        if (showSmsListAdapter == null)
            showSmsListAdapter = new ShowSmsListAdapter(context, smsDataListBean.getSmsBeanArrayList());

        smsListView.setAdapter(showSmsListAdapter);
        showSmsListAdapter.notifyDataSetChanged();
        smsListView.setSelection(smsListView.getAdapter().getCount() - 1);


//        System.out.println("String to Encrypt: " + strToEncrypt);
//        System.out.println("Encrypted: " +  AESEncryption.getInstance().getEncryptedString());

//        final String strToDecrypt =  AESEncryption.getInstance().getEncryptedString();
//        AESEncryption.getInstance().decrypt(strToDecrypt.trim());
//
//        System.out.println("String To Decrypt : " + strToDecrypt);
//        System.out.println("Decrypted : " +  AESEncryption.getInstance().getDecryptedString());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    @Override
    public void updateView() {
        super.updateView();


    }

    protected void sendSMSMessage(String number, String text) {
        Log.i("Send SMS", "");
//        String phoneNo[] = {"+919552983335","+17635684836"};
        String phoneNo[] = {"+919552983335"};


        try {
            //for (int i = 0; i < number.length; i++)
            {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, text, null, null);
                Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showSmsListAdapter = null;
        //   smsDataListBean.destroy();
        // smsDataListBean = null;
    }

}
