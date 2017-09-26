package com.agnt45.audiogram;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    private CircleImageView dp;
    private TextView name,status,friendCount,mutualfriendCount;
    private Button un_follow,posts;
    private String dpUrl;
    private DatabaseReference databaseReference,frienddatabaseReference;
    private Toolbar toolbar;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent profile = this.getIntent();
        final String ref = profile.getStringExtra("ref:");
        name = (TextView) findViewById(R.id.User_name);
        status =(TextView) findViewById(R.id.User_status);
        un_follow = (Button) findViewById(R.id.Follow);
        posts = (Button) findViewById(R.id.posts);
        dp = (CircleImageView) findViewById(R.id.pic_User);
        toolbar = (Toolbar) findViewById(R.id.user_layout_bar);
        friendCount = (TextView) findViewById(R.id.FriendsCount);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(ref);
        showUser(databaseReference);
        mutualfriendCount = (TextView) findViewById(R.id.MutualFriendsCount);
        frienddatabaseReference = FirebaseDatabase.getInstance().getReference();


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(UserActivity.this,HomeActivity.class);
                startActivity(back);
            }
        });







        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d1 = new Dialog(UserActivity.this);
                d1.setContentView(R.layout.viewimage);
                final ImageView Dpic = (ImageView) d1.findViewById(R.id.pic);
                Picasso.with(UserActivity.this).load(dpUrl).into(Dpic);
                d1.show();
            }
        });
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent posts = new Intent(UserActivity.this,PostsActivity.class);
                startActivity(posts);
                finish();
            }
        });
        un_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frienddatabaseReference.child("Users").child(firebaseUser.getUid()).child("Friends").child(ref).child("Request_Status").setValue("SENT").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            databaseReference.child("Friends").child(ref).child("Request_Status").setValue("RECIEVED").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        un_follow.setText("REQUEST SENT");                                    }
                                }
                            });
                        }
                    }
                });




            }
        });
    }

    private void showUser(DatabaseReference databaseReference) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.child("name").getValue().toString();
                String Status = dataSnapshot.child("status").getValue().toString();
                String Image = dataSnapshot.child("image").getValue().toString();


                setData(Name,Status,Image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setData(String Name, String Status, String Image) {
        name.setText(Name);
        status.setText(Status);
        Picasso.with(UserActivity.this).load(Image).into(dp);

    }


}
