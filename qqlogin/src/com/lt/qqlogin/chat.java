package com.lt.qqlogin;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import Common.*;
import com.lt.util.*;

import com.lt.ConnectServer.*;

import java.io.*;
import java.lang.reflect.Field;

import java.util.*;

public class chat extends Activity {
	
	private ListView listView;
	private EditText editText;
	private Button button;
	private TextView textView;
	private MyChatAdapter adapter;//适配器
	//存储聊天的容器
	private List<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>();
	private String[] from={"image","text"};
	//对应的布局
	private int[] to={R.id.chat_list_my_image,R.id.chat_list_my_text,R.id.chat_list_other_image,R.id.chat_list_other_text};
	private int[] layout={R.layout.chat_list_my_item,R.layout.chat_list_other_item};
	private String myId;//我的id
	private String name;//和我聊天的人的名字
	private String id;//和我聊天的人的id
	private float startX;//手指触摸的开始位置
	private float endX;//手指滑动结束位置
	private float xDistance;//滑动距离
	private Dialog dialog;//表情框
	private int [] images=new int[107];//用来存放表情
	//信息的发送者
	private final static int ME=0;
	private final static int OTHER=1;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置为无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//一定要改变成自己的布局
		setContentView(R.layout.chat_main);
		
		listView=(ListView)findViewById(R.id.chat_list);
		editText=(EditText)findViewById(R.id.chat_bottom_content);
		button=(Button)findViewById(R.id.chat_bottom_send);
		textView=(TextView)findViewById(R.id.chat_title_name);
		
