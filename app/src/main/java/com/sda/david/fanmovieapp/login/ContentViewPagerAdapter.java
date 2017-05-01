package com.sda.david.fanmovieapp.login;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sda.david.fanmovieapp.R;

/**
 * Created by david on 01/05/2017.
 */

public class ContentViewPagerAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Login", "Sign up"};
    private LoginFragment loginFragment;
    private SignupFragment signupFragment;

    private Context ctx;

    public ContentViewPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
        loginFragment = LoginFragment.newInstance();
        signupFragment = SignupFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? loginFragment : signupFragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return tabTitles[0];
            case 1:
                return tabTitles[1];
            default:
                return tabTitles[0];
        }
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_title_tab);
        tv.setText(tabTitles[position]);
        return v;
    }
}
