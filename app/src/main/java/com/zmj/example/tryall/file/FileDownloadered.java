package com.zmj.example.tryall.file;

import android.content.Context;
import android.util.Log;

import com.zmj.example.tryall.db.FileService;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZMJ
 * on 2018/7/23
 */
public class FileDownloadered {
	private static final String TAG = "fileDownload";//设置一个log时的一个标志
	private static final int RESPONSEOK = 200;//设置响应码200，代表访问成功
	private FileService fileService;//本地数据库的业务bean
	private boolean exited;//停止下载的标志
	private Context context;
	private int downloadedSize = 0;//已下载的文件的长度
	private int fileSize = 0;//开始的文件长度
	private  DownloadThread[] threads;//根据线程数设置下载的线程池
	private File saveFile;		//数据保存到本地文件中
	private Map<Integer,Integer> data = new ConcurrentHashMap<Integer, Integer>();	//缓存个条线程的下载的长度
	private int block;		//每条线程下载的长度
	private String downLoadUrl;	//下载地址

	public int getThreadSize(){
		return 0;
	}

	public void exit(){
		this.exited = true;	//将退出的标志设为true；
	}

	public boolean getExited(){
		return this.exited;
	}

	public int getFileSize(){
		return fileSize;
	}

	//累计下载的大小  使用同步锁解决并发访问问题
	protected  synchronized void append (int size){
		//把实际下载的长度加到总的下载长度中
		downloadedSize += size;
	}
	//更新指定线程最后下载的位置
	protected synchronized void update(int threadId,int pos){
		//把指定线程id的线程赋予最新的下载长度,以前的值会被覆盖掉
		this.data.put(threadId,pos);
		//更新数据库中指定线程的下载长度
		this.fileService.update(this.downLoadUrl,threadId,pos);
	}

	public FileDownloadered(Context context, File saveDir, String downLoadUrl,int threadnum) {
		try {
			this.context = context;
			URL url = new URL(this.downLoadUrl);
			fileService = new FileService(this.context);		//实例化数据库操作的业务Bean类,需要传一个context值
			if (!saveDir.exists()) saveDir.mkdir();				//如果文件不存在的话指定目录,这里可创建多层目录
			this.threads = new DownloadThread[threadnum];		//根据下载的线程数量创建下载的线程池

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);      //设置连接超时事件为5秒
			conn.setRequestMethod("GET");      //设置请求方式为GET
			//设置用户端可以接收的媒体类型
			conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, " +
					"image/pjpeg, application/x-shockwave-flash, application/xaml+xml, " +
					"application/vnd.ms-xpsdocument, application/x-ms-xbap," +
					" application/x-ms-application, application/vnd.ms-excel," +
					" application/vnd.ms-powerpoint, application/msword, */*");

			conn.setRequestProperty("Accept-Language", "zh-CN");  //设置用户语言
			conn.setRequestProperty("Referer", downLoadUrl);    //设置请求的来源页面,便于服务端进行来源统计
			conn.setRequestProperty("Charset", "UTF-8");    //设置客户端编码
			//设置用户代理
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; " +
					"Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727;" +
					" .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");

			conn.setRequestProperty("Connection", "Keep-Alive");  //设置connection的方式

			conn.connect();//和资源地址建立链接 但尚无信息返回
			printResponseHeader(conn);	//打印返回的Http的头字段集合

			if (conn.getResponseCode() == RESPONSEOK){
				this.fileSize = conn.getContentLength();	//根据响应获取文件的大小
				if (this.fileSize == 0) throw new RuntimeException("未知文件大小");//文件长度小于等于0时抛出运行时异常
				String fileName = getFileName(conn);
				this.saveFile = new File(saveDir,fileName);///根据文件保存目录和文件名保存文件
				Map<Integer,Integer> logdata = fileService.getData(downLoadUrl);//获取下载记录

				if (logdata.size()> 0){
					//遍历集合中的数据,把每条线程已下载的数据长度放入data中
					for (Map.Entry<Integer,Integer> entry:logdata.entrySet()){
						data.put(entry.getKey(),entry.getValue());
					}
				}
				//如果已下载的数据的线程数和现在设置的线程数相同时则计算所有现场已经下载的数据总长度
				if (this.data.size() == this.threads.length){
					//遍历每条线程已下载的数据
					for (int i = 0;i < this.threads.length; i++){
						this.downloadedSize += this.data.get(i + 1);
					}
				}
			}

		}catch (Exception e){

		}
	}
	//获取文件名称
	private String getFileName(HttpURLConnection http){
		//从下载的路径的字符串中获取文件的名称
		String filname = this.downLoadUrl.substring(this.downLoadUrl.lastIndexOf("/") + 1);
		if (filname == null || "".equals(filname.trim())){//如果找不到文件名称
			for (int i = 0;;i++){							//使用无线循环遍历
				String mine = http.getHeaderField(i);		//从返回的流中获取特定索引的头字段的值
				if (mine == null) break;
				//获取content-disposition返回字段,里面可能包含文件名
				if ("content-disposition".equals(http.getHeaderFieldKey(i).toLowerCase())){
					//使用正则表达式查询文件名
					Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
					if (m.find()){
						return m.group(1);//如果有符合正则表达式规则的字符串,返回
					}
				}
			}
			filname = UUID.randomUUID() + ".tmp";//如果都没找到的话,默认取一个文件名
			//由网卡标识数字(每个网卡都有唯一的标识号)以及CPU时间的唯一数字生成的一个16字节的二进制作为文件名
		}
		return filname;
	}

	//获取响应头
	public static Map<String,String> getHttpResponseHeader(HttpURLConnection http){
		//使用LinkedHashMap保证写入和历变的时候顺讯相同，而且允许空值
		Map<String,String> header = new LinkedHashMap<String, String>();
		//此处使用无线循环。因为不知道header的数量
		for (int i = 0;;i++){
			String mine = http.getHeaderField(i);	//获取第i个头字段的值
			if (mine == null) break;				 //没值说明头字段已经循环完毕了,使用break跳出循环
			header.put(http.getHeaderField(i),mine);	 //获得第i个头字段的键
		}
		return header;
	}

	//打印http头
	public static void printResponseHeader(HttpURLConnection http){
		//获取http响应头字段
		Map<String,String> header = getHttpResponseHeader(http);
		//使用增强for循环遍历取得的头字段的值，此时历变的循环顺序与输入顺序相同
		for (Map.Entry<String,String> entry:header.entrySet()){
			//当有键的时候则获取值,如果没有则为空字符串
			String key = entry.getKey() != null ? entry.getKey() + ":"  : "";//当有键的时候则获取值,如果没有则为空字符串
			print(key + entry.getValue());
		}

	}

	private static void print(String msg){
		Log.i(TAG, msg);
	}


}
