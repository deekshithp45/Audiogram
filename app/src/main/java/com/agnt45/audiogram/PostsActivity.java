package com.agnt45.audiogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class PostsActivity extends AppCompatActivity {
    private RecyclerView postView;
    private int numColumn = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        postView = (RecyclerView) findViewById(R.id.post_view);
        postView.setHasFixedSize(true);
        postView.setLayoutManager(new GridLayoutManager(PostsActivity.this,numColumn));

    }
}
