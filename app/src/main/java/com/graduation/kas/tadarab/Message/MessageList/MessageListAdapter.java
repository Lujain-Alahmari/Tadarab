package com.graduation.kas.tadarab.Message.MessageList;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.kas.tadarab.Message.MessageDetailsActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.SingleItemRowHolder> {

    private ArrayList<MessageList> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;
    SavedData savedData;


    public MessageListAdapter(Activity context, ArrayList<MessageList> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        savedData = new SavedData(context);
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);

    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_list, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, @SuppressLint("RecyclerView") final int pos) {

        final MessageList singleItem = itemsList.get(pos);

        holder.date.setText(singleItem.getDatetime());
        if(savedData.getUsertype().equals("academic_advisor")) {

            if(singleItem.getAdmin_id().equals("0")) {
                holder.name.setText(singleItem.getTraining_advisor_name());
            }else{
                holder.name.setText("Admin");
            }


        }else  if(savedData.getUsertype().equals("training_advisor")) {

            if(singleItem.getAdmin_id().equals("0")) {
                holder.name.setText(singleItem.getAcademic_advisor_name());
            }else{
                holder.name.setText("Admin");
            }

        }
        else  if(savedData.getUsertype().equals("admin")) {

            if(singleItem.getAcademic_advisor_id().equals("0")) {
                holder.name.setText(singleItem.getTraining_advisor_name());
            }else{
                holder.name.setText(singleItem.getAcademic_advisor_name());
            }

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, MessageDetailsActivity.class)
                        .putExtra("admin_id",singleItem.getAdmin_id())
                        .putExtra("training_advisor_id",singleItem.getTraining_advisor_id())
                        .putExtra("academic_advisor_id",singleItem.getAcademic_advisor_id()));


            }
        });


    }


        @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView name,date;

        public SingleItemRowHolder(View view) {
            super(view);

            this.date = (TextView) view.findViewById(R.id.date);
            this.name = (TextView) view.findViewById(R.id.name);


        }

    }

}