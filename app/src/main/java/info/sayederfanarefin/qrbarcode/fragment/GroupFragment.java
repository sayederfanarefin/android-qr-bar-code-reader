package info.sayederfanarefin.qrbarcode.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import info.sayederfanarefin.qrbarcode.adapter.RecentChatsAdapterGroup;
import info.sayederfanarefin.qrbarcode.NewGroupChatActivity;
import info.sayederfanarefin.qrbarcode.R;

import info.sayederfanarefin.qrbarcode.model.Chat;
import info.sayederfanarefin.qrbarcode.model.ChatHeaderGroup;
import info.sayederfanarefin.qrbarcode.model.Message_2;
import info.sayederfanarefin.qrbarcode.ui.FirstScreen;
import info.sayederfanarefin.qrbarcode.utils.database;
import info.sayederfanarefin.qrbarcode.utils.values;

public class GroupFragment extends Fragment {

    private LinearLayout button_group;

    public GroupFragment() {

    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            Log.v("=x=", "actvty dstryd");
            // throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            // throw new RuntimeException(e);
            Log.v("=x=", "actvty dstryd");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        findViews(view);
        return view;
    }

    private void findViews(View view) {



    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {

        super.onPause();
    }


}
