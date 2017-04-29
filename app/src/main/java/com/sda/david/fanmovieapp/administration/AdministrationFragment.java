package com.sda.david.fanmovieapp.administration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sda.david.fanmovieapp.R;

/**
 * Created by david on 29/04/2017.
 */

public class AdministrationFragment extends Fragment {

    public static final String TAG = "AdmFrag";
    private static final String ARG_1 = "arg_1";

    private boolean arg1;

    public static AdministrationFragment newInstance() {
        AdministrationFragment fragment = new AdministrationFragment();
        Bundle bundle = new Bundle();
        //Put arguments
        boolean arg1 = true;
        bundle.putBoolean(ARG_1, arg1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            arg1 = getArguments().getBoolean(ARG_1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        initComponents();

        return rootView;
    }

    private void initComponents() {

    }

}
