package com.offshoreindian.lokdon.framework;


import com.offshoreindian.lokdon.fragmentUi.AWSFragment;
import com.offshoreindian.lokdon.fragmentUi.AddProviderFragment;
import com.offshoreindian.lokdon.fragmentUi.AzureFragment;
import com.offshoreindian.lokdon.fragmentUi.DropBoxFragment;
import com.offshoreindian.lokdon.fragmentUi.FavouritesFragment;
import com.offshoreindian.lokdon.fragmentUi.GoogleDriveFragment;
import com.offshoreindian.lokdon.fragmentUi.HelpFragment;
import com.offshoreindian.lokdon.fragmentUi.HomeFragment;
import com.offshoreindian.lokdon.fragmentUi.LoginFragment;
import com.offshoreindian.lokdon.fragmentUi.MessengerFragment;
import com.offshoreindian.lokdon.fragmentUi.ProviderFragment;
import com.offshoreindian.lokdon.fragmentUi.RegistrationFragment;
import com.offshoreindian.lokdon.fragmentUi.ScloudFragment;
import com.offshoreindian.lokdon.fragmentUi.SettingFragment;
import com.offshoreindian.lokdon.fragmentUi.ShowMessageFragment;
import com.offshoreindian.lokdon.fragmentUi.SplashFragment;
import com.offshoreindian.lokdon.fragmentUi.TermFragment;
import com.offshoreindian.lokdon.fragmentUi.UploadMenuFragment;
import com.offshoreindian.lokdon.fragmentUi.WebViewFragment;
import com.offshoreindian.lokdon.fragmentUi.WelcomeFragment;


public class FragmentFactory {

    private static FragmentFactory instance = null;
    public static final int SPALSH_FRAGMENT = 0;
    public static final int REGISTRATION_FRAGMENT = 1;
    public static final int LOGIN_FRAGMENT = 2;
    public static final int HOME_FRAGMENT = 3;
    public static final int MESSENGER_FRAGMENT = 4;
    public static final int SCLOUD_FREGMENT = 5;
    public static final int GOOGLE_DRIVE_FRAGMENT = 6;
    public static final int AWS_FRAGMENT = 7;
    public static final int AZURE_FRAGMENT = 8;
    public static final int SHOE_MESSAGE_FRAGMENT = 9;
    public static final int WEB_VIEW_FRAGMENT = 10;
    public static final int DROP_BOX_FRAGMENT = 11;
    public static final int PROVIDER_SCREEN = 12;
    public static final int ADD_PROVIDER_SCREEN = 13;
    public static final int WELCOME_SCREEN = 14;
    public static final int SETTING_FRAGMENT = 15;
    public static final int TERMS_FRAGMENT = 16;
    public static final int HELP_FRAGMENT = 17;
    public static final int FAVOURITE_FRAGMENT = 18;
    public static final int UPLOAD_FRAGMENT = 19;

    private SplashFragment splashFragment = null;
    private LoginFragment loginFragment = null;
    private RegistrationFragment registrationFragment = null;
    private HomeFragment homeFragment = null;
    private MessengerFragment messengerFragment = null;
    private ScloudFragment scloudFragment = null;
    private GoogleDriveFragment googleDriveFragment = null;
    private AWSFragment awsFragment = null;
    private AzureFragment azureFragment = null;
    private ShowMessageFragment showMessageFragment = null;
    private WebViewFragment webViewFragment = null;
    private DropBoxFragment dropBoxFragment = null;
    private ProviderFragment providerFragment = null;
    private AddProviderFragment addProviderFragment = null;
    private WelcomeFragment welcomeFragment = null;
    private SettingFragment settingFragment = null;
    private TermFragment termFragment = null;
    private HelpFragment helpFragment = null;
    private FavouritesFragment favouritesFragment = null;
    private UploadMenuFragment uploadMenuFragment = null;

    private FragmentFactory() {
    }

    public static FragmentFactory getInstance() {
        if (instance == null)
            instance = new FragmentFactory();
        return instance;
    }

