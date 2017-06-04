package com.offshoreindian.lokdon.bean;

/**
 * Created by praveshkumar on 21/10/16.
 */

public class GoogleDriveFileBean
{
    private String fileName;
    private String fileType;
    private String parentFolderName;
    private boolean isEyncrpt;
    private String fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getParentFolderName() {
        return parentFolderName;
    }

    public void setParentFolderName(String parentFolderName) {
        this.parentFolderName = parentFolderName;
    }

    public boolean isEyncrpt() {
        return isEyncrpt;
    }

    public void setEyncrpt(boolean eyncrpt) {
        isEyncrpt = eyncrpt;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
