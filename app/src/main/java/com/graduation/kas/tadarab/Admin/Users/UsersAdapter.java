package com.graduation.kas.tadarab.Admin.Users;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.graduation.kas.tadarab.Admin.Rate.RateActivity;
import com.graduation.kas.tadarab.Message.MessageDetailsActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.Student.StudentRequest.RequestDetails;
import com.graduation.kas.tadarab.WebSiteData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.SingleItemRowHolder> {

    private ArrayList<Users> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;
    String type;

    public UsersAdapter(Activity context, ArrayList<Users> itemsList, String type) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
        this.type = type;

    }
    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    public  void updateList(ArrayList<Users> list){
        itemsList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, @SuppressLint("RecyclerView") final int pos) {

        final Users singleItem = itemsList.get(pos);

        holder.username.setText(singleItem.getUsername());
        holder.password.setText(singleItem.getPassword());
        holder.name.setText(singleItem.getName());
        holder.email.setText(singleItem.getEmail());
        holder.mobile.setText(singleItem.getMobile());

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("training_advisor")){
                    mContext.startActivity(new Intent(mContext, MessageDetailsActivity.class)
                            .putExtra("admin_id","1")
                            .putExtra("training_advisor_id",singleItem.getId())
                            .putExtra("academic_advisor_id","0"));


                }else  if(type.equals("academic_advisor")){

                    mContext.startActivity(new Intent(mContext, MessageDetailsActivity.class)
                            .putExtra("admin_id","1")
                            .putExtra("academic_advisor_id",singleItem.getId())
                            .putExtra("training_advisor_id","0"));


                }
            }
        });

        if(type.equals("training_advisor")){


            holder.training_arealay.setVisibility(View.VISIBLE);
            holder.addresslay.setVisibility(View.VISIBLE);
            holder.departmentlay.setVisibility(View.GONE);
            holder.training_area.setText(singleItem.getTraining_area());
            holder.address.setText(singleItem.getAddress());
            holder.rates.setVisibility(View.VISIBLE);
            holder.statuslay.setVisibility(View.GONE);
            holder.activeinactive.setVisibility(View.GONE);


        }else  if(type.equals("academic_advisor")){


            holder.training_arealay.setVisibility(View.GONE);
            holder.addresslay.setVisibility(View.GONE);
            holder.departmentlay.setVisibility(View.VISIBLE);
            holder.statuslay.setVisibility(View.VISIBLE);
            holder.status.setText(singleItem.getStatus());
            holder.department.setText(singleItem.getDepartment());
            holder.rates.setVisibility(View.GONE);
            holder.activeinactive.setVisibility(View.VISIBLE);

            if(singleItem.getStatus().equals("active")){
                holder.activeinactive.setText("InActive");
            }else{
                holder.activeinactive.setText("Active");
            }


        }

        holder.rates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, RateActivity.class).putExtra("aid",singleItem.getId()));
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


        holder.activeinactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage(singleItem.getStatus() == "active" ? "Are You Sure You Want To Inactive advisor?" : "Are You Sure You Want To Active advisor?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                progressDialog.setMessage(mContext.getResources().getString(R.string.wait));
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                        WebSiteData.domain+"ActiveInActive.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        Toast.makeText(mContext, response.toString(), Toast.LENGTH_SHORT).show();
                                        UsersActivity.getData(mContext);


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
                                        params.put("id",singleItem.getId());
                                        if(holder.activeinactive.getText().toString().toLowerCase().equals("inactive")) {
                                            params.put("status", "inactive");
                                        }else{
                                            params.put("status", "active");
                                        }
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
                params.put("table",type);
                params.put("idname",type+"_id");

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

        protected TextView username,name,mobile,email,password,department,address,training_area,status;
        ImageView delete,chat;
        LinearLayout training_arealay,addresslay,departmentlay,statuslay;
        Button rates,activeinactive;


        public SingleItemRowHolder(View view) {
            super(view);

            this.username = (TextView) view.findViewById(R.id.username);
            this.name = (TextView) view.findViewById(R.id.name);
            this.mobile = (TextView) view.findViewById(R.id.mobile);
            this.email = (TextView) view.findViewById(R.id.email);
            this.password = (TextView) view.findViewById(R.id.password);



            this.department = (TextView) view.findViewById(R.id.department);
            this.address = (TextView) view.findViewById(R.id.address);
            this.training_area = (TextView) view.findViewById(R.id.training_area);

            this.delete = (ImageView) view.findViewById(R.id.delete);
            this.chat = (ImageView) view.findViewById(R.id.chat);


            this.status = view.findViewById(R.id.status);
            this.statuslay = view.findViewById(R.id.statuslay);

            this.training_arealay =  view.findViewById(R.id.training_arealay);
            this.addresslay =  view.findViewById(R.id.addresslay);
            this.departmentlay =  view.findViewById(R.id.departmentlay);

            this.activeinactive = view.findViewById(R.id.activeinactive);
            this.rates =  view.findViewById(R.id.rates);


        }

    }

}