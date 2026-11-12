package com.graduation.kas.tadarab.TrainingAdvisor.Request;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;
import com.graduation.kas.tadarab.Student.StudentRequest.RequestDetails;
import com.graduation.kas.tadarab.Student.StudentRequest.StudentRequestAdapter;
import com.graduation.kas.tadarab.WebSiteData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_request;
    }

    @Override
    public String getCustomTitle() {
        return "Training Requests";
    }

    RecyclerView recyclerView;
    RequestQueue queue;
    ProgressDialog progressDialog;
    public static ArrayList<RequestDetails> arrayList;
    RequestAdapter adapter;
    SavedData savedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        progressDialog = new ProgressDialog(RequestActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(RequestActivity.this);
        savedData = new SavedData(RequestActivity.this);


        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.list);
        adapter = new RequestAdapter(RequestActivity.this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);


        findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("all");
            }
        });


        findViewById(R.id.rejected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("Rejected");
            }
        });


        findViewById(R.id.waiting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("Waiting");
            }
        });

        findViewById(R.id.accepted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("Accepted");
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        arrayList.clear();
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    WebSiteData.domain+"getAdvisorRequest.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    if (result.length() == 0) {
                        Toast.makeText(getBaseContext(), "No Request found", Toast.LENGTH_SHORT).show();


                        progressDialog.dismiss();
                    }else {
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject object = result.getJSONObject(i);

                            RequestDetails data = new RequestDetails();


                            data.setId(object.getString("request_id"));
                            data.setDate(object.getString("date"));
                            data.setAddress(object.getString("address"));
                            data.setResponse(object.getString("response"));


                            data.setTitle(object.getString("title"));
                            data.setConditions(object.getString("conditions"));
                            data.setDetails(object.getString("details"));
                            data.setPlan(object.getString("plan"));
                            data.setTraining_area(object.getString("training_area"));


                            data.setName(object.getString("name"));
                            data.setMobile(object.getString("mobile"));
                            data.setEamil(object.getString("email"));
                            data.setCv(object.getString("cv"));
                            data.setDepartment(object.getString("department"));
                            data.setAcademic_advisor_id(object.getString("academic_advisor_id"));
                            data.setStudent_id(object.getString("student_id"));


                            arrayList.add(data);


                            adapter.notifyDataSetChanged();

                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getBaseContext(), getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sid",savedData.getId());

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    void filter(String stat){

        if(!stat.equals("all")) {
            ArrayList<RequestDetails> activityClasses = new ArrayList<>();
            for (RequestDetails d : arrayList) {
                if (d.getResponse().contains(stat)) {
                    activityClasses.add(d);
                }
            }


            adapter.updateList(activityClasses);
        }else{

            adapter.updateList(arrayList);
        }
    }


}