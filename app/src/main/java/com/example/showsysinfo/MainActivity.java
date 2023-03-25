package com.example.showsysinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import android.util.DisplayMetrics;
import android.os.Build;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private LinearLayout linearLayout;
    private TextView textViewDeviceInfo;
    private TextView textViewBatteryLevel;
    private TextView textViewSensorData;
    private TextView textViewGPSData;
    private TextView textViewTemperatureData;
    private TextView textViewScreenRes;
    private TextView textViewProximityData;
    private TextView textViewGyroscopeData;
    private TextView textViewLightData;
    private TextView textViewMagneticFieldData;
    private TextView textViewRamData;
    private TextView textViewCpuData;
    private TextView textViewRootInfo;
    private TextView textViewBrandCamera;
    private TextView textViewBrandMotor;
    private TextView textViewBrandBattery;
    private TextView textViewBrandScreen;
    private TextView textViewBrandCPU;
    private TextView textViewBrandGPU;
    private TextView textViewBrandRAM;
    private TextView textViewBrandStorage;
    private TextView textViewBrandWiFi;
    private TextView textViewBrandBluetooth;
    private TextView textViewBrandNetwork;
    private TextView textViewBrandGPS;
    private TextView textViewBrandNFC;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor temperatureSensor;
    private Sensor proximitySensor;
    private Sensor gyroscopeSensor;
    private Sensor lightSensor;
    private Sensor magneticFieldSensor;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private MediaRecorder mRecorder;


    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a View object to use as divider
        View dividerView = new View(this);
        dividerView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                2 // height of divider
        ));
        dividerView.setBackgroundColor(Color.BLACK);
        // create a second divider
        View dividerView2 = new View(this);
        dividerView2.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                2 ));// height of divider
        dividerView2.setBackgroundColor(Color.BLACK);
        // Create the third divider view
        View dividerView3 = new View(this);
        dividerView3.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                2 ));// height of divider
        dividerView3.setBackgroundColor(Color.BLACK);


        // Request location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        // Get references to the UI elements
        linearLayout = findViewById(R.id.linear_layout);
        textViewDeviceInfo = new TextView(this);
        textViewBatteryLevel = new TextView(this);
        textViewSensorData = new TextView(this);
        textViewGPSData = new TextView(this);
        textViewTemperatureData = new TextView(this);
        textViewProximityData = new TextView(this);
        textViewGyroscopeData = new TextView(this);
        textViewLightData = new TextView(this);
        textViewMagneticFieldData = new TextView(this);
        textViewRamData = new TextView(this);
        textViewCpuData = new TextView(this);
        textViewRootInfo = new TextView(this);
        textViewScreenRes = new TextView(this);
        textViewBrandCamera = new TextView(this);
        textViewBrandMotor = new TextView(this);
        textViewBrandBattery = new TextView(this);
        textViewBrandScreen = new TextView(this);
        textViewBrandCPU = new TextView(this);
        textViewBrandGPU = new TextView(this);
        textViewBrandRAM = new TextView(this);
        textViewBrandStorage = new TextView(this);
        textViewBrandWiFi = new TextView(this);
        textViewBrandBluetooth = new TextView(this);
        textViewBrandNetwork = new TextView(this);
        textViewBrandGPS = new TextView(this);
        textViewBrandNFC = new TextView(this);

        mRecorder = new MediaRecorder();

        // Retrieve system information
        String deviceInfo = "Device: " + Build.MANUFACTURER + " " + Build.MODEL
                + "\nAndroid version: " + Build.VERSION.RELEASE;
        textViewDeviceInfo.setText(deviceInfo);
        linearLayout.addView(textViewDeviceInfo);

        // Retrieve Root info
        textViewRootInfo.setText("Root access:");
        linearLayout.addView(textViewRootInfo);
        getRootInfo();

        // Retrieve screen resolution info
        textViewScreenRes.setText("Screen resolution:");
        linearLayout.addView(textViewScreenRes);
        getScreenInfo();

        // Brands:
        String motorBrand = "Motor brand: " + Build.MANUFACTURER;
        textViewBrandMotor.setText(motorBrand);
        linearLayout.addView(textViewBrandMotor);
        String batteryBrand = "Battery brand: " + Build.BRAND;
        textViewBrandBattery.setText(batteryBrand);
        linearLayout.addView(textViewBrandBattery);
        String screenBrand = "Screen brand: " + Build.BRAND;
        textViewBrandScreen.setText(screenBrand);
        linearLayout.addView(textViewBrandScreen);
        String cpuBrand = "CPU brand: " + Build.HARDWARE;
        textViewBrandCPU.setText(cpuBrand);
        linearLayout.addView(textViewBrandCPU);
        String gpuBrand = "GPU brand: " + ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                .getDeviceConfigurationInfo()
                .getGlEsVersion();;
        textViewBrandGPU.setText(gpuBrand);
        linearLayout.addView(textViewBrandGPU);
        String ramBrand = "RAM brand: " + String.valueOf((int) (new File("/proc/meminfo").length() / 1024));;
        textViewBrandRAM.setText(ramBrand);
        linearLayout.addView(textViewBrandRAM);
        String storageBrand = "Storage: " + Build.MANUFACTURER + " " + Build.MODEL;;
        textViewBrandStorage.setText(storageBrand);
        linearLayout.addView(textViewBrandStorage);
        String wifiBrand = "WiFi MAC: " + ((WifiManager) getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();;
        textViewBrandWiFi.setText(wifiBrand);
        linearLayout.addView(textViewBrandWiFi);
        String bluetoothBrand = "Bluetooth: " + BluetoothAdapter.getDefaultAdapter().getName();;
        textViewBrandBluetooth.setText(bluetoothBrand);
        linearLayout.addView(textViewBrandBluetooth);
        String networkBrand = "Network operator: " + ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName();;
        textViewBrandNetwork.setText(networkBrand);
        linearLayout.addView(textViewBrandNetwork);
        String gpsBrand = "GPS brand: " + Build.MANUFACTURER + " " + Build.MODEL;
        textViewBrandGPS.setText(gpsBrand);
        linearLayout.addView(textViewBrandGPS);

        String nfcBrand = getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC) ? "NFC supported" : "NFC not supported";
        textViewBrandNFC.setText(nfcBrand);
        linearLayout.addView(textViewBrandNFC);

        linearLayout.addView(dividerView);

        // Battery
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int batteryLevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int batteryScale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPercent = (batteryLevel / (float) batteryScale) * 100;
        String batteryInfo = "Battery level: " + batteryPercent + "%";
        textViewBatteryLevel.setText(batteryInfo);
        linearLayout.addView(textViewBatteryLevel);

        // Retrieve accelerometer sensor information
        textViewSensorData.setText("accelerometer data:");

        linearLayout.addView(textViewSensorData);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Retrieve GPS information
        textViewGPSData.setText("GPS data:");
        linearLayout.addView(textViewGPSData);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setupLocationUpdates();

        // Retrieve temperature information
        textViewTemperatureData.setText("Temperature data:");
        linearLayout.addView(textViewTemperatureData);

        // Retrieve proximity information
        textViewProximityData.setText("Proximity data:");
        linearLayout.addView(textViewProximityData);

        // Retrieve gyroscope information usage
        textViewGyroscopeData.setText("Gyroscope data:");
        linearLayout.addView(textViewGyroscopeData);

        // Retrieve light information
        textViewLightData.setText("Light data:");
        linearLayout.addView(textViewLightData);

        // Retrieve magnetic field information
        textViewMagneticFieldData.setText("Magnetic field data:");
        linearLayout.addView(textViewMagneticFieldData);

