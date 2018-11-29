package info.sayederfanarefin.qrbarcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.crashlytics.android.Crashlytics;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//import io.fabric.sdk.android.Fabric;
import info.sayederfanarefin.qrbarcode.adapter.DrawerListAdapter;
import info.sayederfanarefin.qrbarcode.adapter.ViewPagerAdapter;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import info.sayederfanarefin.qrbarcode.model.users;
import info.sayederfanarefin.qrbarcode.ui.FirstScreen;
import info.sayederfanarefin.qrbarcode.ui.MusicSettings;
import info.sayederfanarefin.qrbarcode.ui.ProfileActivity;
import info.sayederfanarefin.qrbarcode.utils.FontOverride;
import info.sayederfanarefin.qrbarcode.utils.database;


public class MainActivity extends AppCompatActivity {

    private int[] pageIcon = {
            R.drawable.ic_info_custom,
            R.drawable.ic_time_custom,
            R.drawable.ic_message,
            R.drawable.ic_friends_custom
    };

    public static int[] drawer_icons = {
            R.mipmap.ic_time,
            R.mipmap.message,
            R.mipmap.notification,
            R.mipmap.profile,
            R.mipmap.settings,
            R.mipmap.logout
    };
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView toolbarText;
    private TextView user_nav_name, user_nav_status;
    private ImageView user_nav_pro_pic;

    private ImageButton close_nav;

    private ArrayList<String> navigation_items;
    private DrawerListAdapter drawerListAdapter;
    private ListView lv_drawer;
    private DrawerLayout drawer;
    private RelativeLayout drawer_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViews();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawer.openDrawer(drawer_2);

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    private void SetDrawer() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer_2 = (RelativeLayout) findViewById(R.id.drawer_2);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        drawerListAdapter = new DrawerListAdapter(MainActivity.this, navigation_items, drawer_icons);
        lv_drawer.setAdapter(drawerListAdapter);

        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(navigation_items.get(position).equalsIgnoreCase("Timeline")){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("frag", "timeline");
                    startActivity(intent);
                }else if(navigation_items.get(position).equalsIgnoreCase("Message")){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("frag", "message");
                    startActivity(intent);


                }else if(navigation_items.get(position).equalsIgnoreCase("Profile")){

                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);

                }else if(navigation_items.get(position).equalsIgnoreCase("Music")){

                    Intent intent = new Intent(MainActivity.this, MusicSettings.class);
                    startActivity(intent);

                }
                else if(navigation_items.get(position).equalsIgnoreCase("Logout")){

                }
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();

        isAttached = true;

        Bundle b = getIntent().getExtras();

        if(b != null){
            String frag = b.getString("frag", "0");
            switch(frag){
                case "timeline":
                    viewPager.setCurrentItem(1);
                    break;
                case "info":
                    viewPager.setCurrentItem(0);
                    break;

                case "message":
                    viewPager.setCurrentItem(2);
                    break;
                case "friends":
                    viewPager.setCurrentItem(3);
                    break;

                default:
                    viewPager.setCurrentItem(0);
                    break;
            }
        }
    }
    private void initializeViews(){
        FontOverride.setDefaultFont(this, "MONOSPACE", "font/Montserrat-Light.ttf");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

//custom drawer
        navigation_items = new ArrayList<>();

        user_nav_name = (TextView) findViewById(R.id.user_nav_name);
        user_nav_status = (TextView) findViewById(R.id.user_nav_status);
        user_nav_pro_pic = (ImageView) findViewById(R.id.nav_profile_image);


//adding menu items for naviations
        navigation_items.add("Timeline");
        navigation_items.add("Message");
        navigation_items.add("Music");
        //navigation_items.add("Notifications");
        navigation_items.add("Profile");
        navigation_items.add("Settings");
        navigation_items.add("Logout");

        lv_drawer = (ListView) findViewById(R.id.lv_drawer);
        SetDrawer();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < pageIcon.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setIcon(pageIcon[i]));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOffscreenPageLimit(1);


        toolbarText = (TextView) findViewById(R.id.toolbar_title);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        toolbarText.setText("Home");
                        break;
                    case 1:
                        toolbarText.setText("Timeline");
                        break;
                    case 2:
                        toolbarText.setText("Chat");
                        break;
                    case 3:
                        toolbarText.setText("Friends");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        viewPager.setCurrentItem(0);
        toolbarText.setText("Home");


    }



    @Override
    public void onPause() {
        super.onPause();
        isAttached = false;
    }

    Boolean isAttached = false;
}