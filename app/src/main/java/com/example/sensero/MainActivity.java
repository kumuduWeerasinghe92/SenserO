package com.example.sensero;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sensero.ui.main.SectionsPagerAdapter;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener  {

    public static final String TAG ="MainActivity";
    private  SensorManager sensorM;
    private  Sensor accelerometer;
    public Switch enableSwitch;
    public  int currentTab =0;
    public boolean  tabMoveSwitchChange=false;

    public TextView xv,yv,zv;


    public Map<String, String> map = new HashMap<String, String>();

    public void setSwitch(boolean sts)
    {

        enableSwitch.setChecked(sts);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        enableSwitch = (Switch)  findViewById(R.id.switch1);




        map.put("0","false");
        map.put("1","false");

        sensorM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                currentTab = tab.getPosition();
                if(currentTab ==0)
                {

                    tabMoveSwitchChange=true;
                    if(map.get("0").equals("false"))
                    {

                        setSwitch(false);

                    }else
                        {
                            setSwitch(true);

                        }

                }else if(currentTab ==1)
                {
                    tabMoveSwitchChange=true;
                    if(map.get("1").equals("false"))
                    {
                        setSwitch(false);

                    }else
                    {
                        setSwitch(true);
                    }


                }

                tabMoveSwitchChange=false;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        enableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(!tabMoveSwitchChange) {
                    if (currentTab == 0) {
                        if (map.get("0").equals("false") && isChecked) {
                            map.put("0", "true");
                            sensorM.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);



                        } else {
                            map.put("0", "false");
                            sensorM.unregisterListener(MainActivity.this);
                            xv = findViewById(R.id.firstRead);
                            yv = findViewById(R.id.secondRead);
                            zv = findViewById(R.id.thirdRead);
                            xv.setText("Waiting for sensor X..");
                            yv.setText("Waiting for sensor Y..");
                            zv.setText("Waiting for sensor Z..");

                        }


                    } else if (currentTab == 1) {
                        if (map.get("1").equals("false") && isChecked) {
                            map.put("1", "true");

                        } else {
                            map.put("1", "false");

                        }


                    }
                }




                tabMoveSwitchChange =false;
            }
        });





    }




    @Override
    public void onSensorChanged(SensorEvent event) {

       // Log.d(TAG,"onSensorChanged X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2]);

        xv = findViewById(R.id.firstRead);
        yv = findViewById(R.id.secondRead);
        zv = findViewById(R.id.thirdRead);

        xv.setText("Reading X : "+event.values[0]);
        yv.setText("Reading Y : "+event.values[1]);
        zv.setText("Reading Z : "+event.values[2]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}