//        linearLayout.addView(textViewSensorData);
        linearLayout.addView(dividerView3);

        // Retrieve RAM usage information
        textViewRamData.setText("RAM usage:");
        linearLayout.addView(textViewRamData);
        updateRamUsage();

        // Retrieve CPU usage information
        textViewCpuData.setText("CPU usage:");
        linearLayout.addView(textViewCpuData);
        getCpuUsage();

        linearLayout.addView(dividerView2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            String sensorData = "\t\t\tX: " + x + "\n\t\t\tY: " + y + "\n\t\t\tZ: " + z;
            textViewSensorData.setText("Accelerometer data:\n" + sensorData);
        } else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) == null){
                textViewTemperatureData.setText("Temperature data: " + "N/A");
            }
            else {
                float temperature = event.values[0];
                textViewTemperatureData.setText("Temperature data: " + temperature + "°C");
            }

        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float proximity = event.values[0];
            textViewProximityData.setText("Proximity data: " + proximity + " cm");
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            String gyroscopeData = "\t\t\tX: " + x + "\n\t\t\tY: " + y + "\n\t\t\tZ: " + z;
            textViewGyroscopeData.setText("Gyroscope data:\n" + gyroscopeData);
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light = event.values[0];
            textViewLightData.setText("Light data: " + light + " lx");
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            String magneticFieldData = "\t\t\tX: " + x + "\n\t\t\tY: " + y + "\n\t\t\tZ: " + z;
            textViewMagneticFieldData.setText("Magnetic field data:\n" + magneticFieldData);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }


    private void updateRamUsage() {
        getRamUsage();
        textViewRamData.postDelayed(new Runnable() {
            public void run() {
                updateRamUsage();
            }
        }, 1000); // update every 1 second
    }
    private void getRamUsage() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long availableMemory = memoryInfo.availMem / (1024 * 1024);
        long totalMemory = memoryInfo.totalMem / (1024 * 1024);
        String ramData = String.format(Locale.getDefault() ,"\t\t\tTotal memory: %d MB\n\t\t\tAvailable memory: %d MB\n\t\t\tUsed memory: %d MB",
                totalMemory, availableMemory, (totalMemory - availableMemory));
        textViewRamData.setText("RAM data:\n" + ramData);
    }

    private void getCpuUsage() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/stat"));
            String[] tokens = reader.readLine().split(" +");
            reader.close();

            long user = Long.parseLong(tokens[2]);
            long nice = Long.parseLong(tokens[3]);
            long system = Long.parseLong(tokens[4]);
            long idle = Long.parseLong(tokens[5]);
            long iowait = Long.parseLong(tokens[6]);
            long irq = Long.parseLong(tokens[7]);
            long softirq = Long.parseLong(tokens[8]);
            long totalCpuTime1 = user + nice + system + idle + iowait + irq + softirq;

            try {
                Thread.sleep(360);
            } catch (Exception e) {}

            reader = new BufferedReader(new FileReader("/proc/stat"));
            tokens = reader.readLine().split(" +");
            reader.close();

            user = Long.parseLong(tokens[2]);
            nice = Long.parseLong(tokens[3]);
            system = Long.parseLong(tokens[4]);
            idle = Long.parseLong(tokens[5]);
            iowait = Long.parseLong(tokens[6]);
            irq = Long.parseLong(tokens[7]);
            softirq = Long.parseLong(tokens[8]);
            long totalCpuTime2 = user + nice + system + idle + iowait + irq + softirq;

            double cpuUsage = (double) (totalCpuTime2 - totalCpuTime1 - (idle - iowait)) / (totalCpuTime2 - totalCpuTime1) * 100;
            String cpuData = String.format(Locale.getDefault() ,"%.2f",
                    cpuUsage);
            textViewCpuData.setText("CPU usage data: " + cpuData);
        } catch (IOException e) {
            e.printStackTrace();
            textViewCpuData.setText("CPU usage data: " + "N/A");
        }
    }

    private void setupLocationUpdates() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String gpsData = String.format(Locale.getDefault(), "\t\t\tLatitude: %f\n\t\t\tLongitude: %f", latitude, longitude);
                textViewGPSData.setText("GPS data:\n" + gpsData);
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private void getRootInfo() {
        boolean isRooted = false;
        try {
            Process process = Runtime.getRuntime().exec("su");
            isRooted = true;
        } catch (IOException e) {
            isRooted = false;
        }
        textViewRootInfo.setText("Root access: " + (isRooted ? "Yes" : "No"));
    }

    private void getScreenInfo(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        textViewScreenRes.setText("Screen resolution: " + height + "*" + width);
    }
}
