package com.example.yimageloader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.imageloader.utils.ImageLoader;

public class MainActivity extends Activity {
    private GridView imageGrid;
    public String[] imageUrls = {"http://pic1.win4000.com/wallpaper/2018-12-26/5c23351dad157_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-12-26/5c2334a1b4627_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-12-26/5c2333976171b_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-12-12/5c109f24c3d24_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-11-27/5bfcdc5556904_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-11-21/5bf4cca183520_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-11-13/5bea31214169a_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-11-09/5be4ef509f62a_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-23/5bcebc844f1d5_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-23/5bcebbb641a03_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-18/5bc843d3a191f_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-18/5bc842ff79b33_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-17/5bc69842d10a6_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-16/5bc5aca272873_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-16/5bc5aa7f14c3d_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-16/5bc5a947c9073_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-09/5bbc75a2af588_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-10-09/5bbc74c133857_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-09-27/5bac7a9d96be6_270_185.jpg",
            "http://pic1.win4000.com/wallpaper/2018-09-27/5bac78df07c7d_270_185.jpg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageGrid = (GridView)findViewById(R.id.imageGrid);
        imageGrid.setAdapter(new ImageAdapter(imageUrls,this));
    }

    private class ImageAdapter extends BaseAdapter{
        public String[] imageUrls;
        public Context context;
        public LayoutInflater mInflater;

        public ImageAdapter(String[] imageUrls,Context context) {
            this.imageUrls = imageUrls;
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listitem_image,
                        parent, false);
                holder = new ViewHolder();
                holder.id_imageitem_image = (ImageView) convertView.findViewById(R.id.id_imageitem_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance(context).setImage(imageUrls[position],holder.id_imageitem_image);
            return convertView;
        }

        public class ViewHolder {
            ImageView id_imageitem_image;
        }
    }
}
