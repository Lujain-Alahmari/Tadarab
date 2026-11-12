package com.graduation.kas.tadarab.TrainingAdvisor;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;
import com.graduation.kas.tadarab.WebSiteData;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrainingAdvisorProfileActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_training_advisor_profile;
    }

    @Override
    public String getCustomTitle() {
        return "Profile";
    }

    EditText mobile,email,name,address,training_area;
    Button update;
    ProgressDialog progressDialog;
    RequestQueue queue;
    SavedData savedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(TrainingAdvisorProfileActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(TrainingAdvisorProfileActivity.this);
        savedData = new SavedData(this);

        update = findViewById(R.id.update);


        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        training_area = findViewById(R.id.training_area);
        address = findViewById(R.id.address);


        name.setText(savedData.getName());
        email.setText(savedData.getEmail());
        mobile.setText(savedData.getMobile());


        training_area.setText(savedData.getTraining_area());
        address.setText(savedData.getAddress());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });

    }
    private void Update() {
        Boolean flag = false;
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




        if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Please Enter Mobile number");
            flag = true;
        } else {
            if (mobile.getText().toString().length() != 10) {
                mobile.setError("Please Enter Valid Mobile number");
                flag = true;
            } else if (mobile.getText().toString().charAt(0) != '0' || mobile.getText().toString().charAt(1) != '5') {
                mobile.setError("Please Enter Valid Mobile number");
                flag = true;
            }
        }
        if (email.getText().toString().isEmpty()) {
            email.setError("Please Enter E-mail");
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
                email.setError("Please Enter Valid E-mail");
                flag = true;
            }
        }

        if (!flag) {
            progressDialog.setMessage(getResources().getString(R.string.wait));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    WebSiteData.domain + "TrainingAdvisorUpdateProfile.php", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean mobileavailable = jsonResponse.getBoolean("mobileavailable");
                        boolean emailavailable = jsonResponse.getBoolean("emailavailable");

                        if(mobileavailable){
                            if(emailavailable){
                                Toast.makeText(getBaseContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                savedData.setName(name.getText().toString());
                                savedData.setEmail(email.getText().toString());
                                savedData.setMobile(mobile.getText().toString());
                                savedData.setAddress(address.getText().toString());
                                savedData.setTraining_area(training_area.getText().toString());

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(TrainingAdvisorProfileActivity.this,"Username Already used!", Toast.LENGTH_SHORT).show();

                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(TrainingAdvisorProfileActivity.this, "Mobile Number Already used!", Toast.LENGTH_SHORT).show();

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
                    params.put("id", savedData.getId());
                    params.put("name", name.getText().toString());
                    params.put("mobile", mobile.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("training_area", training_area.getText().toString());
                    params.put("address", address.getText().toString());

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