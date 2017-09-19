package com.agnt45.audiogram;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private TextInputLayout email,passwd;
    private Button blogin;
    private Toolbar mToolbar;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = (Toolbar) findViewById(R.id.login_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("LOG IN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        email =  (TextInputLayout) findViewById(R.id.LogEmail);
        passwd = (TextInputLayout) findViewById(R.id.LogPassword);
        blogin = (Button) findViewById(R.id.Login);
        mProgress = new ProgressDialog(this);

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getEditText().getText().toString();
                String Password = passwd.getEditText().getText().toString();
               Log.d("email:",Email);
               Log.d("password",Password);
                if(TextUtils.isEmpty(Email)||TextUtils.isEmpty(Password)){
                    Toast.makeText(LoginActivity.this,"Something is not right",Toast.LENGTH_SHORT);

                }
                else{
                    mProgress.setTitle("Loggin User .." );
                    mProgress.setMessage("Please wait ....");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();
                    loGin(Email,Password);
                }
            }
        });


    }

    private void loGin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mProgress.dismiss();
                            Intent Loggedin = new Intent(LoginActivity.this,HomeActivity.class);

                            startActivity(Loggedin);
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            mProgress.hide();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }
}
