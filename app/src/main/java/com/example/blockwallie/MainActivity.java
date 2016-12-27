package com.example.blockwallie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //View Objects
    private Button sendBtc, getBtc;
    private TextView textViewBtcValue;

    //QR code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View Objects
        sendBtc = (Button) findViewById(R.id.btcSend);
        getBtc = (Button) findViewById(R.id.btcReceive);
        textViewBtcValue = (TextView) findViewById(R.id.btcValue);

        // Initializing scan object
        qrScan = new IntentIntegrator(this);

        // Set a click listener on that View
        sendBtc.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Initiating the qr code scan
                qrScan.initiateScan();
            }
        });

        // Set a click listener on that View
        getBtc.setOnClickListener(new OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent getBtcIntent = new Intent(MainActivity.this, ReceiveActivity.class);

                // Start the new activity
                startActivity(getBtcIntent);
            }
        });

    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Handling the result of the camera intent
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            // If QR code has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                // If QR contains data
                try {
                    // Converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    // Setting values to textviews
                    textViewBtcValue.setText(obj.getString("crypto"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    // If control comes here
                    // that means the encoded format not matches
                    // in this case you can display whatever data is available on the qrcode
                    // to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
