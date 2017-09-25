package com.agnt45.audiogram;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    private CircleImageView dp;
    private TextView name,status;
    private Button un_follow,posts;
    private String dpUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent profile = this.getIntent();
        String ref = profile.getStringExtra("ref:");
        name = (TextView) findViewById(R.id.User_name);
        status =(TextView) findViewById(R.id.User_status);
        un_follow = (Button) findViewById(R.id.Follow);
        posts = (Button) findViewById(R.id.posts);



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

            }
        });
    }
}
