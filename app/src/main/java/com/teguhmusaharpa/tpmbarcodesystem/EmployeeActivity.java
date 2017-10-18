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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static java.lang.Boolean.FALSE;

public class EmployeeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private FrameLayout frame1, frame2;
    ZXingScannerView mScannerView;
    private String nik;
    private TextView textID;


    private String level, name, status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        frame1 = (FrameLayout) findViewById(R.id.frameLayout1);
        frame2 = (FrameLayout) findViewById(R.id.frameLayout2);

        textID = (TextView) findViewById(R.id.textViewID);

        frame2.setVisibility(View.GONE);
        textID.setText("SCAN ID");
    }

    public void scanID(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view<br />
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.<br />
        mScannerView.startCamera();
        // Start camera<br />
    }

    public void enterID(View view){
        Intent i = new Intent(EmployeeActivity.this, MainActivity.class);
        startActivity(i);
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


        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Scan Result");
        //builder.setMessage(rawResult.getText());
        //AlertDialog alert1 = builder.create();
        //alert1.show();
        setContentView(R.layout.activity_employee);
        mScannerView.stopCamera();
        String result = rawResult.getText().toString();
        String[] parts = result.split("\\."); // escape .
        String part1 = parts[0];
        String part2 = parts[1];
        textID.setText(result);
        nik = part2;


        getEmployee();


        //mScannerView.resumeCameraPreview(this);
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            //ProgressDialog loading;
           // @Override
            //protected void onPreExecute() {
             //   super.onPreExecute();
               // loading = ProgressDialog.show(EmployeeActivity.this,"Fetching...","Wait...",false,false);
            //}

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
               // loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(config.URL_GET_EMP, nik);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void scanBarcode(){


    }

    private void showEmployee(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            name = c.getString(config.TAG_NAMA);
            status = c.getString(config.TAG_STATUS);
            level = c.getString(config.TAG_LEVEL);

            Log.d("test", status);

            if(level.equals("master")||level.equals("employee")) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("QR Code sesuai. Terima kasih telah login.");
                dlgAlert.setTitle("Perhatian");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                frame2 = (FrameLayout) findViewById(R.id.frameLayout2);
                                textID = (TextView) findViewById(R.id.textViewID);
                                frame2.setVisibility(View.VISIBLE);
                                textID.setText(name+" "+nik);
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }else
            {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("QR Code salah atau tidak sesuai. Silahkan hubungi admin");
                dlgAlert.setTitle("Perhatian");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                frame2 = (FrameLayout) findViewById(R.id.frameLayout2);
                                textID = (TextView) findViewById(R.id.textViewID);
                                frame2.setVisibility(View.GONE);
                                textID.setText("SCAN ID");
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
