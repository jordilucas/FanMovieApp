package com.sda.david.fanmovieapp.login;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sda.david.fanmovieapp.R;
import com.sda.david.fanmovieapp.util.PreferencesUtil;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by david on 29/04/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter adapter;
    private CirclePageIndicator mContentIntroIndicator;
    private TabLayout mTabLayout;
    private ViewPager contentViewPager;
    private ContentViewPagerAdapter contentAdapter;

    private String[] phrasesIntro;
    Timer timer;
    int page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phrasesIntro = new String[]{"Seja bem vindo !! \n Procure os seus filmes favoritos aqui", "Salve todos para ter acesso fácil as suas informações no futuro", "Veja sempre quais sãos os top 10 filmes atuais"};

        initComponents();
    }

    private void initComponents() {
        viewPager = (ViewPager) findViewById(R.id.vp_login);
        mContentIntroIndicator = (CirclePageIndicator) findViewById(R.id.cpi_login_intro_indicator);

        adapter = new LoginViewPageAdapter(this, phrasesIntro);
        viewPager.setAdapter(adapter);
        mContentIntroIndicator.notifyDataSetChanged();
        mContentIntroIndicator.setViewPager(viewPager);
        pageSwitcher(4);

        mTabLayout = (TabLayout) findViewById(R.id.tl_login_options);
        contentViewPager = (ViewPager) findViewById(R.id.vp_login_content);
        contentAdapter = new ContentViewPagerAdapter(getSupportFragmentManager(), this);
        contentViewPager.setAdapter(contentAdapter);
        mTabLayout.setupWithViewPager(contentViewPager);
        updateTabs();
    }

    private void updateTabs() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) tab.setCustomView(contentAdapter.getTabView(i));
        }
    }


    public void pageSwitcher(int seconds) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000);
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 2) {
                        page = 0;
                        viewPager.setCurrentItem(page);
                        timer.cancel();
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new RemindTask(), 0, 4 * 1000);
                    } else {
                        viewPager.setCurrentItem(page++);
                    }
                }
            });

        }
    }
}
