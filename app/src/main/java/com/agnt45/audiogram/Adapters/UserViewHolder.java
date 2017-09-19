package com.agnt45.audiogram.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agnt45.audiogram.AllUsersFragment;
import com.agnt45.audiogram.HomeActivity;
import com.agnt45.audiogram.R;
import com.agnt45.audiogram.SettingsActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ar265 on 9/17/2017.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {
    View mView;




    public UserViewHolder(View itemView) {
        super(itemView);
        mView =  itemView;
    }

    public void setName(String name) {
        TextView uName = (TextView) mView.findViewById(R.id.user_name);
        uName.setText(name);
    }

    public void setStatus(String status) {
        TextView uStaus = (TextView) mView.findViewById(R.id.user_status);
        uStaus.setText(status);
    }


    public void setThumbimage(String thumbnail_image) {
        CircleImageView cv1 = (CircleImageView) mView.findViewById(R.id.pic_user_single);
        Picasso.with(mView.getContext()).load(thumbnail_image).into(cv1);


    }
}
