package com.graduation.kas.tadarab.Student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
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

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class SendRateActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_rate;
    }

    String aid = "";

    RequestQueue queue;
    ProgressDialog progressDialog;
    SavedData savedData;


    MaterialRatingBar arearating,trainingrating;




    @Override
    public String getCustomTitle() {
        return "Send Rate";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            aid = b.getString("aid");
        }


        progressDialog = new ProgressDialog(SendRateActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(SendRateActivity.this);
        savedData = new SavedData(SendRateActivity.this);



        arearating = findViewById(R.id.arearating);
        trainingrating = findViewById(R.id.trainingrating);


        findViewById(R.id.sendRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRate();

            }
        });

    }

    private void AddRate() {
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain + "SendRate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SendRateActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                finish();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SendRateActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("training_advisor_id",aid);
                params.put("student_id",savedData.getId());
                params.put("training_rate",String.valueOf(trainingrating.getRating()));
                params.put("area_rate",String.valueOf(arearating.getRating()));
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