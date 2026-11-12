package com.graduation.kas.tadarab.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.graduation.kas.tadarab.LoginActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.WebSiteData;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterStudentActivity extends AppCompatActivity {

    EditText username, mobile, email, password, cpassword, name, uploadcv, StudentID;
    BetterSpinner department;

    Button register;
    ProgressDialog progressDialog;
    RequestQueue queue;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();

    Boolean flag = false;

    private static final int READ_STORAGE_CODE = 1001;
    private static final int PICK_IMAGE_REQUEST = 234;
    String cvbbase64 = "";
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        progressDialog = new ProgressDialog(RegisterStudentActivity.this, R.style.AppTheme_Dark_Dialog);
        queue = Volley.newRequestQueue(RegisterStudentActivity.this);


        register = findViewById(R.id.register);
        department = findViewById(R.id.department);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        StudentID = findViewById(R.id.StudentID);

        cpassword = findViewById(R.id.cpassword);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        uploadcv = findViewById(R.id.uploadcv);


        uploadcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filemaneger();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, WebSiteData.Department);
        department.setAdapter(adapter);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Register();

            }
        });
/*
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (username.getText().toString().isEmpty()) {
                        username.setError("Please Enter Username");
                        flag = true;
                    }
                    else
                    {
                        flag=false;
                    }
                }
            }
        });

        mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (mobile.getText().toString().isEmpty()) {
                        mobile.setError("Please Enter Mobile Number");
                        flag = true;
                    } else {
                        if (mobile.getText().toString().length() != 10) {
                            mobile.setError("Please Enter Valid Mobile Number");
                            flag = true;
                        } else if (mobile.getText().toString().charAt(0) != '0' || mobile.getText().toString().charAt(1) != '5') {
                            mobile.setError("Please Enter Valid Mobile Number");
                            flag = true;
                        }
                        else
                        {
                            flag=false;
                        }
                    }
                }
            }
        });


        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (email.getText().toString().isEmpty()) {
                        email.setError("Please Enter Email");
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
                            email.setError("Please Enter Valid Email");
                            flag = true;
                        }
                        else
                        {
                            flag=false;
                        }

                    }
                }
            }
        });


        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (password.getText().toString().isEmpty()) {
                        password.setError("Please Enter Password");
                        flag = true;
                    }
                    else
                    {
                        flag=false;
                    }
                }
            }
        });

        cpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (cpassword.getText().toString().isEmpty()) {
                        cpassword.setError("Please Enter Confirm Password");
                        flag = true;
                    }
                    else if (!password.getText().toString().equals(cpassword.getText().toString())) {
                        cpassword.setError("Please Check Confirm Password");
                        flag = true;
                    }
                    else
                    {
                        flag=false;

                    }
                }
            }
        });


        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (name.getText().toString().isEmpty()) {
                        name.setError("Please Enter Name");
                        flag = true;
                    }
                    else
                    {
                        flag=false;

                    }
                }
            }
        });


        StudentID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (StudentID.getText().toString().isEmpty()) {
                        StudentID.setError("Please Enter Student ID");
                        flag = true;
                    } else {
                        if (StudentID.getText().toString().length() != 10) {
                            StudentID.setError("Please Enter Valid Student ID");
                            flag = true;
                        }
                        else
                        {
                            flag=false;

                        }
                    }
                }
            }
        });


        uploadcv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (uploadcv.getText().toString().isEmpty()) {
                        uploadcv.setError("Please Select Upload CV");
                        flag = true;
                    }
                    else
                    {
                        flag=false;
                    }
                }
            }
        });

        department.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (department.getText().toString().isEmpty()) {
                        department.setError("Please Select Department");
                        flag = true;
                    }
                    else
                    {
                        flag=false;
                    }
                }
            }
        });



        */
    }

    private void Register() {
        flag=false;
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

        if (uploadcv.getText().toString().isEmpty()) {
            uploadcv.setError("Please Select Upload CV");
            flag = true;
        }


        if (username.getText().toString().isEmpty()) {
            username.setError("Please Enter Username");
            flag = true;
        }
        if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Please Enter Mobile Number");
            flag = true;
        } else {
            if (mobile.getText().toString().length() != 10) {
                mobile.setError("Please Enter Valid Mobile Number");
                flag = true;
            } else if (mobile.getText().toString().charAt(0) != '0' || mobile.getText().toString().charAt(1) != '5') {
                mobile.setError("Please Enter Valid Mobile Number");
                flag = true;
            }
        }
        if (email.getText().toString().isEmpty()) {
            email.setError("Please Enter Email");
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
                email.setError("Please Enter Valid Email");
                flag = true;
            }
        }
        if (cpassword.getText().toString().isEmpty()) {
            cpassword.setError("Please Enter Confirm Password");
            flag = true;
        }
        if (!password.getText().toString().equals(cpassword.getText().toString())) {
            cpassword.setError("Please Check Confirm Password");
            flag = true;
        }


        if (password.getText().toString().isEmpty()) {
            password.setError("Please Enter Password");
            flag = true;
        } else {
            if (password.getText().toString().equals(password.getText().toString().toLowerCase())) {
                password.setError("Password Must contains Capital and small Letter");
                flag = true;

            }

            if (password.getText().toString().length() < 8) {
                password.setError("Password Must contains 8 Letters");
                flag = true;
            }
        }

        if (!flag) {
            progressDialog.setMessage(getResources().getString(R.string.wait));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    WebSiteData.domain + "StudentRegister.php", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean usernameavailable = jsonResponse.getBoolean("usernameavailable");
                        boolean mobileavailable = jsonResponse.getBoolean("mobileavailable");
                        boolean emailavailable = jsonResponse.getBoolean("emailavailable");

                        if (usernameavailable) {
                            if (mobileavailable) {
                                if (emailavailable) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterStudentActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterStudentActivity.this, "Email Already Used!", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterStudentActivity.this, "Mobile Number Already Used!", Toast.LENGTH_SHORT).show();

                            }


                        } else if (!usernameavailable) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterStudentActivity.this, "Username Already Used!", Toast.LENGTH_SHORT).show();

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

                    params.put("name", name.getText().toString());
                    params.put("username", username.getText().toString());
                    params.put("student_id", StudentID.getText().toString());
                    params.put("password", password.getText().toString());
                    params.put("mobile", mobile.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("department", department.getText().toString());
                    params.put("cv", cvbbase64);
                    params.put("filename", uploadcv.getText().toString());

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


    @Override
    protected void onResume() {
        super.onResume();

    }


    private void filemaneger() {

        if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
            intentPDF.setType("application/pdf");
            intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intentPDF, "Select CV"), PICK_IMAGE_REQUEST);
        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_CODE);
        }

    }

    private boolean isPermissionGranted(String permission) {
        int result = ContextCompat.checkSelfPermission(RegisterStudentActivity.this, permission);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    private void requestPermission(String permission, int code) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterStudentActivity.this, permission)) {

        }
        ActivityCompat.requestPermissions(RegisterStudentActivity.this, new String[]{permission}, code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                filePath = data.getData();
                try {
                    InputStream in = getContentResolver().openInputStream(filePath);
                    byte[] bytes = getBytes(in);
                    cvbbase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                    String aaaa = UUID.randomUUID().toString().substring(0, 9);
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