package com.lt.qqlogin;

import java.io.ObjectOutputStream;

import com.lt.ConnectServer.conServer;

import Common.MessageType;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

//一定要为每一个activity进行注册
public class friendList extends FragmentActivity {
	
	private FragmentTabHost mTabHost;
	
	private Class fragmentArray[]={FragmentPage1.class,FragmentPage2.class,FragmentPage3.class};
	
	private String text[]={"第一页","第2页","第3页",};
	
	private LayoutInflater inflater;
	public static String myId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);
		Intent intent=getIntent();
		myId=intent.getStringExtra("myId");
		
		initView();
	}

	private void initView() {
		
		inflater=inflater.from(this);
		
		//使用的是系统给的ID
		mTabHost=(FragmentTabHost)findViewById(android.R.id.tabhost);
		
		//对fragmentTabHost进行初始化
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		
		int count=fragmentArray.length;
		
		for(int i=0;i<count;i++)
		{
			//获得每一个标签
			TabSpec tabspec=mTabHost.newTabSpec(text[i]).setIndicator(getView(i));
			
			//将标签以及内容fragment添加进去
			mTabHost.addTab(tabspec,fragmentArray[i],null);
		}
		
	}

	//获得每一个标签的布局
	private View getView(int i) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.tab_item, null);
		TextView textView=(TextView)view.findViewById(R.id.textview);
		textView.setText(text[i]);
		return view;
	}
	
	protected void onDestroy() {
		try {

			//发送登出消息
			ObjectOutputStream oos = new ObjectOutputStream(conServer.ct.getS()
					.getOutputStream());
			Common.Message m = new Common.Message();
			m.setMesType(MessageType.message_logout);
			oos.writeObject(m);
			oos.close();
			conServer.ct.getS().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
