package com.offshoreindian.lokdon.fragmentUi;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.dropbox.CreateFolderAndFileDropBox;
import com.offshoreindian.lokdon.dropbox.DropBoxChildFragmet;
import com.offshoreindian.lokdon.dropbox.DropBoxListCallBack;
import com.offshoreindian.lokdon.dropbox.DropboxClientFactory;
import com.offshoreindian.lokdon.dropbox.FilesAdapter;
import com.offshoreindian.lokdon.dropbox.PicassoClient;
import com.offshoreindian.lokdon.dropbox.UploadDropBoxFileTask;
import com.offshoreindian.lokdon.framework.BaseFragment;
import com.offshoreindian.lokdon.framework.FragmentFactory;
import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.dropbox.ShowListFolderTask;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.text.DateFormat;
import java.util.ArrayList;


public class DropBoxFragment extends BaseFragment implements View.OnClickListener, UploadDropBoxFileTask.Callback, DropBoxListCallBack, CreateFolderAndFileDropBox.Callback {

    private WebView webView;
    private ProgressBar progressBar;
    private int REQUEST_LINK_TO_DBX = 10;  // This value is up to you
    public final static String EXTRA_PATH = "FilesActivity_Path";
    private static final int PICKFILE_REQUEST_CODE = 1;
    private ProgressDialog dialog, listDialog;
    private String mPath;
    private FilesAdapter mFilesAdapter;
    private FileMetadata mSelectedFile;
    private View view;
    private Dialog dropBoxDialog, createFolderPopup, createFilePopUp,longClickPopup;
    private LinearLayout uploadFileLayout, uploadPhotoLayout, uploadNewFolder, uploadNewFile,shareWithLayout,delete_layout,shareWithLayout_folder,delete_layout_folder;
    private Button create_folder_button, cancel_folder_button, create_file_button, cancel_file_button;
    private EditText input_folder_name, input_file_name;
    private ImageView cross_button;
    private ArrayList<DropBoxChildFragmet> dropBoxChildFragmets;
    private String path;
    private FolderMetadata deleteFolder;
    public DropBoxFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

          path = (String) getFragmentData();
        view = inflater.inflate(R.layout.fragment_dropbox, null);
        applicationController.getActivity().showToolBar();

        progressBar = (ProgressBar) view.findViewById(R.id.loading_progressBar);
        dropBoxChildFragmets = new ArrayList<DropBoxChildFragmet>();

        if (hasDropBoxToken()) {
//            initAndLoadData(applicationController.getValueFromPerference(Constants.DROP_BOX_PREFERENCE_ACCESS_TOKEN));
//            folderFromDropBox();

        } else {
            Auth.startOAuth2Authentication(context, Constants.DROP_BOX_APP_KEY);
        }


        return view;
    }

    public void folderFromDropBox() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.files_list);
        mFilesAdapter = new FilesAdapter(context, PicassoClient.getPicasso(), new FilesAdapter.Callback() {
            @Override
            public void onFolderClicked(FolderMetadata folder) {
                //startActivity(FilesActivity.getIntent(FilesActivity.this, folder.getPathLower()));
                addChildFragment(folder.getPathLower());
            }

            @Override
            public void onFileLongClick(FileMetadata file) {
               showFileLongClickPopUp(file);
            }

            @Override
            public void onFolderLongClick(FolderMetadata folderMetadata) {
                showFolderLongClickPopUp(folderMetadata);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mFilesAdapter);
        loadDropBoxData();

    }


    public void addChildFragment(String path) {
       // FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //DropBoxChildFragmet frag=     new DropBoxChildFragmet(path);
            applicationController.addFragment(FragmentFactory.DROP_BOX_FRAGMENT,path,true);
//        transaction.replace(R.id.drop_box_layout,frag , "test");
//        if(dropBoxChildFragmets.size()>0)
//        transaction.addToBackStack(frag.getClass().getName());
//        transaction.commit();
//        dropBoxChildFragmets.add(frag);
//        getChildFragmentManager().executePendingTransactions();
    }


    @Override
    public void onResume() {
        super.onResume();

        String accessToken = applicationController.getValueFromPerference(Constants.DROP_BOX_PREFERENCE_ACCESS_TOKEN);
        if (accessToken == null) {
            accessToken = Auth.getOAuth2Token();
            if (accessToken != null) {
                applicationController.putValueInPerference(Constants.DROP_BOX_PREFERENCE_ACCESS_TOKEN, accessToken);
                applicationController.putValueInPerference(Constants.PROVIDER_DROPBOX + "", accessToken);
                applicationController.getActivity().updateProviderListView();
                DebugUtil.printLog("DROP BOX " + applicationController.getValueFromPerference(Constants.PROVIDER_DROPBOX + ""));

                initAndLoadData(accessToken);
                folderFromDropBox();
            }
        } else {
            initAndLoadData(accessToken);
            folderFromDropBox();

        }
    }

    private void initAndLoadData(String accessToken) {
        DropboxClientFactory.init(accessToken);
        PicassoClient.init(applicationController.getActivity().getApplicationContext(), DropboxClientFactory.getClient());
        // loadData();
    }


    private void loadDropBoxData() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.show();


        new ShowListFolderTask(DropboxClientFactory.getClient(), this).execute(path);
    }

    @Override
    public void updateView() {
        super.updateView();
    }


    @Override
    public boolean handleFloatingButtonClick() {
        DebugUtil.printLog("handleFloatingButtonClick handleFloatingButtonClick");

         if(dropBoxChildFragmets.size() >0)
         {
             dropBoxChildFragmets.get(dropBoxChildFragmets.size()-1).handleFloatingButtonClick();
         }
        else
         {
             showDropBoxPopUp();
             applicationController.showToastMessages("Folder "+path);

         }

        return true;


    }

    protected boolean hasDropBoxToken() {
        String accessToken = applicationController.getValueFromPerference(Constants.DROP_BOX_PREFERENCE_ACCESS_TOKEN);
        return accessToken != null;
    }

