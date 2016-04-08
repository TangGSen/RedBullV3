package com.sen.redbull.tools;

import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.sen.redbull.mode.StudyProgressDb;

/**
 * Created by Administrator on 2016/3/18.
 * 用户管理
 */
public class StudyProgressManager {


    // 查询
    public static int getTimeById(String leid){

        //从数据库
        StudyProgressDb progressDb = new Select()
                .from(StudyProgressDb.class)
                .where("lessonid = ?", leid)
                .executeSingle();
        if (progressDb==null){
            return 0;
        }
        int time = progressDb.getTime();

        return time;
    }

    // 更改
    public static void insertTimeById(String leid,int time){

        //从数据库
        StudyProgressDb progressDb = new Select()
                .from(StudyProgressDb.class)
                .where("lessonid = ?", leid)
                .executeSingle();
        if (progressDb==null){
            new StudyProgressDb(leid,time).save();
            Log.e("sen","新建id");

        }else{
            //谁能告诉我这样为啥不好使？我用错了么
          // new Update(StudyProgressDb.class).set( "time=?", time).where("lessonid=?" , leid).execute();
            new Delete().from(StudyProgressDb.class).where("lessonid = ?", leid).execute();
            new StudyProgressDb(leid,time);
            Log.e("sen","修改id的time");
        }


    }

//    删除
    public  static void deleLeidData(String leid){
        new Delete().from(StudyProgressDb.class).where("lessonid = ?", leid).execute();
    }

}
