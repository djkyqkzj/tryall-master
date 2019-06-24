package com.zmj.example.tryall;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zmj.example.tryall.bean.TestParcelable;
import com.zmj.example.tryall.bean.TestParcelables;
import com.zmj.example.tryall.fragment.FragmentOne;
import com.zmj.example.tryall.fragment.FragmentTwo;
import com.zmj.example.tryall.model.News;
import com.zmj.example.tryall.ui.DanMuAct;
import com.zmj.example.tryall.ui.DataAnsis;
import com.zmj.example.tryall.ui.GetCityAndWeatherAct;
import com.zmj.example.tryall.ui.HeadPicAct;
import com.zmj.example.tryall.ui.LoginAct;
import com.zmj.example.tryall.ui.Lunbotu;
import com.zmj.example.tryall.ui.MainAct;
import com.zmj.example.tryall.ui.ParcelableDataAct;
import com.zmj.example.tryall.ui.TestButterKnifeAct;
import com.zmj.example.tryall.ui.TestGlideAct;
import com.zmj.example.tryall.ui.TestHttpurlConnAct;
import com.zmj.example.tryall.ui.TestRetrofitAct;
import com.zmj.example.tryall.ui.TextInText;
import com.zmj.example.tryall.ui.ViewPagerFgAct;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private Button ui_mainAct,ui_TextInText,ui_ViewPagerFgAct,ui_Lunbotu,testHttpurl,
	btn_Dataanalysis,btn_TestRetrofit,btn_butterKnife,btn_parcelable,btn_glideLoadImg,
			btn_danMu,btn_fingerPrint,btn_takePhoto,btn_locate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_main);

		ui_mainAct = findViewById(R.id.ui_mainAct);
		ui_TextInText = findViewById(R.id.ui_TextInText);
		ui_ViewPagerFgAct = findViewById(R.id.ui_ViewPagerFgAct);
		ui_Lunbotu = findViewById(R.id.ui_Lunbotu);
		btn_Dataanalysis = findViewById(R.id.btn_Dataanalysis);
		btn_TestRetrofit = findViewById(R.id.btn_TestRetrofit);
		btn_butterKnife = findViewById(R.id.btn_butterKnife);
		btn_parcelable = findViewById(R.id.btn_parcelable);
		btn_takePhoto = findViewById(R.id.btn_takePhoto);

		testHttpurl = findViewById(R.id.testHttpurl);
		btn_glideLoadImg = findViewById(R.id.btn_glideLoadImg);
		btn_danMu = findViewById(R.id.btn_danMu);
		btn_fingerPrint = findViewById(R.id.btn_fingerPrint);
		btn_locate = findViewById(R.id.btn_locate);

		ui_mainAct.setOnClickListener(this);
		ui_TextInText.setOnClickListener(this);
		ui_ViewPagerFgAct.setOnClickListener(this);
		ui_Lunbotu.setOnClickListener(this);
		testHttpurl.setOnClickListener(this);
		btn_Dataanalysis.setOnClickListener(this);
		btn_TestRetrofit.setOnClickListener(this);
		btn_butterKnife.setOnClickListener(this);
		btn_parcelable.setOnClickListener(this);
		btn_glideLoadImg.setOnClickListener(this);
		btn_danMu.setOnClickListener(this);
		btn_fingerPrint.setOnClickListener(this);
		btn_takePhoto.setOnClickListener(this);
		btn_locate.setOnClickListener(this);

		//getScreenWHshowFg();
		//testResolver();
		//操作LitePal
		useLitePal();
	}
	//ContentProvider往另外一个APP中写入数据
	private void testResolver(){
		final ContentResolver resolver = this.getContentResolver();

		ContentValues values = new ContentValues();

		values.put("name","测试");
		Uri uri = Uri.parse("content://com.android.szkl.remoteserviceclient.contentprovider.NameContentProvider/testContentProvider");

		resolver.insert(uri,values);

		Toast.makeText(getApplicationContext(), "数据插入成功", Toast.LENGTH_SHORT).show();
	}

	//获取屏幕宽高显示Fragment
	private void getScreenWHshowFg(){
		//获取屏幕宽高
//		Display dis = getWindowManager().getDefaultDisplay();
//		Point size = new Point();
//		dis.getSize(size);
//		int width = size.x;
//		int heiht = size.y;
		//或者
		DisplayMetrics mertrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mertrics);
		int width1 = mertrics.widthPixels;
		int height1 = mertrics.heightPixels;

		if(width1 < height1){
			FragmentOne fragmentOne = new FragmentOne();
			getFragmentManager().beginTransaction().replace(R.id.liner1,fragmentOne).commit();
		}else {
			FragmentTwo fragmentTwo = new FragmentTwo();
			getFragmentManager().beginTransaction().replace(R.id.liner2,fragmentTwo).commit();
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.ui_mainAct:
				startActivity(new Intent(this, MainAct.class));
				break;
			case R.id.ui_TextInText:
				startActivity(new Intent(this, TextInText.class));
				break;
			case R.id.ui_ViewPagerFgAct:
				startActivity(new Intent(this, ViewPagerFgAct.class));
				break;
			case R.id.ui_Lunbotu:
				startActivity(new Intent(this, Lunbotu.class));
				break;
			case R.id.testHttpurl:
				startActivity(new Intent(this, TestHttpurlConnAct.class));
				break;
			case R.id.btn_Dataanalysis:
				startActivity(new Intent(this, DataAnsis.class));
				break;
			case R.id.btn_TestRetrofit:
				startActivity(new Intent(this, TestRetrofitAct.class));
				break;
			case R.id.btn_butterKnife:
				startActivity(new Intent(this, TestButterKnifeAct.class));
				break;
			case R.id.btn_parcelable:
				jionParcelableData();
				break;
			case R.id.btn_glideLoadImg:
				startActivity(new Intent(this, TestGlideAct.class));
				break;
			case R.id.btn_danMu:
				startActivity(new Intent(this, DanMuAct.class));
				break;
			case R.id.btn_fingerPrint:
				startActivity(new Intent(this, LoginAct.class));
				break;
			case R.id.btn_takePhoto:
//				startActivity(new Intent(this, NewTakePhoto.class));
				startActivity(new Intent(this, HeadPicAct.class));
				break;
				//获取地理位置，并获取天气信息
			case R.id.btn_locate:
				startActivity(new Intent(this, GetCityAndWeatherAct.class));
				break;
			default:
				break;
		}
	}

	//加入序列化数据并传到其他activity
	private void jionParcelableData(){
		TestParcelable tp1 = new TestParcelable();
		tp1.setAge(12);
		tp1.setName("xiaoming");
		tp1.setWeight(45);

		TestParcelable tp2 = new TestParcelable();
		tp2.setAge(18);
		tp2.setName("wangjia");
		tp2.setWeight(48);

		List<TestParcelable> mList = new ArrayList<>();
		mList.add(tp1);
		mList.add(tp2);

		TestParcelables tps = new TestParcelables();
		tps.setmList(mList);
		//intent传值
		Intent intent = new Intent(this, ParcelableDataAct.class);
		intent.putExtra("parcelables",tps);
		startActivity(intent);
	}

	//LitePal操作数据库
	private void useLitePal(){
		//获取数据库实例 LitePal提供的便捷方法
		SQLiteDatabase db = Connector.getDatabase();
		//1.添加一条News
//		News news = new News();
//		news.setTitle("这是一条新闻标题");
//		news.setContent("这是一条新闻内容");
//		news.setPublishDate(new Date());
//		Log.i("LitePal", "存储前："+news.getId());
//		news.saveThrows();
//
//		if (news.save()){
//			Log.i("LitePal", "存储成功："+news.getId());
//		}else {
//			Log.i("LitePal", "存储失败："+news.getId());
//		}
		//2.添加2条Comment 和一条News并建立关系
//		Comment comment1 = new Comment();
//		comment1.setContent("好评！");
//		comment1.setPublishDate(new Date());
//		comment1.save();
//		Comment comment2 = new Comment();
//		comment2.setContent("赞一个！");
//		comment2.setPublishDate(new Date());
//		comment2.save();
//
//		News news = new News();
//		news.getCommentList().add(comment1);
//		news.getCommentList().add(comment2);
//		news.setTitle("第二条新闻标题");
//		news.setContent("第二条新闻内容");
//		news.setPublishDate(new Date());
//		news.setCommentCount(news.getCommentList().size());
//		news.saveThrows();

		//3.更新单条数据数据
//		ContentValues values = new ContentValues();
//		values.put("publishdate",new Date() + "");
//		LitePal.update(News.class,values,2);
		//4.更新某一条件下的多条数据
//		ContentValues contentValues = new ContentValues();
//		contentValues.put("title","今日iphone6 plus发布");
//		contentValues.put("publishdate",System.currentTimeMillis());
//		//conditions数组,可以写入任意多的String类型参数，
//		// 后面所有String类型的参数对应前面的占位符（即？），有几个占位符就就写几个参数
//		LitePal.updateAll(News.class,contentValues,"title = ?","第二条新闻标题");
		//news表中 标题 为“今日iphone6 plus发布”且 评论数量 大于0的所有新闻的标题改成“今日发布iPhone6 Plus”
//		ContentValues contentValues2 = new ContentValues();
//		contentValues2.put("title","今日发布iPhone6 Plus");
//		LitePal.updateAll(News.class,contentValues2,"title = ? and commentcount > ? ","今日iphone6 plus发布","0");
		//5.LitePal自带的更新数据方法  更新指定ID的数据
//		News updateNews = new News();
//		updateNews.setTitle("今日iPhone6发布");
//		updateNews.update(2);
		//6.LitePal自带更新数据方法，更新某一条件下数据
//		News updateNews1 = new News();
//		updateNews1.setTitle("今日iphone6 plus发布");
//		//把news表中标题为“今日iPhone6发布”且评论数量大于0的所有新闻的标题改成“今日iPhone6 Plus发布”
//		updateNews1.updateAll("title = ? and commentcount > ? ","今日iPhone6发布","0");
		//7.将某一字段修改成默认值
//		News updateNews2 = new News();
//		updateNews2.setToDefault("content");
//		updateNews2.updateAll();

		//8.LitePal查询数据   查询指定id
		News news = LitePal.find(News.class,1);//查询id = 1的数据
		News firstNews = LitePal.findFirst(News.class);//查询News表中的第一条数据
		News lastNews = LitePal.findLast(News.class);//获取最后一条数据
		List<News> newsList = LitePal.findAll(News.class,1,3,5,7);//查询多条指定id的数据
		long[] ids = new long[]{1,3,5,7};
		List<News> newsList1 = LitePal.findAll(News.class,ids);//查询多条指定id的数据
		List<News> allNewsList = LitePal.findAll(News.class);//查询所有的News数据

		//9.连缀查询	查询news表中所有评论数大于零的新闻
		List<News> newsList2 = LitePal.where("title = ? and commentcount > ? ","这是一条新闻标题","0").find(News.class);
		List<News> newsList3 = LitePal.select("title","commentcount").where("commentcount > ? ","0").find(News.class);
		//将查询出的新闻按照发布的时间倒序排列    并取出前十条
//		List<News> newsList4 = LitePal
//				.select("title","commentcount")
//				.where("commentcount > ? ","0")
//				.order("publishdate desc")		//时间倒序排列
//				.limit(10)					//取出前十条
//				.offset(10)					//偏移量，原来是查询前10条新闻的，偏移了十个位置之后，就变成了查询第11到第20条新闻了
//				.find(News.class);			//如果偏移量是20，那就表示查询第21到第30条新闻，以此类推
//
//		//10.激进查询
//		News news1 = LitePal.find(News.class,2,true);//isEager是否激进查询标志
//		List<Comment> commentList = news1.getCommentList();//查出News连带与其关联的Comment一起查出来
//		//11.原生查询 LitePal支持原生查询
//		Cursor cursor = LitePal.findBySQL("select * from news where id = ? ", "1");
//
//		//12.LitePal的聚合函数
//		int result = LitePal.count(News.class);		//统计
//		int result1 = LitePal.where("title = ? and commentcount > ?","","0").count(News.class);//统计
//		int result2 = LitePal.sum(News.class,"commentcount",int.class);//查询评论的数量
//		double result3 = LitePal.average(News.class,"commentcount");//平均评论量
//		//注意：sum（）和average（）只能对具有运算能力的列进行求值
//		int result4 = LitePal.max(News.class,"commentcount",int.class);
//		int result5 = LitePal.min(News.class,"commentcount",int.class);


	}
}
