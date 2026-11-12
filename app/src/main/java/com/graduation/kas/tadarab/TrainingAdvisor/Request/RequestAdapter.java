package com.graduation.kas.tadarab.TrainingAdvisor.Request;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.graduation.kas.tadarab.Message.MessageDetailsActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;
import com.graduation.kas.tadarab.Student.StudentRequest.RequestDetails;
import com.graduation.kas.tadarab.WebSiteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.SingleItemRowHolder> {

    private ArrayList<RequestDetails> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;
    SavedData savedData;

    public RequestAdapter(Activity context, ArrayList<RequestDetails> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
        savedData = new SavedData(mContext);

    }

    public  void updateList(ArrayList<RequestDetails> list){
        itemsList = list;
        notifyDataSetChanged();
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_request, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, @SuppressLint("RecyclerView") final int pos) {

        final RequestDetails singleItem = itemsList.get(pos);

        holder.title.setText(singleItem.getTitle());
        holder.date.setText(singleItem.getDate());
        holder.status.setText(singleItem.getResponse());
        holder.sname.setText(singleItem.getName());



        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestDetailsActivity.requestDetails = singleItem;
                mContext.startActivity(new Intent(mContext,RequestDetailsActivity.class));

            }
        });

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, MessageDetailsActivity.class)
                        .putExtra("admin_id","0")
                        .putExtra("training_advisor_id",savedData.getId())
                        .putExtra("academic_advisor_id",singleItem.getAcademic_advisor_id()));

            }
        });

    }



    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView title,date,status,sname;
        protected Button details;
        protected LinearLayout hide,chat;


        public SingleItemRowHolder(View view) {
            super(view);

            this.date = (TextView) view.findViewById(R.id.date);
            this.title = (TextView) view.findViewById(R.id.title);
            this.status = (TextView) view.findViewById(R.id.status);
            this.sname = (TextView) view.findViewById(R.id.sname);


            this.details = (Button) view.findViewById(R.id.details);

            this.hide = (LinearLayout) view.findViewById(R.id.hide);
            this.chat = view.findViewById(R.id.chat);




        }

    }

}