package com.agnt45.audiogram;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agnt45.audiogram.Adapters.PostLikeUserViewHolder;
import com.agnt45.audiogram.Classes.Userid;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostViewActivity extends AppCompatActivity {
    private ImageView picImage;
    private Toolbar mToolbar,uToolbar;
    private CircleImageView dp;
    private TextView dispName,likesCount,CommentsCount;
    private DatabaseReference postImgDatabaseReference;
    private DatabaseReference userDatebaseReference;
    private String name,imgurl;
    private String fileurl,comments,likes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Intent recv_Post_view = this.getIntent();
        String postid = recv_Post_view.getStringExtra("post_KEY");
        String userid = recv_Post_view.getStringExtra("post_USER");
        postImgDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(userid).child(postid);
        userDatebaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        userDatebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue().toString();
                imgurl = dataSnapshot.child("thumbnail_image").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        postImgDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fileurl = dataSnapshot.child("file1Url").getValue().toString();
                likes = dataSnapshot.child("like_count").getValue().toString();
                comments = dataSnapshot.child("comment_count").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        picImage =  (ImageView) findViewById(R.id.pic_post);
        mToolbar = (Toolbar) findViewById(R.id.post_view_layout);
        uToolbar = (Toolbar) findViewById(R.id.post_user_view_layout);
        dp = (CircleImageView) uToolbar.findViewById(R.id.user_dp_post);
        dispName =(TextView) uToolbar.findViewById(R.id.user_name_post);
        likesCount =(TextView) findViewById(R.id.post_likes);
        CommentsCount = (TextView) findViewById(R.id.post_comments);
        dispName.setText(name);
        Picasso.with(PostViewActivity.this).load(imgurl).into(dp);
        Picasso.with(PostViewActivity.this).load(fileurl).into(picImage);
        likesCount.setText(likes);
        CommentsCount.setText(comments);
        likesCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog Like = new Dialog(PostViewActivity.this);
                Like.setContentView(R.layout.like_dialog);
                RecyclerView likeview = (RecyclerView) findViewById(R.id.like_view);
                likeview.setHasFixedSize(true);
                likeview.setLayoutManager(new LinearLayoutManager(PostViewActivity.this));

                FirebaseRecyclerAdapter<Userid,PostLikeUserViewHolder> likeFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Userid, PostLikeUserViewHolder>(
                        Userid.class,R.layout.user_post_like_layout,PostLikeUserViewHolder.class,postImgDatabaseReference.child("like_list")
                ) {
                    @Override
                    protected void populateViewHolder(PostLikeUserViewHolder postLikeUserViewHolder, Userid userid, int i) {
                        postLikeUserViewHolder.getuid(userid.getUid());
                    }
                };
            }
        });
        CommentsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog comment =  new Dialog(PostViewActivity.this);
                comment.setContentView(R.layout.comment_dialog);
                RecyclerView commentview = (RecyclerView) findViewById(R.id.comment_view);
                commentview.setHasFixedSize(true);
                commentview.setLayoutManager(new LinearLayoutManager(PostViewActivity.this));

            }
        });

    }
}
