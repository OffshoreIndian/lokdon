package com.offshoreindian.lokdon.dropbox;

/**
 * Created by praveshkumar on 26/10/16.
 */

import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;

/**
 * Async task to list items in a folder
 */
public class ShowListFolderTask extends AsyncTask<String, Void, ListFolderResult> {

    private final DbxClientV2 mDbxClient;
    private final DropBoxListCallBack mCallback;
    private Exception mException;



    public ShowListFolderTask(DbxClientV2 dbxClient, DropBoxListCallBack callback) {
        mDbxClient = dbxClient;
        mCallback = callback;
    }

    @Override
    protected void onPostExecute(ListFolderResult result) {
        super.onPostExecute(result);

        if (mException != null) {
            mCallback.onErrorDataLoaded(mException);
        } else {
            mCallback.onDataLoaded(result);
        }
    }

    @Override
    protected ListFolderResult doInBackground(String... params) {
        try {
            return mDbxClient.files().listFolder(params[0]);
        } catch (DbxException e) {
            mException = e;
        }

        return null;
    }
}