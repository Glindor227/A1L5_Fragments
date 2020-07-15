package com.geekbrains.a1l5_fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.geekbrains.a1l5_fragments.common.FragmentType;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        // принял спорное решение - но для альбомной ориентации у нас не будет этой всей красоты с CoordinatorLayout.....
        if(toolbar!=null) {
            setSupportActionBar(toolbar);

            initFloatingActionButton();
        }
    }

    private void initFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        // Обработка нажатия на плавающую кнопку
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Здесь вылетит Snackbar
                Snackbar.make(view, getResources().getString(R.string.filter_edit),
                        Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.goTo), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,
                                    CoatOfArmsActivity.class);
                            intent.putExtra("type", FragmentType.Setting);
                            startActivity(intent);
                        }
                    }).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int countOfFragmentInManager = getSupportFragmentManager().getBackStackEntryCount();
        if(countOfFragmentInManager > 0) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().popBackStack("Some_Key",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
