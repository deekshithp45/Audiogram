package com.agnt45.audiogram;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity" ;
    private  TextInputLayout name,email,password,cpassword;
    private Button create;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ProgressDialog mProgress;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        name = (TextInputLayout) findViewById(R.id.reg_dispName);
        email = (TextInputLayout) findViewById(R.id.reg_dispEmail);
        password = (TextInputLayout) findViewById(R.id.reg_passWord);
        cpassword = (TextInputLayout) findViewById(R.id.reg_confPass);
        create =  (Button) findViewById(R.id.create_acnt);
        mToolbar = (Toolbar) findViewById(R.id.reg_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("REGISTER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgress =  new ProgressDialog(this);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getEditText().getText().toString().equals(cpassword.getEditText().getText().toString())){

                    String uname = name.getEditText().getText().toString();
                    String uemail = email.getEditText().getText().toString();
                    String upassword = password.getEditText().getText().toString();
                    if(TextUtils.isEmpty(uname)|| TextUtils.isEmpty(uemail)||TextUtils.isEmpty(upassword)){
                        Toast.makeText(RegisterActivity.this,"PLEASE FILL ALL THE DETAILS..",Toast.LENGTH_SHORT).show();
                    }
                    else  {
                        mProgress.setTitle("Registering User ...");
                        mProgress.setMessage("Please wait ....");
                        mProgress.setCanceledOnTouchOutside(false);
                        mProgress.show();
                        registerUser(uname,uemail,upassword);

                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this,"PASSWORD DOES NOT MATCH",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public  void registerUser(final String aa, String bb, String cc){
        mAuth.createUserWithEmailAndPassword(bb, cc)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser curretUser = FirebaseAuth.getInstance().getCurrentUser();

                            String uid = curretUser.getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            HashMap<String,String> userMap =new HashMap<>();
                            userMap.put("name",aa);
                            userMap.put("status","hey I am on Audiogram");
                            userMap.put("image","Default");
                            userMap.put("thumbnail_image","Default");
                            databaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mProgress.dismiss();
                                        startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                                        finish();
                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            mProgress.hide();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
