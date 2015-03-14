package com.lt.qqlogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lt.util.*;

public class FragmentPage2 extends Fragment{

	private ExpandableListView expandableListView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {		
		super.onActivityCreated(savedInstanceState);
		List<Map<String, Object>> groups=new ArrayList<Map<String,Object>>();
		Map<String, Object> group1=new HashMap<String, Object>();
		group1.put("group", "我的好友");
		groups.add(group1);
		Map<String, Object> group2=new HashMap<String, Object>();
		group2.put("group", "陌生人");
		groups.add(group2);
		Map<String, Object> group3=new HashMap<String, Object>();
		group3.put("group", "黑名单");
		groups.add(group3);
		List<Map<String, Object>> child1=new ArrayList<Map<String,Object>>();
		for(int i=0;i<20;i++)
		{
			Map<String, Object> child1data=new HashMap<String, Object>();
			child1data.put("image", R.drawable.f4);
			child1data.put("bigtext", "逗比"+i);
			child1data.put("id", "20140"+i);
			child1data.put("smalltext", i+"qkasjdlkajsd.asjdk去哦我去打手机拉开");
			child1.add(child1data);
			//System.out.println(child1.get(i).get("bigtext"));
		}
		List<Map<String, Object>> child2=new ArrayList<Map<String,Object>>();
		for(int i=0;i<10;i++)
		{
			Map<String, Object> child2data=new HashMap<String, Object>();
			child2data.put("image", R.drawable.f4);
			child2data.put("bigtext", "hehe"+i);
			child2data.put("id", "20140"+i);
			child2data.put("smalltext", i+"qkasjdlkajsd.asjdk去哦我去打手机拉开");
			child2.add(child2data);
		}
		List<Map<String, Object>> child3=new ArrayList<Map<String,Object>>();
		for(int i=0;i<10;i++)
		{
			Map<String, Object> child3data=new HashMap<String, Object>();
			child3data.put("image", R.drawable.f4);
			child3data.put("bigtext", "hehe"+i);
			child3data.put("id", "20140"+i);
			child3data.put("smalltext", i+"qkasjdlkajsd.asjdk去哦我去打手机拉开");
			child3.add(child3data);
		}
		List<List<Map<String, Object>>> childs=new ArrayList<List<Map<String,Object>>>();
		childs.add(child1);
		childs.add(child2);
		childs.add(child3);
		
		/*SimpleExpandableListAdapter expandableListAdapter=new SimpleExpandableListAdapter(
				getActivity(), groups, R.layout.frag2_explist_group_item, 
				new String[]{"group"}, new int[]{R.id.frag2_explist_group}, childs, 
				R.layout.frag2_explist_child, new String[]{"image","bigtext","smalltext"}, 
				new int[]{R.id.frag2_explist_child_image,R.id.frag2_explist_child_bigtext,R.id.frag2_explist_child_smalltext});
				
				/*new SimpleExpandableListAdapter(getActivity(),
				groups, R.layout.frag2_explist_group_item, new String[]{"group"},new int[]{R.id.frag2_explist_group},
				childs,R.layout.frag2_explist_child,new String[]{"image","bigtext","smalltext",new int[]{R.id.frag2_explist_child_image,
						R.id.frag2_explist_child_bigtext,R.id.frag2_explist_child_smalltext});
				}R.id.frag2_explist_child_image,R.id.frag2_explist_child_bigtext,
					R.id.frag2_explist_child_smalltext*/
		expandableListView=(ExpandableListView)getView().findViewById(R.id.frag2_explist);
		MyExpandableListAdapter expandableListAdapter=new MyExpandableListAdapter(
				getActivity(), groups, childs);
		expandableListView.setAdapter(expandableListAdapter);
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				TextView textView1=(TextView)v.findViewById(R.id.frag2_explist_child_bigtext);
				String string=textView1.getText().toString();
				TextView textView2=(TextView)v.findViewById(R.id.frag2_explist_child_id);
				String string2=textView2.getText().toString();
				//System.out.println(string);
				Intent intent=new Intent(getActivity(), chat.class);
				intent.putExtra("name", string);
				intent.putExtra("id", string2);
				Toast.makeText(getActivity(),  string2, 1).show();
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.enter_righttoleft,R.anim.out_righttoleft);
				return true;
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_2, null);		
	}	
}