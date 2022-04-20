package com.example.qrandbarcodescanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    public static final int CAMERA_REQUEST_PERMISSION = 1;
    public static final int SCAN_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        third party library ka istemal karna hoga
//        https://github.com/yuriy-budiyev/code-scanner
        button = findViewById(R.id.btnScan);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanningActivity();
            }
        });
    }

    private void startScanningActivity(){
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//                    if permission not given ask again for permission
                requestPermissions(new String[] {Manifest.permission.CAMERA},CAMERA_REQUEST_PERMISSION);
            }else {
//                    permission granted, then start scanning
                startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class),SCAN_REQUEST_CODE);
            }
        }else {
//            if lower version then already start scanning
            startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class),SCAN_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class),SCAN_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                button.setText(data.getStringExtra("scanning_result"));
            }
        }
    }
}