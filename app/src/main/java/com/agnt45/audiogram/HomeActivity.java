package com.agnt45.audiogram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.agnt45.audiogram.Adapters.viewTabPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ViewPager mTabpager;
    private viewTabPagerAdapter mAdaptertab;
    private TabLayout mtabLayout;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar)findViewById(R.id.Home_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Audiogram");
        mTabpager = (ViewPager) findViewById(R.id.tabVpagger);
        mAdaptertab =  new viewTabPagerAdapter(getSupportFragmentManager());
        mTabpager.setAdapter(mAdaptertab);
        mtabLayout = (TabLayout) findViewById(R.id.home_Tab);
        mtabLayout.setupWithViewPager(mTabpager);
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            sendLoading();
        }
    }

    public void sendLoading() {
        Intent Main = new Intent(HomeActivity.this,LoadingActivity.class);
        startActivity(Main);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.logout_btn){
            progressDialog.setTitle("Please Wait ..");
            progressDialog.setMessage("Logging out User");
            progressDialog.setCanceledOnTouchOutside(false);
            FirebaseAuth.getInstance().signOut();
            progressDialog.dismiss();
            sendLoading();
        }
        else if(item.getItemId() == R.id.setting_btn){
            Intent settings = new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(settings);
            finish();
        }
        return true;
    }
}
