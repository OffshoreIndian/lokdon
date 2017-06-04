package com.offshoreindian.lokdon.utils;

import com.offshoreindian.lokdon.encryption.AESEncryption;

/**
 * Created by praveshgupta on 17/02/16.
 */
public class Constants {
    public static String SUCCESS = "success";
    public static String LOKDON_PERFERENCE = "lokdon_preference";
    public static String KEY_USER_NAME = "username";
    public static String KEY_EMAIL_ID = "emailid";
    public static String KEY_PHONE_NO = "phoneNo";
    public static String KEY_PASSWORD = "password";// for now its a password
    public static String APP_EVENT_BROADCAST_ACTION = "com.offshoreindian.lokdon.appmatch";
    public static String IS_APP_EVENT_ACTION_DATA = "isAppEvent";
    public static String APP_EVENT_BROADCAST_ACTION_DATA = "packagename";
    public static String INTENT_APP_TYPE = "APP_TYPE";
    public static String APP_TYPE_MES = "MESSAGE";
    public static String suppotedAppPackageName[] = {
            "com.google.android.apps.messaging_off",
            "com.skype.raider_off",
            "com.whatsapp_off",
            "android.process.acore_off",// sony xperia -sp default mess
            "com.android.mms_off",//joe phone
            "com.lenovo.idiafriend_off"// Ravi Phone


    };


    public static String WELCOME_KEY = "WELCOME";

    public static String MES_LOKDON_TEXT = " Use LokDon";
    public static String ENCRYPTION_KEY = "lok";


    public static String DROP_BOX_APP_KEY = "iggrymyi0pwp46f";// id used for this praveshgupta1586@gmail.com
    public static String DROP_BOX_APP_SECRET_KEY = "ety6c4isiifjbx2";

    public static String DROP_BOX_PREFERENCE_ACCESS_TOKEN = "drop_box_access_token";


//    public static String standardCharacter[] =
//            {
//                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
//                    //  ,"a", "b", "c"
//                    //, "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
//                    , " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "-", "+", "=", "<", ">", ",", ".", "/", "?", ";", ":"
//                    , "'", "|", "~", "`", "{"
//                    //, "}", "[", "]"
////                    , "'", "|", "~", "`", "{", "}", "[", "]"
////                    , " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "-", "+", "=", "<", ">", ",", ".", "/", "?", ";", ":"
////                    , "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
////                    , "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
////                    , "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
////                    , "'", "|", "~", "`", "{", "}", "[", "]"
////                    , " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "-", "+", "=", "<", ">", ",", ".", "/", "?", ";", ":"
////                    ,   "X", "Y", "Z",
//
//
//            };



    public final static int NO_PROVIDER_ADD = -1;
    //public final static int PROVIDER_MESSENGER = 0;
    public final static int PROVIDER_DROPBOX = 0;
    public final static int PROVIDER_GOOGLE_DRIVE = 1;
    public final static int PROVIDER_ONE_DRIVE = 2;
    public final static int PROVIDER_ONE_DRIVE_FOR_BUSINESS = 3;
    public final static int PROVIDER_BOX = 4;
    public final static int PROVIDER_SUGARSYNC = 5;
//    public final static int PROVIDER_AMAZON_CLOUD_DRIVE = 7;
//    public final static int PROVIDER_AMAZON_S3 = 8;
    public final static int PROVIDER_TELEKOM_MAGENTACLOUD = 6;
    public final static int PROVIDER_STRATO_HIDRIVE = 7;
    public final static int PROVIDER_GMX_MEDIACENTER = 8;
    public final static int PROVIDER_WEB_DE_SMARTDRIVE = 9;
    public final static int PROVIDER_ORANGE_CLOUD = 10;
    public final static int PROVIDER_HUBIC = 11;
    public final static int PROVIDER_MAILBOX_ORG_DRIVE = 12;
    public final static int PROVIDER_CLOUDME =13;
    public final static int PROVIDER_GRAU_DATASPACE = 14;
    public final static int PROVIDER_STOREGATE = 15;
    public final static int PROVIDER_EGNYTE = 16;
    public final static int PROVIDER_CUBBY = 17;
    public final static int PROVIDER_PSMAIL_CABINET = 18;
    public final static int PROVIDER_LIVEDRIVE = 19;
    public final static int PROVIDER_YANDEX_DISK = 20;
    public final static int PROVIDER_WEBDAV_ADVANCED = 21;
    public final static int PROVIDER_LOCAL_STORAGE = 22;

    public static String provideName[] = {
          //  "Messenger",
            "Dropbox",
            "Google Drive",
            "One Drive",
            "One Drive for Business",
            "Box",
            "SugarSync",
//            "Amazon Cloud Drive",
//            "Amazon S3",
            "Telekom MagentaCloud",
            "Strato HiDrive",
            "GMX MediaCenter",
            "Web.de Smartdrive",
            "Orange Cloud",
            "hubiC",
            "mailbox.org Drive",
            "CloudMe",
            "Grau DataSpace",
            "Storegate",
            "Egnyte",
            "Cubby",
            "PSMail Cabinet",
            "Livedrive (Pro/Standard)",
            "Yandex Disk",
            "WebDAV Advanced",
            "Local Storage"
    };
    public static int provideTagId[] = {
           // PROVIDER_MESSENGER,
            PROVIDER_DROPBOX,
            PROVIDER_GOOGLE_DRIVE,
            PROVIDER_ONE_DRIVE,
            PROVIDER_ONE_DRIVE_FOR_BUSINESS,
            PROVIDER_BOX,
            PROVIDER_SUGARSYNC,
//            PROVIDER_AMAZON_CLOUD_DRIVE,
//            PROVIDER_AMAZON_S3,
            PROVIDER_TELEKOM_MAGENTACLOUD,
            PROVIDER_STRATO_HIDRIVE,
            PROVIDER_GMX_MEDIACENTER,
            PROVIDER_WEB_DE_SMARTDRIVE,
            PROVIDER_ORANGE_CLOUD,
            PROVIDER_HUBIC,
            PROVIDER_MAILBOX_ORG_DRIVE,
            PROVIDER_CLOUDME,
            PROVIDER_GRAU_DATASPACE,
            PROVIDER_STOREGATE,
            PROVIDER_EGNYTE,
            PROVIDER_CUBBY,
            PROVIDER_PSMAIL_CABINET,
            PROVIDER_LIVEDRIVE,
            PROVIDER_YANDEX_DISK,
            PROVIDER_WEBDAV_ADVANCED,
            PROVIDER_LOCAL_STORAGE,
    };


}
