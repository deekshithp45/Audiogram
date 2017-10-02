package com.agnt45.audiogram;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.agnt45.audiogram.Adapters.PostViewHolder;
import com.agnt45.audiogram.Classes.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostsActivity extends AppCompatActivity {
    private RecyclerView postView;
    private int numColumn = 3;
    private Toolbar postToolbar;
    private String uid;
    private AlertDialog.Builder Return;
    private DatabaseReference postDatabaseReference;
    private DatabaseReference uidpostDatabaseref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Intent post = this.getIntent();
        uid = post.getStringExtra("Key");
        postView = (RecyclerView) findViewById(R.id.post_view);
        postView.setHasFixedSize(false);
        postView.setLayoutManager(new GridLayoutManager(PostsActivity.this,numColumn));
        postToolbar = (Toolbar) findViewById(R.id.post_layout_bar);
        Return = new AlertDialog.Builder(this);
        setSupportActionBar(postToolbar);

        postDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");
        getSupportActionBar().setTitle("POSTS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        postDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uid)){
                    Log.d("Android", "done");
                    FirebaseRecyclerAdapter<Posts,PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Posts, PostViewHolder>(
                            Posts.class,R.layout.post_view_layout,PostViewHolder.class,postDatabaseReference
                    ) {
                        @Override
                        protected void populateViewHolder(PostViewHolder postViewHolder, Posts posts, int i) {
                            postViewHolder.setImage(posts.getFile1Url());
                            final String post_id = getRef(i).getKey();
                            postViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent post_view = new Intent(PostsActivity.this,PostViewActivity.class);
                                    post_view.putExtra("post_KEY",post_id);
                                    post_view.putExtra("post_USER",uid);
                                    startActivity(post_view);
                                    finish();
                                }
                            });
                        }
                    };
                    postView.setAdapter(firebaseRecyclerAdapter);
                }
                else{
                    Return.setMessage("No Posts,Press back to return To the profile").setPositiveButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent Retun = new Intent(PostsActivity.this,UserActivity.class);
                            startActivity(Retun);
                            finish();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}
