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

    private RecyclerView friendsListView;
    private Button add_friend_button_empty;

    private boolean isAttached;

    private LinearLayout add_friend_floating;
    private RelativeLayout empty_view_friendsList, not_empty_friendsList;

    private LinearLayoutManager linearLayoutManager;


    public FriendFragment() {

    }
    View view_;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_ = inflater.inflate(R.layout.fragment_friends, container, false);
        setHasOptionsMenu(true);
        add_friend_floating = view_.findViewById(R.id.add_frients_floating);
        add_friend_button_empty = view_.findViewById(R.id.add_friend_button_empty);
        add_friend_button_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_friend_button();
            }
        });

        linearLayoutManager =new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        not_empty_friendsList = view_.findViewById(R.id.not_empty_friendsList);
        empty_view_friendsList = view_.findViewById(R.id.empty_view_friendsList);
        return view_;

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        add_friend_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_friend_button();
            }
        });
    }


    private void add_friend_button(){
        Intent intent = new Intent(getActivity(), AddFriendActivity.class);
        getActivity().startActivity(intent);
    }
    private void onInitialize(View view) {

         friendsListView =  view.findViewById(R.id.friendsListView);

    }
    FriendsFirebaseRecycler friendsListAdapter;
    int count = 0;
    int i=0;

    private void populateListView(){

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
