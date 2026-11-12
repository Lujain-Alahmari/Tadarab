package com.graduation.kas.tadarab.Message;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageDetailsActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_details;
    }

    @Override
    public String getCustomTitle() {
        return "Chat";
    }

    RecyclerView recyclerView;
    RequestQueue queue;
    ProgressDialog progressDialog;
    public static ArrayList<MessageDetails> arrayList;
    MessageDetailsAdapter adapter;
    SavedData savedData;
    EditText content;
    ImageView send;
    String admin_id;
    String training_advisor_id;

    String academic_advisor_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if (b != null) {

            admin_id = b.getString("admin_id");
            training_advisor_id = b.getString("training_advisor_id");
            academic_advisor_id = b.getString("academic_advisor_id");

        }



        progressDialog = new ProgressDialog(MessageDetailsActivity.this, R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(MessageDetailsActivity.this);
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.list);
        adapter = new MessageDetailsAdapter(MessageDetailsActivity.this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageDetailsActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        savedData = new SavedData(MessageDetailsActivity.this);

        content = findViewById(R.id.content);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (content.getText().toString().isEmpty()) {
                    Toast.makeText(MessageDetailsActivity.this, "الرجاء ادخال نص الرسالة", Toast.LENGTH_SHORT).show();

                } else {
                    AddMessage();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessage();
    }

    private void getMessage(){
        arrayList.clear();
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain + "getMessage.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    if (result.length() == 0) {
                        Toast.makeText(MessageDetailsActivity.this, "No Message", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else {
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject object = result.getJSONObject(i);
                            MessageDetails data = new MessageDetails();

                            data.setContent(object.getString("content"));
                            data.setDatetime(object.getString("datetime"));
                            data.setSender(object.getString("sender"));


                            data.setAcademic_advisor_id(object.getString("academic_advisor_id"));
                            data.setTraining_advisor_id(object.getString("training_advisor_id"));
                            data.setAdmin_id(object.getString("admin_id"));


                            data.setAcademic_advisor_name(object.getString("academic_advisor_name"));
                            data.setTraining_advisor_name(object.getString("training_advisor_name"));
                            data.setAdmin_name(object.getString("admin_name"));



                            arrayList.add(data);
                            adapter.notifyDataSetChanged();

                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MessageDetailsActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("training_advisor_id",training_advisor_id);
                params.put("admin_id",admin_id);
                params.put("academic_advisor_id",academic_advisor_id);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    private void AddMessage(){
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain + "AddMessage.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(MessageDetailsActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                content.setText("");
                getMessage();
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MessageDetailsActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("content", content.getText().toString());
                params.put("training_advisor_id",training_advisor_id);
                params.put("admin_id",admin_id);
                params.put("academic_advisor_id",academic_advisor_id);

                params.put("sender",String.valueOf(savedData.getId()));

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