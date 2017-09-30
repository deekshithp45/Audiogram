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
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    private CircleImageView dp;
    private TextView name, status, friendCount, mutualfriendCount;
    private Button un_follow, posts;
    private String dpUrl;
    private DatabaseReference databaseReference, frienddatabaseReference,userFriendsdatabaseReference;
    private Toolbar toolbar;
    private FirebaseUser firebaseUser;
    private String ref;
    private String current_State = "NOT FOLLOWING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent profile = this.getIntent();
        ref = profile.getStringExtra("ref:");
        name = (TextView) findViewById(R.id.User_name);
        status = (TextView) findViewById(R.id.User_status);
        un_follow = (Button) findViewById(R.id.Follow);
        posts = (Button) findViewById(R.id.posts);
        dp = (CircleImageView) findViewById(R.id.pic_User);
        toolbar = (Toolbar) findViewById(R.id.user_layout_bar);
        friendCount = (TextView) findViewById(R.id.FriendsCount);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(ref);
        databaseReference.keepSynced(true);
        showUser(databaseReference);
        if(firebaseUser.getUid().equals(ref)){

            un_follow.setVisibility(View.INVISIBLE);
            un_follow.setEnabled(false);
        }
        userFriendsdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        userFriendsdatabaseReference.keepSynced(true);
        mutualfriendCount = (TextView) findViewById(R.id.MutualFriendsCount);
        frienddatabaseReference = FirebaseDatabase.getInstance().getReference();
        frienddatabaseReference.keepSynced(true);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(UserActivity.this, HomeActivity.class);
                startActivity(back);
                finish();
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
                Intent posts = new Intent(UserActivity.this, PostsActivity.class);
                posts.putExtra("Key",ref);
                startActivity(posts);
                finish();
            }
        });
        un_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                un_follow.setEnabled(false);
                if(current_State.equals("NOT FOLLOWING")) {
                    frienddatabaseReference.child("Friends").
                            child(firebaseUser.getUid()).child(ref).child("Friend_status")
                            .setValue("SENT").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                frienddatabaseReference.child("Friends").child(ref)
                                        .child(firebaseUser.getUid()).child("Friend_status").setValue("RECIEVED").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            un_follow.setEnabled(true);
                                            un_follow.setText("WANT TO CANCEL IT OR WAIT FOR REPLY");
                                            current_State = "SENT";
                                        }
                                    }
                                });
                            }
                        }
                    });

                }
                else if(current_State.equals("SENT")||current_State.equals("ACCEPTED")){
                    frienddatabaseReference.child("Friends").child(firebaseUser.getUid()).child(ref).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                frienddatabaseReference.child("Friends").child(ref).child(firebaseUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            if(current_State.equals("ACCEPTED")){
                                                userFriendsdatabaseReference.child(firebaseUser.getUid()).child("friendsList").child(ref).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            userFriendsdatabaseReference.child(ref).child("friendsList").child(firebaseUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        current_State="NOT FOLLOWING";
                                                                        un_follow.setEnabled(true);
                                                                        un_follow.setText("FOLLOW");
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }


                                    }
                                });
                            }
                        }
                    });
                }
                else if(current_State.equals("REQUEST RECIEVED")){
                    frienddatabaseReference.child("Friends").child(firebaseUser.getUid()).child(ref).child("Friend_status").setValue("ACCEPTED").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                frienddatabaseReference.child("Friends").child(ref).child(firebaseUser.getUid()).child("Friend_status").setValue("ACCEPTED").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        final String TimeStamp = DateFormat.getDateTimeInstance().format(new Date());
                                        userFriendsdatabaseReference.child(firebaseUser.getUid()).child("friendsList").child(ref).setValue(TimeStamp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    userFriendsdatabaseReference.child(ref).child("friendsList").child(firebaseUser.getUid()).setValue(TimeStamp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            current_State="ACCEPTED";
                                                            un_follow.setEnabled(true);
                                                            un_follow.setText("UNFOLLOW");

                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }




    private void showUser(final DatabaseReference databaseReference) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name = dataSnapshot.child("name").getValue().toString();
                String Status = dataSnapshot.child("status").getValue().toString();
                String Image = dataSnapshot.child("image").getValue().toString();
                chk(frienddatabaseReference);

                setData(Name,Status,Image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void chk(DatabaseReference frienddatabaseReference) {
        this.frienddatabaseReference.child("Friends").child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(ref)) {
                            String fstat = dataSnapshot.child(ref).child("Friend_status").getValue().toString();
                            if (fstat.equals("SENT")) {
                                un_follow.setText("WANT TO CANCEL IT OR WAIT FOR REPLY");
                                current_State = "SENT";
                            } else if (fstat.equals("ACCEPTED")) {
                                un_follow.setText("UN_FOLLOW");
                                current_State = "ACCEPTED";

                            } else if (fstat.equals("RECIEVED")) {
                                un_follow.setText("ACCEPT FOLLOW REQUEST");
                                current_State = "REQUEST RECIEVED";
                            }
                        }
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
