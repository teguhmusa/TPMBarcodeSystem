package com.teguhmusaharpa.tpmbarcodesystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class InventoryActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView mScannerView;
    private String nik;

    String name;
    private String level;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);


    }

    public void inventoryList(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view<br />
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.<br />
        mScannerView.startCamera();
        // Start camera<br />
    }


    @Override
    public void onBackPressed() {
        if(mScannerView.isEnabled()) {
            mScannerView.stopCameraPreview();
            mScannerView.stopCamera();
            this.recreate();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();   // Stop camera on pause<br />
    }


    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());

        mScannerView.stopCamera();
        setContentView(R.layout.activity_inventory);
        String result = rawResult.getText().toString();
        String[] parts = result.split(" "); // escape .
        String part1 = parts[1];

        Intent intent = new Intent(this, ShowInventoryActivity.class);
        intent.putExtra(config.LOCATION,part1);
        startActivity(intent);

        //mScannerView.resumeCameraPreview(this);
    }



}
