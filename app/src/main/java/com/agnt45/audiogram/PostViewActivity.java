package com.agnt45.audiogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PostViewActivity extends AppCompatActivity {
    private ImageView picImage;
    private TextView likesCount,CommentsCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
    }
}
