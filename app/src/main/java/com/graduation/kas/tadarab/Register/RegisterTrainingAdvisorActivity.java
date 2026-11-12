package com.graduation.kas.tadarab.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.graduation.kas.tadarab.LoginActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.WebSiteData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterTrainingAdvisorActivity extends AppCompatActivity {

    EditText username,mobile,email,password,cpassword,name,address,training_area;


    Button register;
    ProgressDialog progressDialog;
    RequestQueue queue;
    Boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_training_advisor);
        progressDialog = new ProgressDialog(RegisterTrainingAdvisorActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(RegisterTrainingAdvisorActivity.this);



        register = findViewById(R.id.register);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        training_area = findViewById(R.id.training_area);
        address = findViewById(R.id.address);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Register();

            }
        });

/*
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (username.getText().toString().isEmpty()) {
                        username.setError("Please Enter Username");
                        flag = true;
                    }                }
            }
        });

        mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (mobile.getText().toString().isEmpty()) {
                        mobile.setError("Please Enter Mobile Number");
                        flag = true;
                    } else {
                        if (mobile.getText().toString().length() != 10) {
                            mobile.setError("Please Enter Valid Mobile Number");
                            flag = true;
                        } else if (mobile.getText().toString().charAt(0) != '0' || mobile.getText().toString().charAt(1) != '5') {
                            mobile.setError("Please Enter Valid Mobile Number");
                            flag = true;
                        }
                    }
                }
            }
        });


        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (email.getText().toString().isEmpty()) {
                        email.setError("Please Enter Email");
                        flag = true;
                    } else {
                        Pattern pattern;
                        Matcher matcher;

                        String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                                "[a-zA-Z0-9_+&*-]+)*@" +
                                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                                "A-Z]{2,7}$";
                        pattern = Pattern.compile(EMAIL_PATTERN);
                        matcher = pattern.matcher(email.getText().toString());
                        if (!matcher.matches()) {
                            email.setError("Please Enter Valid Email");
                            flag = true;
                        }

                    }
                }
            }
        });


        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (password.getText().toString().isEmpty()) {
                        password.setError("Please Enter Password");
                        flag = true;
                    }                }
            }
        });

        cpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (cpassword.getText().toString().isEmpty()) {
                        cpassword.setError("Please Enter Confirm Password");
                        flag = true;
                    }
                    if (!password.getText().toString().equals(cpassword.getText().toString())) {
                        cpassword.setError("Please Check Confirm Password");
                        flag = true;
                    }
                }
            }
        });


        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (name.getText().toString().isEmpty()) {
                        name.setError("Please Enter Name");
                        flag = true;
                    }
                }
            }
        });
*/

    }
    private void Register() {
        flag=false;
        if (name.getText().toString().isEmpty()) {
            name.setError("Please Enter Name");
            flag = true;
        }

        if (address.getText().toString().isEmpty()) {
            address.setError("Please Enter Address");
            flag = true;
        }

        if (training_area.getText().toString().isEmpty()) {
            training_area.setError("Please Enter training area");
            flag = true;
        }




        if (username.getText().toString().isEmpty()) {
            username.setError("Please Enter Username");
            flag = true;
        }
        if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Please Enter Mobile Number");
            flag = true;
        } else {
            if (mobile.getText().toString().length() != 10) {
                mobile.setError("Please Enter Valid Mobile Number");
                flag = true;
            } else if (mobile.getText().toString().charAt(0) != '0' || mobile.getText().toString().charAt(1) != '5') {
                mobile.setError("Please Enter Valid Mobile Number");
                flag = true;
            }
        }
        if (email.getText().toString().isEmpty()) {
            email.setError("Please Enter Email");
            flag = true;
        } else {
            Pattern pattern;
            Matcher matcher;

            String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email.getText().toString());
            if (!matcher.matches()) {
                email.setError("Please Enter Valid Email");
                flag = true;
            }

        }
        if (cpassword.getText().toString().isEmpty()) {
            cpassword.setError("Please Enter Confirm Password");
            flag = true;
        }
        if (!password.getText().toString().equals(cpassword.getText().toString())) {
            cpassword.setError("Please Check Confirm Password");
            flag = true;
        }


        if (password.getText().toString().isEmpty()) {
            password.setError("Please Enter Password");
            flag = true;
        } else {
            if (password.getText().toString().equals(password.getText().toString().toLowerCase())) {
                password.setError("Password Must contains Capital and small Letter");
                flag = true;

            }

            if (password.getText().toString().length() < 8) {
                password.setError("Password Must contains 8 Letters");
                flag = true;
            }
        }

        if (!flag) {
            progressDialog.setMessage(getResources().getString(R.string.wait));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    WebSiteData.domain + "TrainingRegister.php", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean usernameavailable = jsonResponse.getBoolean("usernameavailable");
                        boolean mobileavailable = jsonResponse.getBoolean("mobileavailable");
                        boolean emailavailable = jsonResponse.getBoolean("emailavailable");

                        if (usernameavailable) {
                            if(mobileavailable){
                                if(emailavailable){
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterTrainingAdvisorActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterTrainingAdvisorActivity.this, "Email Already Used!", Toast.LENGTH_SHORT).show();

                                }
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(RegisterTrainingAdvisorActivity.this, "Mobile Number Already Used!", Toast.LENGTH_SHORT).show();

                            }


                        }else if(!usernameavailable){
                            progressDialog.dismiss();
                            Toast.makeText(RegisterTrainingAdvisorActivity.this, "Username Already Used!", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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

                    params.put("name", name.getText().toString());
                    params.put("username", username.getText().toString());
                    params.put("password", password.getText().toString());
                    params.put("mobile", mobile.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("address", address.getText().toString());
                    params.put("training_area", training_area.getText().toString());
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


    @Override
    protected void onResume() {
        super.onResume();
    }

}