package com.graduation.kas.tadarab.Admin.Users;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.WebSiteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.SingleItemRowHolder> {

    private ArrayList<Users> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;

    public StudentListAdapter(Activity context, ArrayList<Users> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);

    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student, null);
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

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView username,name,mobile,advisor;

        public SingleItemRowHolder(View view) {
            super(view);

            this.username = (TextView) view.findViewById(R.id.username);
            this.name = (TextView) view.findViewById(R.id.name);
            this.mobile = (TextView) view.findViewById(R.id.mobile);
            this.advisor = (TextView) view.findViewById(R.id.advisor);



        }

    }

}