package com.example.android.fake_location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Switch turnOn;
    private boolean isOn;
    private Toast myToast;
    private String provider;
    private LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        turnOn = (Switch) findViewById(R.id.mySwitch);


        //set it to off
        turnOn.setChecked(false);
        //set a listener
        turnOn.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
                        if(isChecked){
                            isOn = true;
                            displayToast("you are current in Stuttgart");
                            setLocation();
                        }else {
                            isOn = false;
                            displayToast("mock location is off");
                            if(provider!=null)
                                lm.clearTestProviderLocation(provider);
                        }
                    }
                }
        );
    }


    protected boolean setLocation(){
        lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        double[] Amsterdam = {52.37,4.88};
        Location location = new Location(lm.GPS_PROVIDER);
        provider = LocationManager.GPS_PROVIDER;

        if(allowMockLocation())
            lm.addTestProvider(provider,false, false, false, false, false, false, false, 0, android.location.Criteria.ACCURACY_FINE);
        else{
            Log.d("addTestProvider","ALLOW_MOCK_LOCATION is not specified in manifest.");
            return false;
        }

        location.setLatitude(Amsterdam[0]);
        location.setLongitude(Amsterdam[1]);
        location.setAccuracy(3);
        location.setAltitude(0);
        location.setBearing(0);
        location.setSpeed(0);
        location.setTime(System.currentTimeMillis());
        //Android 4.2 and later need this method
        if(Build.VERSION.SDK_INT>16){
            location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }

        lm.setTestProviderEnabled(provider, true);

        lm.setTestProviderStatus
                (
                        provider,
                        LocationProvider.AVAILABLE,
                        null,
                        System.currentTimeMillis()
                );

        lm.setTestProviderLocation
                (
                        provider,
                        location
                );

        return true;
    }

    protected boolean allowMockLocation(){
        String t = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION);
        return !t.equals("0");
    }

    public void displayToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
