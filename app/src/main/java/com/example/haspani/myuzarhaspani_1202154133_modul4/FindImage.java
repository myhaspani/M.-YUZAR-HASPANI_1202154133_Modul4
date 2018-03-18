package com.example.haspani.myuzarhaspani_1202154133_modul4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class FindImage extends AppCompatActivity {
    //declare variable
    private EditText mEditTextLink;
    private Button mBtnCari;
    private ImageView mGambar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_image);
        //binding view
        mEditTextLink = findViewById(R.id.et_url);
        mBtnCari = findViewById(R.id.btn_find_image);
        mGambar = findViewById(R.id.img_from_url);
        //Set button if clicked
        mBtnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });
    }

    private void loadImage()
    {
        //get Text
        String imgUrl = mEditTextLink.getText().toString();
        //execute asynctask
        new loadGambar().execute(imgUrl);
    }

    class loadGambar extends AsyncTask<String,Void,Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //inisiasi progress dialog
            mProgressDialog = new ProgressDialog(FindImage.this);
            //setting progress dialog
            mProgressDialog.setCancelable(false);
            mProgressDialog.setTitle("Loading Gambar");
            //show progress dialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                //inisiai url gambar
                URL uri = new URL(strings[0]);
                //mengambil gambar dan mengubah menjadi bitmap
                bitmap = BitmapFactory.decodeStream((InputStream)uri.getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //menset gambar ke imageview
            mGambar.setImageBitmap(bitmap);
            //menghilangkan progress dialog
            mProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Image are Successfully loaded", Toast.LENGTH_SHORT).show();
        }
    }
}
