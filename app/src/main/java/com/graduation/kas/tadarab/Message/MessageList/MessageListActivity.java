package com.graduation.kas.tadarab.Message.MessageList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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

public class MessageListActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    public String getCustomTitle() {
        return "Inbox";
    }

    RecyclerView recyclerView;
    RequestQueue queue;
    ProgressDialog progressDialog;
    public static ArrayList<MessageList> arrayList;
    MessageListAdapter adapter;
    SavedData savedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(MessageListActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(MessageListActivity.this);
        savedData = new SavedData(MessageListActivity.this);


        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.list);
        adapter = new MessageListAdapter(MessageListActivity.this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);


    }
    @Override
    protected void onResume() {
        super.onResume();
        getMessageList();

    }

    private void getMessageList(){
        arrayList.clear();
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain+ "getMessageList.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    if (result.length() == 0) {
                        Toast.makeText(MessageListActivity.this, "No Message Found", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else {
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject object = result.getJSONObject(i);

                            MessageList data = new MessageList();

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
                Toast.makeText(MessageListActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", savedData.getId());
                params.put("role", savedData.getUsertype());


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