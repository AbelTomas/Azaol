package com.atomasg.azaol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Navigator {

    private final String TAG = getClass().getSimpleName();
    private AppCompatActivity activity;

    public Navigator(AppCompatActivity activity){
        this.activity=activity;
    }

    public void goToMenu() {
        Log.d(TAG,"Going to menu...");
        Intent intert = new Intent(activity, MenuActivity.class);
        activity.startActivity(intert);

    }

    protected void goToRegister() {
        Log.d(TAG,"Going to register...");
        Intent intert = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intert);
    }

    protected void goToSettings() {
        Log.d(TAG,"Going to register...");
        Intent intert = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intert);

    }

//    protected void goToMap() {
//
//    }

    protected void goToList(){
       // Log.d(TAG,"Going to register list...");
       // Intent intert = new Intent(activity, RegisterList.class);
       // activity.startActivity(intert);

    }

    protected void goToLogin() {
        Log.d(TAG,"Going to Login...");
        //   Intent intert = new Intent(Navigation.this, LoginActivity.class);
        //   startActivity(intert);
    }
}


