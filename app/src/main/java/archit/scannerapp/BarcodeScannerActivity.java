package archit.scannerapp;

import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.journeyapps.barcodescanner.CaptureActivity;

/**
 * Created by archit on 19/11/15.
 */
public class BarcodeScannerActivity extends CaptureActivity {

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
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
                Intent homepage = new Intent(BarcodeScannerActivity.this, HistoryActivity.class);
                BarcodeScannerActivity.this.startActivity(homepage);
                BarcodeScannerActivity.this.finish();
            }
        }, 2000);

    }
}
