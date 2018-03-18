package com.example.haspani.myuzarhaspani_1202154133_modul4;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class ListNamaMahasiswa extends AppCompatActivity {
    //declare variable
    private ListView mListView;
    private Button mSync;
    private ProgressBar mPBLoadName;
    private ProgressDialog mProgressDialog;
    private String[] mNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nama_mahasiswa);
        //bind data
        mSync = findViewById(R.id.btn_start_asyntask);
        mListView = findViewById(R.id.list_nama);
        mPBLoadName = findViewById(R.id.pb_load_name);
        //get string names
        mNames = getResources().getStringArray(R.array.names);
        //set adapter menggunakan design drop down default
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new ArrayList<String>()));
        //set button if clicked
        mSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //execute asynctask
                new SyncTask().execute();

            }
        });
    }

    class SyncTask extends AsyncTask<Void,String,Void>
    {
        //declare variable
        private ArrayAdapter<String> adapter;
        int i = 1;

        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<String>)mListView.getAdapter();
            //setting progress dialog
            mProgressDialog = new ProgressDialog(ListNamaMahasiswa.this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(0);
            //setting button cancel
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new SyncTask().cancel(true);
                    mPBLoadName.setVisibility(View.VISIBLE);
                    dialogInterface.dismiss();
                }
            });
            //show progress dialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (String mName : mNames)
            {
                publishProgress(mName);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCancelled()){
                    new SyncTask().cancel(true);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //menambahkan data ke listview
            adapter.add(values[0]);
            //formula
            Integer ps = (int)((i/(float)mNames.length)*100);
            //setting progress sesuai hitungan formula
            mPBLoadName.setProgress(ps);
            mProgressDialog.setProgress(ps);
            //setting pesan sesuai hitungan formula
            mProgressDialog.setMessage(String.valueOf(ps + "%"));
            //increament
            i++;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //menghilangkan progressbar
            mPBLoadName.setVisibility(View.GONE);
            //menghilangkan progress dialog
            mProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "All the names are were add Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
