package com.graduation.kas.tadarab.TrainingAdvisor.TrainingProgram;

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
import com.graduation.kas.tadarab.Admin.Users.Users;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.WebSiteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainingProgramAdapter extends RecyclerView.Adapter<TrainingProgramAdapter.SingleItemRowHolder> {

    private ArrayList<TrainingProgram> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;

    public TrainingProgramAdapter(Activity context, ArrayList<TrainingProgram> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);

    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_training_program, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, @SuppressLint("RecyclerView") final int pos) {

        final TrainingProgram singleItem = itemsList.get(pos);

        holder.title.setText(singleItem.getTitle());
        holder.details.setText(singleItem.getDetails());
        holder.plan.setText(singleItem.getPlan());
        holder.conditions.setText(singleItem.getConditions());


        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateTrainingProgramActivity.trainingProgram = singleItem;
                mContext.startActivity(new Intent(mContext,UpdateTrainingProgramActivity.class));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("Are You Sure You Want To Delete?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Delete(singleItem.getId(),pos);

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });

    }

    private void Delete(String id,int i){
        progressDialog.setMessage(mContext.getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain+"delete.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                itemsList.remove(i);
                notifyDataSetChanged();
                Toast.makeText(mContext, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(mContext, mContext.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id);
                params.put("table","training_program");
                params.put("idname","training_program_id");

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(mContext);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView title,details,plan,conditions;
        ImageView delete,update;


        public SingleItemRowHolder(View view) {
            super(view);

            this.details = (TextView) view.findViewById(R.id.details);
            this.title = (TextView) view.findViewById(R.id.title);
            this.plan = (TextView) view.findViewById(R.id.plan);
            this.conditions = (TextView) view.findViewById(R.id.conditions);

            this.update = (ImageView) view.findViewById(R.id.update);
            this.delete = (ImageView) view.findViewById(R.id.delete);





        }

    }

}