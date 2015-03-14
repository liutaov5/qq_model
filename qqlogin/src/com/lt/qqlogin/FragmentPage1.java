package com.lt.qqlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.*;



public class FragmentPage1 extends Fragment{

	private ListView listview;
	
	@Override
	//在Activity创建好后会调用这个函数
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view=getView();
		listview=(ListView)view.findViewById(R.id.frag1_list);
		listview.setOnItemClickListener(new ItemClickListener());
		show();
		
	}
	private final class ItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			ListView view2=(ListView)parent;
			HashMap<String, Object>map=new HashMap<String, Object>();
			map=(HashMap<String, Object>)view2.getItemAtPosition(position);
			//Toast.makeText(getActivity(), map.get("name")+"", 1).show();
			Intent intent=new Intent(getActivity(), chat.class);
			//将id发送出去
			intent.putExtra("name",map.get("name")+"");
			intent.putExtra("myId", friendList.myId);
			intent.putExtra("id", map.get("name")+"");
			Toast.makeText(getActivity(),  friendList.myId, 1).show();
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.enter_righttoleft,R.anim.out_righttoleft);
		}
		
	}
	private void show()
	{
		List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
		//添加数据
		for(int i=1;i<20;i++)
		{
			HashMap<String, Object>mp=new HashMap<String, Object>();
			mp.put("image", R.drawable.f4);
			mp.put("name", "20140"+i);
			data.add(mp);
		}
		
		//使用适配器加载数据
		SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(), data, R.layout.frag1_list_item, 
				new String[]{"image","name"},new int[]{R.id.frag1_list_imag,R.id.frag1_list_text});
		listview.setAdapter(simpleAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_1, null);		
	}	
}