		//取得intent传递的信息
		Intent intent=getIntent();
		name=intent.getStringExtra("name");
		id=intent.getStringExtra("id");
		textView.setText(name);
		myId=friendList.myId;
		
		
		addData("hehe", OTHER);
		addData("hehe", ME);
		adapter=new MyChatAdapter(this, data, from, to, layout);
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String message=(editText.getText()+"").toString();
				if(message.length()==0)
					return;
				editText.setText("");
				addData(message, ME);
				//将信息发送出去
				try {
					ObjectOutputStream oos = new ObjectOutputStream(
							conServer.ct.getS().getOutputStream());
					Message m=new Message();
					m.setSender(myId);
					m.setGetter(id);
					m.setCon(message);
					m.setMesType(MessageType.messgae_com_con);
					oos.writeObject(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//通知listview数据改变了
				adapter.notifyDataSetChanged();
				//让listview始终显示最后一条
			    //listView.setSelection(data.size()-1);
			    //listView.smoothScrollToPosition(listView.getCount()-1);
				listView.setSelection(adapter.getCount()-1);
			}
		});
		//利用MessageListener接口将接收到的信息曲取过来
		conServer.ct.setMl(new MessageListener() {
			
			@Override
			public void Message(Message m) {
				// TODO Auto-generated method stub
				addData(m.getCon(),OTHER);
				System.out.println(m.getGetter()+"接收"+m.getSender()+m.getCon()+"成功");
				//通知listview数据改变了
				adapter.notifyDataSetChanged();
				//让listview始终显示最后一条
			    //listView.setSelection(data.size()-1);
			    //listView.smoothScrollToPosition(listView.getCount()-1);
				listView.setSelection(adapter.getCount()-1);
			}
		});
		listView.setOnTouchListener(listViewOnTouchListener);
		listView.setAdapter(adapter);
		
	}
	//处理Listview滑动事件
	int lastX, curX;
	 private int totalMove = 0;
	 private boolean firstDown = true;//开关
	 int duration = 150;
	OnTouchListener listViewOnTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				lastX = (int) event.getX();
				totalMove = 0;
				firstDown = false;

				return false;
			}
			case MotionEvent.ACTION_MOVE: {
				if (firstDown) {
					curX = (int) event.getX();
					totalMove = 0;
					firstDown = false;
				}
				curX = (int) event.getX();

				int delatX = curX - lastX;
				// if (delatX > 0) {
				totalMove += delatX;
				lastX = curX;
				// }
				//返回为false，会将事件传递给listview    onTouchEvent会被调用
				return false;
			}
			case MotionEvent.ACTION_UP: {
				boolean result = false;
				if (totalMove > 150) {
					//
					totalMove = 0;
					// things you shouold do here
					chat.this.finish();
					overridePendingTransition(R.anim.enter_lefttoright, R.anim.out_lefttoright);
					//返回true，此次事件被消耗掉了，不再将事件传给listview
					result = true;
				}
				if (totalMove < 0 && Math.abs(totalMove) > 150) {
					totalMove = 0;
					// things you shouold do here

					result = true;
				}
				return result;

			}
			}
			return false;

		}
	};
	
	//按钮返回
	public void back(View v)
	{
		chat.this.finish();
		overridePendingTransition(R.anim.enter_lefttoright, R.anim.out_lefttoright);
	}
	
	
	
	//添加数据
	private void addData(String text,int who)
	{
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("person", who);
		map.put("image",who==ME? R.drawable.f4:R.drawable.f4);
		map.put("text", text);
		data.add(map);
	}
	
	//向右滑动返回
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			startX=event.getX();
			System.out.println("sx="+startX);
		}else if(event.getAction()==MotionEvent.ACTION_UP)
		{
			endX=event.getX();
			System.out.println("ex="+endX);
			//向右滑动时
			if(endX>startX)
			{
				xDistance=endX-startX;
				//滑动的距离大于150时才发生
				System.out.println("d="+xDistance);
				if(xDistance>150)
				{
					chat.this.finish();
					overridePendingTransition(R.anim.enter_lefttoright, R.anim.out_lefttoright);
				}
			}
			//一定要返回TRUE,被系统消费掉
			return true;
		}
		
		return super.onTouchEvent(event);
	}

	

	//自定义适配器
	private class MyChatAdapter extends BaseAdapter{

		//定义需要的参数
		Context context;
		List<HashMap<String, Object>>list=new ArrayList<HashMap<String,Object>>();
		String from[];
		int[] to;
		int[] layout;
		
		//构造函数
		public MyChatAdapter(Context context,List<HashMap<String, Object>> list,
				String from[],int[] to,int[] layout)
		{
			this.context=context;
			this.list=list;
			this.from=from;
			this.to=to;
			this.layout=layout;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}


		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			ViewHolder holder=null;
			
			int who=(Integer) list.get(position).get("person");
			convertView=LayoutInflater.from(context).inflate(layout[who==ME? ME:OTHER],null);
			holder=new ViewHolder();
			holder.imageView=(ImageView)convertView.findViewById(to[who*2]);
			holder.textView=(TextView)convertView.findViewById(to[who*2+1]);
			//得到position位置的信息
			holder.imageView.setImageResource((Integer)list.get(position).get(from[0]));
			String str=list.get(position).get(from[1]).toString();
			String zhengze = "f0[0-9]{2}|f10[0-7]";	
			SpannableString spannableString=ExpressionUtil.getExpressionString(context, str, zhengze);
			holder.textView.setText(spannableString);
			
			return convertView;
		}
		
		//用来存储条目的容器
		class ViewHolder
		{
			public ImageView imageView;
			public TextView textView;
		}
	}
	
	//表情触发事件
	public void showEmoticon(View v)
	{
		dialog=new Dialog(chat.this);
		GridView gridView=createGridView();
		dialog.setContentView(gridView);
		dialog.show();
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Bitmap bitmap=null;
				//根据资源id得到图片
				bitmap=BitmapFactory.decodeResource(getResources(), images[position]);
				ImageSpan imageSpan=new ImageSpan(chat.this, bitmap);
				String str=null;
				if(position<10)
				{
					str="f00"+position;
				}else if(position<100)
				{
					str="f0"+position;
				}else
				{
					str="f"+position;
				}
				SpannableString spannableString=new SpannableString(str);
				//替换为图片
				spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				editText.append(spannableString);
				dialog.dismiss();
			}
		});
	}
	
	//初始化GridView
	private GridView createGridView()
	{
		GridView view=new GridView(this);
		List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		for(int i=0;i<107;++i)
		{
			try {
				if(i<10)
				{
					//得到表情资源ID
					Field field=R.drawable.class.getDeclaredField("f00"+i);
					//因为是静态变量所以参数为null
					int resourcId=Integer.parseInt(field.get(null).toString());
					images[i]=resourcId;
				}else if(i<100){
					Field field = R.drawable.class.getDeclaredField("f0" + i);
					int resourceId = Integer.parseInt(field.get(null).toString());
					images[i] = resourceId;
				}else{
					Field field = R.drawable.class.getDeclaredField("f" + i);
					int resourceId = Integer.parseInt(field.get(null).toString());
					images[i] = resourceId;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			Map<String, Object> item=new HashMap<String, Object>();
			item.put("images", images[i]);
			data.add(item);
			
		}
		SimpleAdapter simpleAdapter=new SimpleAdapter(this, data, R.layout.emoticon_item, 
				new String[]{"images"}, new int[]{R.id.emoticon_image});
		view.setAdapter(simpleAdapter);
		//设置每行的数量，以及行距，列距
		view.setNumColumns(6);
		view.setHorizontalSpacing(1);
		view.setVerticalSpacing(1);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		view.setGravity(Gravity.CENTER);
		return view;
	}
	
}
