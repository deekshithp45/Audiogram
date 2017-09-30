package com.agnt45.audiogram;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agnt45.audiogram.Adapters.UserViewHolder;
import com.agnt45.audiogram.Classes.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllUsersFragment extends Fragment {


    public AllUsersFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_all_users, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.user_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Users,UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UserViewHolder>(
                Users.class,R.layout.user_view_layout,UserViewHolder.class,databaseReference
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder userViewHolder, Users users, int i) {
                userViewHolder.setName(users.getName());
                userViewHolder.setStatus(users.getStatus());
                userViewHolder.setThumbimage(users.getThumbnail_image());
                final String user_id = getRef(i).getKey();
                userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent porfileIntent = new Intent(getActivity(),UserActivity.class);
                        porfileIntent.putExtra("ref:",user_id);
                        startActivity(porfileIntent);

                    }
                });

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }



}
