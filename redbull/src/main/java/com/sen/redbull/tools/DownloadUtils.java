package com.sen.redbull.tools;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.activeandroid.query.Select;
import com.mozillaonline.providers.DownloadManager;
import com.mozillaonline.providers.DownloadManager.Request;
import com.sen.redbull.mode.DownloadFileHistory;
import com.sen.redbull.widget.BaseDialogCumstorTip;
import com.sen.redbull.widget.CustomerDialog;

import java.io.File;

public class DownloadUtils {
    private final static String DOWNROOTDIR = "/RedBullDownload";

    public static void downloadFile(Context context, String url, String fileName, String courseId,String setionId) {
        DownloadManager mDownloadManager = new DownloadManager(context.getContentResolver(), context.getPackageName());
        Uri srcUri = Uri.parse(url);
//		Uri srcUri = Uri.parse("http://dlsw.baidu.com/sw-search-sp/soft/bc/27609/wrar510b4sc.1401936136.exe");
        Request request = new Request(srcUri);
        if (ExistSDCard()) {
//			String downloadDir = Environment.getExternalStorageDirectory()
//					.getAbsolutePath() + DOWNROOTDIR;
//			createDir(downloadDir);
//			request.setDestinationInExternalPublicDir(DOWNROOTDIR + "/", "/");
//		} else {

            String downloadDir = Environment.getDataDirectory().getAbsolutePath() + DOWNROOTDIR;
            createDir(downloadDir);
            request.setDestinationInExternalFilesDir(context,
                    DOWNROOTDIR + "/", setionId + ".mp4");
        }

        request.setTitle(fileName+ "_" + courseId);
        request.setShowRunningNotification(false);

        long downloadId = mDownloadManager.enqueue(request);
        new DownloadFileHistory(fileName,downloadId+"",setionId,url).save();
        BaseDialogCumstorTip.getDefault().showOneBtnDilog(new BaseDialogCumstorTip.DialogButtonOnclickLinster() {
            @Override
            public void onLeftButtonClick(CustomerDialog dialog) {
                if (dialog!=null && dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onRigthButtonClick(CustomerDialog dialog) {

            }
        },250,150,context,"文件正在下载","您可到下载管理查看进度","确定",true,true);

    }


    public static void createDir(String downloadDir) {
        File file = new File(downloadDir);
        if (!file.exists()) {
            file.mkdirs();
        }

    }

    private static boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    public static void insterDownload(Context mContext, String url, String sectionname, String leid,String sectionId) {
       DownloadFileHistory downlodFile = new Select()
                .from(DownloadFileHistory.class)
                .where("lessonid = ?", sectionId)
                .executeSingle();
        if (downlodFile ==null){
            downloadFile(mContext,url,sectionname,leid ,sectionId);
        }else{
            BaseDialogCumstorTip.getDefault().showOneBtnDilog(new BaseDialogCumstorTip.DialogButtonOnclickLinster() {
                @Override
                public void onLeftButtonClick(CustomerDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onRigthButtonClick(CustomerDialog dialog) {

                }
            },250,150,mContext,"已添加到下载队列","您可到下载管理查看进度","关闭",true,true);
        }
    }
}
