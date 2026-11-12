package com.graduation.kas.tadarab.Admin.Rate;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.kas.tadarab.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.SingleItemRowHolder> {

    private ArrayList<Rate> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;


    public RatesAdapter(Activity context, ArrayList<Rate> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);

    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rate, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, @SuppressLint("RecyclerView") final int pos) {

        final Rate singleItem = itemsList.get(pos);

        holder.aname.setText(singleItem.getAname());
        holder.sname.setText(singleItem.getSname());

        holder.trainingrating.setRating(Float.parseFloat(singleItem.getTraining_rate()));

        holder.arearating.setRating(Float.parseFloat(singleItem.getArea_rate()));

    }


    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView sname,aname;
        protected MaterialRatingBar arearating,trainingrating;


        public SingleItemRowHolder(View view) {
            super(view);

            this.sname = (TextView) view.findViewById(R.id.sname);
            this.aname = (TextView) view.findViewById(R.id.aname);



            this.arearating = (MaterialRatingBar) view.findViewById(R.id.arearating);
            this.trainingrating = (MaterialRatingBar) view.findViewById(R.id.trainingrating);



        }

    }

}