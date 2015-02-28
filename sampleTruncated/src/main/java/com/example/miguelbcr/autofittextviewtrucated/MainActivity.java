package com.example.miguelbcr.autofittextviewtrucated;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = ListFragment.newInstance();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.addToBackStack("");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
