package com.graduation.kas.tadarab.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.graduation.kas.tadarab.NavigationDrawerActivity;
import com.graduation.kas.tadarab.R;
import com.graduation.kas.tadarab.SavedData;

public class AdminMainActivity extends NavigationDrawerActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_main;
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
        savedData = new SavedData(AdminMainActivity.this);

        name.setText("Welcome " + savedData.getName());

    }
}