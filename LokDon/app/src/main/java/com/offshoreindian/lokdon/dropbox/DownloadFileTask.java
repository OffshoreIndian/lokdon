package com.offshoreindian.lokdon.dropbox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.offshoreindian.lokdon.encryption.AESEncryption;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Task to download a file from Dropbox and put it in the Downloads folder
 */
public class DownloadFileTask extends AsyncTask<FileMetadata, Void, File> {

    private final Context mContext;
    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;

    public interface Callback {
        void onDownloadComplete(File result);

        void onError(Exception e);
    }

    DownloadFileTask(Context context, DbxClientV2 dbxClient, Callback callback) {
        mContext = context;
        mDbxClient = dbxClient;
        mCallback = callback;
    }

    @Override
    protected void onPostExecute(File result) {
        super.onPostExecute(result);
        if (mException != null) {
            mCallback.onError(mException);
        } else {
            mCallback.onDownloadComplete(result);
        }
    }

    @Override
    protected File doInBackground(FileMetadata... params) {
        FileMetadata metadata = params[0];
        try {
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, metadata.getName());

            // Make sure the Downloads directory exists.
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    mException = new RuntimeException("Unable to create directory: " + path);
                }
            } else if (!path.isDirectory()) {
                mException = new IllegalStateException("Download path is not a directory: " + path);
                return null;
            }

            // Download the file.
            try {
                OutputStream outputStream = new FileOutputStream(file);
                mDbxClient.files().download(metadata.getPathLower(), metadata.getRev())
                        .download(outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            mContext.sendBroadcast(intent);
            // converting the file


            File fileTemp = new File(Environment.getExternalStorageDirectory() + "/" + metadata.getName());

            // if file doesnt exists, then create it
            if (!fileTemp.exists()) {
                fileTemp.createNewFile();
            } else {
                fileTemp.delete();
                fileTemp.createNewFile();

            }

            FileOutputStream fOut = new FileOutputStream(fileTemp, true);
            InputStream inputStream = new FileInputStream(file);
            byte fileContentTemp[] = new byte[(int) inputStream.available()];



            byte [] eyncArray = new byte[(int)inputStream.available()];
            inputStream.read(eyncArray);
            fOut.flush();
            //AESEncryption.getInstance().setKey(Constants.ENCRYPTION_KEY);
            //fOut.write( AESEncryption.getInstance().decryptByte(eyncArray));


            // Data Encryption Logic
            byte[] base64 = Base64.decode(eyncArray, Base64.DEFAULT);
            fOut.write( base64);
            DebugUtil.printLog(" Decode Data Data "+base64.toString());
            fOut.close();

            System.out.println("Done");

            InputStream inputStreamTemp = new FileInputStream(file);
            byte [] fileContent = new byte[(int)inputStreamTemp.available()];
            inputStreamTemp.read(fileContent);
            String strFileContent = new String(fileContent);

            DebugUtil.printLog(" strFileContent downloaded ====   "+strFileContent);

            // Tell android about the file
            Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent1.setData(Uri.fromFile(fileTemp));
            mContext.sendBroadcast(intent1);

/////////////// end of code


            return fileTemp;
        } catch (Exception e) {
            mException = e;
            e.printStackTrace();
        }

        return null;
    }



}
