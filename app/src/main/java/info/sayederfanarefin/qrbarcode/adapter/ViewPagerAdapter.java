package info.sayederfanarefin.qrbarcode.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.sayederfanarefin.qrbarcode.fragment.ChatFragment;
import info.sayederfanarefin.qrbarcode.fragment.FriendFragment;
import info.sayederfanarefin.qrbarcode.fragment.TimelineFragment;
import info.sayederfanarefin.qrbarcode.fragment.HomeFragment;


/**
 * Created by piashsarker on 7/10/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 3) {
            return new FriendFragment();
        } else if (position == 2) {
            return new ChatFragment();
        } else if (position == 1) {
            return new TimelineFragment();
        } else {
            return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}