package com.kelly.testcoordinatorlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author zongkaili
 */
@SuppressLint("InflateParams")
public class ConversationAdapter extends BaseAdapter {
    //    private List<EMConversation> top_list;
    private LayoutInflater inflater;
    private Context context;

    @SuppressLint("SdCardPath")
    public ConversationAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        convertView = creatConvertView();
        // 初始化控件
        // 昵称
        holder.mTvName = (TextView) convertView.findViewById(R.id.recent_list_item_name);
        // 未读消息
        // 最近一条消息
        holder.mTvMsgContent = (TextView) convertView.findViewById(R.id.recent_list_item_msg);
        // 时间
        holder.mTvTime = (TextView) convertView.findViewById(R.id.recent_list_item_time);
        // 发送状态

        return convertView;
    }

    private View creatConvertView() {
        View convertView;
        convertView = inflater.inflate(R.layout.chat_item_conversation_single, null, false);
        return convertView;
    }

    private static class ViewHolder {
        /**
         * 和谁的聊天记录
         */
        TextView mTvName;
        /**
         * 消息未读数
         */
        TextView mTvUnread;
        /**
         * 最后一条消息的内容
         */
        TextView mTvMsgContent;
        /**
         * 最后一条消息的时间
         */
        TextView mTvTime;
        /**
         * 用户头像
         */
        ImageView mIvAvatar;
        /**
         * 消息未发送状态
         **/
        ImageView mMsgState;

    }
}
