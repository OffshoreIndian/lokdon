package com.offshoreindian.lokdon.dropbox;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.offshoreindian.lokdon.R;
import com.offshoreindian.lokdon.framework.ApplicationController;
import com.offshoreindian.lokdon.utils.DebugUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter for file list
 */
public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.MetadataViewHolder> {
    private List<Metadata> mFiles;
    private final Picasso mPicasso;
    private final Callback mCallback;
    private Context context;

    public void setFiles(List<Metadata> files) {
        mFiles = Collections.unmodifiableList(new ArrayList<>(files));
        notifyDataSetChanged();
    }

    public interface Callback {
        void onFolderClicked(FolderMetadata folder);

        //void onFileClicked(FileMetadata file);
        void onFileLongClick(FileMetadata file);

        void onFolderLongClick(FolderMetadata folderMetadata);
    }

    public FilesAdapter(Context context, Picasso picasso, Callback callback) {
        mPicasso = picasso;
        mCallback = callback;
        this.context = context;

    }

    @Override
    public MetadataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.files_item, viewGroup, false);
        return new MetadataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MetadataViewHolder metadataViewHolder, int i) {
        metadataViewHolder.bind(mFiles.get(i));
    }

    @Override
    public long getItemId(int position) {
        return mFiles.get(position).getPathLower().hashCode();
    }

    @Override
    public int getItemCount() {
        return mFiles == null ? 0 : mFiles.size();
    }

    public class MetadataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTextView;
        private final ImageView mImageView;
        private Metadata mItem;

        public MetadataViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTextView = (TextView) itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItem instanceof FolderMetadata) {
                        mCallback.onFolderLongClick((FolderMetadata) mItem);
                    } else if (mItem instanceof FileMetadata) {
                        mCallback.onFileLongClick((FileMetadata) mItem);
                        // mCallback.onFileClicked((FileMetadata)mItem);
                    }

                    return true;
                }
            });

        }

        @Override
        public void onClick(View v) {

            if (mItem instanceof FolderMetadata) {
                mCallback.onFolderClicked((FolderMetadata) mItem);
            } else if (mItem instanceof FileMetadata) {
                openFile((FileMetadata) mItem);
                // mCallback.onFileClicked((FileMetadata)mItem);
            }
        }

        public void bind(Metadata item) {
            mItem = item;
            mTextView.setText(mItem.getName());

            // Load based on file path
            // Prepending a magic scheme to get it to
            // be picked up by DropboxPicassoRequestHandler

            if (item instanceof FileMetadata) {
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String ext = item.getName().substring(item.getName().indexOf(".") + 1);
                String type = mime.getMimeTypeFromExtension(ext);
                if (type != null && type.startsWith("image/")) {
                    mPicasso.load(FileThumbnailRequestHandler.buildPicassoUri((FileMetadata) item))
                            .placeholder(R.mipmap.ic_photo_grey_600_36dp)
                            .error(R.mipmap.ic_photo_grey_600_36dp)
                            .into(mImageView);
                } else {
                    mPicasso.load(R.mipmap.file_default)
                            .noFade()
                            .into(mImageView);
                }
            } else if (item instanceof FolderMetadata) {
                mPicasso.load(R.mipmap.folder_default)
                        .noFade()
                        .into(mImageView);
            }
        }
    }


    //
    private void openFile(FileMetadata mSelectedFile) {

        if (mSelectedFile != null) {
            downloadFile(mSelectedFile);
        } else {
            DebugUtil.printLog("No file selected to download.");
        }

    }

    private void downloadFile(FileMetadata file) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Downloading");
        dialog.show();

        new DownloadFileTask(context, DropboxClientFactory.getClient(), new DownloadFileTask.Callback() {
            @Override
            public void onDownloadComplete(File result) {
                dialog.dismiss();

                if (result != null) {
                    viewFileInExternalApp(result);
                }
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                ApplicationController.getInstance().showToastMessages("An error has occurred to download the file");

            }
        }).execute(file);

    }

    private void viewFileInExternalApp(File result) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = result.getName().substring(result.getName().indexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        intent.setDataAndType(Uri.fromFile(result), type);

        // Check for a handler first to avoid a crash
        PackageManager manager = context.getPackageManager();
        List<ResolveInfo> resolveInfo = manager.queryIntentActivities(intent, 0);
        if (resolveInfo.size() > 0) {
            context.startActivity(intent);
        }
    }


}
