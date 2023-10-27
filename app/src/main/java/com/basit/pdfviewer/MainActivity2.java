package com.basit.pdfviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.basit.pdfviewer.databinding.ActivityMain2Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding binding;
    private DatabaseReference databaseReference;
    private ArrayList<PdfClass> pdfClassArrayList;
    private PdfAdapter pdfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference("pdf");
        pdfClassArrayList = new ArrayList<>();



        getData();

    }

    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfClassArrayList.clear();

                if (snapshot.exists()) {
                    Toast.makeText(MainActivity2.this, "data exist", Toast.LENGTH_SHORT).show();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        PdfClass pdfClassq = dataSnapshot.getValue(PdfClass.class);

                        pdfClassArrayList.add(pdfClassq);


                    }
                    pdfAdapter = new PdfAdapter(MainActivity2.this, pdfClassArrayList);
                    binding.recycelr.setLayoutManager(new LinearLayoutManager(MainActivity2.this));

                    binding.recycelr.setAdapter(pdfAdapter);
                    pdfAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity2.this, "no data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}