package com.sen.redbull.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.sen.redbull.R;
import com.sen.redbull.adapter.NoticesListAdapter;
import com.sen.redbull.base.BaseActivity;
import com.sen.redbull.imgloader.AnimateFirstDisplayListener;
import com.sen.redbull.mode.NoticeItemBean;
import com.sen.redbull.mode.NoticeListHomeBean;
import com.sen.redbull.tools.AcountManager;
import com.sen.redbull.tools.Constants;
import com.sen.redbull.tools.DialogUtils;
import com.sen.redbull.tools.NetUtil;
import com.sen.redbull.tools.ResourcesUtils;
import com.sen.redbull.tools.ToastUtils;
import com.sen.redbull.widget.RecyleViewItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class ActNoticesList extends BaseActivity {


    @Bind(R.id.listview_comment)
    RecyclerView xRecyclerView;
    @Bind(R.id.common_back)
    AppCompatTextView common_back;

    @Bind(R.id.tv_head_name)
    AppCompatTextView tv_head_name;
    @Bind(R.id.btn_write_common)
    AppCompatTextView btn_write_common;

    @Bind(R.id.comment_refresh_widget)
    MaterialRefreshLayout swipe_refresh_widget;

    private LinearLayoutManager linearnLayoutManager;
    ;
    private String bbschildId;
    private String bbschildTitle;


    private List<NoticeItemBean> noticesList;
    private List<NoticeItemBean> allNoticesList;

    private int maxPage = 0;
    private int currentPage = 1;

    private static final int GETDATA_ERROR = 0;
    private static final int SHOW_DATA = 1;

    private NoticesListAdapter adapter;
    private boolean isLoadReflesh = false;
    private boolean isLoadMore = false;


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    Toast.makeText(ActNoticesList.this, "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    NoticeListHomeBean homeBeam = (NoticeListHomeBean) msg.obj;
                    maxPage = homeBeam.getMaxPage();
                    noticesList = homeBeam.getAskList();

                    if (noticesList == null) {
                        Toast.makeText(ActNoticesList.this, "当前没数据", Toast.LENGTH_SHORT).show();
                    }
                    if (noticesList.size() == 0) {
                        Toast.makeText(ActNoticesList.this, "当前没数据", Toast.LENGTH_SHORT).show();
                    }
                    if (!isLoadMore) {
                        allNoticesList.clear();
                    } else {
                        isLoadMore = false;
                    }
                    allNoticesList.addAll(noticesList);

                    noticesList.clear();
                    //创建并设置Adapter
                    if (adapter == null) {

                    } else {
                        adapter.notifyDataSetChanged();
                    }


                    break;


            }
            DialogUtils.closeDialog();
            return false;
        }
    });


    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        bbschildId = intent.getStringExtra("bbschildId");
        bbschildTitle = intent.getStringExtra("bbschildTitle");

    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        Drawable drawable=ResourcesUtils.getResDrawable(ActNoticesList.this,R.mipmap.bbs);
/// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        btn_write_common.setCompoundDrawables(null,null,drawable,null);
        settingRecyleView();

    }

    private void settingRecyleView() {
        allNoticesList = new ArrayList<>();
        linearnLayoutManager = new LinearLayoutManager(this);
        linearnLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(linearnLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        xRecyclerView.setHasFixedSize(true);

        xRecyclerView.addItemDecoration(new RecyleViewItemDecoration(this, R.drawable.shape_recycle_item_decoration));
        adapter = new NoticesListAdapter(ActNoticesList.this, allNoticesList);
        adapter.setOnItemClickListener(new NoticesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, NoticeItemBean childItemBean) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("NoticeItemBean",childItemBean);
                Intent intent = new Intent(ActNoticesList.this,ActNoticesItemDetail.class);
                intent.putExtra("NoticeBundle",bundle);
                startActivity(intent);
            }
        });
        xRecyclerView.setAdapter(adapter);
        swipe_refresh_widget.setLoadMore(true);
        swipe_refresh_widget.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        currentPage = 1;
                        isLoadReflesh = true;

                        getCommntList(1);
                        isLoadReflesh = false;
                        swipe_refresh_widget.finishRefresh();
                    }
                }, 1000);
            }


            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                mHandler.postDelayed(new Runnable() {
                    public void run() {


                        if (maxPage == currentPage) {
                            Toast.makeText(ActNoticesList.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                            swipe_refresh_widget.finishRefreshLoadMore();
                            return;
                        }
                        isLoadMore = true;
                        currentPage++;
                        getCommntList(currentPage);

                        swipe_refresh_widget.finishRefreshLoadMore();
                        ;
                    }
                }, 1000);
            }
        });


    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        tv_head_name.setText(bbschildTitle);

        if (NetUtil.isNetworkConnected(this)) {
            getCommntList(1);
        } else {
            ToastUtils.showTextToast(this, "网络未连接");
        }
    }


    private void getCommntList(int page) {
        if (!isLoadMore && !isLoadReflesh) {
            DialogUtils.showDialog(this, "请稍等");
        }
        if (!NetUtil.isNetworkConnected(this)) {
            DialogUtils.closeDialog();
            Toast.makeText(ActNoticesList.this, "网络未连接", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = Constants.PATH + Constants.PATH_NoticesList;
        OkHttpUtils.post()
                .url(url)
                .addParams("flag", "1")
                .addParams("pageNum", page + "")
                .addParams("bbschildId", bbschildId)
                .addParams("user_id", AcountManager.getAcountId())
                .build()
                .execute(new Callback<NoticeListHomeBean>() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public NoticeListHomeBean parseNetworkResponse(Response response) throws Exception {

                        String string = response.body().string();
                        Log.e("sen", string);
                        NoticeListHomeBean lesssonBean = JSON.parseObject(string, NoticeListHomeBean.class);
                        return lesssonBean;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        mHandler.sendEmptyMessage(GETDATA_ERROR);


                    }

                    @Override
                    public void onResponse(NoticeListHomeBean homeBeam) {
                        Message message = Message.obtain();
                        message.obj = homeBeam;
                        message.what = SHOW_DATA;
                        mHandler.sendMessage(message);

                    }
                });
    }

    //返回
    @OnClick(R.id.common_back)
    public void clickOnBack() {

        finish();
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_write_common)
    public void clickOnWriteComment() {
        Intent intent = new Intent(ActNoticesList.this, ActWriteNotices.class);
        intent.putExtra("bbsId", bbschildId);

        startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
        AnimateFirstDisplayListener.displayedImages.clear();
        super.onDestroy();

    }


}
