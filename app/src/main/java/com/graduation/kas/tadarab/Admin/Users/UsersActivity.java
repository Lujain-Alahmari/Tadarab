package com.graduation.kas.tadarab.Admin.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.graduation.kas.tadarab.WebSiteData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_users;
    }

    @Override
    public String getCustomTitle() {
        Bundle b = getIntent().getExtras();
        if(b.getString("type").equals("training_advisor")){
            return "Training Areas";
        }else if(b.getString("type").equals("academic_advisor")){
            return "Academic Advisor";
        }else{
            return "";
        }
    }

    RecyclerView recyclerView;
    RequestQueue queue;
    public static ProgressDialog progressDialog;
    public static ArrayList<Users> arrayList;
    public static UsersAdapter adapter;
    public static String type = "";
    LinearLayout filter;

    Button all,active,inactive;

    public static String stringfilter = "all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            type = b.getString("type");

        }

        filter = findViewById(R.id.filter);
        all = findViewById(R.id.all);
        active = findViewById(R.id.active);
        inactive = findViewById(R.id.inactive);

        if(type.equals("academic_advisor")){
            filter.setVisibility(View.VISIBLE);
        }else{
            filter.setVisibility(View.GONE);
        }

        progressDialog = new ProgressDialog(UsersActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(UsersActivity.this);

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.list);
        adapter = new UsersAdapter(UsersActivity.this, arrayList,type);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringfilter = "all";
                filter("all");

            }
        });

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringfilter = "active";
                filter("active");

            }
        });

        inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringfilter = "inactive";
                filter("inactive");
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        getData(UsersActivity.this);
    }

    public static void getData(Activity activity){
        arrayList.clear();
        progressDialog.setMessage(activity.getResources().getString(R.string.wait));
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
                        if(type.equals("training_advisor")){
                            Toast.makeText(activity.getBaseContext(), "No Training advisor found", Toast.LENGTH_SHORT).show();
                        }else  if(type.equals("academic_advisor")){
                            Toast.makeText(activity.getBaseContext(), "No Academic advisor found", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity.getBaseContext(), "No Students found", Toast.LENGTH_SHORT).show();
                        }

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
                            if(type.equals("training_advisor")){
                                data.setId(object.getString("training_advisor_id"));
                                data.setAddress(object.getString("address"));
                                data.setTraining_area(object.getString("training_area"));
                            }else  if(type.equals("academic_advisor")){
                                data.setId(object.getString("academic_advisor_id"));
                                data.setDepartment(object.getString("department"));
                                data.setStatus(object.getString("status"));
                            }

                            arrayList.add(data);
                            adapter.notifyDataSetChanged();
                        }
                        filter(stringfilter);

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
                Toast.makeText(activity.getBaseContext(), activity.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("table",type);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public static void filter(String stat){

        if(!stat.equals("all")) {
            ArrayList<Users> activityClasses = new ArrayList<>();
            for (Users d : arrayList) {
                if (d.getStatus().equals(stat)) {
                    activityClasses.add(d);
                }
            }

            adapter.updateList(activityClasses);
        }else{

            adapter.updateList(arrayList);
        }
    }


}