package com.graduation.kas.tadarab.TrainingAdvisor.TrainingProgram;

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
import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;
import com.graduation.kas.tadarab.WebSiteData;

import java.util.HashMap;
import java.util.Map;

public class UpdateTrainingProgramActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_training_program;
    }

    @Override
    public String getCustomTitle() {
        return "Update Training Program";
    }


    ProgressDialog progressDialog;
    RequestQueue queue;
    Button update;
    SavedData savedData;
    EditText title,details,plan,conditions;

    public static TrainingProgram trainingProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        update = findViewById(R.id.update);
        savedData = new SavedData(this);

        queue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(UpdateTrainingProgramActivity.this,R.style.AppTheme_Dark_Dialog);


        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        plan = findViewById(R.id.plan);
        conditions = findViewById(R.id.conditions);


        title.setText(trainingProgram.getTitle());
        details.setText(trainingProgram.getDetails());
        conditions.setText(trainingProgram.getConditions());
        plan.setText(trainingProgram.getPlan());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Boolean flag = true;
                if(title.getText().toString().isEmpty()){
                    title.setError("Please Enter Title");
                    flag = false;

                }  if(details.getText().toString().isEmpty()){
                    details.setError("Please Enter Details");
                    flag = false;

                }if(plan.getText().toString().isEmpty()){
                    plan.setError("Please Enter Plan");
                    flag = false;

                }if(conditions.getText().toString().isEmpty()){
                    conditions.setError("Please Enter Conditions");
                    flag = false;

                }
                if(flag){
                    UpdateTrainingProgram();

                }
            }
        });
    }

    private void UpdateTrainingProgram() {
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain + "UpdateTrainingProgram.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UpdateTrainingProgramActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();

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

                params.put("title",title.getText().toString());
                params.put("details",details.getText().toString());
                params.put("plan",plan.getText().toString());
                params.put("conditions",conditions.getText().toString());
                params.put("training_program_id", trainingProgram.getId());


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