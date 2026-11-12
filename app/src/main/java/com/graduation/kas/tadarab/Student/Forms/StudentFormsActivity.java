package com.graduation.kas.tadarab.Student.Forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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
import com.graduation.kas.tadarab.TrainingAdvisor.Request.Forms.Forms;
import com.graduation.kas.tadarab.WebSiteData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentFormsActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_training_forms;
    }

    @Override
    public String getCustomTitle() {
        return "Forms";

    }

    RecyclerView recyclerView;
    RequestQueue queue;
    ProgressDialog progressDialog;
    public static ArrayList<Forms> arrayList;
    StudentFormsAdapter adapter;
    SavedData savedData;

    String filebbase64 ="a";
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        progressDialog = new ProgressDialog(StudentFormsActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(StudentFormsActivity.this);
        savedData = new SavedData(this);
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.list);
        adapter = new StudentFormsAdapter(StudentFormsActivity.this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        findViewById(R.id.add).setVisibility(View.GONE);

    }
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        arrayList.clear();
        progressDialog.setMessage(getResources().getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebSiteData.domain+"getAll.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    if (result.length() == 0) {
                        Toast.makeText(getBaseContext(), "No Forms found", Toast.LENGTH_SHORT).show();


                        progressDialog.dismiss();
                    }else {
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject object = result.getJSONObject(i);
                            Forms data = new Forms();

                            data.setForm_id(object.getString("form_id"));
                            data.setFile(object.getString("file"));
                            data.setReply(object.getString("reply"));
                            data.setType(object.getString("type"));
                            data.setStudent_id(object.getString("student_id"));
                            if(data.getStudent_id().equals(savedData.getId())) {
                                arrayList.add(data);
                            }

                            adapter.notifyDataSetChanged();

                        }

                        if(arrayList.size() == 0){
                            Toast.makeText(getBaseContext(), "No Forms found", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
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
                params.put("table","forms");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 234) {
            if (resultCode == RESULT_OK) {
                filePath = data.getData();
                try {
                    InputStream in = getContentResolver().openInputStream(filePath);
                    byte[] bytes=getBytes(in);
                    filebbase64 = Base64.encodeToString(bytes,Base64.DEFAULT);
                    String aaaa = UUID.randomUUID().toString().substring(0,9);

                    progressDialog.setMessage(getResources().getString(R.string.wait));
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            WebSiteData.domain+"SendReply.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            getData();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(StudentFormsActivity.this, getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("form_id",StudentFormsAdapter.form_id);
                            params.put("replyname",aaaa);
                            params.put("reply",filebbase64);

                            return params;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(StudentFormsActivity.this);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            30000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(stringRequest);


                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}