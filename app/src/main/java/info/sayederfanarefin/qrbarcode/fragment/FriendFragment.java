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
import info.sayederfanarefin.R;
import info.sayederfanarefin.qrbarcode.utils.database;
import info.sayederfanarefin.qrbarcode.utils.values;


public class FriendFragment extends Fragment {
    private FirebaseListAdapter mFriendAdapter;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mFirebaseAuth;
    private String mUsername;
    private ChildEventListener mChildEventListener;
    private ValueEventListener mValueEventListener;

    private ValueEventListener mUserDatabaseReferencecurrentUsergetUidValueEventListener;
    private RecyclerView friendsListView;
    private Button add_friend_button_empty;

    private boolean isAttached;

    private DatabaseReference mUserDatabaseReference;
    private DatabaseReference mUserDatabaseReferenceCurrentUser;
    private DatabaseReference mFriendsDatabaseReferenceCurrentUser;
    private DatabaseReference mFriendsDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private LinearLayout add_friend_floating;
    private RelativeLayout empty_view_friendsList, not_empty_friendsList;
    private ValueEventListener friendsListValueEventListener;
    private FirebaseUser currentUser;
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
        mFirebaseDatabase = database.getDatabase();
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser= mFirebaseAuth.getCurrentUser();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            currentUser= firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                onInitialize(view);
            } else {
                Intent intent = new Intent(getActivity(), FirstScreen.class);
                getActivity().startActivity(intent);
            }
            }
        };

        if (currentUser != null) {
            onInitialize(view);
        } else {
            Intent intent = new Intent(getActivity(), FirstScreen.class);
            getActivity().startActivity(intent);
        }

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
        mUsername = currentUser.getDisplayName();
        mFriendsDatabaseReference = mFirebaseDatabase.getReference().child(values.dbFriendsLocation);
        mUserDatabaseReference = mFirebaseDatabase.getReference().child(values.dbUserLocation);

         friendsListView =  view.findViewById(R.id.friendsListView);
       // populateListView();
        mFriendsDatabaseReference.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                populateListView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    FriendsFirebaseRecycler friendsListAdapter;
    int count = 0;
    int i=0;

    private void populateListView(){
        mFriendsDatabaseReferenceCurrentUser = mFriendsDatabaseReference.child(currentUser.getUid());//.orderByChild("username");

        friendsListValueEventListener = new ValueEventListener() {
            final List<friends> friendsList= new ArrayList<friends>();
            final List<users> friendsUsersList= new ArrayList<users>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {

                    friendsList.add(imageSnapshot.getValue(friends.class));

                    count++;
                }
                if(count > 0){

                    for( i=0; i < friendsList.size(); i++){
                        mUserDatabaseReference.child(friendsList.get(i).getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                friendsUsersList.add(dataSnapshot.getValue(users.class));
                                if( i == count){
                                    mUserDatabaseReference.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            friendsUsersList.add(dataSnapshot.getValue(users.class));
                                            if( i == count){
                                                empty_view_friendsList.setVisibility(View.GONE);
                                                not_empty_friendsList.setVisibility(View.VISIBLE);
                                                friendsListAdapter = new FriendsFirebaseRecycler(friendsUsersList,getContext());
                                                friendsListView.setLayoutManager(linearLayoutManager);
                                                friendsListView.setItemAnimator(new DefaultItemAnimator());
                                                friendsListView.setAdapter(friendsListAdapter);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {}
                                    });

                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                    }

                }else{
                    empty_view_friendsList.setVisibility(View.VISIBLE);
                    not_empty_friendsList.setVisibility(View.GONE);
                }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            };

        mFriendsDatabaseReferenceCurrentUser.addValueEventListener(friendsListValueEventListener);


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

        if(mUserDatabaseReferenceCurrentUser != null){
            mUserDatabaseReferenceCurrentUser.removeEventListener(mUserDatabaseReferencecurrentUsergetUidValueEventListener);
        }
        isAttached = false;
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
        if(mFriendsDatabaseReferenceCurrentUser!=null){
            mFriendsDatabaseReferenceCurrentUser.removeEventListener(friendsListValueEventListener);
        }
    }

}
