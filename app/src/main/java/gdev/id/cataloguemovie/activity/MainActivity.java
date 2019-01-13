package gdev.id.cataloguemovie.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import gdev.id.cataloguemovie.R;
import gdev.id.cataloguemovie.fragment.FragmentFavorite;
import gdev.id.cataloguemovie.fragment.FragmentNowplaying;
import gdev.id.cataloguemovie.fragment.FragmentUpcoming;

//created by Hendriyawan 5 December 2018
//Submisson 2 (MADE) : Catalogue Movie Ui Ux

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private CircleImageView imgGithubProfile;
    private ViewPagerAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind the view
        ButterKnife.bind(this);

        setupToolbar();
        setupNavigationView();
        setupViewPager();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.action_search_movie)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_view_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent intentSettingLanguage = new Intent(MainActivity.this, ActivitySearchMovie.class);
                    intentSettingLanguage.putExtra(ActivitySearchMovie.EXTRA_QUERY, query.trim()
                    );
                    startActivity(intentSettingLanguage);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_set_language) {
            Intent intentSettings = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intentSettings);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //setup toolbar
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
    }

    //setup NavigationView with ImageProvile
    private void setupNavigationView() {
        //action drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //imageview navigation drawer layout
        imgGithubProfile = navigationView.getHeaderView(0).findViewById(R.id.img_github_profile);
        imgGithubProfile.setImageDrawable(getResources().getDrawable(R.drawable.github_profile));
    }

    //setup viewpager with tablayout
    private void setupViewPager() {
        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_play));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_update));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_favorite));
    }

    //class AdapterViewPager
    class ViewPagerAdapter extends FragmentPagerAdapter {
        String nowPlaying = getResources().getString(R.string.tab_nowplaying);
        String upComing = getResources().getString(R.string.tab_upcoming);
        String Favorite = getResources().getString(R.string.tab_favorite);
        String[] title = {nowPlaying, upComing, Favorite};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentNowplaying();

                case 1:
                    return new FragmentUpcoming();

                case 2:
                    return new FragmentFavorite();
            }
            return null;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
