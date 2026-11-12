package com.graduation.kas.tadarab.Student;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.Register.RegisterStudentActivity;
import com.graduation.kas.tadarab.SavedData;
import com.graduation.kas.tadarab.WebSiteData;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentUpdateProfileActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_student_update_profile;
    }

    @Override
    public String getCustomTitle() {
        return "Profile";
    }

    EditText mobile,email,name,uploadcv,StudentID;
    Button update;
    ProgressDialog progressDialog;
    RequestQueue queue;
    SavedData savedData;
    BetterSpinner department;


    private static final int READ_STORAGE_CODE = 1001;
    private static final int PICK_IMAGE_REQUEST = 234;
    String cvbbase64 ="a";
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(StudentUpdateProfileActivity.this,R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(StudentUpdateProfileActivity.this);
        savedData = new SavedData(this);

        update = findViewById(R.id.update);


        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        department = findViewById(R.id.department);
        uploadcv= findViewById(R.id.uploadcv);
        StudentID = findViewById(R.id.StudentID);

        name.setText(savedData.getName());
        email.setText(savedData.getEmail());
        mobile.setText(savedData.getMobile());
        department.setText(savedData.getDepartment());
        uploadcv.setText(savedData.getCv());
        StudentID.setText(savedData.getId());



        uploadcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filemaneger();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, WebSiteData.Department);
        department.setAdapter(adapter);


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

        if (department.getText().toString().isEmpty()) {
            department.setError("Please Select Departmennt");
            flag = true;
        }

        if (StudentID.getText().toString().isEmpty()) {
            StudentID.setError("Please Enter Student ID");
            flag = true;
        } else {
            if (StudentID.getText().toString().length() != 10) {
                StudentID.setError("Please Enter Valid Student ID");
                flag = true;
            }
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
                    WebSiteData.domain + "StudentUpdateProfile.php", new Response.Listener<String>() {

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
                                savedData.setDepartment(department.getText().toString());
                                savedData.setId(StudentID.getText().toString());

                                if(cvbbase64.equals("a")){
                                    savedData.setCv(uploadcv.getText().toString());
                                }

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(StudentUpdateProfileActivity.this,"Username Already used!", Toast.LENGTH_SHORT).show();

                            }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(StudentUpdateProfileActivity.this, "Mobile Number Already used!", Toast.LENGTH_SHORT).show();

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
                    params.put("department", department.getText().toString());
                    params.put("cv", cvbbase64);
                    params.put("filename", uploadcv.getText().toString());
                    params.put("student_id", StudentID.getText().toString());

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
        int result = ContextCompat.checkSelfPermission(StudentUpdateProfileActivity.this, permission);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    private void requestPermission(String permission, int code) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(StudentUpdateProfileActivity.this, permission)) {

        }
        ActivityCompat.requestPermissions(StudentUpdateProfileActivity.this, new String[]{permission}, code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                filePath = data.getData();
                try {
                    InputStream in = getContentResolver().openInputStream(filePath);
                    byte[] bytes=getBytes(in);
                    cvbbase64 = Base64.encodeToString(bytes,Base64.DEFAULT);
                    String aaaa = UUID.randomUUID().toString().substring(0,9);
                    uploadcv.setText(aaaa + ".pdf");


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