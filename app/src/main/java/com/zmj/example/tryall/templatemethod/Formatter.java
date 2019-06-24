package com.zmj.example.tryall.templatemethod;

/**
 * Created by ZMJ
 * on 2018/8/21
 */
public abstract class Formatter {
	public String forMatterBook(Book book,int format){
		beforeFormatter();
		String result = formating(book);
		afterFormatter();
		return  result;
	}

	protected void beforeFormatter(){

	}
	protected abstract String formating(Book book);
	protected void afterFormatter(){

	}
}
