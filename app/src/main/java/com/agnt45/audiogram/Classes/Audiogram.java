package com.agnt45.audiogram.Classes;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ar265 on 9/27/2017.
 */

public class Audiogram extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
