package com.zmj.example.tryall.model;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZMJ
 * on 2018/8/21
 */
public class News extends LitePalSupport {
	private int id;
	private String title;
	private String content;
	private Date publishDate;
	private int commentCount;

	//与com.zmj.example.tryall.model.Introduction建立一一对应关系  数据库中一一对应只需要其中一个表创建外键
	private Introduction introduction;

	//与Comment表建立一对多的关系	数据库中一对多需要两个表都有外键
	private List<Comment> commentList = new ArrayList<Comment>();

	//与Category 建立多对多的关系 		数据库中需要新建一个表来来表示多对多的关系
	private List<Category> categoryList = new ArrayList<Category>();


	public News() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public Introduction getIntroduction() {
		return introduction;
	}

	public void setIntroduction(Introduction introduction) {
		this.introduction = introduction;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
}
