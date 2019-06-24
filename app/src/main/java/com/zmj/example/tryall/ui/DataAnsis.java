package com.zmj.example.tryall.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zmj.example.tryall.R;
import com.zmj.example.tryall.bean.Person;
import com.zmj.example.tryall.dataanalysis.SaxHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class DataAnsis extends AppCompatActivity implements View.OnClickListener {

	private Button SAX_xml,btn_Json;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_ansis);

		SAX_xml = findViewById(R.id.SAX_xml);
		btn_Json = findViewById(R.id.btn_Json);

		SAX_xml.setOnClickListener(this);
		btn_Json.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.SAX_xml:
				try{
					readXmlForSAX();
				}catch (Exception e){
					e.printStackTrace();
				}
				break;
			case R.id.btn_Json:
				parseEasyJson(personJson);
				if (persons != null){
					Toast.makeText(this,persons.toString(),Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(this,"尚未解析",Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
		}
	}
	//SXA数据解析
	private ArrayList<Person> readXmlForSAX()throws Exception{
		//获取文件资源建立输入流对象
		InputStream is = getAssets().open("person.xml");
		//创建xml解析处理器
		SaxHelper sh = new SaxHelper();
		//得到SAX解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//创建SAX解析器
		SAXParser parser = factory.newSAXParser();
		//将xml解析处理器分配给解析器，对文档进行解析，将事件发送给处理器
		parser.parse(is,sh);
		is.close();
		return  sh.getPersons();
	}

	private String personJson = "[\n" +
			"    { \"id\":\"1\",\"name\":\"基神\",\"age\":\"18\" },\n" +
			"    { \"id\":\"2\",\"name\":\"B神\",\"age\":\"18\"  },\n" +
			"    { \"id\":\"3\",\"name\":\"曹神\",\"age\":\"18\" }\n" +
			"]";
	private ArrayList<Person> persons = null;
	private void parseEasyJson(String esayjson){
		persons = new ArrayList<Person>();

		JSONArray jsonArray = null;
		try{
			jsonArray = new JSONArray(esayjson);
			Person person = new Person();
			for (int i = 0; i < jsonArray.length();i++){
				JSONObject object = jsonArray.getJSONObject(i);
				person.setId(Integer.parseInt(object.getString("id")));
				person.setName(object.optString("name"));
				person.setAge(Integer.parseInt(object.optString("age")));
				persons.add(person);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
