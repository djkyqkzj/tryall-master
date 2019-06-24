package com.zmj.example.tryall.model;

import org.litepal.crud.LitePalSupport;

/**
 * Created by ZMJ
 * on 2018/8/21
 */
public class Introduction extends LitePalSupport {
	private int id;
	private String guide;
	private String digest;

	public Introduction() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}
}
