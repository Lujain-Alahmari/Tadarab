package com.graduation.kas.tadarab.TrainingAdvisor.Request.Forms;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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
import com.graduation.kas.tadarab.TrainingAdvisor.Request.RequestDetailsActivity;
import com.graduation.kas.tadarab.WebSiteData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddFromsActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_froms;
    }

    @Override
    public String getCustomTitle() {
        return "Add Form";

    }

    EditText name,formfile;
    Button add;
    ProgressDialog progressDialog;
    RequestQueue queue;
    SavedData savedData;


    private static final int READ_STORAGE_CODE = 1001;
    private static final int PICK_IMAGE_REQUEST = 234;
    String filebbase64 ="a";
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(AddFromsActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(AddFromsActivity.this);
        savedData = new SavedData(this);

        add = findViewById(R.id.add);


        name = findViewById(R.id.name);
        formfile = findViewById(R.id.formfile);




        formfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filemaneger();
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddForm();
            }
        });

    }
    private void AddForm() {
        Boolean flag = false;
        if (name.getText().toString().isEmpty()) {
            name.setError("Please Enter Form Name");
            flag = true;
        }

        if (formfile.getText().toString().isEmpty()) {
            formfile.setError("Please Pick Form File");
            flag = true;
        }

        if (!flag) {
            progressDialog.setMessage(getResources().getString(R.string.wait));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    WebSiteData.domain + "AddForm.php", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Toast.makeText(AddFromsActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
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
                    params.put("type", name.getText().toString());
                    params.put("student_id", RequestDetailsActivity.requestDetails.getStudent_id());
                    params.put("filea", filebbase64);
                    params.put("filename", formfile.getText().toString());

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

    private void filemaneger() {

        if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
            intentPDF.setType("application/pdf");
            intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intentPDF , "Select CV"), PICK_IMAGE_REQUEST);
        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_CODE);
        }

    }

    private boolean isPermissionGranted(String permission) {
        int result = ContextCompat.checkSelfPermission(AddFromsActivity.this, permission);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    private void requestPermission(String permission, int code) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(AddFromsActivity.this, permission)) {

        }
        ActivityCompat.requestPermissions(AddFromsActivity.this, new String[]{permission}, code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                filePath = data.getData();
                try {
                    InputStream in = getContentResolver().openInputStream(filePath);
                    byte[] bytes=getBytes(in);
                    filebbase64 = Base64.encodeToString(bytes,Base64.DEFAULT);
                    String aaaa = UUID.randomUUID().toString().substring(0,9);
                    formfile.setText(aaaa + ".pdf");


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