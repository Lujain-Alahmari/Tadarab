package com.graduation.kas.tadarab;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SavedData {

    String mobile;
    String usertype;
    String name;
    String email;
    String username;
    String id;
    String department;
    String training_area;
    String address;
    String cv;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;


    public String getCv() {
        return sp.getString("cv", cv);
    }

    public void setCv(String cv) {
        this.cv = cv;
        spEditor=sp.edit();
        spEditor.putString("cv", cv);
        spEditor.commit();
    }




    public String getTraining_area() {
        return sp.getString("training_area", training_area);
    }

    public void setTraining_area(String training_area) {
        this.training_area = training_area;
        spEditor=sp.edit();
        spEditor.putString("training_area", training_area);
        spEditor.commit();
    }



    public String getAddress() {
        return sp.getString("address", address);
    }

    public void setAddress(String address) {
        this.address = address;
        spEditor=sp.edit();
        spEditor.putString("address", address);
        spEditor.commit();
    }




    public String getDepartment() {
        return sp.getString("department", department);
    }

    public void setDepartment(String department) {
        this.department = department;
        spEditor=sp.edit();
        spEditor.putString("department", department);
        spEditor.commit();
    }


    public String getUsername() {
        return sp.getString("username", username);
    }

    public void setUsername(String username) {
        this.username = username;
        spEditor=sp.edit();
        spEditor.putString("username", username);
        spEditor.commit();
    }


    public String getEmail() {
        return sp.getString("email", email);
    }

    public void setEmail(String email) {
        this.email = email;
        spEditor=sp.edit();
        spEditor.putString("email", email);
        spEditor.commit();
    }




    public void setName(String name)
    {
        this.name =name;
        spEditor=sp.edit();
        spEditor.putString("name", name);
        spEditor.commit();
    }
    public String getName()
    {
        return sp.getString("name", name);
    }


    public SavedData(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);

    }
    public void setMobile(String mobile)
    {
        this.mobile =mobile;
        spEditor=sp.edit();
        spEditor.putString("mobile", mobile);
        spEditor.commit();
    }
    public String getMobile()
    {
        return sp.getString("mobile", mobile);
    }

    public void setId(String id)
    {
        this.id=id;
        spEditor=sp.edit();
        spEditor.putString("id",id);
        spEditor.commit();
    }
    public  String getId()
    {
        return sp.getString("id",id);
    }

    public void setUsertype(String usertype)
    {
        this.usertype = usertype;
        spEditor=sp.edit();
        spEditor.putString("role", usertype);
        spEditor.commit();
    }

    public String getUsertype()
    {
        return sp.getString("role", usertype);
    }

    public boolean setLogin(boolean status) {
        spEditor = sp.edit();
        spEditor.putBoolean("is_logged_in", status);
        spEditor.commit();
        return true;
    }

    public boolean getLoggedIn() {
        return sp.getBoolean("is_logged_in", false);
    }

}
