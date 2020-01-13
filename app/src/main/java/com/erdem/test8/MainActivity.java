package com.erdem.test8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private int LOCATION_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Data.setupData(MainActivity.this);
        //MachineLearning
        MachineLearning.extract_DATA_SET();
        //GPS
        askForLocationPermission();
        getCordinates();
        //
        TextView txt = findViewById(R.id.text1);
        txt.setText(String.valueOf(Data.COUNT));

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.saveData(MainActivity.this);
            }
        });

    }
    /**/
    static double CORD[]={0,0};//LAT,LON
    public void getCordinates(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PermissionChecker.PERMISSION_GRANTED)return;
        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(2000);
        locationRequest.setInterval(2000);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                    CORD[0] = locationResult.getLastLocation().getLatitude();
                    CORD[1] = locationResult.getLastLocation().getLongitude();
                    String lat=String.valueOf(CORD[0]);
                    String lon=String.valueOf(CORD[1]);
                    String minute_of_day=calculateDay_and_MinuteOfTheDay();
                    Data.expand_DATA_STRING(lat,lon,minute_of_day,2000,MainActivity.this);
                    MachineLearning.determineClosest();
                    //Data.saveData(MainActivity.this);
            }
        }, getMainLooper());
    }
    /**/
    public void askForLocationPermission(){
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //NO PROBLEM
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
        ////////
    }//void ends
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions ,@NonNull int[] grantResults){
        if(requestCode==LOCATION_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"izin verildi",Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(this,"izin alınamadı",Toast.LENGTH_SHORT).show();
                askForLocationPermission();
            }
        }
    }
    ////////////////////////////////////////////////////////
    public String calculateDay_and_MinuteOfTheDay(){
        //GMT0;
        //MONDAY ->1
        Calendar calendar = Calendar.getInstance();
        int DAY = calendar.get(Calendar.DAY_OF_WEEK);
        long MINUTE=((System.currentTimeMillis()/60000)%1440);
        String day=String.valueOf(DAY);
        String minute=String.valueOf(MINUTE);
        return (day+"-"+minute);
    }


}
