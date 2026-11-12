package com.graduation.kas.tadarab.Admin.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class StudentDetailsActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_student_details;
    }

    @Override
    public String getCustomTitle() {
        return "Students Details";
    }

    TextView username,name,mobile,email,password,department,aname;
    ImageView delete,cv;
    public static Users users;
    SavedData savedData;
    LinearLayout ll1,ll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected void onResume() {
        super.onResume();
        username =  findViewById(R.id.username);
        name =  findViewById(R.id.name);
        mobile =  findViewById(R.id.mobile);
        email =  findViewById(R.id.email);
        password =  findViewById(R.id.password);
        department =  findViewById(R.id.department);
        aname =  findViewById(R.id.aname);
        delete = findViewById(R.id.delete);
        cv = findViewById(R.id.cv);
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);

        username.setText(users.getUsername());
        name.setText(users.getName());
        mobile.setText(users.getMobile());
        email.setText(users.getEmail());
        password.setText(users.getPassword());
        department.setText(users.getDepartment());
        savedData = new SavedData(StudentDetailsActivity.this);


        if(users.getAname().equals("") || users.getAname().equals("null")){
            aname.setText("No Advisor");

        }else{
            aname.setText(users.getAname());
        }

        if(savedData.getUsertype().equals("admin")){
            delete.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.VISIBLE);
            findViewById(R.id.setAdvisor).setVisibility(View.VISIBLE);
        }else{
            delete.setVisibility(View.GONE);
            findViewById(R.id.setAdvisor).setVisibility(View.GONE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);

        }

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebSiteData.domain + "uploaded/"+users.getCv()));
                startActivity(browserIntent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(StudentDetailsActivity.this);
                builder1.setMessage("Are You Sure You Want To Delete?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Delete(users.getId());

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        findViewById(R.id.setAdvisor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(),ChooseAdvisorActivity.class).putExtra("sid",users.getId()));

            }
        });
    }

    private void Delete(String id){
        ProgressDialog progressDialog = new ProgressDialog(StudentDetailsActivity.this, R.style.AppTheme_Dark_Dialog);

        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain+"delete.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Toast.makeText(StudentDetailsActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(StudentDetailsActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id);
                params.put("table","student");
                params.put("idname","student_id");

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(StudentDetailsActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }


}