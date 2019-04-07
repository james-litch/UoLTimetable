package com.group51.uoltimetable.activities;

import android.content.res.Configuration;
import android.os.Bundle;
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
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        setContentView(R.layout.activity_main);
        initializeStuff();

        // since, NoActionBar was defined in theme, we set toolbar as our action bar.
        setSupportActionBar(toolbar);

        //this basically defines on click on each menu item.
        setUpNavigationView(navigationView);

        //This is for the Hamburger icon.
        drawerToggle = setupDrawerToggle();
        drawerLayout.addDrawerListener(drawerToggle);

        //Inflate the first fragment,this is like home fragment before user selects anything.
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameContent, new TabFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_schedule);
        setTitle("Schedule");

    }

    void initializeStuff() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationDrawer);
    }

    /**
     * Inflate the fragment according to item clicked in navigation drawer.
     */
    private void setUpNavigationView(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //replace the current fragment with the new fragment.
                Fragment selectedFragment = selectDrawerItem(menuItem);
                if (selectedFragment != null) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameContent, selectedFragment).commit();
                    // the current menu item is highlighted in navigation tray.
                    navigationView.setCheckedItem(menuItem.getItemId());
                    setTitle(menuItem.getTitle());
                }
                //close the drawer when user selects a nav item.

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    /**
     * This method returns the fragment according to navigation item selected.
     */
    public Fragment selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_schedule:
                fragment = new TabFragment();
                break;
            case R.id.nav_calendar:
                fragment = new TabFragment();

                break;
            case R.id.nav_attendance:
                fragment = new TabFragment();

                break;
            case R.id.nav_sign_out:
                session.logoutUser();
        }
        return fragment;
    }

    /**
     * This is to setup our Toggle icon. The strings R.string.drawer_open and R.string.drawer close, are for accessibility (generally audio for visually impaired)
     * use only. It is now showed on the screen. While the remaining parameters are required initialize the toggle.
     */
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    /**
     * This makes sure that the action bar home button that is the toggle button, opens or closes the drawer when tapped.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This synchronizes the drawer icon that rotates when the drawer is swiped left or right.
     * Called inside onPostCreate so that it can synchronize the animation again when the Activity is restored.
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /**
     * This is to handle generally orientation changes of your device. It is mandatory to include
     * android:configChanges="keyboardHidden|orientation|screenSize" in your activity tag of the manifest for this to work.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}


