package com.sen.redbull.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.sen.redbull.R;
import com.sen.redbull.base.BaseActivity;
import com.sen.redbull.mode.EventNoThing;
import com.sen.redbull.tools.AcountManager;
import com.sen.redbull.tools.Constants;
import com.sen.redbull.tools.DialogUtils;
import com.sen.redbull.tools.NetUtil;
import com.sen.redbull.tools.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class ActWriteNotices extends BaseActivity {
    private String getContext;
    private String getThemeText;
    private String bbsid;

    @Bind(R.id.write_back)
    AppCompatTextView write_back;
    @Bind(R.id.et_content)
    AppCompatEditText et_content;
    @Bind(R.id.et_theme)
    AppCompatEditText et_theme;
    @Bind(R.id.btn_submit)
    AppCompatTextView btn_submit;
    private static final int ERROR = 0;
    private static final int SHOW_DATA = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            DialogUtils.closeUnCancleDialog();
            switch (msg.what) {
                case 0:
                    setSubmitBtn(true);
                    Toast.makeText(ActWriteNotices.this, "网络异常，请重试", Toast.LENGTH_SHORT).show();

                    break;
                case 1:
                    boolean isSuccess = (boolean) msg.obj;

                    if (isSuccess) {
                        ToastUtils.showTextToast(ActWriteNotices.this,"创建成功");
                        finish();
                    } else {
                        setSubmitBtn(true);
                        ToastUtils.showTextToast(ActWriteNotices.this,"创建失败，请重新创建");

                    }


                    break;


            }
            return false;
        }
    });



    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        bbsid = intent.getStringExtra("bbsId");
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.actvity_write_notices);
        ButterKnife.bind(this);
        setSubmitBtn(true);


    }

    public void onEvent(EventNoThing event) { //接收方法  在发关事件的线程接收

    }

    @OnClick(R.id.write_back)
    public void close() {

        finish();
    }

    @OnClick(R.id.btn_submit)
    public void submitContent() {

        getContext = et_content.getText().toString().trim();
        getThemeText = et_theme.getText().toString().trim();
        if (TextUtils.isEmpty(getContext) && TextUtils.isEmpty(getThemeText)) {
            ToastUtils.showTextToast(ActWriteNotices.this, "请输入主题和内容");
        }else  if (TextUtils.isEmpty(getContext)) {
            ToastUtils.showTextToast(ActWriteNotices.this, "请输入内容");
        } else if (TextUtils.isEmpty(getThemeText)) {
            ToastUtils.showTextToast(ActWriteNotices.this, "请输入主题");
        }  else {
            btn_submit.setEnabled(false);
            submitReview(getThemeText,getContext);
        }
    }

    public void setSubmitBtn(boolean isCan) {
        btn_submit.setEnabled(isCan);
    }

    private void submitReview(String getThemeText, String context) {
        if (!NetUtil.isNetworkConnected(this)) {
            setSubmitBtn(true);
            Toast.makeText(ActWriteNotices.this, "网络未连接", Toast.LENGTH_SHORT).show();
            return;
        }
        DialogUtils.showunCancleDialog(ActWriteNotices.this, "请稍后");

        String url = Constants.PATH + Constants.PATH_USER_POSTING;
        OkHttpUtils.post()
                .url(url)
                .addParams("userid", AcountManager.getAcountId())
                .addParams("bbsid", bbsid)
                .addParams("flag", "1")
                .addParams("content", context)
                .addParams("title", getThemeText)
                .build()
                .execute(new Callback<Boolean>() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public Boolean parseNetworkResponse(Response response) throws Exception {

                        String string = response.body().string();
//                        Log.e("sen", string);
                        Boolean success = JSON.parseObject(string).getBoolean("success");
                        return success;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        mHandler.sendEmptyMessage(ERROR);


                    }

                    @Override
                    public void onResponse(Boolean isSuccess) {
                        Message message = Message.obtain();
                        message.obj = isSuccess;
                        message.what = SHOW_DATA;
                        mHandler.sendMessage(message);

                    }
                });

    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();

    }

}
