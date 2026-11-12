package com.graduation.kas.tadarab.Admin.Users;

import androidx.appcompat.app.AppCompatActivity;
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
import com.graduation.kas.tadarab.WebSiteData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseAdvisorActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_users;
    }

    @Override
    public String getCustomTitle() {
        return "Choose Advisor";

    }

    RecyclerView recyclerView;
    RequestQueue queue;
    ProgressDialog progressDialog;
    public static ArrayList<Users> arrayList;
    ChooseAcademicAdapter adapter;
    String type = "academic_advisor";
    String sid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            sid = b.getString("sid");

        }

        progressDialog = new ProgressDialog(ChooseAdvisorActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(ChooseAdvisorActivity.this);

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.list);
        adapter = new ChooseAcademicAdapter(ChooseAdvisorActivity.this, arrayList,sid);
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
                WebSiteData.domain+"getAll.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    if (result.length() == 0) {
                        Toast.makeText(getBaseContext(), "No Academic advisor found", Toast.LENGTH_SHORT).show();

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
                            data.setId(object.getString("academic_advisor_id"));
                            data.setDepartment(object.getString("department"));
                            data.setStatus(object.getString("status"));

                            if(data.getStatus().equals("active")){
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
                params.put("table",type);
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