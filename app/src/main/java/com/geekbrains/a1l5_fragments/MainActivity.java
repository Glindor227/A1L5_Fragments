package com.geekbrains.a1l5_fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.geekbrains.a1l5_fragments.common.FragmentType;
import com.geekbrains.a1l5_fragments.database.CitiesTable;
import com.geekbrains.a1l5_fragments.database.DatabaseHelper;
import com.geekbrains.a1l5_fragments.fragments.AboutAuthorFragment;
import com.geekbrains.a1l5_fragments.fragments.CitiesFragment;
import com.geekbrains.a1l5_fragments.fragments.FeedbackFragment;
import com.geekbrains.a1l5_fragments.tools.GeoLocation;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // криво конечно, но по null тульару будем ориентироваться какая layoгt используется
    private Toolbar toolbar=null;
    public int PERMISSION_REQUEST_CODE = 227;
    GeoLocation geoLocation;
    public static SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        // принял спорное решение - но для альбомной ориентации у нас не будет этой всей красоты с CoordinatorLayout.....
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
            initFloatingActionButton();
            initCitiesFragment();
        }
        initDrawerMenu(toolbar);//боковое меню будет для всех
        initDB();
    }

    private void initDB() {
        Log.d("Glindor227","initDB");
        if(database==null) {
            database = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        }
    }


    private void initCitiesFragment() {
        CitiesFragment citiesFragment = new CitiesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.portMainActivityContainer, citiesFragment).commit();
    }

    private void initDrawerMenu(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if(toolbar!=null) {//для горизонтальной оринтации не делаем "гамбургер'
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open,R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }
        navigationView.setNavigationItemSelectedListener(this);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1]
                            == PackageManager.PERMISSION_GRANTED)) {
                geoLocation.FindLocation(true);
            }
        }
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
                Toast.makeText(getApplicationContext(), "Заглушка: Смена сервера",
                        Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_city_const: {
                if(geoLocation==null)
                    geoLocation = new GeoLocation(this);
                geoLocation.FindLocation(false);
                break;
            }
            case R.id.menu_exit: {//
                // не уверен что это хороший пункт меню:)
                // это для тех кто тоскует по Windows style
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment newFragment = null;// будем этот фрагмент вставлять(показывать пользователю)

        int id = menuItem.getItemId();
        if (id == R.id.programmist) newFragment = new AboutAuthorFragment();
        else if (id == R.id.cities) newFragment = new CitiesFragment();
        else if (id == R.id.feedback) newFragment = new FeedbackFragment();

        if(newFragment != null){
            getSupportFragmentManager()
                .beginTransaction()
                // в зависимости от layout(от ориентации) разные контейнеры для фрагментов.
                .replace(toolbar==null ? R.id.coat_of_arms : R.id.portMainActivityContainer,newFragment)
                .commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addCity(String nameCity,double latitude,double longitude) {
        Log.d("Glindor227","Add ("+nameCity+")");
        CitiesTable.addCity(nameCity,latitude,longitude,database);
        recreate();
    }
}
