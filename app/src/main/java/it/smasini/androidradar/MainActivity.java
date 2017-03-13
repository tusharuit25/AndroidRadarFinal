package it.smasini.androidradar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

import it.smasini.radar.RadarPoint;
import it.smasini.radar.RadarView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private RadarView radarView;
    private ArrayList<RadarPoint> points = new ArrayList<RadarPoint>();
    private SeekBar mSeekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final float[] refLat = new float[2];
        refLat[0] = 18.0417f;
        refLat[1] = 74.1862f;
        radarView = (RadarView) findViewById(R.id.radar_view);
        radarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAll(refLat);
            }
        });
        radarView.setOnRadarPinClickListener(new RadarView.OnRadarPinClickListener() {
            @Override
            public void onPinClicked(String identifier) {
                Toast.makeText(MainActivity.this, identifier, Toast.LENGTH_LONG).show();
            }
        });


        radarView.setReferencePoint(new RadarPoint("center", refLat[0],refLat[1],5,0));
        startAll(refLat);
        mSeekbar = (SeekBar)findViewById(R.id.seekBar);

        mSeekbar.setMax(909260);
        mSeekbar.setOnSeekBarChangeListener(this);
    }

    private void startAll(final float[] reflat){
        points = new ArrayList<RadarPoint>();
        radarView.resetPoints();
        radarView.startAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int angle1 = (int)getBearing(reflat[0],reflat[1],17.6805,74.0183);
                RadarPoint r1 = new RadarPoint("Satara", 17.6805f,74.0183f, 5,angle1);

                int angle2 = (int)getBearing(reflat[0],reflat[1],17.9487,73.8919);
                RadarPoint r2 = new RadarPoint("Wai", 17.9487f,73.8919f, 5,angle2);

                int angle3 = (int)getBearing(reflat[0],reflat[1],18.0417,74.1862);
                RadarPoint r3 = new RadarPoint("Lonand", 18.0417f,74.1862f, 5,angle3);

                int angle4 = (int)getBearing(reflat[0],reflat[1],18.1484f,73.9775f);
                RadarPoint r4 = new RadarPoint("Shirwal", 18.1484f,73.9775f, 5,angle4);

                int angle5 = (int)getBearing(reflat[0],reflat[1],16.7050f,74.2433f);
                RadarPoint r5 = new RadarPoint("Kolhapur", 16.7050f,74.2433f, 5,angle5);

                int angle6 = (int)getBearing(reflat[0],reflat[1],15.2993f,74.1240f);
                RadarPoint r6 = new RadarPoint("Goa", 15.2993f,74.1240f,5,angle6);

                int angle7 = (int)getBearing(reflat[0],reflat[1],21.1458f,79.0882f);
                RadarPoint r7 = new RadarPoint("Nagpur", 21.1458f,79.0882f,5,angle7);

                int angle8 = (int)getBearing(reflat[0],reflat[1],20.9374f,77.7796f);
                RadarPoint r8 = new RadarPoint("Amaravati", 20.9374f,77.7796f,5,angle8);

                int angle9 = (int)getBearing(reflat[0],reflat[1],44.118592f,12.242053f);
                RadarPoint r9 = new RadarPoint("identifier9", 44.118592f,12.242053f,5,angle9);

                int angle10 = (int)getBearing(reflat[0],reflat[1],44.116289f,12.240840f);
                RadarPoint r10 = new RadarPoint("identifier10", 44.116289f,12.240840f,5,angle10);

                points.add(r1);
                points.add(r2);
                points.add(r3);
                points.add(r4);
                points.add(r5);
                points.add(r6);
                points.add(r7);
                points.add(r8);
                points.add(r9);
                points.add(r10);

                radarView.setPoints(points);

            }
        }, 1000);
    }

    double getBearing(double startLat, double startLong, double endLat, double endLong){
        startLat = radians(startLat);
        startLong = radians(startLong);
        endLat = radians(endLat);
        endLong = radians(endLong);

        double dLong = endLong - startLong;

        double dPhi = Math.log(Math.tan(endLat/2.0+Math.PI/4.0)/Math.tan(startLat/2.0+Math.PI/4.0));
        if (Math.abs(dLong) > Math.PI){
            if (dLong > 0.0)
                dLong = (float)(-(2.0 * Math.PI - dLong));
            else
                dLong = (float)(2.0 * Math.PI + dLong);
        }

        return ((degrees((float)(Math.atan2(dLong, dPhi))) + 360.0) % 360.0);
    }
    float radians(double n) {
        return (float) (n * (Math.PI / 180));
    }
    float degrees(float n) {
        return (int) (n * (180 / Math.PI));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        int distance = seekBar.getProgress();
        radarView.setMaxDistance(distance);


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
