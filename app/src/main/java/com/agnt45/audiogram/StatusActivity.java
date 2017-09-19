package com.agnt45.audiogram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatusActivity extends AppCompatActivity {
    private Toolbar statusBar;
    private TextInputLayout staTus;
    private FirebaseUser mcurrentUser;
    private DatabaseReference mUserreference;
    private Button change_status;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        statusBar = (Toolbar) findViewById(R.id.status_bar);
        staTus = (TextInputLayout) findViewById(R.id.status_input);
        change_status =  (Button) findViewById(R.id.cstatus);
        setSupportActionBar(statusBar);
        getSupportActionBar().setTitle("CHANGE STATUS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        statusBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StatusActivity.this,SettingsActivity.class));
                finish();
            }
        });
        mcurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserreference = FirebaseDatabase.getInstance().getReference().child("Users").child(mcurrentUser.getUid());
        mUserreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String status = dataSnapshot.child("status").getValue().toString();
                staTus.getEditText().setText(status);
                change_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(StatusActivity.this);
                        progressDialog.setTitle("Updating Status");
                        progressDialog.setMessage("Please Wait for a while ...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        mUserreference.child("status").setValue(staTus.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                }
                                else{
                                    progressDialog.hide();
                                    Toast.makeText(StatusActivity.this,"THERE WAS AN ERROR PLEASE TRY AGAIN",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
