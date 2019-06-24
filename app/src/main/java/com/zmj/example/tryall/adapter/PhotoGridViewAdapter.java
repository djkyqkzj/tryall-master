package com.zmj.example.tryall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.zmj.example.tryall.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaoning on 2017/3/11 下午6:18.
 */

public class PhotoGridViewAdapter extends BaseAdapter {

    private static final int MAX_FILE_NUM = 4;

    private Context context;
    private LayoutInflater inflater;

    public PhotoGridViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        photoBeanList = new ArrayList<>();
        photoBeanList.add(new PhotoBean(false, ""));
    }

    private List<PhotoBean> photoBeanList;

    public void addPhoto(final String path) {
        photoBeanList.remove(photoBeanList.size() - 1);
        photoBeanList.add(new PhotoBean(true, path));
        if (photoBeanList.size() >= MAX_FILE_NUM) {
            Toast.makeText(context, "已经达到最大文件个数：" + MAX_FILE_NUM, Toast.LENGTH_LONG).show();
        } else {
            photoBeanList.add(new PhotoBean(false, ""));
        }
        this.notifyDataSetChanged();
    }

    public void removePhoto(final int index) {
        photoBeanList.remove(index);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return photoBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.photo_gridview_item, parent, false);
        final ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        final ImageView addPhotoImageView = (ImageView) convertView.findViewById(R.id.addPhotoImageView);
        final PhotoBean photoBean = (PhotoBean) getItem(position);
        if (photoBean.hasPhoto) {
            photoImageView.setVisibility(View.VISIBLE);
            photoImageView.setImageBitmap(dealImg(photoBean.path));
            addPhotoImageView.setVisibility(View.GONE);
        } else {
            photoImageView.setVisibility(View.GONE);
            addPhotoImageView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public static class PhotoBean {
        public PhotoBean() {

        }
        public PhotoBean(boolean hasPhoto, String path) {
            this.hasPhoto = hasPhoto;
            this.path = path;
        }
        public boolean hasPhoto = false;
        public String path = "";
    }

    private Bitmap dealImg(String filePath) {

        FileInputStream fs = null;

//        Bitmap bitmap = ImageFactory.getBitmap(filePath);
//        try {
//            ImageFactory.compressAndGenImage(bitmap,filePath,200);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.outWidth = 10;
            options.outHeight = 10;
            options.inSampleSize = 10;// 特别注意，这个值越大，相片质量越差，图像越小
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inDither = false;
            options.inTempStorage = new byte[12 * 1024];
//            options.inTempStorage = new byte[12 * 1024];
            try {
//            	if (Build.VERSION.SDK_INT >= 24){
//					Uri tempUri = FileProvider.getUriForFile(context,"com.szkl.android.complaints.fileprovider",new File(filePath));
//					fs = new FileInputStream(new File(tempUri.getPath()));
//					Toast.makeText(context,"filePAth:" + tempUri.getPath(),Toast.LENGTH_SHORT).show();
//					fs = new FileInputStream(new File(filePath));
//				}else {
//					fs = new FileInputStream(new File(filePath));
//				}
                fs = new FileInputStream(new File(filePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
//				UseFileSingleton.appendStringToFileWithTimestamp(context,"PhotoGridViewAdapter",e);
            }
            return BitmapFactory.decodeFileDescriptor(fs.getFD(), null, options);
        } catch (Exception e) {
//			UseFileSingleton.appendStringToFileWithTimestamp(context,"PhotoGridViewAdapter",e);
            return null;
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
//					UseFileSingleton.appendStringToFileWithTimestamp(context,"PhotoGridViewAdapter",e);
                }
            }
        }
    }
}
