package com.graduation.kas.tadarab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.graduation.kas.tadarab.AacademicAdvisor.AacademicAdvisorMainActivity;
import com.graduation.kas.tadarab.Admin.AdminMainActivity;
import com.graduation.kas.tadarab.Student.StudentMainActivity;
import com.graduation.kas.tadarab.TrainingAdvisor.TrainingAdvisorMainActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    SavedData savedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("en"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());


        savedData = new SavedData(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(savedData.getLoggedIn()){
                    if(savedData.getUsertype().equals("admin")){
                        Intent i = new Intent(MainActivity.this, AdminMainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    } if(savedData.getUsertype().equals("academic_advisor")){
                        Intent i = new Intent(MainActivity.this, AacademicAdvisorMainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }if(savedData.getUsertype().equals("student")){
                        Intent i = new Intent(MainActivity.this, StudentMainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }if(savedData.getUsertype().equals("training_advisor")){
                        Intent i = new Intent(MainActivity.this, TrainingAdvisorMainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }




                }else{
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }

            }
        }, 3000);



    }
}