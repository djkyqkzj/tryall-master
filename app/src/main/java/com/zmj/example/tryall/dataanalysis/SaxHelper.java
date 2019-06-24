package com.zmj.example.tryall.dataanalysis;

import android.util.Log;

import com.zmj.example.tryall.bean.Person;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ZMJ
 * on 2018/7/13
 */
public class SaxHelper extends DefaultHandler {
	private Person person;
	private ArrayList<Person> persons;

	//当前解析元素标签
	private String tagName = null;

	//读到文档开始标志时调用该方法，通常在这里完成初始化操作
	@Override
	public void startDocument() throws SAXException {
		this.persons = new ArrayList<Person>();
		Log.i("ASX", "startDocument: 读取到文档头开始解析");
	}

	//读到一个开始标签时调用，第二个参数为标签名，最后一个参数为属性数组
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (localName.equals("person")){
			person = new Person();
			person.setId(Integer.parseInt(attributes.getValue("id")));
			Log.i("SAX", "startElement: 开始处理person元素");
		}
		this.tagName = localName;
	}

	//读取内容，第一个参数为字符串内容，起始位置，长度
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (this.tagName != null){
			String data = new String(ch,start,length);
			//读取标签中的内容
			if (this.tagName.equals("name")){
				this.person.setName(data);
				Log.i("SAX", "characters: 处理name元素内容");
			}else if (this.tagName.equals("age"));
				this.person.setAge(Integer.parseInt(data));
			Log.i("SAX", "characters: 处理age元素内容");
		}
	}

	//处理元素结束时调用,这里将对象添加到集合中
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equals("person")){
			this.persons.add(person);
			person = null;
			Log.i("SAX", "endElement: 处理person元素结束");
		}
		this.tagName = null;
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		Log.i("SAX", "endDocument: 读取到文档尾，结束xml解析");
	}

	//获取person集合
	public ArrayList<Person> getPersons(){
		return persons;
	}
}
