package com.graduation.kas.tadarab.Student;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.graduation.kas.tadarab.Admin.Users.Users;
import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;
import com.graduation.kas.tadarab.Student.Forms.StudentFormsActivity;
import com.graduation.kas.tadarab.WebSiteData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TrainingAreaInfoActivity  extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_training_area_info;
    }

    @Override
    public String getCustomTitle() {
        return "Training Area Info";
    }

    protected TextView username,name,mobile,email,password,address,training_area;
    RequestQueue queue;
    ProgressDialog progressDialog;
    SavedData savedData;
    String training_advisor_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(TrainingAreaInfoActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(TrainingAreaInfoActivity.this);
        savedData = new SavedData(TrainingAreaInfoActivity.this);


        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        training_area = findViewById(R.id.training_area);


        findViewById(R.id.sendRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),SendRateActivity.class).putExtra("aid",training_advisor_id));
            }
        });


        findViewById(R.id.forms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), StudentFormsActivity.class));
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain+"getMyTrainingAreaInfo.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    if (result.length() == 0) {
                        Toast.makeText(getBaseContext(), "No Training advisor found", Toast.LENGTH_SHORT).show();
                        finish();
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
                            data.setId(object.getString("training_advisor_id"));
                            data.setAddress(object.getString("address"));
                            data.setTraining_area(object.getString("training_area"));
                            training_advisor_id =  object.getString("training_advisor_id");

                            if(object.getString("name").equals("null")){

                                Toast.makeText(getBaseContext(), "No Training advisor found", Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
//                                protected TextView username,name,mobile,email,password,address,training_area;
                                username.setText(data.getUsername());
                                name.setText(data.getName());
                                mobile.setText(data.getMobile());
                                email.setText(data.getEmail());
                                address.setText(data.getAddress());
                                training_area.setText(data.getTraining_area());


                            }


                        }
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
                params.put("id",savedData.getId());

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