package com.sen.redbull.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sen.redbull.R;
import com.sen.redbull.mode.BbsBean;
import com.sen.redbull.mode.BbsListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BbsExpandAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<BbsListBean> bbsListDate;
	private List<BbsBean> bbsDate;
	private Map<String, String>  map ;

	public BbsExpandAdapter(Context context, List<BbsListBean> bbsListDate,
			List<BbsBean> bbsDate) {
		super();
		this.context = context;
		this.bbsListDate = bbsListDate;
		this.bbsDate = bbsDate;
		map = new HashMap<String, String>() ;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return bbsListDate.get(arg0).getTbztzbbs().get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View v, ViewGroup arg4) {
		// TODO Auto-generated method stub
		v = LayoutInflater.from(context).inflate(R.layout.bbs_explistview, null);
		LinearLayout layout = (LinearLayout) v
				.findViewById(R.id.bbs_explistview_layout);
		layout.setTag(bbsListDate.get(groupPosition).getTbztzbbs().get(childPosition).getId());
		ImageView imv = (ImageView) v.findViewById(R.id.bbs_explistview_imv);
		TextView tv_name = (TextView) v.findViewById(R.id.bbs_explistview_tv);
		tv_name.setText(bbsListDate.get(groupPosition).getTbztzbbs()
				.get(childPosition).getName());
		bbsDate = bbsListDate.get(groupPosition).getTbztzbbs();
		map.put(bbsDate.get(childPosition).getId(), bbsDate.get(childPosition).getName());
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent(context, NoticesList.class);
				intent.putExtra("bbschildId", arg0.getTag()+"");
				intent.putExtra("bbschildTitle", map.get(arg0.getTag()));
				context.startActivity(intent);*/
			}
		});
		return v;
	}

	@Override
	public int getChildrenCount(int arg0) {
		// TODO Auto-generated method stub
		return bbsListDate.get(arg0).getTbztzbbs().size();
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return bbsListDate.get(arg0);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return bbsListDate.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getGroupView(int position, boolean arg1, View view,
			ViewGroup group) {
		// TODO Auto-generated method stub
		view = LayoutInflater.from(context).inflate(
				R.layout.bbs_explistview_group, null);
		bbsDate = bbsListDate.get(position).getTbztzbbs();
		TextView tv_name = (TextView) view
				.findViewById(R.id.bbs_explistview_group_tv);
		tv_name.setText(bbsListDate.get(position).getText());

		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