    public BaseFragment getFragmentView(int viewId) {
        switch (viewId) {
            case SPALSH_FRAGMENT: {
                if (splashFragment == null)
                    splashFragment = new SplashFragment();
                return splashFragment;
            }
            case LOGIN_FRAGMENT: {
                if (loginFragment == null)
                    loginFragment = new LoginFragment();
                return loginFragment;
            }
            case REGISTRATION_FRAGMENT: {
                if (registrationFragment == null)
                    registrationFragment = new RegistrationFragment();
                return registrationFragment;
            }
            case HOME_FRAGMENT: {
                if (homeFragment == null)
                    homeFragment = new HomeFragment();
                return homeFragment;
            }
            case MESSENGER_FRAGMENT: {
                if (messengerFragment == null)
                    messengerFragment = new MessengerFragment();
                return messengerFragment;
            }
            case SCLOUD_FREGMENT: {
                if (scloudFragment == null)
                    scloudFragment = new ScloudFragment();
                return scloudFragment;
            }
            case GOOGLE_DRIVE_FRAGMENT: {
                if (googleDriveFragment == null)
                    googleDriveFragment = new GoogleDriveFragment();
                return googleDriveFragment;
            }
            case AWS_FRAGMENT: {
                if (awsFragment == null)
                    awsFragment = new AWSFragment();
                return awsFragment;
            }
            case AZURE_FRAGMENT: {
                if (azureFragment == null)
                    azureFragment = new AzureFragment();
                return azureFragment;
            }
            case SHOE_MESSAGE_FRAGMENT: {
                if (showMessageFragment == null)
                    showMessageFragment = new ShowMessageFragment();
                return showMessageFragment;
            }
            case WEB_VIEW_FRAGMENT: {
                if (webViewFragment == null)
                    webViewFragment = new WebViewFragment();
                return webViewFragment;
            }
            case DROP_BOX_FRAGMENT: {
                // if (dropBoxFragment == null)
                dropBoxFragment = new DropBoxFragment();
                return dropBoxFragment;
            }
            case PROVIDER_SCREEN: {
                if (providerFragment == null)
                    providerFragment = new ProviderFragment();
                return providerFragment;
            }
            case ADD_PROVIDER_SCREEN: {
                if (addProviderFragment == null)
                    addProviderFragment = new AddProviderFragment();
                return addProviderFragment;
            }
            case WELCOME_SCREEN: {
                if (welcomeFragment == null)
                    welcomeFragment = new WelcomeFragment();
                return welcomeFragment;
            }
            case SETTING_FRAGMENT: {
                if (settingFragment == null)
                    settingFragment = new SettingFragment();
                return settingFragment;
            }
            case TERMS_FRAGMENT: {
                if (termFragment == null)
                    termFragment = new TermFragment();
                return termFragment;
            }
            case HELP_FRAGMENT: {
                if (helpFragment == null)
                    helpFragment = new HelpFragment();
                return helpFragment;
            }
            case FAVOURITE_FRAGMENT: {
                if (favouritesFragment == null)
                    favouritesFragment = new FavouritesFragment();
                return favouritesFragment;
            }
            case UPLOAD_FRAGMENT: {
                if (uploadMenuFragment == null)
                    uploadMenuFragment = new UploadMenuFragment();
                return uploadMenuFragment;
            }
        }
        return null;
    }


