package com.zmj.example.tryall.templatemethod;

/**
 * Created by ZMJ
 * on 2018/8/21
 */
public class XMLFormatter extends Formatter {

	@Override
	protected String formating(Book book) {
		String result = "";
		result += "<book_name>" + book.getBookName() + "</book_name>\n";
		result += "<pages>" + book.getPages() + "</pages>\n";
		result += "<price>" + book.getPrice() + "</price>\n";
		result += "<author>" + book.getAuthor() + "</author>";
		result += "<isbn>" + book.getIsbn() + "</isbn>\n";
		return result;
	}
}
