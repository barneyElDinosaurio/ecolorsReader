package com.wxyz.root.ecolorsreader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkAndRequestPermissions()) {

            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    ////////////////////////////ANOTHER METHODS//////////////////////////////////

    //HANDLE RESULT FOR QR CODE
    @Override
    public void handleResult(Result rawResult) {

        // Do something with the result here
        Intent i = new Intent(getApplicationContext(),AddPoints.class);
        i.putExtra("id",rawResult.getText());//Send info of id to other class
        startActivity(i);
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);

    }

    //PERMISSION IN REALTIME FOR CAMERA
    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


}
