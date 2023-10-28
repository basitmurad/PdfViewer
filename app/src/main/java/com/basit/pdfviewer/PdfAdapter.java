package com.basit.pdfviewer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.MyHolder> {

    private Context context;
    private ArrayList<PdfClass> pdfClassArrayList;

    public PdfAdapter(Context context, ArrayList<PdfClass> pdfClassArrayList) {
        this.context = context;
        this.pdfClassArrayList = pdfClassArrayList;
    }

    @NonNull
    @Override
    public PdfAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custum_viewer_layout,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfAdapter.MyHolder holder, int position) {

PdfClass pdfClass1 = pdfClassArrayList.get(position);


        holder.url.setText("Unit " +String.valueOf(pdfClass1.getUnit()));

        holder.name.setText(String.valueOf(pdfClass1.getPdfName()));

        Toast.makeText(context, ""+pdfClass1.getPdfUrl(), Toast.LENGTH_SHORT).show();


        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pdfClass1.getPdfUrl()));
                context.startActivity(intent);
            }
        });

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , PdfViewerActivity.class);
                intent.putExtra("pdfurl", pdfClass1.getPdfUrl());
                Log.d("Url ", pdfClass1.getPdfUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pdfClassArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView name, url , btnDownload , btnView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.textTopic1);
            btnDownload = itemView.findViewById(R.id.btnDownload);
            btnView = itemView.findViewById(R.id.btnView);
            url = itemView.findViewById(R.id.textUnit1);
        }
    }
}
