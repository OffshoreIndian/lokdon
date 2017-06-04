package com.offshoreindian.lokdon.dropbox;

import com.dropbox.core.v2.files.ListFolderResult;

/**
 * Created by praveshkumar on 26/10/16.
 */


public interface DropBoxListCallBack {
    void onDataLoaded(ListFolderResult result);

    void onErrorDataLoaded(Exception e);
}