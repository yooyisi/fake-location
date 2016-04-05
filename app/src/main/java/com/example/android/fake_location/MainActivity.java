package com.example.android.fake_location;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Switch turnOn;
    private boolean isOn;
    private Toast myToast;

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
                        }else {
                            isOn = false;
                            displayToast("mock location is off");
                        }
                    }
                }
        );
    }

    public void displayToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
