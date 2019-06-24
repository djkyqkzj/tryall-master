package com.zmj.example.tryall.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zmj.example.tryall.R;
import com.zmj.example.tryall.utils.CircleCrop;

import java.io.File;
import java.io.IOException;

public class HeadPicAct extends AppCompatActivity implements View.OnClickListener{
	private ImageView img_head;

	private String path = Environment.getExternalStorageDirectory() + "/Android/data/com.szkl.android.complaints/head_icon/";
	private String headUrl = "http://192.168.31.40:8080/struts2_spring3_hibernate3_1.0/getheadpicture.action?appuser.name=" + "18302451883";
	private Uri imgUriOri = null;
	private Uri destHeadUri = null;

	private final int TAKE_PHOTO_PERMISSION = 111;
	private final int SELECT_PHOTO_PREMISSION = 114;
	private final int TAKE_PHOTO_REQUEST = 112;
	private final int SELECT_PHOTO_REQUEST = 113;
	private final int CROP_PHOTO_REQUEST = 115;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_pic);
		img_head = findViewById(R.id.img_head);

		img_head.setOnClickListener(this);

		initHead();
	}

	private void initHead(){
		File headPic = new File(path);
		if (headPic.exists() && headPic != null){
			Glide.with(this).load(headPic).placeholder(R.drawable.ic_fp_40px).error(R.drawable.tab_my_pressed).centerCrop().into(img_head);
		}else {
			Glide.with(this).load(headUrl).placeholder(R.drawable.ic_fp_40px).error(R.drawable.tab_my_pressed).into(img_head);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.img_head:
				showTakeOrSelectHeadDialog();
				break;
		}
	}

	private void showTakeOrSelectHeadDialog(){
		new AlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage("请选择拍照或从相册选取")
				.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						getTakePhotoPermission();
					}
				}).setNegativeButton("相册", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				getSelectPhotoPermission();
			}
		}).show();
	}
	@TargetApi(23)
	private void getTakePhotoPermission(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
					|| checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
					|| checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.CAMERA},TAKE_PHOTO_PERMISSION);
				return;
			}
		}
		Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File headFileDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.zmj/example/tryall/head_icon/");
		if (!headFileDir.exists()){
			headFileDir.mkdirs();
		}
		File headFile = new File(headFileDir.getAbsolutePath() + "/18302451883head1.jpg");
		if (headFile.exists())headFile.delete();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
			imgUriOri = FileProvider.getUriForFile(this,"com.zmj.example.tryall.fileProvider",headFile);
			takeIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,imgUriOri);
		}else {
			imgUriOri = Uri.fromFile(headFile);
			takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,imgUriOri);
		}
		startActivityForResult(takeIntent,TAKE_PHOTO_REQUEST);
	}
	@TargetApi(23)
	private void getSelectPhotoPermission(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
					|| checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},SELECT_PHOTO_PREMISSION);
				return;
			}
		}
		Intent selectIntent = new Intent(Intent.ACTION_GET_CONTENT);
		selectIntent.setType("image/*");
		startActivityForResult(selectIntent,SELECT_PHOTO_REQUEST);

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == TAKE_PHOTO_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
			Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File headFileDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.zmj.example.tryall/head_icon/");
			if (!headFileDir.exists()){
				headFileDir.mkdirs();
			}
			File headFile = new File(headFileDir.getAbsolutePath() + "/18302451883head1.jpg");
			if (headFile.exists())headFile.delete();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
				imgUriOri = FileProvider.getUriForFile(this,"com.zmj.example.tryall.fileProvider",headFile);
				takeIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
				takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,imgUriOri);
			}else {
				imgUriOri = Uri.fromFile(headFile);
				takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,imgUriOri);
			}
			startActivityForResult(takeIntent,TAKE_PHOTO_REQUEST);
		}else if (requestCode == SELECT_PHOTO_PREMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
			Intent selectIntent = new Intent(Intent.ACTION_GET_CONTENT);
			selectIntent.setType("image/*");
			startActivityForResult(selectIntent,SELECT_PHOTO_REQUEST);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK){
			return;
		}
		switch (requestCode){
			case TAKE_PHOTO_REQUEST:
				croupPic(imgUriOri);
				break;
			case SELECT_PHOTO_REQUEST:
				if (data != null){
					Uri imgUriSel = data.getData();
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
						//相册会返回一个由相册安全策略定义的Uri，app使用这个Uri直接放入裁剪程序会不识别，抛出[暂不支持此类型：华为7.0]
						//formatUri会返回根据Uri解析出的真实路径
						String imgPathSel = ImgInfoUtils.getPath(this,imgUriSel);
						//根据真实路径转换成File，然后通过FileProvider重新安全化在放入裁剪程序才可识别
						imgUriSel = FileProvider.getUriForFile(this,"com.zmj.example.tryall.fileProvider",new File(imgPathSel));
						//开始裁剪
						croupPic(imgUriSel);
					}else{
						//直接裁剪
						croupPic(imgUriSel);
					}
				}
				break;
			case CROP_PHOTO_REQUEST:
				File file = new File(destHeadUri.getPath());
				Glide.with(this).load(file).transform(new CircleCrop(this)).into(img_head);
				break;
			default:
				break;
		}
	}

	//裁剪图片
	private void croupPic(Uri uri){
		Intent intent = new Intent("com.android.camera.action.CROP");
		File cropPhotoFile = null;
		try{
			cropPhotoFile = ImgInfoUtils.createCropImageFile(HeadPicAct.this,"18302451883");
		}catch (IOException e){
			e.printStackTrace();
		}
		if (cropPhotoFile != null){
			//7.0 安全机制下不允许保存裁剪后的图片
			//所以仅仅将File Uri传入MediaStore.EXTRA_OUTPUT来保存裁剪后的图像
			destHeadUri = Uri.fromFile(cropPhotoFile);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
				intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//				destHeadUri = FileProvider.getUriForFile(this,"com.zmj.example.tryall.fileProvider",cropPhotoFile);
			}
			intent.setDataAndType(uri, "image/*");
			intent.putExtra("crop", true);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 300);
			intent.putExtra("outputY", 300);
			intent.putExtra("return-data", false);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,destHeadUri);//将剪切的图片保存到目标Uri中
			startActivityForResult(intent,CROP_PHOTO_REQUEST);
		}
	}
}
