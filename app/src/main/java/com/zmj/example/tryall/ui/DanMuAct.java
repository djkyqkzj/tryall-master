package com.zmj.example.tryall.ui;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.utils.PermissionUtils;
import com.zmj.example.tryall.utils.ScreenUtils;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class DanMuAct extends AppCompatActivity  implements View.OnClickListener{
	private VideoView video_view;
	private boolean showDanmaku ;
	private DanmakuView danmaku_view;
	private DanmakuContext danmakuContext;
	private LinearLayout operationLayout;
	private EditText et_content;
	private Button btn_send;

	private BaseDanmakuParser parser = new BaseDanmakuParser() {//弹幕解析器
		@Override
		protected IDanmakus parse() {
			return new Danmakus();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dan_mu);

		PermissionUtils.requestMorePermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},223);
		video_view = findViewById(R.id.video_view);
		try{
			String path = Environment.getExternalStorageDirectory() + "/1502264961197.mp4";
			Uri uri = Uri.parse(path);
			video_view.setMediaController(new MediaController(this));
			video_view.setVideoURI(uri);
			video_view.start();
//			video_view.requestFocus();
		}catch (Exception e){
			e.printStackTrace();
		}
		danmaku_view = findViewById(R.id.danmaku_view);
		operationLayout = findViewById(R.id.operationLayout );
		et_content = findViewById(R.id.et_content);
		btn_send = findViewById(R.id.btn_send);

		danmaku_view.setOnClickListener(this);
		btn_send.setOnClickListener(this);


		danmaku_view.enableDanmakuDrawingCache(true);	//提升绘制效率
		danmaku_view.setCallback(new DrawHandler.Callback() {
			@Override
			public void prepared() {
				showDanmaku = true;
				danmaku_view.start();
				generateSomeDammaKu();
			}

			@Override
			public void updateTimer(DanmakuTimer timer) {

			}

			@Override
			public void danmakuShown(BaseDanmaku danmaku) {

			}

			@Override
			public void drawingFinished() {

			}
		});
		danmakuContext = DanmakuContext.create();//对弹幕的各种全局属性进行配置 字体、最大显示行数等
		danmaku_view.prepare(parser,danmakuContext);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.danmaku_view:
				if (operationLayout.getVisibility() == View.GONE){
					operationLayout.setVisibility(View.VISIBLE);
					video_view.pause();
				}else {
					operationLayout.setVisibility(View.GONE);
				}
				break;
			case R.id.btn_send:
				String content = et_content.getText().toString().trim();
				if (!TextUtils.isEmpty(content)){
					addDanmaKu(content,true);
					et_content.setText("");
					operationLayout.setVisibility(View.GONE);
					InputMethodManager inputMethodManager = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					if (inputMethodManager.isActive()){
						//inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(),0);
						inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
					}
					video_view.start();
				}
				break;
			default:
				break;
		}
	}

	/**
	 * 弹幕的内容 和是否有边框
	 * @param content
	 * @param withBorder
	 */
	private void addDanmaKu(String content,boolean withBorder){
		BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_LR);
		danmaku.text = content;
		danmaku.padding = 5;
		danmaku.textSize = ScreenUtils.sp2px(DanMuAct.this,20);
		danmaku.textColor = Color.WHITE;
		danmaku.setTime(danmaku_view.getCurrentTime());
		if (withBorder){
			danmaku.borderColor = Color.GREEN;
		}
		danmaku_view.addDanmaku(danmaku);
	}

	//随机生成数据
	private void generateSomeDammaKu(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (showDanmaku){
					int time = new Random().nextInt(300);
					String content = "" + time + time;
					addDanmaKu(content,false);
					try{
						Thread.sleep(time);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (danmaku_view != null && danmaku_view.isPrepared() && danmaku_view.isPaused()){
			danmaku_view.pause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (danmaku_view != null && danmaku_view.isPrepared() && danmaku_view.isPaused()){
			danmaku_view.resume();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		showDanmaku = false;
		if (danmaku_view != null){
			danmaku_view.release();
			danmaku_view = null;
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && Build.VERSION.SDK_INT >= 19){
			View decorView = getWindow().getDecorView();
			decorView.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
			);
		}
	}


}
