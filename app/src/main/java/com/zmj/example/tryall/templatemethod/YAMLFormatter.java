package com.zmj.example.tryall.templatemethod;

/**
 * Created by ZMJ
 * on 2018/8/21
 */
public class YAMLFormatter extends Formatter {
	@Override
	protected String formating(Book book) {
		String result = "";
		result += "book_nam:" + book.getBookName() + "\n";
		result += "pages:" + book.getPages() + "\n";
		result += "price:" + book.getPrice() + "\n";
		result += "author:" + book.getAuthor() + "\n";
		result += "isbn:" + book.getIsbn() + "\n";
		return result;
	}
}
