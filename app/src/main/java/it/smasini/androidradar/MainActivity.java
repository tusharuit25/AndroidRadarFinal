package it.smasini.androidradar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;
import it.smasini.radar.RadarPoint;
import it.smasini.radar.RadarUtility;
import it.smasini.radar.RadarView;
public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private RadarView.OnRadarPinClickListener onRadarPinClickListener;
    private RadarView radarView;
    private ArrayList<RadarPoint> points = new ArrayList<RadarPoint>();
    private SeekBar mSeekbar;
    ImageView bkImage;
    float factor = 1.0f;
    ScaleGestureDetector scaleGestureDetector;
    double maxDistance = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final float[] refLat = new float[2];
        refLat[0] = 18.5115421f;
        refLat[1] = 73.922030499999991f;
        radarView = (RadarView) findViewById(R.id.radar_view);
     /*   radarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAll(refLat);
            }
        });*/
        setOnRadarPinClickListener(new RadarView.OnRadarPinClickListener() {
            @Override
            public void onPinClicked(String identifier) {
                Toast.makeText(MainActivity.this, identifier, Toast.LENGTH_SHORT).show();
            }
        });
        radarView.setReferencePoint(new RadarPoint("center", refLat[0], refLat[1], 5, 0, false, false));
        startAll(refLat);
        mSeekbar = (SeekBar) findViewById(R.id.seekBar);
        mSeekbar.setMax((int) maxDistance);
        mSeekbar.setOnSeekBarChangeListener(this);
        radarView.setFocusableInTouchMode(true);
        radarView.requestFocus();
        radarView.radar.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v,
                                           MotionEvent m) {
                        scaleGestureDetector.onTouchEvent(m);
                        try {
                            ArrayList<RadarPoint> rp = radarView.radar.getTouchedPin(m);
                            if (rp != null) {
                                for (RadarPoint rPoint : rp) {
                                    String pinIdentifier = String.valueOf(rPoint.getDistance());
                                    if (pinIdentifier != null) {
                                        onRadarPinClickListener.onPinClicked(pinIdentifier);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                        }
                        return true;
                    }
                }
        );
        scaleGestureDetector = new ScaleGestureDetector(MainActivity.this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor() - 1;
                if (scaleFactor > 0) {
                    mSeekbar.setProgress((int) (mSeekbar.getProgress() - (maxDistance / 10)));
                } else {
                    mSeekbar.setProgress((int) (mSeekbar.getProgress() + (maxDistance / 10)));
                }

                return true;
            }
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }
            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                // super.onScaleEnd(detector);
            }
        });
    }
    private void startAll(final float[] reflat) {
        points = new ArrayList<RadarPoint>();
        radarView.resetPoints();
        radarView.startAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int angle1 = (int) getBearing(reflat[0], reflat[1], 18.0, 73.0);
                RadarPoint r1 = new RadarPoint("1", 18.0f, 73.0f, 5, angle1, false, false);
                double distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r1.x, r1.y);
                r1.setDistance((float) distance);


                //
               // int angle1 = (int) getBearing(reflat[0], reflat[1], 18.0, 73.0);
                Random rand = new Random();
                int angleTemp = rand.nextInt(360) + 1;
                RadarPoint rTemp = new RadarPoint("1", 18.0f, 73.0f, 5, angleTemp, false, false);
                double distanceTemp = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, rTemp.x, rTemp.y);
                rTemp.setDistance((float) distanceTemp);
                //

                int angle2 = (int) getBearing(reflat[0], reflat[1], 18.5114, 73.9224);
                RadarPoint r2 = new RadarPoint("2", 18.5114f, 73.9224f, 5, angle2, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r2.x, r2.y);
                r2.setDistance((float) distance);
                int angle3 = (int) getBearing(reflat[0], reflat[1], 18.5118, 73.9232);
                RadarPoint r3 = new RadarPoint("3", 18.5118f, 73.9232f, 5, angle3, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r3.x, r3.y);
                r3.setDistance((float) distance);
                int angle4 = (int) getBearing(reflat[0], reflat[1], 18.5115, 73.9234);
                RadarPoint r4 = new RadarPoint("4", 18.5115f, 73.9234f, 5, angle4, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r4.x, r4.y);
                r4.setDistance((float) distance);
                int angle5 = (int) getBearing(reflat[0], reflat[1], 18.142, 73.8567);
                RadarPoint r5 = new RadarPoint("5", 18.142f, 73.8567f, 5, angle5, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r5.x, r5.y);
                r5.setDistance((float) distance);
                int angle6 = (int) getBearing(reflat[0], reflat[1], 18.7546, 73.4062);
                RadarPoint r6 = new RadarPoint("6", 18.7546f, 73.4062f, 5, angle6, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r6.x, r6.y);
                r6.setDistance((float) distance);
                int angle7 = (int) getBearing(reflat[0], reflat[1], 18.6546, 73.4062);
                RadarPoint r7 = new RadarPoint("7", 18.6546f, 73.4062f, 5, angle7, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r7.x, r7.y);
                r7.setDistance((float) distance);
                int angle8 = (int) getBearing(reflat[0], reflat[1], 20.9374f, 77.7796f);
                RadarPoint r8 = new RadarPoint("8", 20.9374f, 77.7796f, 5, angle8, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r8.x, r8.y);
                r8.setDistance((float) distance);
                int angle9 = (int) getBearing(reflat[0], reflat[1], 24.118592f, 72.242053f);
                RadarPoint r9 = new RadarPoint("9", 24.118592f, 72.242053f, 5, angle9, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r9.x, r9.y);
                r9.setDistance((float) distance);
                int angle10 = (int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r10 = new RadarPoint("10", 14.116289f, 72.240840f, 5, angle10, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r10.x, r10.y);
                r10.setDistance((float) distance);
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
                points.add(rTemp);
                for (RadarPoint rp : points) {
                    distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, rp.x, rp.y);
                    if (distance > maxDistance) {
                        maxDistance = distance;
                    }
                    rp.setDistance((float) distance);
                }
                radarView.setMaxDistance((int) maxDistance);
                radarView.getRadar().setMaxDistance((int) maxDistance);
                radarView.setPoints(points);
                mSeekbar.setMax((int) maxDistance);
                mSeekbar.setProgress((int) maxDistance / 2);
            }
        }, 1000);
    }
    double getBearing(double startLat, double startLong, double endLat, double endLong) {
        startLat = radians(startLat);
        startLong = radians(startLong);
        endLat = radians(endLat);
        endLong = radians(endLong);
        double dLong = endLong - startLong;
        double dPhi = Math.log(Math.tan(endLat / 2.0 + Math.PI / 4.0) / Math.tan(startLat / 2.0 + Math.PI / 4.0));
        if (Math.abs(dLong) > Math.PI) {
            if (dLong > 0.0)
                dLong = (float) (-(2.0 * Math.PI - dLong));
            else
                dLong = (float) (2.0 * Math.PI + dLong);
        }
        return ((degrees((float) (Math.atan2(dLong, dPhi))) + 360.0) % 360.0);
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
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
        //return super.onTouchEvent(event);
    }
    public void setOnRadarPinClickListener(RadarView.OnRadarPinClickListener onRadarPinClickListener) {
        this.onRadarPinClickListener = onRadarPinClickListener;
    }
}
