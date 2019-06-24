package com.zmj.example.tryall.file;

import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ZMJ
 * on 2018/7/23
 */
public class DownloadThread extends Thread {
	private static final String TAG = "downloadThread";
	private File saveFile;//下载数据保存的文件
	private URL downUrl;//下载的URL
	private int block;//每一条线程下载的大小
	private int threadId = -1;//初始化线程id设置
	private int downLength ;//该线程已下载的数据长度
	private boolean finish = false;//线程是否下载完成的标志
	private FileDownloadered downloader;//文件下载器

	public DownloadThread(FileDownloadered downloader,URL downUrl,File saveFile,int block,int downLength,int threadId){
		this.downloader = downloader;
		this.saveFile = saveFile;
		this.downUrl = downUrl;
		this.downLength = downLength;
		this.block = block;
		this.threadId = threadId;
	}

	@Override
	public void run() {
		if (downLength < block){//未下载完成
			try {
				HttpURLConnection http = (HttpURLConnection) downUrl.openConnection();
				http.setConnectTimeout(5 * 1000);
				http.setRequestMethod("GET");
				http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
				http.setRequestProperty("Accept-Language", "zh-CN");
				http.setRequestProperty("Referer",downUrl.toString());
				http.setRequestProperty("Charset","UTF-8");
				int startpos = block * (threadId - 1) + downLength;//开始位置
				int endpos = block * threadId -1;//结束位置
				http.setRequestProperty("Range","bytes=" + startpos + "-" + endpos);//设置获取实体数据的范围
				http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				http.setRequestProperty("Connection", "Keep-Alive");

				InputStream inStream = http.getInputStream();//获取网络链接的输入流
				byte[] buffer = new byte[1024];	//设置本地缓存1M
				int offset = 0;			//每次读取的数据量
				print("Thread" + this.threadId + "start download from position" + startpos);//打印该线程开始下载的位置
				RandomAccessFile threadfile = new RandomAccessFile(this.saveFile,"rwd");
				threadfile.seek(startpos);//找到下载的位置
				//用户没有要求停止下载，同时没有达到请求数据的末尾时会一直循环读取数据
				while (!downloader.getExited() && (offset = inStream.read(buffer,0,1024)) != -1){
					threadfile.write(buffer,0,offset);		//直接把数据写到文件中
					downLength += offset;				 //把新线程已经写到文件中的数据加入到下载长度中
					downloader.update(this.threadId,downLength);//把该线程已经下载的数据长度更新到数据库和内存Hash表中
					downloader.append(offset);
				}
				threadfile.close();
				inStream.close();
				print("Thread" + this.threadId + "download finish");
				this.finish = true;		//设置完成标记为true,无论下载完成还是用户主动中断下载
			}catch (Exception e){
				this.downLength = -1;//设置该线程下载的长度为-1
				print("Thread" + this.threadId + ":" +e);
			}
		}
	}

	private static void print(String msg){
		Log.i(TAG, msg);
	}

	public boolean isFinish(){
		return finish;
	}

	public long getDownLength(){
		return downLength;
	}
}
