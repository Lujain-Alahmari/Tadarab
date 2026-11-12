package com.graduation.kas.tadarab;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.graduation.kas.tadarab.AacademicAdvisor.AacademicAdvisorMainActivity;
import com.graduation.kas.tadarab.AacademicAdvisor.AcademicAdvisorProfileActivity;
import com.graduation.kas.tadarab.AacademicAdvisor.MyStudent.MyStudentActivity;
import com.graduation.kas.tadarab.Admin.AdminMainActivity;
import com.graduation.kas.tadarab.Admin.AdminUpdateProfileActivity;
import com.graduation.kas.tadarab.Admin.Users.StudentListActivity;
import com.graduation.kas.tadarab.Admin.Users.UsersActivity;
import com.graduation.kas.tadarab.Message.MessageDetailsActivity;
import com.graduation.kas.tadarab.Message.MessageList.MessageListActivity;
import com.graduation.kas.tadarab.Student.StudentMainActivity;
import com.graduation.kas.tadarab.Student.StudentRequest.StudentRequestActivity;
import com.graduation.kas.tadarab.Student.StudentUpdateProfileActivity;
import com.graduation.kas.tadarab.Student.TrainingArea.TrainingAreaActivity;
import com.graduation.kas.tadarab.Student.TrainingAreaInfoActivity;
import com.graduation.kas.tadarab.TrainingAdvisor.Request.RequestActivity;
import com.graduation.kas.tadarab.TrainingAdvisor.TrainingAdvisorMainActivity;
import com.graduation.kas.tadarab.TrainingAdvisor.TrainingAdvisorProfileActivity;
import com.graduation.kas.tadarab.TrainingAdvisor.TrainingProgram.MyTrainingProgramActivity;


public abstract class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView navigationView;

    SavedData savedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigration_activity);
        savedData = new SavedData(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getCustomTitle());
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.main_content);
        getLayoutInflater().inflate(getLayoutId(), frameLayout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedData.getUsertype().equals("admin")) {
            navigationView.inflateMenu(R.menu.admin);
        }else if(savedData.getUsertype().equals("academic_advisor")) {
            navigationView.inflateMenu(R.menu.academic_advisor);
        }else if(savedData.getUsertype().equals("training_advisor")) {
            navigationView.inflateMenu(R.menu.training_advisor);
        }else if(savedData.getUsertype().equals("student")) {
            navigationView.inflateMenu(R.menu.student);
        }






    }

    public abstract int getLayoutId();

    public abstract String getCustomTitle();

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(savedData.getUsertype().equals("admin")) {
            int id = item.getItemId();
            if (id == R.id.profile) {
                launchActivity(AdminUpdateProfileActivity.class);
            }else if (id == R.id.password) {

                launchActivity(UpdatePasswordActivity.class);

            }else if (id == R.id.academic_advisor) {

                launchActivity(UsersActivity.class,"academic_advisor");

            }else if (id == R.id.training_advisor) {

                launchActivity(UsersActivity.class,"training_advisor");

            }else if (id == R.id.student) {

                launchActivity(StudentListActivity.class);

            } else if (id == R.id.messagelist) {

                launchActivity(MessageListActivity.class);

            }
            else if (id == R.id.logout) {
                savedData.setLogin(false);
                savedData.setEmail("");
                savedData.setId("");
                savedData.setMobile("");
                savedData.setName("");
                startActivity(new Intent(getBaseContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        else if(savedData.getUsertype().equals("academic_advisor")) {
            int id = item.getItemId();
            if (id == R.id.profile) {
                launchActivity(AcademicAdvisorProfileActivity.class);
            }else if (id == R.id.password) {

                launchActivity(UpdatePasswordActivity.class);

            }else if (id == R.id.mystudent) {

                launchActivity(MyStudentActivity.class);

            }else if (id == R.id.home) {

                launchActivity(AacademicAdvisorMainActivity.class);

            }else if (id == R.id.messagelist) {

                launchActivity(MessageListActivity.class);

            }
            else if (id == R.id.adminmessage) {

                startActivity(makePageIntent(MessageDetailsActivity.class)
                        .putExtra("admin_id","1")
                        .putExtra("training_advisor_id","0")
                        .putExtra("academic_advisor_id",savedData.getId()));


                overridePendingTransition(0,0);
            }



            else if (id == R.id.logout) {
                savedData.setLogin(false);
                savedData.setEmail("");
                savedData.setId("");
                savedData.setMobile("");
                savedData.setName("");
                startActivity(new Intent(getBaseContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        else if(savedData.getUsertype().equals("training_advisor")) {
            int id = item.getItemId();
            if (id == R.id.profile) {
                launchActivity(TrainingAdvisorProfileActivity.class);
            }else if (id == R.id.password) {

                launchActivity(UpdatePasswordActivity.class);

            }else if (id == R.id.home) {

                launchActivity(TrainingAdvisorMainActivity.class);

            }

            else if (id == R.id.myRequest) {

                launchActivity(RequestActivity.class);

            }
            else if (id == R.id.messagelist) {

                launchActivity(MessageListActivity.class);

            }
            else if (id == R.id.adminmessage) {

                startActivity(makePageIntent(MessageDetailsActivity.class)
                        .putExtra("admin_id","1")
                        .putExtra("training_advisor_id",savedData.getId())
                        .putExtra("academic_advisor_id","0"));


                overridePendingTransition(0,0);
            }

            else if (id == R.id.trainingprogram) {

                launchActivity(MyTrainingProgramActivity.class);

            }
            else if (id == R.id.logout) {
                savedData.setLogin(false);
                savedData.setEmail("");
                savedData.setId("");
                savedData.setMobile("");
                savedData.setName("");
                startActivity(new Intent(getBaseContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        else if(savedData.getUsertype().equals("student")) {
            int id = item.getItemId();
            if (id == R.id.profile) {
                launchActivity(StudentUpdateProfileActivity.class);
            }else if (id == R.id.password) {

                launchActivity(UpdatePasswordActivity.class);

            }else if (id == R.id.trainingAreainfo) {

                launchActivity(TrainingAreaInfoActivity.class);

            }else if (id == R.id.home) {

                launchActivity(StudentMainActivity.class);

            }else if (id == R.id.training_area) {

                launchActivity(TrainingAreaActivity.class);

            }


            else if (id == R.id.myRequest) {

                launchActivity(StudentRequestActivity.class);

            }






            else if (id == R.id.logout) {
                savedData.setLogin(false);
                savedData.setEmail("");
                savedData.setId("");
                savedData.setMobile("");
                savedData.setName("");
                startActivity(new Intent(getBaseContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;

    }

    private void launchActivity(Class _class) {
        //Avoid launch the same activity
        if (this.getClass() != _class) {
            startActivity(makePageIntent(_class));
            overridePendingTransition(0,0);
            //  finish();
        }
    }

    private void launchActivity(Class _class,String extra) {
        //Avoid launch the same activity
//        if (this.getClass() != _class) {
            startActivity(makePageIntent(_class).putExtra("type",extra));
            overridePendingTransition(0,0);
            // finish();
//        }
    }

    public Intent makePageIntent(Class _class) {
        Intent intent = new Intent(this, _class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
