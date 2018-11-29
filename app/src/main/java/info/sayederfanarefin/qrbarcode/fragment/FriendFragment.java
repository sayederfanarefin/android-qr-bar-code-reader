package info.sayederfanarefin.qrbarcode.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import info.sayederfanarefin.qrbarcode.adapter.FriendsFirebaseRecycler;
import info.sayederfanarefin.qrbarcode.model.friends;
import info.sayederfanarefin.qrbarcode.model.users;
import info.sayederfanarefin.qrbarcode.ui.AddFriendActivity;
import info.sayederfanarefin.qrbarcode.ui.FirstScreen;
import info.sayederfanarefin.qrbarcode.R;
import info.sayederfanarefin.qrbarcode.utils.database;
import info.sayederfanarefin.qrbarcode.utils.values;


public class FriendFragment extends Fragment {
    private boolean isAttached;

    public FriendFragment() {
    }
    View view_;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_ = inflater.inflate(R.layout.fragment_friends, container, false);
        setHasOptionsMenu(true);
        return view_;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void add_friend_button(){
        Intent intent = new Intent(getActivity(), AddFriendActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.friends_fragement_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_friends:
                Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                intent.putExtra("frag", "search");
                startActivity(intent);
                return true;
            case R.id.edit_friends:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttached = true;
        Log.v("=x=", "Friends Frag Attached");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        isAttached = false;
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }

    }

}
