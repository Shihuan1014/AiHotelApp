package edu.hnu.aihotel.util;

public abstract class UploadProgressListener {

    public abstract void onProgress(long totalLength,long currentLength);
}
