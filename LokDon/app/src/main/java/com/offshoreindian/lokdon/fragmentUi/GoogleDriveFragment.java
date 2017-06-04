package com.offshoreindian.lokdon.fragmentUi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.activity.LokdonActivity;
import com.offshoreindian.lokdon.adapter.GoogleDriveFileListAdapter;
import com.offshoreindian.lokdon.bean.GoogleDriveFileBean;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;


public class GoogleDriveFragment extends BaseFragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions googleSignInOptions;
  //  private CallbackManager callbackManager;
    private boolean fileOperation = false;
    private DriveId mFileId;
    public DriveFile file;
    private static final int REQUEST_CODE_OPENER = 2;
    private int GOOGLE_LOGIN_REQUEST_CODE = 11;
    private TextView errorTextview;
    private ListView listView;
    private Button openFile, createFile;
    private ArrayList<GoogleDriveFileBean> driveFileBeanArrayList;
    private GoogleDriveFileListAdapter listAdapter;

    public GoogleDriveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google_drive, null);
        //applicationController.getActivity().showHomeScreenLogo();
        applicationController.getActivity().showToolBar();
        errorTextview = (TextView) view.findViewById(R.id.error_textview);
        errorTextview.setVisibility(View.INVISIBLE);

        listView = (ListView) view.findViewById(R.id.drive_list_view);
        listView.setVisibility(View.VISIBLE);


        openFile = (Button) view.findViewById(R.id.openfile);
        createFile = (Button) view.findViewById(R.id.createfile);


        openFile.setOnClickListener(this);
        createFile.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openfile: {
                onClickOpenFile(null);
            }
            break;
            case R.id.createfile: {
                onClickCreateFile(null);
            }
            break;

        }
    }


    @Override
    public void updateView() {
        super.updateView();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        if (!result.hasResolution()) {
            GoogleApiAvailability.getInstance().getErrorDialog(applicationController.getActivity(), result.getErrorCode(), 0).show();
            return;
        }
        try {
            result.startResolutionForResult(applicationController.getActivity(), GOOGLE_LOGIN_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {

            DebugUtil.printExceptionLog("Exception while starting resolution activity", e);
        }
        if (errorTextview != null) {
            errorTextview.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addScope(Drive.SCOPE_APPFOLDER)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }

        mGoogleApiClient.connect();


       // context.startActivity(new Intent(LokdonActivity.lokdonActivity, HomeActivity.class));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            // disconnect Google API client connection
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        applicationController.showToastMessages("Connected ");
        if (errorTextview != null) {
            errorTextview.setVisibility(View.INVISIBLE);
        }
        applicationController.putValueInPerference(Constants.PROVIDER_GOOGLE_DRIVE+"","TRUE");
        applicationController.getActivity().updateProviderListView();
        driveFileBeanArrayList = new ArrayList<GoogleDriveFileBean>();
        readAllFile();
    }

    /**
     * It invoked when connection suspend
     *
     * @param cause
     */
    @Override
    public void onConnectionSuspended(int cause) {
        applicationController.showToastMessages("Google onConnectionSuspended");
        if (errorTextview != null) {
            errorTextview.setVisibility(View.VISIBLE);
        }
    }


    public void onClickCreateFile(View view) {
        fileOperation = true;

        // create new contents resource
//        Drive.DriveApi.newDriveContents(mGoogleApiClient)
//                .setResultCallback(driveContentsCallback);


        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle("Lokdon_"+new Random().nextInt(500)).build();
        Drive.DriveApi.getRootFolder(mGoogleApiClient).createFolder(
                mGoogleApiClient, changeSet).setResultCallback(callback);
    }

    final ResultCallback<DriveFolder.DriveFolderResult> callback = new ResultCallback<DriveFolder.DriveFolderResult>() {
        @Override
        public void onResult(DriveFolder.DriveFolderResult result) {
            if (!result.getStatus().isSuccess()) {
                applicationController.showToastMessages("Error while trying to create the folder.");
                return;
            }
            DebugUtil.printLog(" Folder Id "+result.getDriveFolder().getDriveId());
            applicationController.showToastMessages("Created a folder: " + result.getDriveFolder().getDriveId());
        }
    };

    public void onClickOpenFile(View view) {
        fileOperation = false;

        // create new contents resource
        Drive.DriveApi.newDriveContents(mGoogleApiClient)
                .setResultCallback(driveContentsCallback);
    }

    /**
     * Open list of folder and file of the Google Drive
     */
    public void OpenFileFromGoogleDrive() {

        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
              //  .setMimeType(new String[]{"text/plain", "text/html"})
                .build(mGoogleApiClient);
        try {
            applicationController.getActivity().startIntentSenderForResult(intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);

        } catch (IntentSender.SendIntentException e) {

            DebugUtil.printExceptionLog("Unable to send intent", e);
        }

    }


    final private ResultCallback<DriveApi.DriveIdResult> idCallback = new ResultCallback<DriveApi.DriveIdResult>() {
        @Override
        public void onResult(DriveApi.DriveIdResult result) {
            if (!result.getStatus().isSuccess()) {
                applicationController.showToastMessages("Cannot find DriveId. Are you authorized to view this file?");
                return;
            }
            DriveId driveId = result.getDriveId();
            DriveFolder folder = driveId.asDriveFolder();
            folder.listChildren(mGoogleApiClient)
                    .setResultCallback(metadataResult);
        }
    };

    final private ResultCallback<DriveApi.MetadataBufferResult> metadataResult = new
            ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult result) {
                    if (!result.getStatus().isSuccess()) {
                        applicationController.showToastMessages("Problem while retrieving files");
                        return;
                    }
//                    mResultsAdapter.clear();
//                    mResultsAdapter.append(result.getMetadataBuffer());
                    for (Metadata m : result.getMetadataBuffer()) {
                        DebugUtil.printLog(" title " + m.getTitle() + "(" + (m.isFolder() ? "folder" : "file") + ")" + m.getFileExtension() + " ex " + m.getMimeType());
                        GoogleDriveFileBean bean = new GoogleDriveFileBean();
                        bean.setFileName(m.getTitle() + "." + m.getFileExtension());
                        bean.setFileSize(m.getFileSize() + "");
                        driveFileBeanArrayList.add(bean);
                        if (m.isFolder())
                            Drive.DriveApi.getFolder(mGoogleApiClient, m.getDriveId())
                                    .listChildren(mGoogleApiClient)
                                    .setResultCallback(fileCallback1);

//                        if (driveFileBeanArrayList.size() > 0) {
//                            if (listAdapter == null)
//                                listAdapter = new GoogleDriveFileListAdapter(context, driveFileBeanArrayList);
//                            listView.setAdapter(listAdapter);
//                            listAdapter.notifyDataSetChanged();
//                        }
                    }
                    applicationController.showToastMessages("Successfully listed files.");
                }
            };



    void search(String prnId, String titl, String mime) {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            // add query conditions, build query
            ArrayList<Filter> fltrs = new ArrayList<>();
            if (prnId != null){
                fltrs.add(Filters.in(SearchableField.PARENTS,
                        prnId.equalsIgnoreCase("root") ?
                                Drive.DriveApi.getRootFolder(mGoogleApiClient).getDriveId() : DriveId.decodeFromString(prnId)));
            }
            if (titl != null) fltrs.add(Filters.eq(SearchableField.TITLE, titl));
            if (mime != null) fltrs.add(Filters.eq(SearchableField.MIME_TYPE, mime));
            Query qry = new Query.Builder().addFilter(Filters.and(fltrs)).build();

            // fire the query
            Drive.DriveApi.query(mGoogleApiClient, qry).setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult rslt) {
                    if (rslt != null && rslt.getStatus().isSuccess()) {
                        MetadataBuffer mdb = null;
                        try {
                            mdb = rslt.getMetadataBuffer();
                            if (mdb != null ) for (Metadata md : mdb) {
                                if (md == null || !md.isDataValid()) continue;
                                String title = md.getTitle();
                                DriveId driveId = md.getDriveId();

                                DebugUtil.printLog(" Title : "+title);
                             }
                        } finally { if (mdb != null) mdb.close(); }
                    }
                }
            });
        }
    }
    public void readAllFile() {
        // create a file in root folder
        search(null,null,null);
//        DriveFolder folder = Drive.DriveApi.getRootFolder(mGoogleApiClient);
//        folder.listChildren(mGoogleApiClient).setResultCallback(rootFolderCallback);
//


//        DebugUtil.printLog("Drive ID : " + Drive.DriveApi.getRootFolder(mGoogleApiClient).getDriveId().toString());
//         DriveId sFolderId = DriveId.decodeFromString("DriveId:"+Drive.DriveApi.getRootFolder(mGoogleApiClient).getDriveId().toString());
//        DebugUtil.printLog("sFolderId ID : " +sFolderId);
        DriveFolder folder = Drive.DriveApi.getFolder(mGoogleApiClient,  Drive.DriveApi.getRootFolder(mGoogleApiClient).getDriveId());
         folder.listChildren(mGoogleApiClient).setResultCallback(childrenRetrievedCallback);
//
//        ;

//        Drive.DriveApi.fetchDriveId(mGoogleApiClient, sFolderId)
//                .setResultCallback(idCallback);
    }
    ResultCallback<DriveApi.MetadataBufferResult> childrenRetrievedCallback = new
            ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult result) {
                    if (!result.getStatus().isSuccess()) {
                        applicationController.showToastMessages("Problem while retrieving files");
                        return;
                    }
                    MetadataBuffer buffer = result.getMetadataBuffer();
                    for (Metadata m : buffer) {
                        DebugUtil.printLog(" title " + m.getTitle() + "(" + (m.isFolder() ? "folder" : "file") + ")" + m.getFileExtension() + " ex " + m.getMimeType());
                        GoogleDriveFileBean bean = new GoogleDriveFileBean();
                        bean.setFileName(m.getTitle() + "." + m.getFileExtension());
                        bean.setFileSize(m.getFileSize() + "");
                        driveFileBeanArrayList.add(bean);
//                        if (m.isFolder())
//                            Drive.DriveApi.getFolder(mGoogleApiClient, m.getDriveId())
//                                    .listChildren(mGoogleApiClient)
//                                    .setResultCallback(fileCallback1);
//
//                        if (driveFileBeanArrayList.size() > 0) {
//                            if (listAdapter == null)
//                                listAdapter = new GoogleDriveFileListAdapter(context, driveFileBeanArrayList);
//                            listView.setAdapter(listAdapter);
//                            listAdapter.notifyDataSetChanged();
//                        }
                    }

//                    mResultsAdapter.clear();
//                    mResultsAdapter.append(result.getMetadataBuffer());
                    applicationController.showToastMessages("Successfully listed files.");
                }
            };
    /**
     * This is Result result handler of Drive contents.
     * this callback method call CreateFileOnGoogleDrive() method
     * and also call OpenFileFromGoogleDrive() method, send intent onActivityResult() method to handle result.
     */
    final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {

                    if (result.getStatus().isSuccess()) {

                        if (fileOperation == true) {

                            CreateFileOnGoogleDrive(result);

                        } else {

                            OpenFileFromGoogleDrive();

                        }
                    }


                }
            };

    /**
     * Create a file in root folder using MetadataChangeSet object.
     *
     * @param result
     */
    public void CreateFileOnGoogleDrive(DriveApi.DriveContentsResult result) {


        final DriveContents driveContents = result.getDriveContents();

        // Perform I/O off the UI thread.
        new Thread() {
            @Override
            public void run() {
                // write content to DriveContents
                OutputStream outputStream = driveContents.getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream);
                try {
                    writer.write("Hello Lokdon!");
                    writer.close();
                } catch (IOException e) {
                    Log.e("Test", e.getMessage());
                }

                MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                        .setTitle("Lokdon file" + new Random().nextInt(200))
                        .setMimeType("mp3")
                        .setStarred(true).build();
                Drive.DriveApi.getRootFolder(mGoogleApiClient)
                        .createFile(mGoogleApiClient, changeSet, driveContents)
                        .setResultCallback(fileCallback);


            }
        }.start();
    }

    final private ResultCallback<DriveApi.MetadataBufferResult> rootFolderCallback =
            new ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult metadataBufferResult) {
                    MetadataBuffer buffer = metadataBufferResult.getMetadataBuffer();
                    for (Metadata m : buffer) {
                        DebugUtil.printLog(" title " + m.getTitle() + "(" + (m.isFolder() ? "folder" : "file") + ")" + m.getFileExtension() + " ex " + m.getMimeType());
                        GoogleDriveFileBean bean = new GoogleDriveFileBean();
                        bean.setFileName(m.getTitle() + "." + m.getFileExtension());
                        bean.setFileSize(m.getFileSize() + "");
                        driveFileBeanArrayList.add(bean);
                        if (m.isFolder())
                            Drive.DriveApi.getFolder(mGoogleApiClient, m.getDriveId())
                                    .listChildren(mGoogleApiClient)
                                    .setResultCallback(fileCallback1);

                        if (driveFileBeanArrayList.size() > 0) {
                            if (listAdapter == null)
                                listAdapter = new GoogleDriveFileListAdapter(context, driveFileBeanArrayList);
                            listView.setAdapter(listAdapter);
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }
            };


    final private ResultCallback<DriveApi.MetadataBufferResult> fileCallback1 =
            new ResultCallback<DriveApi.MetadataBufferResult>() {
                @Override
                public void onResult(DriveApi.MetadataBufferResult metadataBufferResult) {
                    MetadataBuffer buffer = metadataBufferResult.getMetadataBuffer();
                    //for(Metadata m : buffer){
                    DebugUtil.printLog("Buffer count  " + buffer.getCount());
                    for (int i = 0; i < buffer.getCount(); i++) {
                        Metadata m = buffer.get(i);
                        DebugUtil.printLog(m.toString());
                        Drive.DriveApi.getFile(mGoogleApiClient, m.getDriveId())
                                .open(mGoogleApiClient, DriveFile.MODE_READ_ONLY,
                                        new DriveFile.DownloadProgressListener() {
                                            @Override
                                            public void onProgress(long bytesDownloaded, long bytesExpected) {
                                                // Update progress dialog with the latest progress.
                                                int progress = (int) (bytesDownloaded * 100 / bytesExpected);
                                                Log.d("TAG", String.format("Loading progress: %d percent", progress));
                                            }
                                        }
                                )
                                .setResultCallback(contentsCallback);
                    }
                }
            };


    final private ResultCallback<DriveApi.DriveContentsResult> contentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult contentsResult) {
                    DebugUtil.printLog("got file contents");
                    File file = new File("storage/emulated/0/Downtests/tessing.txt");
                    file.mkdirs();
                    try {
                        InputStream input = contentsResult.getDriveContents().getInputStream();
                        OutputStream output = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = input.read(buf)) > 0) {
                            output.write(buf, 0, len);
                        }
                        input.close();
                        output.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            };

    /**
     * Handle result of Created file
     */
    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
            ResultCallback<DriveFolder.DriveFileResult>() {
                @Override
                public void onResult(DriveFolder.DriveFileResult result) {
                    if (result.getStatus().isSuccess()) {


                        applicationController.showToastMessages("file created: " + "" +
                                result.getDriveFile().getDriveId());
                    }

                    return;

                }
            };

    /**
     * Handle Response of selected file
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode, final Intent data) {
        switch (requestCode) {

            case REQUEST_CODE_OPENER:

                if (resultCode == Activity.RESULT_OK) {

                    mFileId = (DriveId) data.getParcelableExtra(
                            OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);

                    Log.e("file id", mFileId.getResourceId() + "");

                    String url = "https://drive.google.com/open?id=" + mFileId.getResourceId();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


}
