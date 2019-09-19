package com.example.android.scaninfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;


import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;


public class ResultActivity extends AppCompatActivity {

    TextView result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result = (TextView) findViewById(R.id.result);
        final Barcode barcode = getIntent().getParcelableExtra("barcode");
        result.setText(barcode.displayValue);

//        YourResult = (TextView) findViewById(R.id.TextView_YourResult);
//        final Barcode barcode = getIntent().getParcelableExtra("barcode");
//        result.setText(barcode.displayValue);

    }
}


