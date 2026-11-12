package com.graduation.kas.tadarab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.graduation.kas.tadarab.Register.RegisterAcademicAdvisorActivity;
import com.graduation.kas.tadarab.Register.RegisterStudentActivity;
import com.graduation.kas.tadarab.Register.RegisterTrainingAdvisorActivity;

public class UserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        findViewById(R.id.student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), RegisterStudentActivity.class));
            }
        });


        findViewById(R.id.Acadimicadvisor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), RegisterAcademicAdvisorActivity.class));
            }
        });


        findViewById(R.id.trainingadvisor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), RegisterTrainingAdvisorActivity.class));
            }
        });
    }
}