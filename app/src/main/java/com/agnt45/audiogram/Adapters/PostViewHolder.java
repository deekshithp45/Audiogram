package com.agnt45.audiogram.Adapters;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.agnt45.audiogram.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ar265 on 9/25/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder{
    View mView;
    public PostViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void setImage(String file1Url) {
        ImageView postthumb = (ImageView) mView.findViewById(R.id.post_image);
        Picasso.with(mView.getContext()).load(file1Url).into(postthumb);

    }
}
