package com.pawan.schooldiary.home.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import com.pawan.schooldiary.app.BackHandlerInterface;
import com.pawan.schooldiary.app.IFragmentHelper;
import com.pawan.schooldiary.app.OnBackStackChangedListener;
import com.pawan.schooldiary.home.model.ViewPagerHelper;
import com.pawan.schooldiary.home.teacher.fragment.home.TeacherHomeFragment_;
import com.pawan.schooldiary.home.teacher.fragment.profile.TeacherProfileFragment_;
import com.pawan.schooldiary.home.utils.Utils;
import com.pawan.schooldiary.registerOrLogin.activity.RegisterOrLoginActivity_;


public class TeacherHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ViewPagerHelper, IFragmentHelper {
    private OnBackStackChangedListener onBackStackChangedListener;
    private ViewPagerHelper viewPagerHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TeacherHomeActivity");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.teacher_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_teacher_home, new TeacherHomeFragment_())
                .addToBackStack(null)
                .commit();

        //onBackStackChangedListener = new OnBackStackChangedListener(getSupportFragmentManager());
        //getSupportFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
        //onBackStackChangedListener.onBackStackChanged();
    }

    public void initViewPagerHelper(Fragment fragment) {
        viewPagerHelper = (ViewPagerHelper) fragment;
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

    private void backPressedOnViewPager() {
        if(getSupportFragmentManager().getBackStackEntryAt(0).getName().equals("com.pawan.schooldiary.home.teacher.fragment.home.TeacherHomeFragment_")) {
            if(viewPagerHelper.getViewPager().getCurrentItem() == 0)
                finish();
            else
                viewPagerHelper.getViewPager().setCurrentItem(viewPagerHelper.getViewPager().getCurrentItem() - 1);
        } else {
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        Fragment fragment = new TeacherHomeFragment_();
        if (id == R.id.nav_camera) {
            fragment = new TeacherProfileFragment_();
        } else if (id == R.id.nav_send) {
            Utils.clearPreferences(getApplicationContext());
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(this, RegisterOrLoginActivity_.class);
            startActivity(intent);
            return true;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_teacher_home, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

    private void removeCurrentFragment() {
        if (!this.isFinishing()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.content_teacher_home);
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
            Fragment existingFrag = manager.findFragmentById(R.id.content_teacher_home);
            Fragment stackFragment = manager.findFragmentByTag(fragmentTag);
            if (existingFrag != null && existingFrag.getClass().getCanonicalName().equals(fragment.getClass().getCanonicalName())) {
                manager.beginTransaction().detach(existingFrag).attach(existingFrag).commitNow();
                return;
            }

            if (!this.isFinishing()) {
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.content_teacher_home, stackFragment == null ? fragment : stackFragment);

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
