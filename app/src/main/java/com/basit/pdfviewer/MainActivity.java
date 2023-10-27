package com.basit.pdfviewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.basit.pdfviewer.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int PICK_PDF_REQUEST = 1;
    private StorageReference storageReference;
    private DatabaseReference reference;

    private ProgressDialog progressDialog;

    private String name, unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait \n Uploading your pdf");

        reference = FirebaseDatabase.getInstance().getReference("pdf");
        storageReference = FirebaseStorage.getInstance().getReference();
        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.unit.getText().toString().isEmpty() && binding.chapter.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fill the detail to upload a file", Toast.LENGTH_SHORT).show();
                } else {

                    name = binding.chapter.getText().toString();
                    unit = binding.unit.getText().toString();
                    openFilePicker();

                }
            }
        });

        binding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf"); // Filter to only show PDF files
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(Intent.createChooser(intent, "Select a PDF file"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                // Handle the selected PDF file here
                Uri selectedPdfUri = data.getData();
                progressDialog.show();
//                Toast.makeText(this, ""+selectedPdfUri, Toast.LENGTH_SHORT).show();
                uploadPdfToFirebase(selectedPdfUri);
                // Now, you can upload the selected PDF to Google Drive or perform any other desired action.
            }
        }
    }


    private void uploadPdfToFirebase(Uri pdfUri) {
        // Create a unique name for the PDF file in Firebase Storage
//        String pdfFileName = "pdf_" + System.currentTimeMillis() + ".pdf";


        StorageReference pdfRef = storageReference.child(name);


        pdfRef.putFile(pdfUri)
                .addOnSuccessListener(taskSnapshot -> {

                    pdfRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                String downloadUrl = uri.toString();

//                                Toast.makeText(this, "Upload successfully" + downloadUrl, Toast.LENGTH_SHORT).show();

                                PdfClass pdfClass = new PdfClass(name, downloadUrl, unit);

                                reference.push().setValue(pdfClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();

                                        binding.chapter.setText("");
                                        binding.unit.setText("");
                                        Toast.makeText(MainActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                        Log.d("Exception ", e.getLocalizedMessage());
                                    }
                                });


                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "no uploaded" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("no Upload", e.getLocalizedMessage());
                            });
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(this, "try again" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    Log.d("try again", "" + e.getLocalizedMessage());

                });
    }
}