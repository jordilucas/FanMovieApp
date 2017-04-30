package com.sda.david.fanmovieapp.login;

import android.support.v4.view.PagerAdapter;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sda.david.fanmovieapp.R;

/**
 * Created by david on 30/04/2017.
 */

public class LoginViewPageAdapter extends PagerAdapter{

    // Declare Variables
    Context context;
    String[] phrasesIntro;
    LayoutInflater inflater;

    public LoginViewPageAdapter(Context context, String[] phrasesIntro) {
        this.context = context;
        this.phrasesIntro = phrasesIntro;
    }

    @Override
    public int getCount() {
        return phrasesIntro.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        TextView txtPhraseIntro;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_login_intro, container, false);

        txtPhraseIntro = (TextView) itemView.findViewById(R.id.tv_phrase_intro);

        txtPhraseIntro.setText(phrasesIntro[position]);

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
