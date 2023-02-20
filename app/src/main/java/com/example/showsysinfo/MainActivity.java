package com.example.showsysinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import android.os.Build;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String TAG = "myTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onStart: ");

        TextView textView_model = findViewById(R.id.textViewModel);
        TextView textView_id = findViewById(R.id.textViewID);
        TextView textView_version = findViewById(R.id.textViewVersion);
        TextView textView_base = findViewById(R.id.textViewBase);
        TextView textView_manufacturer = findViewById(R.id.textViewManufacturer);
        TextView textView_product = findViewById(R.id.textViewProduct);
        TextView textView_brand = findViewById(R.id.textViewBrand);
        TextView textView_device = findViewById(R.id.textViewDevice);
        TextView textView_language = findViewById(R.id.textViewLan);
        TextView textView_host = findViewById(R.id.textViewHost);
        TextView textView_disp = findViewById(R.id.textViewDisp);
        TextView textView_type = findViewById(R.id.textViewType);
        TextView textView_user = findViewById(R.id.textViewUser);
        TextView textView_sdk = findViewById(R.id.textViewSDK);
        TextView textView_finger = findViewById(R.id.textViewFinger);
        TextView textView_incremental = findViewById(R.id.textViewIncremental);

        textView_model.setText(Build.MODEL);
        textView_id.setText(Build.ID);
        textView_version.setText(Build.MODEL);
//        textView_base.setText(Build.VERSION_CODES.BASE);
        textView_manufacturer.setText(Build.MANUFACTURER);
        textView_product.setText(Build.PRODUCT);
        textView_brand.setText(Build.BRAND);
        textView_device.setText(Build.DEVICE);
        textView_language.setText(Locale.getDefault().getLanguage());
        textView_host.setText(Build.HOST);
        textView_disp.setText(Build.DISPLAY);
        textView_type.setText(Build.TYPE);
        textView_user.setText(Build.USER);
        textView_sdk.setText(Build.VERSION.SDK);
        textView_finger.setText(Build.FINGERPRINT);
        textView_incremental.setText(Build.VERSION.INCREMENTAL);


        Log.i("TAG", "SERIAL: " + Build.SERIAL);
        Log.i("TAG","MODEL: " + Build.MODEL);
        Log.i("TAG","ID: " + Build.ID);
        Log.i("TAG","Manufacture: " + Build.MANUFACTURER);
        Log.i("TAG","brand: " + Build.BRAND);
        Log.i("TAG","type: " + Build.TYPE);
        Log.i("TAG","user: " + Build.USER);
        Log.i("TAG","BASE: " + Build.VERSION_CODES.BASE);
        Log.i("TAG","INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.i("TAG","SDK  " + Build.VERSION.SDK);
        Log.i("TAG","BOARD: " + Build.BOARD);
        Log.i("TAG","BRAND " + Build.BRAND);
        Log.i("TAG","HOST " + Build.HOST);
        Log.i("TAG","FINGERPRINT: "+Build.FINGERPRINT);
        Log.i("TAG","Version Code: " + Build.VERSION.RELEASE);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onResume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "onStop");
//        textView.setText("pause");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d(TAG, "onRestart");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy");
//    }
}