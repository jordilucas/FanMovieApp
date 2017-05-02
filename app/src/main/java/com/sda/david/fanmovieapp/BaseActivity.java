package com.sda.david.fanmovieapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sda.david.fanmovieapp.administration.AdministrationFragment;
import com.sda.david.fanmovieapp.favorites.FavoritesFragment;
import com.sda.david.fanmovieapp.login.LoginActivity;
import com.sda.david.fanmovieapp.model.User;
import com.sda.david.fanmovieapp.movies.MovieFragment;
import com.sda.david.fanmovieapp.top10.Top10Fragment;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ARG_USER = "arg_user";

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(getIntent() != null) {
            user = getIntent().getParcelableExtra(ARG_USER);
        }

        Fragment fragment = MovieFragment.newInstance();
        commitFragment(fragment, MovieFragment.TAG);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_right_menu, menu);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_movies) {
            Fragment fragment = MovieFragment.newInstance();
            commitFragment(fragment, MovieFragment.TAG);
        } else if (id == R.id.nav_favorites) {
            Fragment fragment = FavoritesFragment.newInstance(user);
            commitFragment(fragment, FavoritesFragment.TAG);
        } else if (id == R.id.nav_top10) {
            Fragment fragment = Top10Fragment.newInstance();
            commitFragment(fragment, Top10Fragment.TAG);
        } else if (id == R.id.nav_register) {
            Fragment fragment = AdministrationFragment.newInstance();
            commitFragment(fragment, AdministrationFragment.TAG);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void commitFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.main_content, fragment, tag)
                .addToBackStack(tag)
//                .setCustomAnimations(
//                        R.anim.card_flip_right_in,
//                        R.anim.card_flip_right_out,
//                        R.anim.card_flip_left_in,
//                        R.anim.card_flip_left_out)
//                .setCustomAnimations(R.anim.res_anim_fadein, R.anim.res_anim_fadeout)
                .commitAllowingStateLoss();
    }
}
