package info.sayederfanarefin.qrbarcode.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import info.sayederfanarefin.qrbarcode.adapter.TimelineFirebaseRecycler;
import info.sayederfanarefin.qrbarcode.R;
import info.sayederfanarefin.qrbarcode.model.post;
import info.sayederfanarefin.qrbarcode.model.users;
import info.sayederfanarefin.qrbarcode.ui.NewStatusActivity;
import info.sayederfanarefin.qrbarcode.utils.database;
import info.sayederfanarefin.qrbarcode.utils.values;

import static android.app.Activity.RESULT_OK;


public class TimelineFragment extends Fragment {

    SharedPreferences sharedPref ;//= getActivity().getPreferences(Context.MODE_PRIVATE);


    Button button ;//= (Button)
    Boolean flag_first = true;

    private LinearLayoutManager linearLayoutManager;
    private Toolbar mToolBar;

    private ProgressDialog mProgress;
    public TimelineFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v =  inflater.inflate(R.layout.fragment_history, container, false);

        findAndPopulateViews(v);
        init();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void findAndPopulateViews(View view){


        //mToolBar = (Toolbar) findViewById(R.id.toolbar);
        final AppCompatActivity act = (AppCompatActivity) getActivity();
        if (act.getSupportActionBar() != null) {
            Toolbar toolbar = (Toolbar) act.findViewById(R.id.toolbar);
        }


        timeline_self = (RecyclerView) view.findViewById(R.id.view_timeline_self);

        linearLayoutManager =new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        empty_view = view.findViewById(R.id.empty_view);


        mProgress = new ProgressDialog(getActivity());

        button = view.findViewById(R.id.post_button_empty);
    }

    int x = 0;


    int count = 0;

    private void init(){
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    private RecyclerView timeline_self;


    RelativeLayout empty_view;
    TimelineFirebaseRecycler timeLineAdapter;



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.history_fragement_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_timeline_music:
                Intent intent_upload = new Intent();
                intent_upload.setType("audio/*");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_upload,666);

                return true;

            default:
                return true; //super.onOptionsItemSelected(item);
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == 666 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        init();
        int scroll_pos = sharedPref.getInt("saved_pos", -1);
        if(scroll_pos != -1 && linearLayoutManager != null){
            linearLayoutManager.scrollToPosition(scroll_pos+1);

        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            //  throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            // throw new RuntimeException(e);
        }

    }
}
