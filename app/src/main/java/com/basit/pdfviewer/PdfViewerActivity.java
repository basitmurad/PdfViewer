package com.basit.pdfviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;

import com.basit.pdfviewer.databinding.ActivityPdfViewerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfViewerActivity extends AppCompatActivity {

    protected ActivityPdfViewerBinding binding;


    DatabaseReference databaseReference ;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        path = getIntent().getStringExtra("pdfurl");
        new PdfDownloadTask().execute(path);
    }




    public class PdfDownloadTask extends AsyncTask<String,Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url=new URL(strings[0]);

                HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();

                if (urlConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            binding.pdfView.fromStream(inputStream).load();
        }
    }
}