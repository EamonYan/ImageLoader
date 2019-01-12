package com.imageloader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {
    public static FileUtil mFileUtil = null;

    private Context context;

    private FileUtil(Context context) {
        this.context = context;
    }

    public static FileUtil getInstance(Context context){
        if (null == mFileUtil){
            synchronized (FileUtil.class) {
                if (mFileUtil == null) {
                    mFileUtil = new FileUtil(context);
                }
            }
        }
        return mFileUtil;
    }

    /**
     * 保存图片到本地
     * @param fileName
     * @param bitmap
     */
    public void writeImageToStorage(String fileName, Bitmap bitmap){
        try {
            File file = new File(context.getFilesDir(), fileName);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 读取本地图片
     * @param fileName
     * @return
     */
    public Bitmap readImageFromStorage(String fileName){
        File file = new File(context.getFilesDir(), fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        return bitmap;
    }
}
