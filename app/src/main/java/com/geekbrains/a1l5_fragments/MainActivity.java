package com.geekbrains.a1l5_fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.geekbrains.a1l5_fragments.common.FragmentType;

import java.util.Objects;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        handleMenuItemClick(item);
        return true;
    }

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_setting: {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,
                        CoatOfArmsActivity.class);
                intent.putExtra("type", FragmentType.Setting);
                startActivity(intent);

                break;
            }
            case R.id.menu_server_change: {
                Toast.makeText(getApplicationContext(), "Смена сервера", Toast.LENGTH_SHORT)
                        .show();
                break;
            }
            case R.id.menu_city_const: {
                Toast.makeText(getApplicationContext(), "Выбор города", Toast.LENGTH_SHORT)
                        .show();

                break;
            }
            case R.id.menu_exit: {// не уверен что это хороший пункт меню:)
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
            }
            default: {
                Toast.makeText(getApplicationContext(), "Action not found", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
