package com.offshoreindian.lokdon.dropbox;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.offshoreindian.lokdon.encryption.AESEncryption;
import com.offshoreindian.lokdon.framework.ApplicationController;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Async task to upload a file to a directory
 */
public class UploadDropBoxFileTask extends AsyncTask<String, Void, FileMetadata> {

    private final Context mContext;
    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;

    public interface Callback {
        void onUploadComplete(FileMetadata result);

        void onError(Exception e);
    }

    public UploadDropBoxFileTask(Context context, DbxClientV2 dbxClient, Callback callback) {
        mContext = context;
        mDbxClient = dbxClient;
        mCallback = callback;

    }

    @Override
    protected void onPostExecute(FileMetadata result) {
        super.onPostExecute(result);
        if (mException != null) {
            mCallback.onError(mException);
        } else if (result == null) {
            mCallback.onError(null);
        } else {
            mCallback.onUploadComplete(result);
        }
    }

    @Override
    protected FileMetadata doInBackground(String... params) {
        String localUri = params[0];
        File localFile = UriHelpers.getFileForUri(mContext, Uri.parse(localUri));

        if (localFile != null) {
            String remoteFolderPath = params[1];

            // Note - this is not ensuring the name is a valid dropbox file name
            String remoteFileName = localFile.getName();
            try {

                File file = new File(Environment.getExternalStorageDirectory() + "/" + remoteFileName);

                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    file.delete();
                    file.createNewFile();

                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);


                InputStream inputStream = new FileInputStream(localFile);
                byte fileContent[] = new byte[(int) inputStream.available()];


                DebugUtil.printLog((int) inputStream.available() + " file size " + inputStream.available());
                FileOutputStream fOut = new FileOutputStream(file);
                fOut.flush();


                byte[] eyncArray = new byte[(int) inputStream.available()];
                inputStream.read(eyncArray);
                fOut.flush();
              //  AESEncryption.getInstance().setKey(Constants.ENCRYPTION_KEY);
                //fOut.write(AESEncryption.getInstance().encryptByte(eyncArray));

                 // Data Encryption Logic
                byte[] base64 = Base64.encode(eyncArray, Base64.DEFAULT);
                DebugUtil.printLog(" Encoded Data "+base64.toString());
                fOut.write(base64);
                fOut.close();

                InputStream inputStreamTemp = new FileInputStream(file);

                inputStreamTemp.read(fileContent);
                String strFileContent = new String(fileContent);

                DebugUtil.printLog(" strFileContent upload ====   " + strFileContent);


                System.out.println("Done");

                InputStream inputStreamUpload = new FileInputStream(file);
                return mDbxClient.files().uploadBuilder(remoteFolderPath + "/" + remoteFileName)
                        .withMode(WriteMode.OVERWRITE)
                        .uploadAndFinish(inputStreamUpload);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return null;
    }

}
