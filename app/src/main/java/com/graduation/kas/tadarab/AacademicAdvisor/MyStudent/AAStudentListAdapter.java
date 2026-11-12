package com.graduation.kas.tadarab.AacademicAdvisor.MyStudent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduation.kas.tadarab.Admin.Users.StudentDetailsActivity;
import com.graduation.kas.tadarab.Admin.Users.Users;
import com.graduation.kas.tadarab.Message.MessageDetailsActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AAStudentListAdapter extends RecyclerView.Adapter<AAStudentListAdapter.SingleItemRowHolder> {

    private ArrayList<Users> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;

    SavedData savedData;
    public AAStudentListAdapter(Activity context, ArrayList<Users> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
        savedData = new SavedData(mContext);

    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student_aa, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, @SuppressLint("RecyclerView") final int pos) {

        final Users singleItem = itemsList.get(pos);

        holder.username.setText(singleItem.getUsername());
        holder.name.setText(singleItem.getName());
        holder.mobile.setText(singleItem.getMobile());
        if(singleItem.getAname().equals("") || singleItem.getAname().equals("null")){
            holder.advisor.setText("No Advisor");
        }else{
            holder.advisor.setText(singleItem.getAname());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentDetailsActivity.users = singleItem;
                mContext.startActivity(new Intent(mContext,StudentDetailsActivity.class));
            }
        });

        if(singleItem.getTraining_advisor_id().equals("null") || singleItem.getTraining_advisor_id() == null){
            holder.chat.setVisibility(View.GONE);
        }else{
            holder.chat.setVisibility(View.VISIBLE);
        }

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, MessageDetailsActivity.class)
                        .putExtra("admin_id","0")
                        .putExtra("training_advisor_id",singleItem.getTraining_advisor_id())
                        .putExtra("academic_advisor_id",savedData.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView username,name,mobile,advisor;
        protected LinearLayout chat;

        public SingleItemRowHolder(View view) {
            super(view);

            this.username = (TextView) view.findViewById(R.id.username);
            this.name = (TextView) view.findViewById(R.id.name);
            this.mobile = (TextView) view.findViewById(R.id.mobile);
            this.advisor = (TextView) view.findViewById(R.id.advisor);

            this.chat =  view.findViewById(R.id.chat);


        }

    }

}