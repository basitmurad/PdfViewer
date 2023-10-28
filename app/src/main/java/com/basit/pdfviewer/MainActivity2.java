package com.basit.pdfviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.basit.pdfviewer.databinding.ActivityMain2Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding binding;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ArrayList<PdfClass> pdfClassArrayList;
    private PdfAdapter pdfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




//        String url = "https://firebasestorage.googleapis.com/v0/b/pdf-viewer-41ebd.appspot.com/o/myResume.pdf?alt=media&token=e74df641-a202-4597-b7a7-0bca2552dda3";
//        String url2 = "https://firebasestorage.googleapis.com/v0/b/pdf-viewer-41ebd.appspot.com/o/Se%20Assign%201.pdf?alt=media&token=cdf1c76a-3f29-48f7-81b7-af72a5a8891a";
//
//        String url3 = "https://firebasestorage.googleapis.com/v0/b/pdf-viewer-41ebd.appspot.com/o/statement.pdf?alt=media&token=2baa685e-4024-4cc7-810a-153ea305ae10";
//        pdfClassArrayList.add(new PdfClass("Resume", url, "1"));
//        pdfClassArrayList.add(new PdfClass("Software Engineering", url2, "2"));
//        pdfClassArrayList.add(new PdfClass("Statement", url3, "3"));


//        pdfAdapter = new PdfAdapter(MainActivity2.this, pdfClassArrayList);
//        binding.recycelr.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
//
//        binding.recycelr.setAdapter(pdfAdapter);
//        pdfAdapter.notifyDataSetChanged();



        binding.btn9th.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity2.this,SubjectActivity.class));
                    }
                }
        );

    }

}