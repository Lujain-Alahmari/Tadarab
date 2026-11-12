package com.graduation.kas.tadarab.Student.TrainingArea.TrainigProgram;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.graduation.kas.tadarab.SavedData;
import com.graduation.kas.tadarab.TrainingAdvisor.TrainingProgram.TrainingProgram;
import com.graduation.kas.tadarab.TrainingAdvisor.TrainingProgram.UpdateTrainingProgramActivity;
import com.graduation.kas.tadarab.WebSiteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentTrainingProgramAdapter extends RecyclerView.Adapter<StudentTrainingProgramAdapter.SingleItemRowHolder> {

    private ArrayList<TrainingProgram> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;

    public StudentTrainingProgramAdapter(Activity context, ArrayList<TrainingProgram> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);

    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_training_program_student, null);
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
        holder.sendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("Are You Sure You Want To Send Request?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SendRequest(singleItem.getId(),pos);

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
    private void SendRequest(String id,int i){
        SavedData savedData = new SavedData(mContext);
        progressDialog.setMessage(mContext.getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain+"SendRequest.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
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
                params.put("training_program_id",id);
                params.put("student_id",savedData.getId());
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
        protected Button sendrequest;


        public SingleItemRowHolder(View view) {
            super(view);

            this.details = (TextView) view.findViewById(R.id.details);
            this.title = (TextView) view.findViewById(R.id.title);
            this.plan = (TextView) view.findViewById(R.id.plan);
            this.conditions = (TextView) view.findViewById(R.id.conditions);
            this.sendrequest = (Button) view.findViewById(R.id.sendrequest);





        }

    }

}