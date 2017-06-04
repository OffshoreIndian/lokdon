package com.offshoreindian.lokdon.dropbox;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.offshoreindian.lokdon.encryption.AESEncryption;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;

/**
 * Async task to upload a file to a directory
 */
public class CreateFolderAndFileDropBox extends AsyncTask<String, Void, Boolean> {

    private final Context mContext;
    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;
    public static final int FOLDER_TYPE = 1;
    public static final int FILE_TYPE = 2;

    public interface Callback {
        void onFileFolderCreated();

        void onErrorFileFolder(Exception e);
    }

    public CreateFolderAndFileDropBox(Context context, DbxClientV2 dbxClient, Callback callback) {
        mContext = context;
        mDbxClient = dbxClient;
        mCallback = callback;
     }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (mException != null) {
            mCallback.onErrorFileFolder(mException);
        } else if (!result ) {
            mCallback.onErrorFileFolder(null);
        } else {
            mCallback.onFileFolderCreated();
        }
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String remoteFolderPath = params[0];
        String name = params[1];
        int type = Integer.parseInt(params[2]);

            // Note - this is not ensuring the name is a valid dropbox file name

            try {
            if(type == FOLDER_TYPE)
            {
                mDbxClient.files().createFolder(remoteFolderPath+"/"+name);

            }
            else
            {
                File file = new File(Environment.getExternalStorageDirectory() + "/" + name);

                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    file.delete();
                    file.createNewFile();

                }

                InputStream inputStreamUpload = new FileInputStream(file);
                 mDbxClient.files().uploadBuilder(remoteFolderPath + "/" + name)
                        .withMode(WriteMode.OVERWRITE)
                        .uploadAndFinish(inputStreamUpload);
            }


            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }




        return true;
    }

}
