package com.zmj.example.tryall.model;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMJ
 * on 2018/8/21
 */
public class Category extends LitePalSupport {
	private int id;
	private String name;

	//与News建立多对多关系
	private List<News> newsList = new ArrayList<News>();

	public Category() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
