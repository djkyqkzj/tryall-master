package com.zmj.example.tryall.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.bean.Person;
import com.zmj.example.tryall.dataanalysis.SaxHelper;
import com.zmj.example.tryall.utils.GetData;
import com.zmj.example.tryall.utils.PostUtils;

import java.util.ArrayList;

public class TestHttpurlConnAct extends AppCompatActivity {
	private TextView txtMenu,txtshow;
	private ImageView imgpic;
	private WebView webView;
	private ScrollView scroll;
	private Bitmap bitmap;
	private String detial = "";
	boolean flag = false;
	private static final String PIC_URL = "http://ww2.sinaimg.cn/large/7a8aed7bgw1evshgr5z3oj20hs0qo0vq.jpg";
	private static final String HTML_URL = "https://www.baidu.com/";

	//刷新界面
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			//super.handleMessage(msg);
			switch (msg.what){
				case 0x001:
					hideAllViews();
					imgpic.setVisibility(View.VISIBLE);
					imgpic.setImageBitmap(bitmap);
					Toast.makeText(TestHttpurlConnAct.this,"图片加载完毕",Toast.LENGTH_SHORT).show();
					break;
				case 0x002:
					hideAllViews();
					scroll.setVisibility(View.VISIBLE);
					txtshow.setText(detial);
					Toast.makeText(TestHttpurlConnAct.this,"HTML载完毕",Toast.LENGTH_SHORT).show();
					break;
				case 0x003:
					hideAllViews();
					webView.setVisibility(View.VISIBLE);
					webView.loadDataWithBaseURL("",detial,"text/html","UTF-8","");
					//webView.loadUrl(HTML_URL);
					Toast.makeText(TestHttpurlConnAct.this,"WEBVIEW载完毕",Toast.LENGTH_SHORT).show();
					break;
				case 0x004:
					if (issuccess == ""){
						Toast.makeText(TestHttpurlConnAct.this,"登录失败",Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(TestHttpurlConnAct.this,"登录结果：aa" + issuccess,Toast.LENGTH_SHORT).show();
					}
				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_httpurl_conn);

		setViews();
	}

	private void setViews(){
		txtMenu = findViewById(R.id.txtMenu);
		txtshow = findViewById(R.id.txtshow);
		imgpic = findViewById(R.id.imgpic);
		webView = findViewById(R.id.webView);
		scroll = findViewById(R.id.scroll);
		//定义上下文菜单
		registerForContextMenu(txtMenu);
	}

	//隐藏所有控件的方法
	private void hideAllViews(){
		imgpic.setVisibility(View.GONE);
		webView.setVisibility(View.GONE);
		scroll.setVisibility(View.GONE);
	}

	//重写上下文菜单方法

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.menus,menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//return super.onContextItemSelected(item);
		switch (item.getItemId()){
			case R.id.one:
				new Thread(){
					@Override
					public void run() {
						try {
							byte[] data = GetData.getImage(PIC_URL);
							bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
						}catch (Exception e){
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(0x001);
					}
				}.start();
				break;
			case R.id.two:
				new  Thread(){
					@Override
					public void run() {
						try {
							detial =  GetData.getHtml(HTML_URL);
						}catch (Exception e){
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(0x002);
					}
				}.start();
				break;
			case R.id.three:
				if (detial.equals("")){
					Toast.makeText(this,"先请求html",Toast.LENGTH_SHORT).show();
				}else {
					mHandler.sendEmptyMessage(0x003);
				}
				break;
			case R.id.four:
				testPost();
				mHandler.sendEmptyMessage(0x004);
				break;
			case R.id.analiesXML:
				analyXml();
				break;
			default:
				break;
		}
		return true;
	}

	String reqUrl = "http://192.168.31.45:8080/Test/demo";
	String name = "ZMJ";
	String pass = "ZMJ";
	String issuccess ="";
	private void testPost(){
		new Thread(){
			@Override
			public void run() {
				issuccess = PostUtils.loginByPost(reqUrl,name,pass);
			}
		}.start();
	}

	//解析XML文件
	private ArrayList<Person> analyXml(){
		SaxHelper sh = new SaxHelper();


		return sh.getPersons();
	}
}
