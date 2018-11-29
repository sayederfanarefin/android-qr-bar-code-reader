package info.sayederfanarefin.qrbarcode.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import info.sayederfanarefin.qrbarcode.fragment.InviteFriendsContactsFragment;
import info.sayederfanarefin.qrbarcode.fragment.InviteFriendsEmailFragment;


/**
 * Created by piashsarker on 7/10/17.
 */

public class ViewPagerAdapter_inviteFriends extends FragmentPagerAdapter {

    public ViewPagerAdapter_inviteFriends(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new InviteFriendsContactsFragment();
        }else {
            return new InviteFriendsEmailFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}