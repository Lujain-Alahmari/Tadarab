package com.graduation.kas.tadarab;

import android.app.ProgressDialog;
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

import java.util.HashMap;
import java.util.Map;

public class UpdatePasswordActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @Override
    public String getCustomTitle() {
        return "Update Password";
    }


    EditText newpassword,cpassword,oldpassword;
    Button update;
    ProgressDialog progressDialog;
    RequestQueue queue;
    SavedData savedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        savedData = new SavedData(UpdatePasswordActivity.this);
        progressDialog = new ProgressDialog(UpdatePasswordActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(UpdatePasswordActivity.this);


        newpassword = findViewById(R.id.newpassword);
        cpassword = findViewById(R.id.cpassword);
        oldpassword = findViewById(R.id.oldpassword);
        update = findViewById(R.id.update);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword();
            }
        });

    }

    private void ChangePassword() {
        if (oldpassword.getText().toString().isEmpty()) {
            oldpassword.setError("Please Enter Old Password");
        } else if (newpassword.getText().toString().isEmpty()) {
            newpassword.setError("Please Enter New Password");
        } else {
            if (newpassword.getText().toString().length() < 8) {
                newpassword.setError("New Password Must Contains 8 Letters at least");
                return;
            }
            if (newpassword.getText().toString().equals(newpassword.getText().toString().toLowerCase())) {
                newpassword.setError("New Password Must Contains Capital and Small Letters");
                return;
            }
            if (cpassword.getText().toString().isEmpty()) {
                cpassword.setError("Please Enter Confirm Password");
                return;
            }

            if (!newpassword.getText().toString().equals(cpassword.getText().toString())) {
                cpassword.setError("Please Check Confirm Password");
                return;
            }


            progressDialog.setMessage(getResources().getString(R.string.wait));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    WebSiteData.domain + "UpdatePassword.php", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if(response.toString().toLowerCase().contains("updated")){
//                        finish();
                    }

                    Toast.makeText(UpdatePasswordActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdatePasswordActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("id", String.valueOf(savedData.getId()));
                    params.put("oldpassword", oldpassword.getText().toString());
                    params.put("newpassword", newpassword.getText().toString());
                    params.put("type", savedData.getUsertype());

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