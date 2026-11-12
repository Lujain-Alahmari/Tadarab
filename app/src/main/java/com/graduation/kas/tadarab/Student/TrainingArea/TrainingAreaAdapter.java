package com.graduation.kas.tadarab.Student.TrainingArea;

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
import com.graduation.kas.tadarab.Admin.Users.StudentDetailsActivity;
import com.graduation.kas.tadarab.Admin.Users.Users;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.Student.TrainingArea.TrainigProgram.StudenntTrainingProgramActivity;
import com.graduation.kas.tadarab.WebSiteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainingAreaAdapter extends RecyclerView.Adapter<TrainingAreaAdapter.SingleItemRowHolder> {

    private ArrayList<TrainingArea> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;

    public TrainingAreaAdapter(Activity context, ArrayList<TrainingArea> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);

    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_training_area, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, @SuppressLint("RecyclerView") final int pos) {

        final TrainingArea singleItem = itemsList.get(pos);

        holder.name.setText(singleItem.getTraining_area());
        holder.address.setText(singleItem.getAddress());
        holder.program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext, StudenntTrainingProgramActivity.class)
                        .putExtra("tid",singleItem.getId()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView address,name;
        protected Button program;


        public SingleItemRowHolder(View view) {
            super(view);

            this.address = (TextView) view.findViewById(R.id.address);
            this.name = (TextView) view.findViewById(R.id.name);
            this.program = (Button) view.findViewById(R.id.program);



        }

    }

}