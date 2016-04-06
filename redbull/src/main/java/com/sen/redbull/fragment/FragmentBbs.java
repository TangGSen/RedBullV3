package com.sen.redbull.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.sen.redbull.R;
import com.sen.redbull.adapter.BbsExpandAdapter;
import com.sen.redbull.base.BaseFragment;
import com.sen.redbull.mode.BBSData;
import com.sen.redbull.mode.BbsBean;
import com.sen.redbull.mode.BbsListBean;
import com.sen.redbull.mode.BbsListHomeBean;
import com.sen.redbull.tools.AcountManager;
import com.sen.redbull.tools.Constants;
import com.sen.redbull.tools.DialogUtils;
import com.sen.redbull.tools.ResourcesUtils;
import com.sen.redbull.tools.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;


@SuppressLint("NewApi")
public class FragmentBbs extends BaseFragment {
	private TextView tv_theme;
	private ExpandableListView explistview;
	private List<BbsListBean> bbsListBean;
	private List<BbsBean> bbsDate;
	private BbsExpandAdapter adapter;
	private View rootView;
	@Bind(R.id.bbs_expandablelistview)
	ExpandableListView bbs_expandablelistview;
	private static final int GETDATA_ERROR = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_bbs_layout, container, false);
	}

	@Override
	protected void dealAdaptationToPhone() {

	}

	@Override
	protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		settingListView();
		return null;
	}

	private void settingListView() {
		explistview.setGroupIndicator(null);
		bbsListBean = new ArrayList<BbsListBean>();
		bbsDate = new ArrayList<BbsBean>();
		explistview.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1,
										int arg2, long arg3) {
				return true;
			}
		});

	}

	@Override
	protected void initData() {
		getDataFromNet(AcountManager.getAcountId());
	}


	private Handler mHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {


			switch (msg.what) {
				case 0:
					Toast.makeText(getActivity(), R.string.net_error_retry, Toast.LENGTH_SHORT).show();
					break;

				case 1:
					BBSData bbsDatas = (BBSData) msg.obj;
					if (bbsDatas==null){
						ToastUtils.showTextToast(getContext(),"没有数据，请重试");
					 return  false;
					}

					bbsListBean =bbsDatas.getBbsListBean();
					bbsDate = bbsDatas.getBbsDate();
					adapter = new BbsExpandAdapter(getActivity(), bbsListBean, bbsDate);
					explistview.setAdapter(adapter);
					for (int i = 0; i < bbsListBean.size(); i++) {
						if (bbsListBean.get(i).getTbztzbbs() != null) {
							explistview.expandGroup(i);
						}

					}


					break;

			}
			//该关闭就关闭
			DialogUtils.closeDialog();
			return false;
		}
	});


	private void getDataFromNet(String userid) {
		//下拉刷新和加载更多就不用show
		//if (!isReFlesh)
		DialogUtils.showDialog(getActivity(), ResourcesUtils.getResString(getContext(), R.string.dialog_show_wait));
		String url = Constants.PATH + Constants.PATH_BBSList;
		OkHttpUtils.post()
				.url(url)
				.addParams("userid", userid)
				.build()
				.execute(new Callback<BBSData>() {


					@Override
					public BBSData parseNetworkResponse(Response response) throws Exception {
						String string = response.body().string();
						Log.e("sen", string);
						BbsListHomeBean bbsListHomeBean = JSON.parseObject(string, BbsListHomeBean.class);
						BBSData bbsData =null;
						if ("true".equals(bbsListHomeBean.getSuccess())) {
							bbsListBean = bbsListHomeBean.getBBSList();
							for (int h = 0; h < bbsListBean.size(); h++) {
								if (bbsListBean.get(h).getTbztzbbs() == null) {
									bbsDate = null;
								} else {
									bbsDate = bbsListBean.get(h).getTbztzbbs();
								}
								bbsData = new BBSData(bbsListBean, bbsDate);
							}
						}


							return bbsData;
					}

						@Override
						public void onError(Call call, Exception e) {
							mHandler.sendEmptyMessage(GETDATA_ERROR);
						}

						@Override
						public void onResponse(BBSData data) {
							Message message = Message.obtain();
							message.obj = data;
							message.what = 1;
							mHandler.sendMessage(message);
					}
				});
			}


}




	






	



