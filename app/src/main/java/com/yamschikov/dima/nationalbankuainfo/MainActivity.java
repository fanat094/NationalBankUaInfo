package com.yamschikov.dima.nationalbankuainfo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies.RateForeignCurrenciesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    private BaseFragmentManager mBaseFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mBaseFragmentManager = new BaseFragmentManager();
        setContent(new RateForeignCurrenciesFragment(), false);
    }


    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            removeCurrentFragment();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        BaseFragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_rate_foreigencurrencies:
                fragment = new RateForeignCurrenciesFragment();
                break;

        }
        if (!(fragment == null || isAlreadyContains(fragment))) {
            setContent(fragment);
        }
        mDrawer.closeDrawer(8388611);
        return true;
    }


    public void setCheckedItem(int id) {

        if (this.mNavigationView != null) {
            this.mNavigationView.setCheckedItem(id);
        }
    }

    public boolean isAlreadyContains(BaseFragment fragment) {
        return this.mBaseFragmentManager.isAlreadyContains(fragment);
    }

    public void setContent(BaseFragment fragment) {
        this.mBaseFragmentManager.setFragment(this, fragment, R.id.frame_layout);
    }

    public void setContent(BaseFragment fragment, boolean addToBackStack) {
        this.mBaseFragmentManager.setFragment(this, fragment, R.id.frame_layout, addToBackStack);
    }

    public boolean removeCurrentFragment() {
        return this.mBaseFragmentManager.removeCurrentFragment(this);
    }

    public void setToolbarTitle(String toolbarTitle) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(toolbarTitle);
        }

    }
}
