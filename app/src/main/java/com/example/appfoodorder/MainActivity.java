package com.example.appfoodorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appfoodorder.fragment.*;
import com.example.appfoodorder.navigation.CustomBottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomBottomNavigationView customBottomNavigationView = findViewById(R.id.customBottomBar);
        customBottomNavigationView.inflateMenu(R.menu.bottom_menu);
        loadFragment(new HomeFragment());
        // Write a message to the databases

        customBottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private CustomBottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new CustomBottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag;
            switch (item.getItemId()){
                case R.id.action_home:
                    frag = new HomeFragment();
                    loadFragment(frag);
                    return true;
                case R.id.action_profile:
                    frag = new ProfileFragment();
                    loadFragment(frag);
                    return  true;
                case R.id.action_support:
                    frag = new SupportFragment();
                    loadFragment(frag);
                    return true;
                case R.id.action_setting:
                    frag = new SettingsFragment();
                    loadFragment(frag);
                    return true;
            }
            return false;
        }
    };
}