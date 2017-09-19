package com.agnt45.audiogram;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SettingsActivity extends AppCompatActivity {
    private DatabaseReference mUserreference;
    private FirebaseUser mcurrentUser;
    private CircleImageView profile_pic;
    private TextView Name,Status;
    private Button changeImage,changeStatus;
    private StorageReference storageReference;
    private Toolbar settingBar;
    private String image;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        storageReference = FirebaseStorage.getInstance().getReference();
        profile_pic = (CircleImageView) findViewById(R.id.profile_image) ;
        Name = (TextView) findViewById(R.id.Disp_name);
        profile_pic =(CircleImageView) findViewById(R.id.profile_image);
        Status = (TextView) findViewById(R.id.Disp_status);
        changeStatus = (Button) findViewById(R.id.change_status);
        changeImage = (Button) findViewById(R.id.change_image);
        settingBar = (Toolbar) findViewById(R.id.setting_bar);
        progressDialog =  new ProgressDialog(this);
        setSupportActionBar(settingBar);
        getSupportActionBar().setTitle("ACCOUNT SETTINGS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        settingBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,HomeActivity.class));
                finish();
            }
        });


        mcurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserreference = FirebaseDatabase.getInstance().getReference().child("Users").child(mcurrentUser.getUid());
        viewData(mUserreference);
        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Status_change = new Intent(SettingsActivity.this,StatusActivity.class);
                startActivity(Status_change);
                finish();
            }
        });
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d1 = new Dialog(SettingsActivity.this);
                d1.setContentView(R.layout.viewimage);
                final ImageView Dpic = (ImageView) d1.findViewById(R.id.pic);
                Picasso.with(SettingsActivity.this).load(image).into(Dpic);
                d1.show();
            }
        });
    }

    private void setData(String name, String status, String image, String thumb_image) {
        Name.setText(name);
        Status.setText(status);
        if(!(image.equals("default"))){
            Picasso.with(SettingsActivity.this).load(image).into(profile_pic);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File thumbimgfilePath = new File(resultUri.getPath());
                Bitmap thumbimg = null;
                try {
                    thumbimg = new Compressor(this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(75)
                            .compressToBitmap(thumbimgfilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbimg.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] byte_thumb = baos.toByteArray();
                /*Toast.makeText(SettingsActivity.this,resultUri.toString(),Toast.LENGTH_LONG).show();*/
                final StorageReference filePath = storageReference.child("Users").child("Profile_Pic").child(mcurrentUser.getUid()).child("dp.jpg");
                final StorageReference thumb_filePath = storageReference.child("Users").child("Profile_Pic").child(mcurrentUser.getUid()).child("thumb_dp.jpg");

                progressDialog.setTitle("Uploading Image..");
                progressDialog.setMessage("Image selected is being Uploaded please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            final String picUrl = task.getResult().getDownloadUrl().toString();
                            UploadTask uploadTask = thumb_filePath.putBytes(byte_thumb);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {
                                    String thumb_picUrl = thumb_task.getResult().getDownloadUrl().toString();
                                    if(thumb_task.isSuccessful()){
                                        Map imgHashMap = new HashMap();
                                        imgHashMap.put("image",picUrl);
                                        imgHashMap.put("thumbnail_image",thumb_picUrl);

                                        mUserreference.updateChildren(imgHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> tasK) {
                                                if(tasK.isSuccessful()){
                                                    viewData(mUserreference);
                                                }
                                            }
                                        });
                                    }
                                }
                            });



                        }
                        else{
                            progressDialog.hide();
                            Toast.makeText(SettingsActivity.this,"ERROR IN UPLOADING IMAGE",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    private void viewData(DatabaseReference Userreference){
        Userreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumbnail_image").getValue().toString();
                setData(name,status,image,thumb_image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(SettingsActivity.this,"UNABLE TO ACCESS DATA,CHECK INTERNET CONNECTION",Toast.LENGTH_LONG).show();

            }
        });
    }
}