//    @Override
//    public boolean onBackKeyPressed() {
//         DebugUtil.printLog(" getChildFragmentManager().getBackStackEntryCount()  " + getChildFragmentManager().getBackStackEntryCount());
//        if (dropBoxChildFragmets.size() > 0) {
//            // Get the fragment fragment manager - and pop the backstack
//            dropBoxChildFragmets.get(dropBoxChildFragmets.size()-1).onBackKeyPressed();
//            getChildFragmentManager().popBackStack();
//            return true;
//        }
//        // Else, nothing in the direct fragment back stack
//        else {
//            // Let super handle the back press
//            return super.onBackKeyPressed();
//        }
//    }


    private void launchFilePicker() {
        // Launch intent to pick file for upload
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKFILE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                // This is the result of a call to launchFilePicker
                uploadFile(data.getData().toString());
            }
        }
    }


    private void uploadFile(String fileUri) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Uploading");
        dialog.show();

        new UploadDropBoxFileTask(context, DropboxClientFactory.getClient(), this).execute(fileUri, path);
    }


    @Override
    public void onUploadComplete(FileMetadata result) {
        if (dialog != null)
            dialog.dismiss();
        String message = result.getName() + " size " + result.getSize() + " modified " +
                DateFormat.getDateTimeInstance().format(result.getClientModified());
        applicationController.showToastMessages("File uploaded successfully");

        // Reload the folder
        folderFromDropBox();
    }

    @Override
    public void onError(Exception e) {
        if (dialog != null)
            dialog.dismiss();

        applicationController.showToastMessages("An error has occurred to upload the file");
    }

    @Override
    public void onDataLoaded(ListFolderResult result) {
        if (dialog != null)
            dialog.dismiss();

        if (mFilesAdapter != null)
            mFilesAdapter.setFiles(result.getEntries());
    }

    @Override
    public void onErrorDataLoaded(Exception e) {
        if (dialog != null)
            dialog.dismiss();
        e.printStackTrace();
        applicationController.showToastMessages("An error has occurred");
    }


    private void showDropBoxPopUp() {

        if (dropBoxDialog == null) {
            dropBoxDialog = new Dialog(context, R.style.Dialog_theme);
            dropBoxDialog.setCancelable(true);
            dropBoxDialog.setContentView(R.layout.dropbox_popup);
            Drawable d = new ColorDrawable(Color.BLACK);
            d.setAlpha(130);
            //dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dropBoxDialog.getWindow().setBackgroundDrawable(d);
            // dropBoxDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            uploadFileLayout = (LinearLayout) dropBoxDialog.findViewById(R.id.uploadFileLayout);
            uploadFileLayout.setOnClickListener(this);

            uploadPhotoLayout = (LinearLayout) dropBoxDialog.findViewById(R.id.uploadPhotoLayout);
            uploadPhotoLayout.setOnClickListener(this);

            uploadNewFolder = (LinearLayout) dropBoxDialog.findViewById(R.id.uploadNewFolder);
            uploadNewFolder.setOnClickListener(this);

            uploadNewFile = (LinearLayout) dropBoxDialog.findViewById(R.id.uploadNewFile);
            uploadNewFile.setOnClickListener(this);


            cross_button = (ImageView) dropBoxDialog.findViewById(R.id.cross_button);
            cross_button.setOnClickListener(this);

        }

        dropBoxDialog.show();

    }


    private void showCreateFolderPopup() {

        if (createFolderPopup == null) {
            createFolderPopup = new Dialog(context, R.style.Dialog_theme);
            createFolderPopup.setCancelable(true);
            createFolderPopup.setContentView(R.layout.drop_create_folder);
            Drawable d = new ColorDrawable(Color.BLACK);
            d.setAlpha(130);
            //dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            createFolderPopup.getWindow().setBackgroundDrawable(d);
            // dropBoxDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            input_folder_name = (EditText) createFolderPopup.findViewById(R.id.input_folder_name);

            create_folder_button = (Button) createFolderPopup.findViewById(R.id.create_folder_button);
            create_folder_button.setOnClickListener(this);

            cancel_folder_button = (Button) createFolderPopup.findViewById(R.id.cancel_folder_button);
            cancel_folder_button.setOnClickListener(this);

            cross_button = (ImageView) createFolderPopup.findViewById(R.id.cross_button);
            cross_button.setOnClickListener(this);

        }

        createFolderPopup.show();

    }


    private void showCreateNewFilePopup() {

        if (createFilePopUp == null) {
            createFilePopUp = new Dialog(context, R.style.Dialog_theme);
            createFilePopUp.setCancelable(true);
            createFilePopUp.setContentView(R.layout.create_file_popup);
            Drawable d = new ColorDrawable(Color.BLACK);
            d.setAlpha(130);
            //dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            createFilePopUp.getWindow().setBackgroundDrawable(d);
            // dropBoxDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            input_file_name = (EditText) createFilePopUp.findViewById(R.id.input_file_name);

            create_file_button = (Button) createFilePopUp.findViewById(R.id.create_file_button);
            create_file_button.setOnClickListener(this);

            cancel_file_button = (Button) createFilePopUp.findViewById(R.id.cancel_file_button);
            cancel_file_button.setOnClickListener(this);

            cross_button = (ImageView) createFilePopUp.findViewById(R.id.cross_button);
            cross_button.setOnClickListener(this);

        }

        createFilePopUp.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.uploadFileLayout: {
                launchFilePicker();
                if (dropBoxDialog != null)
                    dropBoxDialog.dismiss();
            }
            break;
            case R.id.uploadNewFolder: {
                if (dropBoxDialog != null)
                    dropBoxDialog.dismiss();
                showCreateFolderPopup();
            }
            break;
            case R.id.uploadNewFile: {
                if (dropBoxDialog != null)
                    dropBoxDialog.dismiss();
                showCreateNewFilePopup();
            }
            break;
            case R.id.create_folder_button: {
                final String folderName = input_folder_name.getText().toString();
                if (folderName != null && !folderName.equalsIgnoreCase("")) {
                    try {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                        dialog = new ProgressDialog(context);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setCancelable(false);
                        dialog.setMessage("Creating Folder");
                        dialog.show();
                        new CreateFolderAndFileDropBox(context, DropboxClientFactory.getClient(), this).execute(path, folderName, CreateFolderAndFileDropBox.FOLDER_TYPE + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (createFolderPopup != null) {
                        createFolderPopup.dismiss();
                    }
                } else {
                    applicationController.showToastMessages(" Enter folder name");
                }
            }
            break;
            case R.id.cancel_folder_button: {
                if (createFolderPopup != null) {
                    createFolderPopup.dismiss();
                }
            }
            break;
            case R.id.cross_button: {
                if (createFolderPopup != null) {
                    createFolderPopup.dismiss();
                }

                if (dropBoxDialog != null) {
                    dropBoxDialog.dismiss();
                }
                if (createFilePopUp != null) {
                    createFilePopUp.dismiss();
                }
                if (longClickPopup != null) {
                    longClickPopup.dismiss();
                }
            }
            break;
            case R.id.create_file_button: {
                final String fileName = input_file_name.getText().toString();
                if (fileName != null && !fileName.equalsIgnoreCase("")) {
                    try {
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                        dialog = new ProgressDialog(context);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setCancelable(false);
                        dialog.setMessage("Creating File");
                        dialog.show();
                        new CreateFolderAndFileDropBox(context, DropboxClientFactory.getClient(), this).execute(path, fileName, CreateFolderAndFileDropBox.FILE_TYPE + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (createFilePopUp != null) {
                        createFilePopUp.dismiss();
                    }
                } else {
                    applicationController.showToastMessages(" Enter folder name");
                }
            }
            break;
            case R.id.cancel_file_button: {
                if (createFilePopUp != null) {
                    createFilePopUp.dismiss();
                }
            }
            break;
            case R.id.share_with: {
                applicationController.showToastMessages("Long Share");
                if (longClickPopup != null) {
                    longClickPopup.dismiss();
                }
            }
            break;
            case R.id.delete_layout: {
                applicationController.showToastMessages("Long Delete");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                            DbxClientV2 dbxClient =    DropboxClientFactory.getClient();
                            dbxClient.files().delete(delete_file.getPathDisplay());
                               applicationController.getActivity().runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       folderFromDropBox();
                                   }
                               });


                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }).start();




               // dbxClient.files().delete(deleteFolder.)
                if (longClickPopup != null) {
                    longClickPopup.dismiss();
                }
            }
            break;
            case R.id.delete_layout_folder: {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DbxClientV2 dbxClient =    DropboxClientFactory.getClient();
                            dbxClient.files().delete(deleteFolder.getPathDisplay());

                           // dbxClient.sharing().addFileMember(deleteFolder.getPathDisplay(),)
                            applicationController.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    folderFromDropBox();
                                }
                            });


                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();




                // dbxClient.files().delete(deleteFolder.)
                if (longClickPopup != null) {
                    longClickPopup.dismiss();
                }
            }
            break;
        }
    }


    @Override
    public void onFileFolderCreated() {
        applicationController.showToastMessages("Folder Created");
        if (dialog != null)
            dialog.dismiss();

        // Reload the folder
        folderFromDropBox();
    }

    @Override
    public void onErrorFileFolder(Exception e) {
        applicationController.showToastMessages("Problem to create folder");
        if (dialog != null)
            dialog.dismiss();

    }

    FileMetadata delete_file;
    private void showFileLongClickPopUp(FileMetadata file) {

        if (longClickPopup == null) {
            longClickPopup = new Dialog(context, R.style.Dialog_theme);
            longClickPopup.setCancelable(true);
            longClickPopup.setContentView(R.layout.dropbox_long_click_popup);
            Drawable d = new ColorDrawable(Color.BLACK);
            d.setAlpha(130);
            //dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            longClickPopup.getWindow().setBackgroundDrawable(d);
            // dropBoxDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            shareWithLayout = (LinearLayout) longClickPopup.findViewById(R.id.share_with);
            shareWithLayout.setOnClickListener(this);

            delete_layout = (LinearLayout) longClickPopup.findViewById(R.id.delete_layout);
            delete_layout.setOnClickListener(this);

            delete_file = file;

            cross_button = (ImageView) longClickPopup.findViewById(R.id.cross_button);
            cross_button.setOnClickListener(this);

        }

        longClickPopup.show();

    }

    private void showFolderLongClickPopUp(FolderMetadata file) {


        if (longClickPopup == null) {
            longClickPopup = new Dialog(context, R.style.Dialog_theme);
            longClickPopup.setCancelable(true);
            longClickPopup.setContentView(R.layout.dropbox_folder_long_click_popup);
            Drawable d = new ColorDrawable(Color.BLACK);
            d.setAlpha(130);
            //dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            longClickPopup.getWindow().setBackgroundDrawable(d);
            // dropBoxDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            shareWithLayout_folder = (LinearLayout) longClickPopup.findViewById(R.id.share_with_folder);
            shareWithLayout_folder.setOnClickListener(this);

            delete_layout_folder = (LinearLayout) longClickPopup.findViewById(R.id.delete_layout_folder);
            delete_layout_folder.setOnClickListener(this);

            deleteFolder = file;

            cross_button = (ImageView) longClickPopup.findViewById(R.id.cross_button);
            cross_button.setOnClickListener(this);

        }

        longClickPopup.show();

    }
}