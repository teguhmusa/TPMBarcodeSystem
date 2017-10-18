package com.teguhmusaharpa.tpmbarcodesystem;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class UpdateLocationActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView mScannerView;
    EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);

        result = (EditText) findViewById(R.id.eamEditText);




    }
    public void scanUploc_OnClick(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view<br />
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.<br />
        mScannerView.startCamera();         // Start camera<br />
    }

    public void updateUploc_OnClick(View view){
        if(!result.getText().toString().equals(""))
        {

        }
    }
    @Override
    public void onBackPressed() {

        if(mScannerView.isEnabled()) {
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


        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Scan Result");
        //builder.setMessage(rawResult.getText());
        //AlertDialog alert1 = builder.create();
        //alert1.show();
        mScannerView.stopCamera();
        setContentView(R.layout.activity_update_location);
        result = (EditText) findViewById(R.id.eamEditText);
        result.setText(rawResult.getText().toString());
        //mScannerView.resumeCameraPreview(this);
    }


}
