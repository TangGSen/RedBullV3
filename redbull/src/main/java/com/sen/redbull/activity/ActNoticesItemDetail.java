package com.sen.redbull.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sen.redbull.R;
import com.sen.redbull.adapter.ReplyListAdapter;
import com.sen.redbull.base.BaseActivity;
import com.sen.redbull.imgloader.AnimateFirstDisplayListener;
import com.sen.redbull.mode.NoticeItemBean;
import com.sen.redbull.mode.ReplyNoticeItemBean;
import com.sen.redbull.mode.ReplyNoticeListBean;
import com.sen.redbull.tools.AcountManager;
import com.sen.redbull.tools.Constants;
import com.sen.redbull.tools.DataTool;
import com.sen.redbull.tools.DialogUtils;
import com.sen.redbull.tools.ImageLoadOptions;
import com.sen.redbull.tools.NetUtil;
import com.sen.redbull.tools.ToastUtils;
import com.sen.redbull.widget.RecyleViewItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ActNoticesItemDetail extends BaseActivity {


    @Bind(R.id.reply_imv_head)
    AppCompatImageView reply_imv_head;
    @Bind(R.id.tv_name)
    AppCompatTextView tv_name;
    @Bind(R.id.tv_theme)
    AppCompatTextView tv_theme;
    @Bind(R.id.tv_content)
    AppCompatTextView tv_content;
    @Bind(R.id.tv_time)
    AppCompatTextView tv_time;
    @Bind(R.id.imgbtn_report)
    AppCompatImageButton imgbtn_report;
    @Bind(R.id.reply_refresh_widget)
    MaterialRefreshLayout reply_refresh_widget;
    @Bind(R.id.listview_reply_notice)
    RecyclerView listview_reply_notice;
    @Bind(R.id.imgbtn_close)
    AppCompatTextView imgbtn_close;
    private NoticeItemBean childItemBean;

    private int maxPage;
    private int currentPage = 1;
    private List<ReplyNoticeItemBean> replyList;
    private List<ReplyNoticeItemBean> allReplyList;
    private ReplyListAdapter adapter;
    private boolean isLoadReflesh;
    private boolean isLoadMore;

    private static final int GETDATA_ERROR = 0;
    private static final int SHOW_DATA = 1;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    Toast.makeText(ActNoticesItemDetail.this, "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    ReplyNoticeListBean homeBeam = (ReplyNoticeListBean) msg.obj;
                    maxPage = homeBeam.getMaxPage();
                    replyList = homeBeam.getAnswerList();

                    if (replyList == null) {
                        return false;
                    }

                    if (!isLoadMore) {
                        allReplyList.clear();
                    }
                    isLoadMore = false;
                    isLoadReflesh = false;

                    allReplyList.addAll(replyList);
                    replyList.clear();
                    //创建并设置Adapter
                    if (adapter == null) {
                        adapter = new ReplyListAdapter(ActNoticesItemDetail.this, allReplyList);
                        listview_reply_notice.setAdapter(adapter);
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
        Bundle bundle = intent.getBundleExtra("NoticeBundle");
        childItemBean = (NoticeItemBean) bundle.getSerializable("NoticeItemBean");


    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.acttivity_reply_noticelist);
        ButterKnife.bind(this);
        settingRecyleView();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (childItemBean == null) {
            ToastUtils.showTextToast(ActNoticesItemDetail.this, "获取数据失败，请返回重试");
            return;
        }
        initViewData();
        if (NetUtil.isNetworkConnected(this)) {
            getReplyData(currentPage);
        } else {
            ToastUtils.showTextToast(ActNoticesItemDetail.this, "网络未连接");
        }


    }


    private void initViewData() {

        tv_name.setText(childItemBean.getCreator());
        tv_content.setText(childItemBean.getContext());
        tv_theme.setText(childItemBean.getTitle());
        String time = DataTool.timeShow(childItemBean.getCreatetime());
        tv_time.setText(time);
        String photourl = Constants.PATH_PORTRAIT + childItemBean.getPhoto();

        ImageLoader.getInstance().displayImage(photourl, reply_imv_head, ImageLoadOptions.getCommentImageOptions(), new AnimateFirstDisplayListener());

    }

    private void settingRecyleView() {
        allReplyList = new ArrayList<>();
        LinearLayoutManager linearnLayoutManager = new LinearLayoutManager(this);
        linearnLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview_reply_notice.setLayoutManager(linearnLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        listview_reply_notice.setHasFixedSize(true);

        listview_reply_notice.addItemDecoration(new RecyleViewItemDecoration(this, R.drawable.shape_recycle_item_decoration));
        // adapter = new CommentListAdapter(ActCommentList.this, allCommonList);
        listview_reply_notice.setAdapter(adapter);
        reply_refresh_widget.setLoadMore(true);
        reply_refresh_widget.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        currentPage = 1;
                        isLoadReflesh = true;
                        getReplyData(currentPage);

                        reply_refresh_widget.finishRefresh();
                    }
                }, 1000);
            }


            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (maxPage == currentPage) {
                            //  Toast.makeText(ActNoticesItemDetail.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                            reply_refresh_widget.finishRefreshLoadMore();
                            return;
                        }
                        isLoadMore = true;
                        currentPage++;
                        getReplyData(currentPage);

                        reply_refresh_widget.finishRefreshLoadMore();
                        ;
                    }
                }, 1000);
            }
        });


    }


    private void getReplyData(int currentPage) {

        if (!isLoadMore && !isLoadReflesh) {
            DialogUtils.showDialog(this, "请稍等");
        }

        String url = Constants.PATH + Constants.PATH_NoticesList;
        OkHttpUtils.post()
                .url(url)
                .addParams("flag", "2")
                .addParams("pageNum", currentPage + "")
                .addParams("bbschildId", childItemBean.getBbschildren_id())
                .addParams("user_id", AcountManager.getAcountId())
                .addParams("ask_id", childItemBean.getId())
                .build()
                .execute(new Callback<ReplyNoticeListBean>() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public ReplyNoticeListBean parseNetworkResponse(Response response) throws Exception {

                        String string = response.body().string();
                        Log.e("sen", string);
                        ReplyNoticeListBean lesssonBean = JSON.parseObject(string, ReplyNoticeListBean.class);
                        return lesssonBean;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        mHandler.sendEmptyMessage(GETDATA_ERROR);


                    }

                    @Override
                    public void onResponse(ReplyNoticeListBean homeBeam) {
                        Message message = Message.obtain();
                        message.obj = homeBeam;
                        message.what = SHOW_DATA;
                        mHandler.sendMessage(message);

                    }
                });
    }

    //点击事件
    @OnClick(R.id.imgbtn_report)
    public void writeReply() {
        Intent intent = new Intent(ActNoticesItemDetail.this, ActWriteReplyNote.class);
       intent.putExtra("bbsid",childItemBean.getBbschildren_id());
       intent.putExtra("askid",childItemBean.getId());
        startActivity(intent);
    }  //点击事件
    @OnClick(R.id.imgbtn_close)
    public void close() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        AnimateFirstDisplayListener.displayedImages.clear();
    }

}
