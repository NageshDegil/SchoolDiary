package com.pawan.schooldiary.home.parents.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.IFragmentHelper;
import com.pawan.schooldiary.app.OnBackStackChangedListener;
import com.pawan.schooldiary.home.model.ViewPagerHelper;
import com.pawan.schooldiary.home.parents.fragment.home.ParentsHomeFragment_;
import com.pawan.schooldiary.home.parents.fragment.profile.ParentsProfileFragment;
import com.pawan.schooldiary.home.parents.fragment.profile.ParentsProfileFragment_;
import com.pawan.schooldiary.home.teacher.fragment.profile.TeacherProfileFragment_;
import com.pawan.schooldiary.home.utils.Utils;
import com.pawan.schooldiary.registerOrLogin.activity.RegisterOrLoginActivity_;

public class ParentsHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ViewPagerHelper, IFragmentHelper {

    private OnBackStackChangedListener onBackStackChangedListener;
    private ViewPagerHelper viewPagerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ParentHomeActivity");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.parents_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.parent_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_parent_home, new ParentsHomeFragment_())
                .addToBackStack(null)
                .commit();
    }
    public void initViewPagerHelper(Fragment fragment) {
        viewPagerHelper = (ViewPagerHelper) fragment;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.parents_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void backPressedOnViewPager() {
        if(getSupportFragmentManager().getBackStackEntryAt(0).getName().equals("com.pawan.schooldiary.home.parent.fragment.home.ParentHomeFragment_")) {
            if(viewPagerHelper.getViewPager().getCurrentItem() == 0)
                finish();
            else
                viewPagerHelper.getViewPager().setCurrentItem(viewPagerHelper.getViewPager().getCurrentItem() - 1);
        } else {
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parent_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.parents_drawer_layout);
        int id = item.getItemId();
        Fragment fragment = new ParentsHomeFragment_();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            fragment = new ParentsProfileFragment_();
        }  else if (id == R.id.nav_send) {
            Utils.clearPreferences(getApplicationContext());
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(this, RegisterOrLoginActivity_.class);
            startActivity(intent);
            return true;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_parent_home, fragment)
                .commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void removeCurrentFragment() {
        if (!this.isFinishing()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.content_parent_home);
            if (currentFrag != null)
                transaction.remove(currentFrag);

            transaction.commit();
        }
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        try {
            String backStateName = fragment.getClass().getName();
            String fragmentTag = backStateName;
            FragmentManager manager = getSupportFragmentManager();

            /* we are using teh existing fragment here to prevent it to be added to to backstack if teh user presses it twice */
            Fragment existingFrag = manager.findFragmentById(R.id.content_parent_home);
            Fragment stackFragment = manager.findFragmentByTag(fragmentTag);
            if (existingFrag != null && existingFrag.getClass().getCanonicalName().equals(fragment.getClass().getCanonicalName())) {
                manager.beginTransaction().detach(existingFrag).attach(existingFrag).commitNow();
                return;
            }

            if (!this.isFinishing()) {
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.content_parent_home, stackFragment == null ? fragment : stackFragment);

                if (addToBackStack) {
                    ft.addToBackStack(backStateName);
                    ft.commit();
                } else
                    ft.commitNow();
            }
        } catch(Exception e) {

        }
    }

    @Override
    public ViewPager getViewPager() {
        return null;
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
