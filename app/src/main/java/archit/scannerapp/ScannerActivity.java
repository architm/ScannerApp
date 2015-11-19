package archit.scannerapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import archit.model.Constants;
import archit.persistence.HistoryRepository;
import archit.persistence.HistoryRepositoryImpl;

/**
 * handling the QR/Barcode Scanning Operation
 * Used Zxing Barcode Scanning Library for detection
 */
public class ScannerActivity extends Activity {

    private static final String TAG = "code";

    HistoryRepository hr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        try {
            hr = new HistoryRepositoryImpl(this.getApplicationContext(), Constants.DB_NAME.getValue());
        } catch (IOException | CouchbaseLiteException e) {
            Log.e(TAG, e.getMessage());
        }
        IntentIntegrator integrator = new IntentIntegrator(ScannerActivity.this);
        integrator.setCaptureActivity(BarcodeScannerActivity.class);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                    if (scanResult.getContents() != null) {
                        String re = scanResult.getContents();
                        try {
                            saveEntity(re);
                        } catch (IOException | CouchbaseLiteException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }
                Intent homepage = new Intent(ScannerActivity.this, HistoryActivity.class);
                ScannerActivity.this.startActivity(homepage);
                ScannerActivity.this.finish();
        }
    }

    private void saveEntity(String entity) throws IOException, CouchbaseLiteException {
        Log.i(TAG, "Saving Data: " + entity);
        Map<String, Object> entries = new HashMap<String, Object>();
        String dateTime = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss").format(new Date());
        entries.put(dateTime, entity);
        updateData("history", entries);
    }


    private void updateData(String key, Map<String, Object> entry) throws IOException, CouchbaseLiteException {
        hr.updateKey(key, entry);
    }

   /* boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);/*//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }*/
}