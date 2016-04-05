package com.sen.redbull.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sen.redbull.R;
import com.sen.redbull.mode.ExamItemBean;
import com.sen.redbull.tools.DataTool;

import java.util.List;

/**
 * Created by Sen on 2016/2/22.
 */
public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ViewHolder> {
    private List<ExamItemBean> mData;
    private Context mContext;

    public ExamListAdapter(Context context, List<ExamItemBean> data) {
        mContext = context;
        mData = data;
    }

//    public void addLessonBeanData(List<LessonItemBean> data){
//        mData.addAll(data);
//        notifyDataSetChanged();
//    }

    private OnItemClickListener onItemClickListener = null;


    //Item click thing
    public interface OnItemClickListener {
        void onItemClick(View view, int position, ExamItemBean childItemBean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHodler, final int position) {


        ExamItemBean examBean = mData.get(position);
        viewHodler.tv_exam_name.setText("试卷名称："+examBean.getTitle());
        viewHodler.txt_pass.setText("通过分数："+examBean.getPass_mark());

        viewHodler.txt_join_able.setText("可考次数："+examBean.getJoincount());
        viewHodler.txt_had_join.setText("已考次数："+examBean.getYetjoincount());
        String str_begin =examBean.getEntertime_begin();
        String str_end =examBean.getEntertime_end();
        if (!TextUtils.isEmpty(str_begin)) {
            str_begin = DataTool.longToTime(Long.parseLong(str_begin));
        }else {
            str_begin = "";
        }
        if (!TextUtils.isEmpty(str_end)) {
            str_end =DataTool.longToTime(Long.parseLong(str_end));
        }else {
            str_end = "";
        }

        viewHodler.txt_exam_begin.setText("开考日期："+str_begin);
        viewHodler.txt_exam_end.setText("结束日期："+str_end);

        if (onItemClickListener != null) {
            viewHodler.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClickListener.onItemClick(viewHodler.itemView, position,mData.get(position));
                }

            });
        }




    }







    @Override
    public int getItemCount() {
        return mData.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tv_exam_name,txt_pass,txt_exam_begin,txt_join_able,txt_exam_end,txt_had_join;

        public ViewHolder(View view) {
            super(view);
            tv_exam_name = (AppCompatTextView) view.findViewById(R.id.tv_exam_name);
            txt_pass = (AppCompatTextView) view.findViewById(R.id.txt_pass);
            txt_exam_begin = (AppCompatTextView) view.findViewById(R.id.txt_exam_begin);
            txt_exam_end = (AppCompatTextView) view.findViewById(R.id.txt_exam_end);
            txt_join_able = (AppCompatTextView) view.findViewById(R.id.txt_join_able);
            txt_had_join = (AppCompatTextView) view.findViewById(R.id.txt_had_join);


        }


    }
}
