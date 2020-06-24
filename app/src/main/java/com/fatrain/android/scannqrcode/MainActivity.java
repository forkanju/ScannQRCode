package com.fatrain.android.scannqrcode;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private TextView textResult;
    private DatabaseReference databaseReference;
    private LinearLayout mLinearLayoutResult;
    private Button btnSave, btnFetch;
    private String lastKey = "";
    private String qrCodeResult = "";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("result");
        scannerView = findViewById(R.id.scan_id);
        mLinearLayoutResult = findViewById(R.id.layout_result);
        textResult = findViewById(R.id.text_result);
        btnSave = findViewById(R.id.btn_save);
        btnFetch = findViewById(R.id.btn_fetch);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataOnFirebase();
            }
        });
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);

            }
        });

        //Request Permission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(MainActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this, "You must accept this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

    }

    @Override
    protected void onStart() {
        super.onStart();
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //Here we receive rew result.
        if (result.getText() != null && !result.getText().isEmpty()) {
            qrCodeResult = result.getText();
            textResult.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            textResult.setText("Result From QRCode: " + qrCodeResult);
            mLinearLayoutResult.setVisibility(View.VISIBLE);
            scannerView.setVisibility(View.GONE);
        } else {
            mLinearLayoutResult.setVisibility(View.GONE);
            scannerView.setVisibility(View.VISIBLE);
            textResult.setText("QR Code Result : Error Please try again!");
            textResult.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }
    }

    //Save data to firebase.
    public void saveDataOnFirebase() {
        if (qrCodeResult != null && !qrCodeResult.isEmpty()) {
            lastKey = databaseReference.push().getKey();
            Model model = new Model(qrCodeResult);
            databaseReference.child(lastKey).setValue(model);
            Toast.makeText(MainActivity.this, "QR Code text value added to firebase!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "QR Code value scan failed, Please try again!", Toast.LENGTH_SHORT).show();
        }

    }


}
