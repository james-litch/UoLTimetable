package com.group51.uoltimetable.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.group51.uoltimetable.R;
import com.group51.uoltimetable.fragments.AttendanceFragment;
import com.group51.uoltimetable.fragments.CalendarFragment;
import com.group51.uoltimetable.fragments.LectureInfoFragment;
import com.group51.uoltimetable.fragments.TabFragment;
import com.group51.uoltimetable.utilities.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager session;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //checks to see if user has already logged in.
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        if (session.isLecturer()) {
            setContentView(R.layout.activity_main_lecturer);
            navigationView = findViewById(R.id.navigation_drawer_lecturer);
            drawerLayout = findViewById(R.id.drawer_layout_lecturer);


        } else {
            setContentView(R.layout.activity_main);
            navigationView = findViewById(R.id.navigation_drawer);
            drawerLayout = findViewById(R.id.drawer_layout);

        }
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        //responsible for menu clicks.
        setUpNavigationView(navigationView);

        //set up menu drawer icon.
        drawerToggle = setupDrawerToggle();
        drawerLayout.addDrawerListener(drawerToggle);

        // sets up the initial fragment.
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameContent, new TabFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_schedule);
        setTitle("Schedule");


    }


    public void replaceWithInfoFragment() {
        Fragment lectureInfoFragment = new LectureInfoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameContent, lectureInfoFragment).addToBackStack(null).commit();

    }

    //launch fragment based on item clicked in nav drawer.
    private void setUpNavigationView(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //replace current fragment with new fragment.
                Fragment selectedFragment = selectDrawerItem(menuItem);
                if (selectedFragment != null) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStack();
                    fragmentManager.beginTransaction().replace(R.id.frameContent, selectedFragment).commit();
                    // current menu item is highlighted in nav tray.
                    navigationView.setCheckedItem(menuItem.getItemId());
                    setTitle(menuItem.getTitle());
                }
                //close drawer when user selects a nav item.

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    // returns fragment based on nav_item selected.
    public Fragment selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_schedule:
                fragment = new TabFragment();
                break;
            case R.id.nav_calendar:
                fragment = new CalendarFragment();

                break;
            case R.id.nav_attendance:
                fragment = new AttendanceFragment();

                break;
            case R.id.nav_sign_out:
                session.logoutUser();
                finishAffinity();
        }
        return fragment;
    }

    // for accessibility purposes.
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    // nav drawer button opens and closes nav drawer when pressed.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // responsible for the animation of the menu icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    //handles orientation changes of device.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


}


