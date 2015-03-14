/**
 * 自定义的expandablelitview适配器
 */
package com.lt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lt.qqlogin.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter{

	
	Context context;
	List<Map<String, Object>> groups;
	List<List<Map<String, Object>>> childs;
	
	
	public MyExpandableListAdapter(Context context,
			List<Map<String, Object>> groups,
			List<List<Map<String, Object>>> childs) {
		this.context = context;
		this.groups = groups;
		this.childs = childs;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		//不能返回错了
		return childs.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childs.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.frag2_explist_group_item, null);
		TextView textView=(TextView)convertView.findViewById(R.id.frag2_explist_group);
		String string=groups.get(groupPosition).get("group").toString();
		textView.setText(string);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.frag2_explist_child, null);
		ImageView imageView=(ImageView)convertView.findViewById(R.id.frag2_explist_child_image);
		TextView textView1=(TextView)convertView.findViewById(R.id.frag2_explist_child_bigtext);
		TextView textView2=(TextView)convertView.findViewById(R.id.frag2_explist_child_id);
		TextView textView3=(TextView)convertView.findViewById(R.id.frag2_explist_child_smalltext);
		
		imageView.setImageResource((Integer)childs.get(groupPosition).get(childPosition).get("image"));
		textView1.setText(childs.get(groupPosition).get(childPosition).get("bigtext").toString());
		textView2.setText(childs.get(groupPosition).get(childPosition).get("id").toString());
		textView3.setText(childs.get(groupPosition).get(childPosition).get("smalltext").toString());
		
		//System.out.println("hahhaha");
		return convertView;
	}

	//可以设置二级条目点击事件
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
