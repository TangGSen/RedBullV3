package com.sen.redbull.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.alibaba.fastjson.JSON;
import com.sen.redbull.R;
import com.sen.redbull.base.BaseActivity;
import com.sen.redbull.tools.AcountManager;
import com.sen.redbull.tools.Constants;
import com.sen.redbull.tools.ResourcesUtils;
import com.sen.redbull.tools.ToastUtils;
import com.sen.redbull.widget.BaseDialogCumstorTip;
import com.sen.redbull.widget.CustomerDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class ActHome extends BaseActivity {

   @Bind(R.id.imv_resourse)
   AppCompatImageView imv_resourse;
    @Bind(R.id.imv_bbs)
    AppCompatImageView imv_bbs;
    @Bind(R.id.imv_learn)
    AppCompatImageView imv_learn;

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_act_home);
        ButterKnife.bind(this);
        getVeraon();
        getVersonNet();
    }

    private void getVersonNet() {
        String url = Constants.PATH + Constants.VERSION;
        OkHttpUtils.post()
                .url(url)
                .build()
                .execute(new Callback<Boolean>() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public Boolean parseNetworkResponse(Response response) throws Exception {

                        String string = response.body().string();
                        Log.e("sen", string);
                        Boolean success = JSON.parseObject(string).getBoolean("success");
                        String versionNet = JSON.parseObject(string).getString("version");
                        if (success && !TextUtils.isEmpty(version)){
                            if (!version.equals(versionNet)){
                                return true;
                            }
                        }
                       return false;
                    }

                    @Override
                    public void onError(Call call, Exception e) {


                    }

                    @Override
                    public void onResponse(Boolean flag) {
                        if (flag){
                            showUpdateApk();
                        }
                    }
                });
    }

    private void showUpdateApk() {

        BaseDialogCumstorTip.getDefault().showTwoBtnDialog(new BaseDialogCumstorTip.DialogButtonOnclickLinster() {
            @Override
            public void onLeftButtonClick(CustomerDialog dialog) {
                dialog.dismiss();
                Intent intent = new Intent(ActHome.this,NewVersionActivity.class);
                String newversion = Constants.APK_PATH;
                intent.putExtra("newversion", newversion);
                startActivity(intent);
            }

            @Override
            public void onRigthButtonClick(CustomerDialog dialog) {
                dialog.dismiss();
            }
        },ActHome.this,"更新提示","新版本已发布，请更新！","立即更新","下次再说",true,true);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    private String version;
    /**
     * 获取项目的版本号
     **/
    private void getVeraon() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }




    @OnClick(R.id.imv_resourse)
    public void resoursePage() {
        jumpToMain(0);
    }

    @OnClick(R.id.imv_bbs)
    public void bbsPage() {
        jumpToMain(1);
    }

    @OnClick(R.id.imv_learn)
    public void studyPage() {
        jumpToMain(2);
    }



    public void jumpToMain(int position){
       if (AcountManager.isAcountExist()){
           Intent intent = new Intent(ActHome.this,MainActivity.class);
           intent.putExtra("position",position);
           startActivity(intent);
       }else{
           Intent intent = new Intent(ActHome.this,ActLogin.class);
           intent.putExtra("position",position);
           intent.putExtra("Frome","ActHome");
           startActivity(intent);
       }

    }
    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if((System.currentTimeMillis()-exitTime) >2000)  {
                ToastUtils.showTextToast(ActHome.this, ResourcesUtils.getResString(ActHome.this,R.string.two_down_back_exitapp));

                exitTime = System.currentTimeMillis();
            }else {
                exitApp();
            }
        }
        return true;
    }


}
