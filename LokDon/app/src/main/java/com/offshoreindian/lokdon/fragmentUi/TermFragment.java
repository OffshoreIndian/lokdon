package com.offshoreindian.lokdon.fragmentUi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.DebugUtil;


public class TermFragment extends BaseFragment implements View.OnClickListener {

     public TermFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_terms, null);
        TextView termTv =(TextView) view.findViewById(R.id.term_tv);
        termTv.setText(Html.fromHtml(text));
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


    String text = "<b>LokDon Messaging Terms of Service</b><br>\n" +
            "\nBy using the LokDon.com Messaging Suite (\"Service\"), you are agreeing to be bound by the following terms and conditions (\"Terms of Service\").\n" +
            "LokDon.com reserves the right to update and change the Terms of Service from time to time without notice. Any new features that augment or enhance the current Service, including the release of new tools and resources, shall be subject to the Terms of Service. Continued use of the Service after any such changes shall constitute your consent to such changes. You can review the most current version of the Terms of Service at any time at: HYPERLINK \"http://www.lokdon.com/terms\" http://www.lokdon.com/terms\n" +
            "<br><br><b>Account Terms</b><br>\n" +
            "In order to be a System User, you must be 18 years or older.\n" +
            "No \"bot\" or automated registration is permitted.\n" +
            "\nYou must provide your legal full legal name or businesses name, a valid email address, valid phone number and any other information requested in order to complete the signup process.\n" +
            "Only persons that have properly been added within the application may use your account. Changes to the number of users permitted on an account are up to the discretion of LokDon.com's management.\n" +
            "You are responsible for maintaining the security of your account and password. LokDon.com cannot and will not be liable for any loss or damage from your failure to comply with this security obligation.\n" +
            "You are responsible for all Content posted or distributed and activity that occurs under your account.\n" +
            "SPAM and SPAM-like messages are strictly prohibited. Accounts used for this purpose will be immediately terminated and account holder permanently banned. In states where there is active legislation, all account information will be provided to appropriate law enforcement agencies.\n" +
            "One person or legal entity may not maintain more than one free account.\n" +
            "You may not use the Service for any illegal or unauthorized purpose. You must not, in the use of the Service, violate any laws in your jurisdiction (including but not limited to copyright laws).\n" +
            "<br><br><b>SMS Program</b><br>\n" +
            "LokDon.com offers their customers mobile access to their messages over SMS. Enrollment requires the recipient providing a mobile phone number. The mobile phone number's verification is done by responding to a confirmation message upon sign up. Message frequency may vary. Message & data rates may apply. Customers will be allowed to opt out of this program at any time.\n" +
            "<br><br><b>Support:</b><br>\n" +
            "For support, call (763) 291-6314\n" +
            "<br><br><b>Questions:</b><br>\n" +
            "You can contact us at  HYPERLINK \"mailto:support@txtsignal.com\" support@LokDon.com, or at any time from your mobile phone, text \"HELP\" to: 84033. We can answer any questions you have about the program.\n" +
            "<br><br><b>To Stop The Program:</b><br>\n" +
            "To stop the messages coming to your phone, you can opt out of the program via SMS. Just text the word \"STOP\" to: 84033. You'll receive a one-time opt-out confirmation text message. After that, you will receive no future messages.\n" +
            "<br><br><b>Terms & Conditions:</b><br>\n" +
            "By participating in the Company Mobile program, you are agreeing to the terms and conditions presented here. To view the Privacy Policy,  HYPERLINK \"http://secure.txtsignal.com/terms/privacy\" click here.\n" +
            "<br><br><b>Carriers Supported:</b><br>\n" +
            "AT&T, Sprint, Nextel, Boost, Verizon Wireless, U.S. Cellular®, T-Mobile®, Cincinnati Bell, Alltel, Virgin Mobile USA, Cellular South, Unicell, Centennial, Ntelos\n" +
            "Violation of any of these agreements will result in the termination of your Account. While LokDon.com prohibits such conduct and Content on the Service, you understand and agree that LokDon.com cannot be responsible for the Content distributed through the Service. You agree to use the Service at your own risk.\n" +
            "Payment, Refunds, Upgrading and Downgrading Terms\n" +
            "A valid credit card is required for paying accounts. Free trial period accounts are not required to provide a credit card number. Paper billing is available at an additional cost.\n" +
            "The Service is billed in advance on a monthly basis and is non-refundable. There will be no refunds or credits for partial months of service, or refunds for months unused with an open account. In order to treat everyone equally, no exceptions will be made.\n" +
            "All fees are exclusive of all taxes, levies, or duties imposed by taxing authorities, and you shall be responsible for payment of all such taxes, levies, or duties, excluding only United States (federal or state) taxes.\n" +
            "For any upgrade or downgrade in plan level, your credit card that you provided will automatically be charged the new rate on your next billing cycle. Any additional fees are based on usage and including in the immediate month following use.\n" +
            "Downgrading your Service may cause the loss of Content or capacity of your Account. LokDon.com does not accept any liability for such loss.\n" +
            "<br><br><b>Cancellation and Termination<br></b>\n" +
            "You are solely responsible for properly canceling your account. An email or phone request to cancel your account is not considered cancellation. You can cancel your account at any time by clicking on the Account group and clicking \"Delete Account\". The Account screen provides a simple, no questions asked two-click instant cancellation process.\n" +
            "All of your Content will be immediately deleted from the Service upon cancellation.\n" +
            "If you cancel the Service before the end of your current paid up month, your cancellation will take effect immediately but you will not be charged again.\n" +
            "LokDon.com, in its sole discretion, has the right to suspend or terminate your account and refuse any and all current or future use of the Service, or any other LokDon.com service, for any reason at any time. Such termination of the Service will result in the deactivation or deletion of your Account or your access to your Account, and the forfeiture and relinquishment of all content in your Account. LokDon.com reserves the right to refuse service to anyone for any reason at any time.\n" +
            "<br><b><br>Modifications to the Service and Prices<br></b>\n" +
            "LokDon.com reserves the right at any time and from time to time to modify or discontinue, temporarily or permanently, the Service (or any part thereof) with or without notice.\n" +
            "Prices of all Services, including but not limited to monthly subscription plan fees to the Service, are subject to change upon 30 days notice from us. Such notice may be provided at any time by posting the changes to the LokDon Signal web site ( HYPERLINK \"http://www.LokDon.com/prices.html\" http://www.LokDon.com/prices.html) or the Service itself.\n" +
            "LokDon.com shall not be liable to you or to any third party for any modification, price change, suspension or discontinuance of the Service.\n" +
            "<br><b><br>Copyright and Content Ownership<br></b>\n" +
            "We claim no intellectual property rights over the material you provide to the Service. Your profile and materials uploaded remain yours.\n" +
            "LokDon.com does not pre-screen content, but LokDon.com and its designee have the right (but not the obligation) in their sole discretion to refuse or remove any Content that is available via the Service.\n" +
            "<br><br><b>General Conditions<br></b>\n" +
            "Your use of the Service is at your sole risk. The service is provided on an \"as is\" and \"as available\" basis.\n" +
            "You must not modify, adapt or hack the Service or modify another web site so as to falsely imply that it is associated with the Service, LokDon.com, or any other LokDon.com service.\n" +
            "You agree not to reproduce, duplicate, copy, sell, resell or exploit any portion of the Service, use of the Service, or access to the Service without the express written permission of LokDon.com.\n" +
            "We may, but have no obligation to, remove Accounts containing or that have distributed Content that we determine in our sole discretion are unlawful, offensive, threatening, libelous, defamatory, pornographic, obscene or otherwise objectionable or violates any party's intellectual property or these Terms of Service.\n" +
            "Verbal, physical, written or other abuse (including threats of abuse or retribution) of any LokDon.com customer, employee, member, or officer will result in immediate account termination.\n" +
            "You understand that the technical processing and transmission of the Service, including your Content, may be transferred unencrypted and involve (a) transmissions over various networks; and (b) changes to conform and adapt to technical requirements of connecting networks or devices.\n" +
            "You must not distribute SPAM-like messages. Period. No discussion.\n" +
            "You must not transmit any worms or viruses or any code of a destructive nature.\n" +
            "If your bandwidth usage exceeds 250 MB/month, or significantly exceeds the average bandwidth usage (as determined solely by LokDon.com) of other LokDon.com Messaging Suite customers, we reserve the right to immediately disable your account or take other measures to reduce your bandwidth consumption.\n" +
            "LokDon.com does not warrant that (i) the service will meet your specific requirements, (ii) the service will be uninterrupted, timely, secure, or error-free, (iii) the results that may be obtained from the use of the service will be accurate or reliable, (iv) the quality of any products, services, information, or other material purchased or obtained by you through the service will meet your expectations, and (v) any errors in the Service will be corrected.\n" +
            "You expressly understand and agree that LokDon.com shall not be liable for any direct, indirect, incidental, special, consequential or exemplary damages, including but not limited to, damages for loss of profits, goodwill, use, data or other intangible losses (even if LokDon.com has been advised of the possibility of such damages), resulting from: (i) the use or the inability to use the service; (ii) the cost of procurement of substitute goods and services resulting from any goods, data, information or services purchased or obtained or messages received or transactions entered into through or from the service; (iii) unauthorized access to or alteration of your transmissions or data; (iv) statements or conduct of any third party on the service; (v) termination of your account; or (vi) any other matter relating to the service.\n" +
            "The failure of LokDon.com to exercise or enforce any right or provision of the Terms of Service shall not constitute a waiver of such right or provision. The Terms of Service constitutes the entire agreement between you and LokDon.com and govern your use of the Service, superseding any prior agreements between you and LokDon.com (including, but not limited to, any prior versions of the Terms of Service).\n" +
            "Questions about the Terms of Service should be sent to  HYPERLINK \"mailto:support@txtsignal.com\" support@LokDon.com\n" +
            "\n ";


}
