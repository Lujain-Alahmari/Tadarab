package com.graduation.kas.tadarab.TrainingAdvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;

public class TrainingAdvisorMainActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_training_academic_main;
    }

    @Override
    public String getCustomTitle() {
        return "Home";
    }


    SavedData savedData;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        name = findViewById(R.id.name);
        savedData = new SavedData(TrainingAdvisorMainActivity.this);


        name.setText("Welcome " + savedData.getName());

    }
}