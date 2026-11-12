package com.graduation.kas.tadarab.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import me.himanshusoni.chatmessageview.ChatMessageView;

public class MessageDetailsAdapter extends RecyclerView.Adapter<MessageDetailsAdapter.MessageHolder> {
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1;

    private List<MessageDetails> mMessages;
    private Context mContext;
    SavedData savedData;

    public MessageDetailsAdapter(Context context, List<MessageDetails> data) {
        mContext = context;
        mMessages = data;
        savedData = new SavedData(context);

    }

    @Override
    public int getItemCount() {
        return mMessages == null ? 0 : mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageDetails item = mMessages.get(position);

        if(!savedData.getUsertype().equals("admin")) {
            if (savedData.getId().equals(item.getSender())) {
                return MY_MESSAGE;
            } else {
                return OTHER_MESSAGE;
            }
        }else{
            if(item.getSender().equals("1") ){
                return OTHER_MESSAGE;

            }else{
                return MY_MESSAGE;

            }
        }

    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MY_MESSAGE) {
            return new MessageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_message, parent, false));
        } else {
            return new MessageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_other_message, parent, false));
        }
    }

    public void add(MessageDetails message) {
        mMessages.add(message);
        notifyItemInserted(mMessages.size() - 1);
    }

    @Override
    public void onBindViewHolder(final MessageHolder holder, final int position) {
        MessageDetails chatMessage = mMessages.get(position);

        holder.tvTime.setText(chatMessage.getDatetime());

        holder.tvMessage.setText(chatMessage.getContent());
        holder.tvMessage.setVisibility(View.VISIBLE);



    }

    class MessageHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;
        ChatMessageView chatMessageView;
        MessageHolder(View itemView) {
            super(itemView);
            chatMessageView = (ChatMessageView) itemView.findViewById(R.id.chatMessageView);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);

        }
    }


}