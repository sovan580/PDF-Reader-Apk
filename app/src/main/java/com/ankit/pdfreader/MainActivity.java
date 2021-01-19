package com.ankit.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION =1 ;
    File folder;
    public static ArrayList<File> mFiles=new ArrayList<>();
    RecyclerView recyclerView;
    String[] item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rv_View);
        permission();

    }

    private void permission() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(this,"Please Grant Permissison",Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION);
            }
            
        }
        else{
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            initView();
        }
    }

    private void initView() {
        folder=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        mFiles=getPDFfiles(folder);
        ArrayList<File> myPdf=getPDFfiles(Environment.getExternalStorageDirectory());
        item=new String[myPdf.size()];
        for(int i=0;i<item.length;i++)
        {
            item[i]=myPdf.get(i).getName().replace(".pdf","");
        }
        PDFAdapter pdfAdapter=new PDFAdapter(this,mFiles,item);
        recyclerView.setAdapter(pdfAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

    }

    private ArrayList<File> getPDFfiles(File folder) {
        ArrayList<File> arrayList=new ArrayList<>();
        File[] files=folder.listFiles();
        if(files!=null)
        {
            for(File singleFile:files){
                if(singleFile.isDirectory() && !singleFile.isHidden()){
                    arrayList.addAll(getPDFfiles(singleFile));
                }
                else{
                    if(singleFile.getName().endsWith(".pdf")){
                        arrayList.add(singleFile);
                    }
                }
            }
        }
        return arrayList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_PERMISSION){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                initView();
            }
            else{
                Toast.makeText(this,"Please Grant Permission",Toast.LENGTH_SHORT).show();
            }
        }
    }
}