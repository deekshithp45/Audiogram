package com.agnt45.audiogram.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.agnt45.audiogram.AllUsersFragment;
import com.agnt45.audiogram.ChatFragment;
import com.agnt45.audiogram.FriendsFragment;
import com.agnt45.audiogram.RequestFragment;

/**
 * Created by ar265 on 9/16/2017.
 */

public class viewTabPagerAdapter extends FragmentPagerAdapter{


    public viewTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {


        switch (pos){
            case 0:
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;
            case 1:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            case 2:
                FriendsFragment friendsFragment =  new FriendsFragment();
                return friendsFragment;
            case 3:
                AllUsersFragment allUsersFragment =  new AllUsersFragment();
                return allUsersFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
    public CharSequence getPageTitle(int pos){
        switch (pos){
            case 0:
                return "REQUESTS";
            case 1:
                return "CHATS";
            case 2:
                return "FRIENDS";
            case 3:
                return "ALL USERS";
            default:
                return null;
        }
    }
}
