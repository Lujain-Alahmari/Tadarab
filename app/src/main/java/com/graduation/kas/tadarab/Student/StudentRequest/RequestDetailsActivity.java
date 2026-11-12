package com.graduation.kas.tadarab.Student.StudentRequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;

public class RequestDetailsActivity  extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_details;
    }

    @Override
    public String getCustomTitle() {
        return "Request Details";
    }


    public static RequestDetails requestDetails;
    TextView title,details,plan,conditions,address,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        plan = findViewById(R.id.plan);
        conditions = findViewById(R.id.conditions);
        address = findViewById(R.id.address);
        name = findViewById(R.id.name);


        title.setText(requestDetails.getTitle());
        details.setText(requestDetails.getDetails());
        plan.setText(requestDetails.getPlan());
        conditions.setText(requestDetails.getConditions());
        address.setText(requestDetails.getAddress());
        name.setText(requestDetails.getTraining_area());



    }
}