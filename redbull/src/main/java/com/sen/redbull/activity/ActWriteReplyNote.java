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
import com.sen.redbull.mode.EventSubmitComentSucess;
import com.sen.redbull.tools.AcountManager;
import com.sen.redbull.tools.Constants;
import com.sen.redbull.tools.DialogUtils;
import com.sen.redbull.tools.NetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class ActWriteReplyNote extends BaseActivity {
    private String bbsid;
    private String askid;
    private String getContext;

    @Bind(R.id.write_back)
    AppCompatTextView write_back;
    @Bind(R.id.et_content)
    AppCompatEditText et_content;
    @Bind(R.id.btn_submit)
    AppCompatTextView btn_submit;
    @Bind(R.id.tv_head_name)
    AppCompatTextView tv_head_name;
    private static final int ERROR = 0;
    private static final int SHOW_DATA = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            DialogUtils.closeUnCancleDialog();
            switch (msg.what) {
                case 0:
                    setSubmitBtn(true);
                    Toast.makeText(ActWriteReplyNote.this, "网络异常，请重试", Toast.LENGTH_SHORT).show();

                    break;
                case 1:
                    boolean isSuccess = (boolean) msg.obj;
                    if (isSuccess) {
                        Toast.makeText(ActWriteReplyNote.this, "回复成功，等待审核", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new EventSubmitComentSucess());
                        finish();
                    } else {
                        setSubmitBtn(true);
                        Toast.makeText(ActWriteReplyNote.this, "回复失败，请重试", Toast.LENGTH_SHORT).show();
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

        bbsid = intent.getStringExtra("bbsid");
        askid = intent.getStringExtra("askid");
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_write_comment);
        ButterKnife.bind(this);
        setSubmitBtn(true);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        tv_head_name.setText("回复");
        et_content.setHint("回复内容");
    }

    @OnClick(R.id.write_back)
    public void close() {

        finish();
    }

    @OnClick(R.id.btn_submit)
    public void submitContent() {

        getContext = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(getContext)) {
            Toast.makeText(ActWriteReplyNote.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            btn_submit.setEnabled(false);
            submitReview(getContext);
        }
    }

    public void setSubmitBtn(boolean isCan) {
        btn_submit.setEnabled(isCan);
    }

    private void submitReview(String context) {
        if (!NetUtil.isNetworkConnected(this)) {
            setSubmitBtn(true);
            Toast.makeText(ActWriteReplyNote.this, "网络未连接", Toast.LENGTH_SHORT).show();
            return;
        }
        DialogUtils.showunCancleDialog(ActWriteReplyNote.this, "请稍后");

        String url = Constants.PATH + Constants.PATH_USER_POSTING;
        OkHttpUtils.post()
                .url(url)
                .addParams("userid", AcountManager.getAcountId())
                .addParams("flag", "2")
                .addParams("content", getContext)
                .addParams("bbsid", bbsid)
                .addParams("askid", askid)
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
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();

    }

}
