package com.graduation.kas.tadarab.TrainingAdvisor.Request.Forms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.WebSiteData;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class TrainingFormsAdapter extends RecyclerView.Adapter<TrainingFormsAdapter.SingleItemRowHolder> {

    private ArrayList<Forms> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;

    public TrainingFormsAdapter(Activity context, ArrayList<Forms> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);

    }


    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_training_forms, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, @SuppressLint("RecyclerView") final int pos) {

        final Forms singleItem = itemsList.get(pos);

        if(singleItem.getReply().equals(" ")) {
            holder.reply.setText("لا يوجد رد");
            holder.downloadreply.setVisibility(View.GONE);
            holder.reply.setVisibility(View.VISIBLE);
        }else{
            holder.reply.setText(singleItem.getReply());
            holder.downloadreply.setVisibility(View.VISIBLE);
            holder.reply.setVisibility(View.GONE);
        }

        holder.type.setText(singleItem.getType());
        holder.file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebSiteData.domain + "uploaded/"+singleItem.getFile()));
                mContext.startActivity(browserIntent);

            }
        });

        holder.downloadreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebSiteData.domain + "uploaded/"+singleItem.getReply()));
                mContext.startActivity(browserIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView type,reply;
        protected Button file,downloadreply;


        public SingleItemRowHolder(View view) {
            super(view);

            this.type = (TextView) view.findViewById(R.id.type);
            this.reply = (TextView) view.findViewById(R.id.reply);
            this.file = (Button) view.findViewById(R.id.file);
            this.downloadreply =  view.findViewById(R.id.downloadreply);



        }

    }

}