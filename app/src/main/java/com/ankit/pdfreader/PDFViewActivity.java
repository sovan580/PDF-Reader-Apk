package com.ankit.pdfreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PDFViewActivity extends AppCompatActivity {
    PDFView pdfView;
    int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);
        pdfView=findViewById(R.id.pdfView);
        position=getIntent().getIntExtra("position",-1);
        viewPDF();
    }

    private void viewPDF() {
        pdfView.fromFile(MainActivity.mFiles.get(position))
                .enableSwipe(true)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
}