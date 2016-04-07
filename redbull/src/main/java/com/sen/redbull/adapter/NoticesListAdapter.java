package com.sen.redbull.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sen.redbull.R;
import com.sen.redbull.imgloader.AnimateFirstDisplayListener;
import com.sen.redbull.mode.NoticeItemBean;
import com.sen.redbull.tools.Constants;
import com.sen.redbull.tools.DataTool;
import com.sen.redbull.tools.ImageLoadOptions;

import java.util.List;

/**
 * Created by Sen on 2016/2/22.
 */
public class NoticesListAdapter extends RecyclerView.Adapter<NoticesListAdapter.ViewHolder> {
    private List<NoticeItemBean> mData;
    private Context mContext;

    public NoticesListAdapter(Context context, List<NoticeItemBean> data) {
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
        void onItemClick(View view, int position, NoticeItemBean childItemBean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticeslist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClickListener.onItemClick(holder.itemView, position, mData.get(position));
                }

            });
        }

        NoticeItemBean itemBean = mData.get(position);

        String userphoUrl = Constants.PATH_PORTRAIT+itemBean.getPhoto();
        holder.img_userphoto.setTag(userphoUrl);
        ImageLoader.getInstance().displayImage((String) holder.img_userphoto.getTag(), holder.img_userphoto, ImageLoadOptions.getCommentImageOptions(),new AnimateFirstDisplayListener());

        holder.tv_username.setText(itemBean.getCreator());
        holder.item_tv_time.setText(DataTool.timeShow(itemBean.getCreatetime()));

        holder.tv_notices_title.setText(itemBean.getTitle());
        holder.tv_content.setText(itemBean.getContext());
        holder.notices_tv_replyc.setText("("+itemBean.getAnswercount()+")");





    }

   private String dealString(String time){
       if (!TextUtils.isEmpty(time)){
         return  time = time.substring(0 ,16);
       }
       return "";
   }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tv_username,tv_notices_title,item_tv_time,tv_content,notices_tv_replyc;
        public AppCompatImageView img_userphoto;

        public ViewHolder(View view) {
            super(view);
            tv_username = (AppCompatTextView) view.findViewById(R.id.tv_username);
            tv_notices_title = (AppCompatTextView) view.findViewById(R.id.tv_notices_title);
            item_tv_time = (AppCompatTextView) view.findViewById(R.id.item_tv_time);
            tv_content = (AppCompatTextView) view.findViewById(R.id.tv_content);
            notices_tv_replyc = (AppCompatTextView) view.findViewById(R.id.notices_tv_replyc);
            img_userphoto = (AppCompatImageView) view.findViewById(R.id.img_userphoto);

        }


    }
}
