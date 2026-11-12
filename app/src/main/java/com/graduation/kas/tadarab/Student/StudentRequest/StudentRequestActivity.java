package com.graduation.kas.tadarab.Student.StudentRequest;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.graduation.kas.tadarab.SavedData;
import com.graduation.kas.tadarab.Student.TrainingArea.TrainigProgram.StudenntTrainingProgramActivity;
import com.graduation.kas.tadarab.Student.TrainingArea.TrainigProgram.StudentTrainingProgramAdapter;
import com.graduation.kas.tadarab.TrainingAdvisor.TrainingProgram.TrainingProgram;
import com.graduation.kas.tadarab.WebSiteData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentRequestActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_student_request;
    }

    @Override
    public String getCustomTitle() {
        return "My Request";
    }

    RecyclerView recyclerView;
    RequestQueue queue;
    ProgressDialog progressDialog;
    public static ArrayList<RequestDetails> arrayList;
    StudentRequestAdapter adapter;
    SavedData savedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        progressDialog = new ProgressDialog(StudentRequestActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(StudentRequestActivity.this);
        savedData = new SavedData(StudentRequestActivity.this);


        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.list);
        adapter = new StudentRequestAdapter(StudentRequestActivity.this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);



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
                WebSiteData.domain+"getStudentRequest.php", new Response.Listener<String>() {
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

}