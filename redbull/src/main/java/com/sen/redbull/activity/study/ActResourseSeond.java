package com.sen.redbull.activity.study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.sen.redbull.R;
import com.sen.redbull.adapter.ResourceRecyclerAdapter;
import com.sen.redbull.base.BaseActivity;
import com.sen.redbull.imgloader.AnimateFirstDisplayListener;
import com.sen.redbull.mode.EventNoThing;
import com.sen.redbull.mode.ResouceLessonHomeBean;
import com.sen.redbull.mode.ResourSecondItemBean;
import com.sen.redbull.tools.AcountManager;
import com.sen.redbull.tools.Constants;
import com.sen.redbull.tools.DialogUtils;
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
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class ActResourseSeond extends BaseActivity {
    @Bind(R.id.recycleview_resouce_second)
    RecyclerView xRecyclerView;
    @Bind(R.id.resouce_back)
    AppCompatTextView resouce_back;


    @Bind(R.id.resourse_refresh_widget)
    SwipeRefreshLayout swipe_refresh_widget;

    private String classif;


    private static final int GETDATA_ERROR = 0;

    private ResourceRecyclerAdapter adapter;
    private boolean isLoadReflesh = false;
    private LinearLayoutManager linearnLayoutManager;


    private List<ResourSecondItemBean> mLesssListData;
    private List<ResourSecondItemBean> allLesssListData;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    ToastUtils.showTextToast(ActResourseSeond.this, "网络异常，请刷新重试");
                    break;

                case 1:

                    ResouceLessonHomeBean homeBeam = (ResouceLessonHomeBean) msg.obj;
                    mLesssListData = homeBeam.getCourseslist();
                    // 当返回的数据为空的时候，那么就要显示这个
                    if (mLesssListData == null) {
                        ToastUtils.showTextToast(ActResourseSeond.this, "没有数据");
                        return false;
                    }
                    if (mLesssListData.size() == 0) {
                        ToastUtils.showTextToast(ActResourseSeond.this, "没有数据");
                    }

                    allLesssListData.clear();
                    allLesssListData.addAll(mLesssListData);
                    mLesssListData.clear();
                    showRecyclerviewItemData(allLesssListData);

                    break;

            }
            DialogUtils.closeDialog();
            swipe_refresh_widget.setRefreshing(false);
            return false;
        }
    });


    private void showRecyclerviewItemData(List<ResourSecondItemBean> LesssListData) {
        if (adapter == null) {
            //创建并设置Adapter
            adapter = new ResourceRecyclerAdapter(ActResourseSeond.this, allLesssListData);
            xRecyclerView.setAdapter(adapter);
            setOnItemClick();
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public void onEvent(EventNoThing eventNoThing) {


    }


    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        classif = intent.getStringExtra("calssif");


    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_secondresourse_list);
        ButterKnife.bind(this);
        settingRecyleView();

    }

    private void settingRecyleView() {
        allLesssListData = new ArrayList<>();
        linearnLayoutManager = new LinearLayoutManager(this);
        linearnLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(linearnLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        xRecyclerView.setHasFixedSize(true);

        xRecyclerView.addItemDecoration(new RecyleViewItemDecoration(this, R.drawable.shape_recycle_item_decoration));
        adapter = new ResourceRecyclerAdapter(ActResourseSeond.this, allLesssListData);
        xRecyclerView.setAdapter(adapter);
        //设置Item增加、移除动画

        setOnItemClick();
        swipe_refresh_widget.setColorSchemeResources(R.color.theme_color, R.color.theme_color);
        swipe_refresh_widget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        isLoadReflesh = true;
                        getDataFromNet(AcountManager.getAcountId());
                        isLoadReflesh = false;

                    }
                }, 1000);
            }
        });

        xRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipe_refresh_widget.setEnabled(topRowVerticalPosition >= 0);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }

    private void setOnItemClick() {
        adapter.setOnItemClickListener(new ResourceRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ResourSecondItemBean childItemBean) {
                Intent intent = new Intent(ActResourseSeond.this, ActResouceLessonDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemLessonBean", childItemBean);
                bundle.putInt("itemPosition", position);
                intent.putExtra("FragmentStudyBundle", bundle);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        if (NetUtil.isNetworkConnected(this)) {
            if (!TextUtils.isEmpty(classif)) {
                getDataFromNet(AcountManager.getAcountId());
            }
        } else {
            ToastUtils.showTextToast(ActResourseSeond.this, "网络未连接");
        }
    }


    private void getDataFromNet(String userid) {
        //下拉刷新和加载更多就不用show
        if (!isLoadReflesh)
            DialogUtils.showDialog(ActResourseSeond.this, "请稍后");
        String url = Constants.PATH + Constants.PATH_REPOSITORY;
        OkHttpUtils.post()
                .url(url)
                .addParams("userid", userid)
                .addParams("calssif", classif)
                .build()
                .execute(new Callback<ResouceLessonHomeBean>() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public ResouceLessonHomeBean parseNetworkResponse(Response response) throws Exception {

                        String string = response.body().string();
                        Log.e("sen", string);
                        ResouceLessonHomeBean lesssonBean = JSON.parseObject(string, ResouceLessonHomeBean.class);
                        return lesssonBean;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        mHandler.sendEmptyMessage(GETDATA_ERROR);
                    }

                    @Override
                    public void onResponse(ResouceLessonHomeBean homeBeam) {
                        Message message = Message.obtain();
                        message.obj = homeBeam;
                        message.what = 1;
                        mHandler.sendMessage(message);

                    }
                });
    }

    //返回
    @OnClick(R.id.resouce_back)
    public void clickOnBack() {
        finish();
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
        AnimateFirstDisplayListener.displayedImages.clear();
        super.onDestroy();

    }


}
