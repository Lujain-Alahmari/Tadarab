package com.graduation.kas.tadarab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.graduation.kas.tadarab.AacademicAdvisor.AacademicAdvisorMainActivity;
import com.graduation.kas.tadarab.Admin.AdminMainActivity;
import com.graduation.kas.tadarab.Student.StudentMainActivity;
import com.graduation.kas.tadarab.TrainingAdvisor.TrainingAdvisorMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button login;
    TextView forget,register;
    ProgressDialog progressDialog;
    RequestQueue queue;
    SavedData savedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(LoginActivity.this);
        savedData = new SavedData(this);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login =  findViewById(R.id.login);
        register = findViewById(R.id.register);
        forget = findViewById(R.id.forget);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),UserTypeActivity.class));
            }
        });


        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ForgetPasswordActivity.class));

            }
        });
    }

    private void Login(){
        Boolean flag = false;

        if(username.getText().toString().isEmpty()){
            username.setError("Please Enter Username");
            flag = true;

        }
        if(password.getText().toString().isEmpty()){
            password.setError("Please Enter Password");
            flag = true;
        }

        if(!flag){

            progressDialog.setMessage(getString(R.string.wait));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    WebSiteData.domain + "Login.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("loginresult");
                        String name, mobile, id,email= " ";
                        if (success) {
                            savedData.setUsername(username.getText().toString());

                            String usertype = jsonResponse.getString("usertype");
                            if (usertype.equals("admin")) {
                                JSONArray array = jsonResponse.getJSONArray("admin");
                                id = array.getJSONObject(0).getString("id");
                                name = array.getJSONObject(0).getString("name");
                                mobile = array.getJSONObject(0).getString("mobile");
                                email = array.getJSONObject(0).getString("email");
                                savedData.setId(id);
                                savedData.setMobile(mobile);
                                savedData.setName(name);
                                savedData.setEmail(email);
                                savedData.setUsertype(usertype);
                                savedData.setLogin(true);
                                progressDialog.dismiss();

                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), AdminMainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();


                            }
                            if (usertype.equals("training_advisor")) {
                                JSONArray array = jsonResponse.getJSONArray("training_advisor");
                                id = array.getJSONObject(0).getString("id");
                                name = array.getJSONObject(0).getString("name");
                                mobile = array.getJSONObject(0).getString("mobile");
                                email = array.getJSONObject(0).getString("email");

                                savedData.setId(id);
                                savedData.setMobile(mobile);
                                savedData.setName(name);
                                savedData.setEmail(email);
                                savedData.setAddress(array.getJSONObject(0).getString("address"));
                                savedData.setTraining_area(array.getJSONObject(0).getString("training_area"));
                                savedData.setUsertype(usertype);
                                savedData.setLogin(true);
                                progressDialog.dismiss();

                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), TrainingAdvisorMainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();


                            }

                            if (usertype.equals("academic_advisor")) {
                                JSONArray array = jsonResponse.getJSONArray("academic_advisor");

                                id = array.getJSONObject(0).getString("id");
                                name = array.getJSONObject(0).getString("name");
                                mobile = array.getJSONObject(0).getString("mobile");
                                email = array.getJSONObject(0).getString("email");
                                savedData.setId(id);
                                savedData.setMobile(mobile);
                                savedData.setDepartment(array.getJSONObject(0).getString("department"));
                                savedData.setName(name);
                                savedData.setEmail(email);
                                savedData.setUsertype(usertype);
                                savedData.setLogin(true);
                                progressDialog.dismiss();

                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), AacademicAdvisorMainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();


                            }

                            if (usertype.equals("student")) {
                                JSONArray array = jsonResponse.getJSONArray("student");
                                id = array.getJSONObject(0).getString("id");
                                name = array.getJSONObject(0).getString("name");
                                mobile = array.getJSONObject(0).getString("mobile");
                                email = array.getJSONObject(0).getString("email");
                                savedData.setId(id);
                                savedData.setMobile(mobile);
                                savedData.setName(name);
                                savedData.setDepartment(array.getJSONObject(0).getString("department"));
                                savedData.setCv(array.getJSONObject(0).getString("cv"));
                                savedData.setEmail(email);
                                savedData.setUsertype(usertype);
                                savedData.setLogin(true);
                                progressDialog.dismiss();

                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), StudentMainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();


                            }
                        }

                        else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Username or password incorrect!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username.getText().toString());
                    params.put("password", password.getText().toString());
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
}