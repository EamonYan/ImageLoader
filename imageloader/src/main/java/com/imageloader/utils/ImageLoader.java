package com.imageloader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static ImageLoader instance;

    private Context context;

    private CacheUtil imageCache;

    public ImageLoader(Context context) {
        this.context = context;
        Map<String, SoftReference<Bitmap>> cacheMap = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) { // SDK版本判断
            this.imageCache = new CacheUtil(cacheMap);
        }
    }

    public static ImageLoader getInstance(Context context) {
        if (null == instance) {
            synchronized (ImageLoader.class) {
                if (null == instance) {
                    instance = new ImageLoader(context);
                }
            }
        }
        return instance;
    }


    /**
     * 将图片添加到缓存中
     */
    private void putBitmapIntoCache(String fileName, Bitmap bitmap) {
        // 将图片的字节数组写入到内存中
        FileUtil.getInstance(context).writeImageToStorage(fileName, bitmap);
        // 将图片存入强引用（LruCache）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            imageCache.put(fileName, bitmap);
        }
    }

    /**
     * 从缓存中取出图片
     */
    private Bitmap getBitmapFromCache(String fileName) {
        // 从强引用（LruCache）中取出图片
        Bitmap bm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1) { // SDK版本判断
            bm = imageCache.get(fileName);
            if (bm == null) {
                // 如果图片不存在强引用中，则去软引用（SoftReference）中查找
                Map<String, SoftReference<Bitmap>> cacheMap = imageCache.getCacheMap();
                SoftReference<Bitmap> softReference = cacheMap.get(fileName);
                if (softReference != null) {
                    bm = softReference.get();
                    imageCache.put(fileName, bm);
                } else {
                    // 如果图片不存在软引用中，则去内存中找
                    bm = FileUtil.getInstance(context).readImageFromStorage(fileName);
                    if(null != bm) {
                        imageCache.put(fileName, bm);
                    }
                }
            }
        }
        return bm;
    }

    public void setImage(final String url, final ImageView imageView) {
        final String fileName = url.substring(url.lastIndexOf(File.separator) + 1);
        Bitmap bm = getBitmapFromCache(fileName);
        if (bm != null) {
            imageView.setImageBitmap(bm);
        } else {
            // 从网络获取图片
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = HttpUtil.getInstance().getImageFromServer(url);
                    if (bitmap != null) {
                        // 将图片字节数组写入到缓存中
                        putBitmapIntoCache(fileName, bitmap);
                        // 将从网络获取到的图片设置给ImageView
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }).start();
        }
    }
}
