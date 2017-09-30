package com.agnt45.audiogram.Classes;

/**
 * Created by ar265 on 9/27/2017.
 */

public class Posts {
    public Posts() {
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFile1Url() {
        return file1Url;
    }

    public void setFile1Url(String file1Url) {
        this.file1Url = file1Url;
    }

    public String getFile2Url() {
        return file2Url;
    }

    public void setFile2Url(String file2Url) {
        this.file2Url = file2Url;
    }

    private String fileType,file1Url,file2Url;

    public Posts(String fileType, String file1Url, String file2Url) {
        this.fileType = fileType;
        this.file1Url = file1Url;
        this.file2Url = file2Url;
    }

}
