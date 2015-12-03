package com.swati.debt1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.swati.debt1.R.*;
import static com.swati.debt1.R.layout.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        db=new DB(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                builder.setTitle("Add Entry")
                        .setView(inflater.inflate(layout.entry, null))
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                saving();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, string.navigation_drawer_open, string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void saving() {
        TextView person_name=(TextView)findViewById(id.person_name);
        TextView money=(TextView)findViewById(id.money);
         EditText name=(EditText)findViewById(id.name);
        EditText money_lend=(EditText)findViewById(id.money_lend);
         final String per_name=name.getText().toString();
        final int mon_lend=Integer.parseInt(money_lend.getText().toString());
        RadioGroup group=(RadioGroup)findViewById(id.group);
        RadioButton seldebt=(RadioButton)findViewById(id.bdebt);
        RadioButton selowe=(RadioButton)findViewById(id.bowe);
        RadioButton selsav=(RadioButton)findViewById(id.bsav);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
                 public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == id.bdebt) {
                    db.insertEntry("debt",per_name,mon_lend,0,0);
                } else if(checkedId == id.bowe) {
                    db.insertEntry("owe",per_name,mon_lend,0,0);
                } else if(checkedId==id.bsav){
                    db.insertEntry("saving",per_name,mon_lend,0,0);
                }
             }

        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;

        if (id == R.id.debts) {

            fragment=new MyDebt();
            updateFragment(fragment);
        } else if (id == R.id.owe) {
            fragment=new OwesMe();
                updateFragment(fragment);
        } else if (id == R.id.saving) {
            fragment=new Savings();
               updateFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();

    }


}
