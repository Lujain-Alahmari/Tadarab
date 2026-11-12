package com.graduation.kas.tadarab.AacademicAdvisor.MyStudent;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.graduation.kas.tadarab.Admin.Users.StudentListAdapter;
import com.graduation.kas.tadarab.Admin.Users.Users;
import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;
import com.graduation.kas.tadarab.WebSiteData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyStudentActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_student;
    }

    @Override
    public String getCustomTitle() {
        return "Students";
    }

    RecyclerView recyclerView;
    RequestQueue queue;
    ProgressDialog progressDialog;
    public static ArrayList<Users> arrayList;
    AAStudentListAdapter adapter;
    SavedData savedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(MyStudentActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(MyStudentActivity.this);
        savedData = new SavedData(MyStudentActivity.this);

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.list);
        adapter = new AAStudentListAdapter(MyStudentActivity.this, arrayList);
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
                WebSiteData.domain+"getAllStudent.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    if (result.length() == 0) {
                        Toast.makeText(getBaseContext(), "No Students found", Toast.LENGTH_SHORT).show();


                        progressDialog.dismiss();
                    }else {
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject object = result.getJSONObject(i);
                            Users data = new Users();

                            data.setName(object.getString("name"));
                            data.setEmail(object.getString("email"));
                            data.setMobile(object.getString("mobile"));
                            data.setUsername(object.getString("username"));
                            data.setPassword(object.getString("password"));
                            data.setCv(object.getString("cv"));
                            data.setDepartment(object.getString("department"));
                            data.setAcademic_advisor_id(object.getString("academic_advisor_id"));
                            data.setAname(object.getString("aname"));
                            data.setId(object.getString("student_id"));
                            data.setTraining_advisor_id(object.getString("training_advisor_id"));


                            if(data.getAcademic_advisor_id().equals(savedData.getId())){
                                arrayList.add(data);
                            }


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