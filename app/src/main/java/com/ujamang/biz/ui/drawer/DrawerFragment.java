package com.ujamang.biz.ui.drawer;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;
import com.ujamang.biz.R;

public class DrawerFragment extends Fragment {

    //drawerLayout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView drawerNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        // Inflate the layout for this fragment

        //drawerLayout
        drawerLayout = view.findViewById(R.id.main_drawerLayout);
        drawerNavigationView = view.findViewById(R.id.main_drawer_nav);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.start, R.string.close);
        //drawerLayout.addDrawerListener(toggle);


        return view;
    }
}