    public void destroyView(int viewId) {
        switch (viewId) {
            case SPALSH_FRAGMENT: {
                if (splashFragment != null)
                    splashFragment.destroy();
            }
            break;
            case LOGIN_FRAGMENT: {
                if (loginFragment != null)
                    loginFragment.destroy();
            }
            break;
            case REGISTRATION_FRAGMENT: {
                if (registrationFragment != null)
                    registrationFragment.destroy();
            }
            break;
            case HOME_FRAGMENT: {
                if (homeFragment != null)
                    homeFragment.destroy();
            }
            break;
            case MESSENGER_FRAGMENT: {
                if (messengerFragment != null)
                    messengerFragment.destroy();
            }
            break;
            case SCLOUD_FREGMENT: {
                if (scloudFragment != null)
                    scloudFragment.destroy();
            }
            break;
            case GOOGLE_DRIVE_FRAGMENT: {
                if (googleDriveFragment != null)
                    googleDriveFragment.destroy();
            }
            break;
            case AWS_FRAGMENT: {
                if (awsFragment != null)
                    awsFragment.destroy();
            }
            break;
            case AZURE_FRAGMENT: {
                if (azureFragment != null)
                    azureFragment.destroy();
            }
            break;
            case SHOE_MESSAGE_FRAGMENT: {
                if (showMessageFragment != null)
                    showMessageFragment.destroy();
            }
            break;
            case WEB_VIEW_FRAGMENT: {
                if (webViewFragment != null)
                    webViewFragment.destroy();
            }
            break;
            case DROP_BOX_FRAGMENT: {
                if (dropBoxFragment != null)
                    dropBoxFragment.destroy();
            }
            break;
            case PROVIDER_SCREEN: {
                if (providerFragment != null)
                    providerFragment.destroy();
            }
            break;
            case ADD_PROVIDER_SCREEN: {
                if (addProviderFragment != null)
                    addProviderFragment.destroy();
            }
            break;
            case WELCOME_SCREEN: {
                if (welcomeFragment != null)
                    welcomeFragment.destroy();
            }
            break;
            case SETTING_FRAGMENT: {
                if (settingFragment != null)
                    settingFragment.destroy();
            }
            break;
            case TERMS_FRAGMENT: {
                if (termFragment != null)
                    termFragment.destroy();
            }
            break;
            case HELP_FRAGMENT: {
                if (helpFragment != null)
                    helpFragment.destroy();
            }
            break;
            case FAVOURITE_FRAGMENT: {
                if (favouritesFragment != null)
                    favouritesFragment.destroy();
            }
            break;
            case UPLOAD_FRAGMENT: {
                if (uploadMenuFragment != null)
                    uploadMenuFragment.destroy();
            }
            break;
        }
    }

    public void destroyAllView() {
        if (splashFragment != null)
            splashFragment.destroy();
        splashFragment = null;


        if (loginFragment != null)
            loginFragment.destroy();
        loginFragment = null;

        if (registrationFragment != null)
            registrationFragment.destroy();
        registrationFragment = null;

        if (homeFragment != null)
            homeFragment.destroy();
        homeFragment = null;


        if (messengerFragment != null)
            messengerFragment.destroy();
        messengerFragment = null;

        if (scloudFragment != null)
            scloudFragment.destroy();
        scloudFragment = null;


        if (googleDriveFragment != null)
            googleDriveFragment.destroy();
        googleDriveFragment = null;

        if (awsFragment != null)
            awsFragment.destroy();
        awsFragment = null;

        if (azureFragment != null)
            azureFragment.destroy();
        azureFragment = null;

        if (showMessageFragment != null)
            showMessageFragment.destroy();
        showMessageFragment = null;

        if (webViewFragment != null)
            webViewFragment.destroy();
        webViewFragment = null;

        if (dropBoxFragment != null)
            dropBoxFragment.destroy();
        dropBoxFragment = null;

        if (providerFragment != null)
            providerFragment.destroy();
        providerFragment = null;


        if (addProviderFragment != null)
            addProviderFragment.destroy();
        addProviderFragment = null;
        if (welcomeFragment != null)
            welcomeFragment.destroy();
        welcomeFragment = null;

        if (settingFragment != null)
            settingFragment.destroy();
        settingFragment = null;

        if (termFragment != null)
            termFragment.destroy();
        termFragment = null;

        if (helpFragment != null)
            helpFragment.destroy();
        helpFragment = null;
        if (favouritesFragment != null)
            favouritesFragment.destroy();
        favouritesFragment = null;

        if (uploadMenuFragment != null)
            uploadMenuFragment.destroy();
        uploadMenuFragment = null;
    }

}
