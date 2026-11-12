package com.graduation.kas.tadarab.Student.Forms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.TrainingAdvisor.Request.Forms.AddFromsActivity;
import com.graduation.kas.tadarab.TrainingAdvisor.Request.Forms.Forms;
import com.graduation.kas.tadarab.WebSiteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class StudentFormsAdapter extends RecyclerView.Adapter<StudentFormsAdapter.SingleItemRowHolder> {

    private static final int READ_STORAGE_CODE = 1001;
    private static final int PICK_IMAGE_REQUEST = 234;

    private ArrayList<Forms> itemsList;
    private Activity mContext;
    ProgressDialog progressDialog;
    public static String form_id = "";

    public StudentFormsAdapter(Activity context, ArrayList<Forms> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);

    }


    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student_forms, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, @SuppressLint("RecyclerView") final int pos) {

        final Forms singleItem = itemsList.get(pos);

        if(singleItem.getReply().equals(" ")) {
            holder.reply.setText("No answer");
            holder.downloadreply.setVisibility(View.GONE);
            holder.reply.setVisibility(View.VISIBLE);
        }else{
            holder.reply.setText(singleItem.getReply());
            holder.downloadreply.setVisibility(View.VISIBLE);
            holder.reply.setVisibility(View.GONE);
        }

        holder.type.setText(singleItem.getType());
        holder.file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebSiteData.domain + "uploaded/"+singleItem.getFile()));
                mContext.startActivity(browserIntent);

            }
        });

        holder.downloadreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebSiteData.domain + "uploaded/"+singleItem.getReply()));
                mContext.startActivity(browserIntent);

            }
        });

        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form_id = singleItem.getForm_id();
                filemaneger();

            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView type,reply;
        protected Button file,send,downloadreply;

        public SingleItemRowHolder(View view) {
            super(view);

            this.type = (TextView) view.findViewById(R.id.type);
            this.reply = (TextView) view.findViewById(R.id.reply);
            this.file = (Button) view.findViewById(R.id.file);
            this.send = (Button) view.findViewById(R.id.send);
            this.downloadreply =  view.findViewById(R.id.downloadreply);


        }

    }

    private void filemaneger() {

        if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
            intentPDF.setType("application/pdf");
            intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
            mContext.startActivityForResult(Intent.createChooser(intentPDF , "Select CV"), PICK_IMAGE_REQUEST);
        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_CODE);
        }

    }
    private boolean isPermissionGranted(String permission) {
        int result = ContextCompat.checkSelfPermission(mContext, permission);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    private void requestPermission(String permission, int code) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, permission)) {

        }
        ActivityCompat.requestPermissions(mContext, new String[]{permission}, code);
    }


}