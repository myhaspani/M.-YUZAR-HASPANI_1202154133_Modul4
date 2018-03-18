package com.example.haspani.myuzarhaspani_1202154133_modul4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void ListNama(View view) {
        Intent i = new Intent(this,ListNamaMahasiswa.class);
        startActivity(i);
    }

    public void FindImage(View view) {
        Intent i = new Intent(this,FindImage.class);
        startActivity(i);
    }
}
