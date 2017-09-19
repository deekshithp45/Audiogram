package com.agnt45.audiogram;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoadingActivity extends AppCompatActivity {

    private Button Registerm,Loginm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Registerm = (Button) findViewById(R.id.regButton);
        Loginm = (Button) findViewById(R.id.logButton);
        Registerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Register = new Intent(LoadingActivity.this,RegisterActivity.class);
                startActivity(Register);
                finish();
            }
        });
        Loginm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(LoadingActivity.this,LoginActivity.class);
                startActivity(Login);
                finish();
            }
        });
    }
}
