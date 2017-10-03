package com.agnt45.audiogram.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.agnt45.audiogram.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ar265 on 10/3/2017.
 */

public class PostLikeUserViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public PostLikeUserViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void getuid(String uid) {
        DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        keyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String dp = dataSnapshot.child("thumbnail_image").getValue().toString();
                CircleImageView dpimg =  (CircleImageView) mView.findViewById(R.id.pic_user_like);
                TextView Name = (TextView) mView.findViewById(R.id.user_name_like);
                Name.setText(name);
                Picasso.with(mView.getContext()).load(dp).into(dpimg);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
