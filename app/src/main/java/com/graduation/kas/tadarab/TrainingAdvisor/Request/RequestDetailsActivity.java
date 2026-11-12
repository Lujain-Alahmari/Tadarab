package com.graduation.kas.tadarab.TrainingAdvisor.Request;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.Student.StudentRequest.RequestDetails;
import com.graduation.kas.tadarab.TrainingAdvisor.Request.Forms.TrainingFormsActivity;
import com.graduation.kas.tadarab.WebSiteData;

import java.util.HashMap;
import java.util.Map;

public class RequestDetailsActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_details2;
    }

    @Override
    public String getCustomTitle() {
        return "Request Details";
    }


    public static RequestDetails requestDetails;
    TextView title,details,plan,conditions,address,name,name2,mobile,email,department,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        plan = findViewById(R.id.plan);
        conditions = findViewById(R.id.conditions);
        address = findViewById(R.id.address);
        name = findViewById(R.id.name);


        name2 = findViewById(R.id.name2);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        department = findViewById(R.id.department);
        status = findViewById(R.id.status);


        title.setText(requestDetails.getTitle());
        details.setText(requestDetails.getDetails());
        plan.setText(requestDetails.getPlan());
        conditions.setText(requestDetails.getConditions());
        address.setText(requestDetails.getAddress());
        name.setText(requestDetails.getTraining_area());
        status.setText(requestDetails.getResponse());

        name2.setText(requestDetails.getName());
        mobile.setText(requestDetails.getMobile());
        email.setText(requestDetails.getEamil());
        department.setText(requestDetails.getDepartment());


        if(requestDetails.getResponse().toLowerCase().contains("cancel")){
            findViewById(R.id.hide).setVisibility(View.GONE);
        }else{
            findViewById(R.id.hide).setVisibility(View.VISIBLE);

        }

        if(requestDetails.getResponse().toLowerCase().contains("acce")){
            findViewById(R.id.forms).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.forms).setVisibility(View.GONE);

        }
        findViewById(R.id.forms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), TrainingFormsActivity.class));
            }
        });

        findViewById(R.id.cv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebSiteData.domain + "uploaded/"+requestDetails.getCv()));
                startActivity(browserIntent);

            }
        });

        findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(RequestDetailsActivity.this);
                builder1.setMessage("Are You Sure You Want To Accept Request?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CancelRequest("Accepted");

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


        findViewById(R.id.reject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(RequestDetailsActivity.this);
                builder1.setMessage("Are You Sure You Want To Reject Request?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CancelRequest("Rejected");

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


    private void CancelRequest(String statu){
        ProgressDialog progressDialog = new ProgressDialog(RequestDetailsActivity.this,R.style.AppTheme_Dark_Dialog);

        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain+"UpdateRequestStatus.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(RequestDetailsActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RequestDetailsActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("request_id",requestDetails.getId());
                params.put("status",statu);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(RequestDetailsActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

}