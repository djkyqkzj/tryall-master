package com.zmj.example.tryall.model;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

/**
 * Created by ZMJ
 * on 2018/8/21
 */
public class Comment extends LitePalSupport {
	private int id;
	private String content;
	private Date publishDate;

	//与News建立关系  多对一   一个News对应多个comment
	private News news;

	public Comment() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
}
