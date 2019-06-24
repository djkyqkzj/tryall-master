package com.zmj.example.tryall.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.adapter.PhotoGridViewAdapter;

import java.io.File;

public class NewTakePhoto extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

	private GridView photoGridView;
	private PhotoGridViewAdapter photoGridViewAdapter;

	private String selectedFile1Path = "";
	private String selectedFile2Path = "";
	private String selectedFile3Path = "";
	private String selectedFile4Path = "";
	private String selectedFile5Path = "";

	private static final int SEL_FILE1 = 1;
	private static final int SEL_FILE2 = 2;
	private static final int SEL_FILE3 = 3;
	private static final int SEL_FILE4 = 4;
	private static final int SEL_FILE5 = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_take_photo);

		photoGridView = findViewById(R.id.photoGridView);
		photoGridViewAdapter = new PhotoGridViewAdapter(this);
		photoGridView.setAdapter(photoGridViewAdapter);

		photoGridView.setOnItemSelectedListener(this);
		photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showADialog((position + 1));
			}
		});

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		showADialog((position + 1));
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}

	private void showADialog(final int index){
		new AlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage("选择照片或者拍照")
				.setPositiveButton("文件", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						takePhoto(index);
					}
				})
				.setNegativeButton("拍照", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						chosePic(index);
					}
				})
				.show();
	}

	private void takePhoto(final int index){
		if(getPermission()){
			Intent takePhotoIntent = new Intent();
			File file = new File("/mnt/sdcard/file" + (index + 50) + ".jpg" );
			if (file.exists()) file.delete();
			if (Build.VERSION.SDK_INT >= 24){
				takePhotoIntent.setAction(Intent.ACTION_GET_CONTENT);
				Uri uri = FileProvider.getUriForFile(this,"com.zmj.example.tryall.fileProvider",file);
				takePhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
				takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
			}else {
				takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
			}
			startActivityForResult(takePhotoIntent,index + 50);
		}

	}

	private void chosePic(final  int index){
		if (getPermission()){
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent,index);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode > 50 && requestCode < 55){		//拍照结果
			Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri contentUri = Uri.fromFile(new File("/mnt/sdcard/file" + requestCode + ".jpg"));
			mediaScanIntent.setData(contentUri);
			sendBroadcast(mediaScanIntent);
			final String imagePath = "/mnt/sdcard/file" + requestCode + ".jpg";
			File imageFile = new File(imagePath);
			if (imageFile == null || imageFile.exists() == false){
				return;
			}
			if (requestCode == 51){
				photoGridViewAdapter.addPhoto(imagePath);
				selectedFile1Path = imagePath;
			}else if (requestCode == 52){
				photoGridViewAdapter.addPhoto(imagePath);
				selectedFile1Path = imagePath;
			}else if (requestCode == 53){
				photoGridViewAdapter.addPhoto(imagePath);
				selectedFile1Path = imagePath;
			}else if (requestCode == 54){
				photoGridViewAdapter.addPhoto(imagePath);
				selectedFile1Path = imagePath;
			}else if (requestCode == 55){
				photoGridViewAdapter.addPhoto(imagePath);
				selectedFile1Path = imagePath;
			}
			return;
		}

		if (requestCode == Activity.RESULT_OK && data != null){	//相册结果
			if (requestCode == SEL_FILE1 || requestCode == SEL_FILE2 || requestCode == SEL_FILE3 || requestCode == SEL_FILE4 || requestCode == SEL_FILE5){
				Uri selectedImage = data.getData();
				String[] filePathColimus = {MediaStore.Images.Media.DATA};
				Cursor cursor = getContentResolver().query(selectedImage,filePathColimus,null,null,null);
				cursor.moveToFirst();
				int colomIndex = cursor.getColumnIndex(filePathColimus[0]);
				String imagePath = cursor.getString(colomIndex);

				if (requestCode == SEL_FILE1) {
					photoGridViewAdapter.addPhoto(imagePath);
					selectedFile1Path = imagePath;
				} else if (requestCode == SEL_FILE2) {
					photoGridViewAdapter.addPhoto(imagePath);
					selectedFile2Path = imagePath;
				} else if (requestCode == SEL_FILE3) {
					photoGridViewAdapter.addPhoto(imagePath);
					selectedFile3Path = imagePath;
				} else if (requestCode == SEL_FILE4) {
					photoGridViewAdapter.addPhoto(imagePath);
					selectedFile4Path = imagePath;
				} else if (requestCode == SEL_FILE5) {
					photoGridViewAdapter.addPhoto(imagePath);
					selectedFile5Path = imagePath;
				}
				cursor.close();
			}
		}
	}

	private boolean getPermission(){
		if (Build.VERSION.SDK_INT >= 23){
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.CAMERA},5);
				return false;
			}else {
				return true;
			}
		}else {
			return true;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
			Toast.makeText(this,"已经获取权限！",Toast.LENGTH_SHORT).show();
		}
	}


}
