package com.example.zach.bbva;

import android.support.v4.app.*;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity {
    Button listBtn;
    Button mapBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  listBtn = (Button) findViewById(R.id.map);
   //     mapBtn  =(Button) findViewById(R.id.list);
        if (findViewById(R.id.container) != null) {
            if (savedInstanceState == null) {
              getSupportFragmentManager().beginTransaction().add(R.id.container, new MapsFragment()).commit();
              // getSupportFragmentManager().beginTransaction().add(R.id.container, new ListViewFragment()).commit();

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // return true so that the menu pop up is opened

       // getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.map:
               // getSupportFragmentManager().beginTransaction().replace(R.id.container, new MapsFragment()).addToBackStack(null).commit();

                return true;
            case R.id.list:
               // getSupportFragmentManager().beginTransaction().replace(R.id.container,new ListFragment()).addToBackStack(null).commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
