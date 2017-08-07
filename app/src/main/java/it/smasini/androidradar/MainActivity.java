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
        //sadhana society 18.502761, 73.945151
        refLat[0] = 50.0684722f;
        refLat[1] = 8.6431724f;
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
        radarView.setReferencePoint(new RadarPoint(getBaseContext(), "center", refLat[0], refLat[1], 5, 0, false, false));
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
                                    String pinIdentifier = String.valueOf(rPoint.getIdentifier());
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
                    mSeekbar.setProgress((int) (mSeekbar.getProgress() - (maxDistance * 1000 / 10)));
                } else {
                    mSeekbar.setProgress((int) (mSeekbar.getProgress() + (maxDistance * 1000 / 10)));
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


                float radardistance = 0;
                Random rand = new Random();
                int angleTemp = rand.nextInt(360) + 1;
                //mega center 18.504804, 73.926584
                //users
                //int angle1 = (int) getBearing(reflat[0], reflat[1],  18.5193526,73.9473528);
                RadarPoint r1 = new RadarPoint(getBaseContext(), "Sabine", 50.0918137f, 8.6606882f, 5, angleTemp, true, false);
                double distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r1.x, r1.y);
                radardistance = (float) distance / 1000;
                r1.setDistance(radardistance);


                int angle2 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.518447, 73.948554);
                RadarPoint r2 = new RadarPoint(getBaseContext(), "Tobi", 50.045906002633274f, 8.244423f, 5, angle2, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r2.x, r2.y);
                radardistance = (float) distance / 1000;
                r2.setDistance(radardistance);

                int angle3 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r3 = new RadarPoint(getBaseContext(), "Daconi", 50.1045329f, 8.6894271f, 5, angle3, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r3.x, r3.y);
                radardistance = (float) distance / 1000;
                r3.setDistance(radardistance);

                int angle4 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r4 = new RadarPoint(getBaseContext(), "susi", 50.0018097f, 8.6562648f, 5, angle4, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r4.x, r4.y);
                radardistance = (float) distance / 1000;
                r4.setDistance(radardistance);

                int angle5 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.469374, 73.982543);
                RadarPoint r5 = new RadarPoint(getBaseContext(), "Sultan", 50.1397608f, 8.6693793f, 5, angle5, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r5.x, r5.y);
                radardistance = (float) distance / 1000;
                r5.setDistance(radardistance);

                int angle6 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.7546, 73.4062);
                RadarPoint r6 = new RadarPoint(getBaseContext(), "6", 50.1933833f, 8.5895697f, 5, angle6, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r6.x, r6.y);
                radardistance = (float) distance / 1000;
                r6.setDistance(radardistance);

                int angle7 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.6546, 73.4062);
                RadarPoint r7 = new RadarPoint(getBaseContext(), "7", 50.1740587f, 8.7318007f, 5, angle7, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r7.x, r7.y);
                radardistance = (float) distance / 1000;
                r7.setDistance(radardistance);

                int angle8 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 20.9374f, 77.7796f);
                RadarPoint r8 = new RadarPoint(getBaseContext(), "Daisy", 50.2289897f, 8.5997901f, 5, angle8, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r8.x, r8.y);
                radardistance = (float) distance / 1000;
                r8.setDistance(radardistance);

                int angle9 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 24.118592f, 72.242053f);
                RadarPoint r9 = new RadarPoint(getBaseContext(), "Queen_S", 50.1470259f, 8.8155218f, 5, angle9, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r9.x, r9.y);
                radardistance = (float) distance / 1000;
                r9.setDistance(radardistance);

                int angle10 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r10 = new RadarPoint(getBaseContext(), "Chris", 49.8691908f, 8.6472559f, 5, angle10, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r10.x, r10.y);
                radardistance = (float) distance / 1000;
                r10.setDistance(radardistance);


                int angle11 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r11 = new RadarPoint(getBaseContext(), "Alex", 50.2672f, 8.2746f, 5, angle11, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r11.x, r11.y);
                radardistance = (float) distance / 1000;
                r11.setDistance(radardistance);


                int angle12 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r12 = new RadarPoint(getBaseContext(), "Feri", 50.030317f, 8.2789149f, 5, angle12, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r12.x, r12.y);
                radardistance = (float) distance / 1000;
                r12.setDistance(radardistance);

                int angle13 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r13 = new RadarPoint(getBaseContext(), "Demmrich_PT", 50.090848f, 8.2564688f, 5, angle12, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r13.x, r13.y);
                radardistance = (float) distance / 1000;
                r13.setDistance(radardistance);

                int angle14 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r14 = new RadarPoint(getBaseContext(), "Florian", 50.090848f, 8.2564688f, 5, angle12, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r14.x, r14.y);
                radardistance = (float) distance / 1000;
                r14.setDistance(radardistance);


                // meetups

                int angle15 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r15 = new RadarPoint(getBaseContext(), "Warm Up Workout on the WFD", 50.068620f, 8.645475f, 5, angle15, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r15.x, r15.y);
                radardistance = (float) distance / 1000;
                r15.setDistance(radardistance);

                int angle16 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r16 = new RadarPoint(getBaseContext(), "Blender Bottle on the WFD", 50.068620f, 8.645475f, 5, angle16, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r16.x, r16.y);
                radardistance = (float) distance / 1000;
                r16.setDistance(radardistance);

                int angle17 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r17 = new RadarPoint(getBaseContext(), "Elements - The Fitnessstudio of the Future WFD", 50.068620f, 8.645475f, 5, angle17, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r17.x, r17.y);
                radardistance = (float) distance / 1000;
                r17.setDistance(radardistance);


                int angle18 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r18 = new RadarPoint(getBaseContext(), "Fashionshow on the WFD", 50.068620f, 8.645475f, 5, angle18, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r18.x, r18.y);
                radardistance = (float) distance / 1000;
                r18.setDistance(radardistance);


                int angle19 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r19 = new RadarPoint(getBaseContext(), "Crossfitness Influnecer Battle on the WFD", 50.068620f, 8.645475f, 5, angle19, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r19.x, r19.y);
                radardistance = (float) distance / 1000;
                r19.setDistance(radardistance);


                int angle20 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r20 = new RadarPoint(getBaseContext(), "Scitec Nutriton Show WFD", 50.068620f, 8.645475f, 5, angle20, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r20.x, r20.y);
                radardistance = (float) distance / 1000;
                r20.setDistance(radardistance);


                int angle21 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r21 = new RadarPoint(getBaseContext(), "Fitness Gladiator Influencer Parkour on the WFD", 50.068620f, 8.645475f, 5, angle21, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r21.x, r21.y);
                radardistance = (float) distance / 1000;
                r21.setDistance(radardistance);


                int angle22 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r22 = new RadarPoint(getBaseContext(), "Training with the Rocka Athletes on the WFD", 50.068620f, 8.645475f, 5, angle22, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r22.x, r22.y);
                radardistance = (float) distance / 1000;
                r22.setDistance(radardistance);

                int angle23 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r23 = new RadarPoint(getBaseContext(), "Stage Stage Influencer Race on the WFD", 50.068620f, 8.645475f, 5, angleTemp, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r23.x, r23.y);
                radardistance = (float) distance / 1000;
                r1.setDistance(radardistance);


                int angle24 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.518447, 73.948554);
                RadarPoint r24 = new RadarPoint(getBaseContext(), "Fashionshow on the WFD", 50.068620f, 8.645475f, 5, angle24, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r24.x, r24.y);
                radardistance = (float) distance / 1000;
                r24.setDistance(radardistance);

                int angle25 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r25 = new RadarPoint(getBaseContext(), "Calisthenics big finals by Turnbar on the WFD", 50.068620f, 8.645475f, 5, angle25, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r25.x, r25.y);
                radardistance = (float) distance / 1000;
                r25.setDistance(radardistance);

                int angle26 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r26 = new RadarPoint(getBaseContext(), "Sophia Thiel at the WFD6", 50.068620f, 8.645475f, 5, angle26, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r26.x, r26.y);
                radardistance = (float) distance / 1000;
                r26.setDistance(radardistance);

                int angle27 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.469374, 73.982543);
                RadarPoint r27 = new RadarPoint(getBaseContext(), "The world's smallest gym the WFD", 50.068620f, 8.645475f, 5, angle27, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r27.x, r27.y);
                radardistance = (float) distance / 1000;
                r27.setDistance(radardistance);

                int angle28 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.7546, 73.4062);
                RadarPoint r28 = new RadarPoint(getBaseContext(), "Womens best influnecer on the WFD", 50.1153744f, 8.3349853f, 5, angle28, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r28.x, r28.y);
                radardistance = (float) distance / 1000;
                r28.setDistance(radardistance);

                int angle29 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.6546, 73.4062);
                RadarPoint r29 = new RadarPoint(getBaseContext(), "Fitness gladiators big finals on the WFD", 50.068620f, 8.645475f, 5, angle29, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r29.x, r29.y);
                radardistance = (float) distance / 1000;
                r29.setDistance(radardistance);

                int angle30 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 20.9374f, 77.7796f);
                RadarPoint r30 = new RadarPoint(getBaseContext(), "Smilodox Show with Flying Uwe and Team on the WFD", 50.068620f, 8.645475f, 5, angle30, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r30.x, r30.y);
                radardistance = (float) distance / 1000;
                r30.setDistance(radardistance);

                int angle31 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 24.118592f, 72.242053f);
                RadarPoint r31 = new RadarPoint(getBaseContext(), "Fashionshow on the WFD", 50.068620f, 8.645475f, 5, angle31, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r31.x, r31.y);
                radardistance = (float) distance / 1000;
                r31.setDistance(radardistance);

                int angle32 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r32 = new RadarPoint(getBaseContext(), "Obstacle Race Finals on the WFD", 50.068620f, 8.645475f, 5, angle32, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r32.x, r32.y);
                radardistance = (float) distance / 1000;
                r32.setDistance(radardistance);


                int angle33 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r33 = new RadarPoint(getBaseContext(), "Star Battles on the WFD", 50.068620f, 8.645475f, 5, angle33, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r33.x, r33.y);
                radardistance = (float) distance / 1000;
                r33.setDistance(radardistance);


                int angle34 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r34 = new RadarPoint(getBaseContext(), "Cross Fitness Big Finals on the WFD", 50.068620f, 8.645475f, 5, angle34, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r34.x, r34.y);
                radardistance = (float) distance / 1000;
                r34.setDistance(radardistance);

                int angle35 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r35 = new RadarPoint(getBaseContext(), "Opening Party of the WFD", 50.068620f, 8.645475f, 5, angle35, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r35.x, r35.y);
                radardistance = (float) distance / 1000;
                r35.setDistance(radardistance);

                int angle36 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r36 = new RadarPoint(getBaseContext(), "Closing Ceremony of the WFD", 50.068620f, 8.645475f, 5, angle36, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r36.x, r36.y);
                radardistance = (float) distance / 1000;
                r36.setDistance(radardistance);

                int angle37 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r37 = new RadarPoint(getBaseContext(), "Sunrise Yoga on the WFD", 50.070164f, 8.646286f, 5, angle37, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r37.x, r37.y);
                radardistance = (float) distance / 1000;
                r37.setDistance(radardistance);

                int angle38 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r38 = new RadarPoint(getBaseContext(), "Kangoo jumps on the WFD", 50.070164f, 8.646286f, 5, angle38, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r38.x, r38.y);
                radardistance = (float) distance / 1000;
                r38.setDistance(radardistance);

                int angle39 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r39 = new RadarPoint(getBaseContext(), "Fitness First on the WFD", 50.070164f, 8.646286f, 5, angle39, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r39.x, r39.y);
                radardistance = (float) distance / 1000;
                r39.setDistance(radardistance);


                int angle40 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r40 = new RadarPoint(getBaseContext(), "Les Mills Bodyattack on the WFD", 50.070164f, 8.646286f, 5, angle40, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r40.x, r40.y);
                radardistance = (float) distance / 1000;
                r40.setDistance(radardistance);


                int angle41 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r41 = new RadarPoint(getBaseContext(), "Jumping Fitness on the WFD", 50.070164f, 8.646286f, 5, angle41, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r41.x, r41.y);
                radardistance = (float) distance / 1000;
                r41.setDistance(radardistance);


                int angle42 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r42 = new RadarPoint(getBaseContext(), "Live casting workout on the WFD", 50.070164f, 8.646286f, 5, angle42, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r42.x, r42.y);
                radardistance = (float) distance / 1000;
                r42.setDistance(radardistance);


                int angle43 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r43 = new RadarPoint(getBaseContext(), "Les Mills Bodycombat on the WFD", 50.070164f, 8.646286f, 5, angle43, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r43.x, r43.y);
                radardistance = (float) distance / 1000;
                r43.setDistance(radardistance);


                int angle44 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r44 = new RadarPoint(getBaseContext(), "Sophia Thiel's World Record Workout at the WFD", 50.070164f, 8.646286f, 5, angle44, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r44.x, r44.y);
                radardistance = (float) distance / 1000;
                r44.setDistance(radardistance);

                int angle45 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r45 = new RadarPoint(getBaseContext(), "Zumba on the WFD", 50.070164f, 8.646286f, 5, angle45, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r45.x, r45.y);
                radardistance = (float) distance / 1000;
                r45.setDistance(radardistance);


                int angle46 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r46 = new RadarPoint(getBaseContext(), "Reebok Dance with José Martinez on the WFD", 50.070164f, 8.646286f, 5, angle46, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r46.x, r46.y);
                radardistance = (float) distance / 1000;
                r46.setDistance(radardistance);


                int angle47 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.518447, 73.948554);
                RadarPoint r47 = new RadarPoint(getBaseContext(), "Zumba get ready for party on the WFD", 550.070164f, 8.646286f, 5, angle47, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r47.x, r47.y);
                radardistance = (float) distance / 1000;
                r47.setDistance(radardistance);

                int angle48 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r48 = new RadarPoint(getBaseContext(), "Big Final Party of the WFD", 50.070164f, 8.646286f, 5, angle48, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r48.x, r48.y);
                radardistance = (float) distance / 1000;
                r48.setDistance(radardistance);

                int angle49 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r49 = new RadarPoint(getBaseContext(), "Center Stage Closing Ceremony of the WFD", 50.070164f, 8.646286f, 5, angle49, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r49.x, r49.y);
                radardistance = (float) distance / 1000;
                r49.setDistance(radardistance);

                int angle50 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.469374, 73.982543);
                RadarPoint r50 = new RadarPoint(getBaseContext(), "Fit4Couple - Train with the professionals on the WFD", 50.070742f, 8.646905f, 5, angle50, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r50.x, r50.y);
                radardistance = (float) distance / 1000;
                r50.setDistance(radardistance);

                int angle51 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.7546, 73.4062);
                RadarPoint r51 = new RadarPoint(getBaseContext(), "Top athlete & entrepreneur in talk on the WFD", 50.070742f, 8.646905f, 5, angle51, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r51.x, r51.y);
                radardistance = (float) distance / 1000;
                r51.setDistance(radardistance);

                int angle52 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.6546, 73.4062);
                RadarPoint r52 = new RadarPoint(getBaseContext(), "Influencer online shops on the WFD", 50.070742f, 8.646905f, 5, angle52, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r52.x, r52.y);
                radardistance = (float) distance / 1000;
                r52.setDistance(radardistance);

                int angle53 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 20.9374f, 77.7796f);
                RadarPoint r53 = new RadarPoint(getBaseContext(), "Influencer workout in the training park on the WFD", 50.070742f, 8.646905f, 5, angle53, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r53.x, r53.y);
                radardistance = (float) distance / 1000;
                r53.setDistance(radardistance);

                int angle54 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 24.118592f, 72.242053f);
                RadarPoint r54 = new RadarPoint(getBaseContext(), "Girls Talk Part 1 Q & A with your stars on the WFD", 50.070742f, 8.646905f, 5, angle54, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r54.x, r54.y);
                radardistance = (float) distance / 1000;
                r54.setDistance(radardistance);

                int angle55 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r55 = new RadarPoint(getBaseContext(), "Influencer workout in the training park on the WFD", 50.070742f, 8.646905f, 5, angle55, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r55.x, r55.y);
                radardistance = (float) distance / 1000;
                r55.setDistance(radardistance);


                int angle56 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r56 = new RadarPoint(getBaseContext(), "Relationship Relationship Q & A Fame Couples on the WFD", 50.070742f, 8.646905f, 5, angle56, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r56.x, r56.y);
                radardistance = (float) distance / 1000;
                r56.setDistance(radardistance);


                int angle57 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r57 = new RadarPoint(getBaseContext(), "Fit4Fun Q & A Workout on the WFD", 50.070742f, 8.646905f, 5, angle57, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r57.x, r57.y);
                radardistance = (float) distance / 1000;
                r57.setDistance(radardistance);

                int angle58 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r58 = new RadarPoint(getBaseContext(), "Women's best Ambassador Talk on the WFD", 50.070742f, 8.646905f, 5, angle58, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r58.x, r58.y);
                radardistance = (float) distance / 1000;
                r58.setDistance(radardistance);

                int angle59 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r59 = new RadarPoint(getBaseContext(), "Gannikus Round Table Q & A on the WFD", 50.070742f, 8.646905f, 5, angle59, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r59.x, r59.y);
                radardistance = (float) distance / 1000;
                r59.setDistance(radardistance);

                int angle60 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r60 = new RadarPoint(getBaseContext(), "Stage Girls Talk Part 2 Q & A with your stars on the WFD", 50.070742f, 8.646905f, 5, angle60, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r60.x, r60.y);
                radardistance = (float) distance / 1000;
                r60.setDistance(radardistance);

                int angle61 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r61 = new RadarPoint(getBaseContext(), "Powerful Happy End! Of the WFD", 50.070742f, 8.646905f, 5, angle61, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r61.x, r61.y);
                radardistance = (float) distance / 1000;
                r61.setDistance(radardistance);

                int angle62 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r62 = new RadarPoint(getBaseContext(), "Big final party of the WFD", 50.070742f, 8.646905f, 5, angle62, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r62.x, r62.y);
                radardistance = (float) distance / 1000;
                r62.setDistance(radardistance);


                int angle63 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r63 = new RadarPoint(getBaseContext(), "Closing Ceremony of the WFD", 50.070742f, 8.646905f, 5, angle63, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r63.x, r63.y);
                radardistance = (float) distance / 1000;
                r63.setDistance(radardistance);


                int angle64 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r64 = new RadarPoint(getBaseContext(), "Fitdankbaby on the WFD", 50.070923f, 8.647205f, 5, angle64, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r64.x, r64.y);
                radardistance = (float) distance / 1000;
                r64.setDistance(radardistance);


                int angle65 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r65 = new RadarPoint(getBaseContext(), "Painfree fascia on the WFD", 50.070923f, 8.647205f, 5, angle65, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r65.x, r65.y);
                radardistance = (float) distance / 1000;
                r65.setDistance(radardistance);


                int angle66 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r66 = new RadarPoint(getBaseContext(), "Slashpipe on the WFD", 50.070923f, 8.647205f, 5, angle66, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r66.x, r66.y);
                radardistance = (float) distance / 1000;
                r66.setDistance(radardistance);


                int angle67 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r67 = new RadarPoint(getBaseContext(), "Safs & Beta Yoga on the WFD", 50.070923f, 8.647205f, 5, angle67, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r67.x, r67.y);
                radardistance = (float) distance / 1000;
                r67.setDistance(radardistance);

                int angle68 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r68 = new RadarPoint(getBaseContext(), "Womens best Influencer Workout on the WFD", 50.070923f, 8.647205f, 5, angle68, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r68.x, r68.y);
                radardistance = (float) distance / 1000;
                r68.setDistance(radardistance);


                int angle69 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.518447, 73.948554);
                RadarPoint r69 = new RadarPoint(getBaseContext(), "Scotfit & Body Culture on the WFD", 50.070923f, 8.647205f, 5, angle69, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r2.x, r2.y);
                radardistance = (float) distance / 1000;
                r69.setDistance(radardistance);

                int angle70 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r70 = new RadarPoint(getBaseContext(), "Iron Cross on the WFD", 50.070923f, 8.647205f, 5, angle70, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r70.x, r70.y);
                radardistance = (float) distance / 1000;
                r70.setDistance(radardistance);

                int angle71 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r71 = new RadarPoint(getBaseContext(), "Animal Athletics on the WFD", 50.070923f, 8.647205f, 5, angle71, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r71.x, r71.y);
                radardistance = (float) distance / 1000;
                r71.setDistance(radardistance);

                int angle72 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.469374, 73.982543);
                RadarPoint r72 = new RadarPoint(getBaseContext(), "Active Stage Sophia Thiel World Record Workout at the WFD", 50.070923f, 8.647205f, 5, angle72, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r72.x, r72.y);
                radardistance = (float) distance / 1000;
                r72.setDistance(radardistance);

                int angle73 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.7546, 73.4062);
                RadarPoint r73 = new RadarPoint(getBaseContext(), "Bokwa team live on stage at the WFD", 50.070923f, 8.647205f, 5, angle73, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r73.x, r73.y);
                radardistance = (float) distance / 1000;
                r73.setDistance(radardistance);

                int angle74 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.6546, 73.4062);
                RadarPoint r74 = new RadarPoint(getBaseContext(), "Big Final Party of the WFD", 50.070923f, 8.647205f, 5, angle74, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r74.x, r74.y);
                radardistance = (float) distance / 1000;
                r74.setDistance(radardistance);

                int angle75 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 20.9374f, 77.7796f);
                RadarPoint r75 = new RadarPoint(getBaseContext(), "Closing Cermony of the WFD", 50.070923f, 8.647205f, 5, angle75, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r75.x, r75.y);
                radardistance = (float) distance / 1000;
                r75.setDistance(radardistance);

                int angle76 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 24.118592f, 72.242053f);
                RadarPoint r76 = new RadarPoint(getBaseContext(), "Freelatics Wiesbaden", 50.0707239f, 8.2553316f, 5, angle76, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r76.x, r76.y);
                radardistance = (float) distance / 1000;
                r76.setDistance(radardistance);

                int angle77 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r77 = new RadarPoint(getBaseContext(), "Training with Daniel", 50.0029966f, 8.259852f, 5, angle77, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r77.x, r77.y);
                radardistance = (float) distance / 1000;
                r77.setDistance(radardistance);


                int angle78 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r78 = new RadarPoint(getBaseContext(), "Training with rene", 50.0705977f, 8.2414235f, 5, angle78, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r78.x, r78.y);
                radardistance = (float) distance / 1000;
                r78.setDistance(radardistance);


                int angle79 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r79 = new RadarPoint(getBaseContext(), "Training with rene", 50.0797089f, 8.2438408f, 5, angle79, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r79.x, r79.y);
                radardistance = (float) distance / 1000;
                r79.setDistance(radardistance);

                int angle80 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r80 = new RadarPoint(getBaseContext(), "indoortraining with Demmrich_PT", 50.0782184f, 8.239760799999999f, 5, angle80, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r80.x, r80.y);
                radardistance = (float) distance / 1000;
                r80.setDistance(radardistance);

                int angle81 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r81 = new RadarPoint(getBaseContext(), "Training with GoodVibrationS ...", 50.177057f, 9.044541f, 5, angle81, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r81.x, r81.y);
                radardistance = (float) distance / 1000;
                r81.setDistance(radardistance);

                int angle82 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r82 = new RadarPoint(getBaseContext(), "Training with JoergOberlePT", 49.9863234f, 9.0718908f, 5, angle82, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r82.x, r82.y);
                radardistance = (float) distance / 1000;
                r82.setDistance(radardistance);

                int angle83 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r83 = new RadarPoint(getBaseContext(), "Functional training with JoergOberlePT", 49.986323399999996f, 9.0718908f, 5, angle83, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r83.x, r83.y);
                radardistance = (float) distance / 1000;
                r83.setDistance(radardistance);

                int angle84 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r84 = new RadarPoint(getBaseContext(), "indoortraining with Demmrich_PT", 50.0681734f, 8.195854299999999f, 5, angle84, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r84.x, r84.y);
                radardistance = (float) distance / 1000;
                r84.setDistance(radardistance);


                int angle85 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r85 = new RadarPoint(getBaseContext(), "Functional training with JoergOberlePT", 49.989253f, 9.1215188f, 5, angle85, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r85.x, r85.y);
                radardistance = (float) distance / 1000;
                r85.setDistance(radardistance);


                int angle86 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r86 = new RadarPoint(getBaseContext(), "Mamivit with JoergOberlePT", 49.965880999999996f, 9.166311f, 5, angle86, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r86.x, r86.y);
                radardistance = (float) distance / 1000;
                r86.setDistance(radardistance);


                int angle87 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r87 = new RadarPoint(getBaseContext(), "Mamivit with JoergOberlePT", 49.965880999999996f, 9.166311f, 5, angle87, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r87.x, r87.y);
                radardistance = (float) distance / 1000;
                r87.setDistance(radardistance);


                int angle88 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r88 = new RadarPoint(getBaseContext(), "FamilyVit with JoergOberlePT", 49.965880999999996f, 9.166311f, 5, angle88, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r88.x, r88.y);
                radardistance = (float) distance / 1000;
                r88.setDistance(radardistance);


                int angle89 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r89 = new RadarPoint(getBaseContext(), "Mamivit with JoergOberlePT", 49.843717299999994f, 9.1613228f, 5, angle89, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r89.x, r89.y);
                radardistance = (float) distance / 1000;
                r89.setDistance(radardistance);

                int angle90 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r90 = new RadarPoint(getBaseContext(), "Mamivit with JoergOberlePT", 49.9966879f, 9.264915799999999f, 5, angle90, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r90.x, r90.y);
                radardistance = (float) distance / 1000;
                r90.setDistance(radardistance);


                /*int angle91 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.518447, 73.948554);
                RadarPoint r91 = new RadarPoint(getBaseContext(), "91", 50.079744f, 8.244423f, 5, angle91, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r91.x, r91.y);
                radardistance = (float) distance / 1000;
                r91.setDistance(radardistance);

                int angle92 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r92 = new RadarPoint(getBaseContext(), "92", 50.07976f, 8.2437965f, 5, angle92, false, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r3.x, r3.y);
                radardistance = (float) distance / 1000;
                r3.setDistance(radardistance);

                int angle93 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.5193526,73.9473528);
                RadarPoint r93 = new RadarPoint(getBaseContext(), "93", 50.0684722f,8.6431724f, 5, angle93, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r93.x, r93.y);
                radardistance = (float) distance / 1000;
                r93.setDistance(radardistance);

                int angle94 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.469374, 73.982543);
                RadarPoint r94 = new RadarPoint(getBaseContext(), "94", 50.1153744f, 8.3349853f, 5, angle94, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r94.x, r94.y);
                radardistance = (float) distance / 1000;
                r94.setDistance(radardistance);

                int angle95 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.7546, 73.4062);
                RadarPoint r95 = new RadarPoint(getBaseContext(), "95", 50.1153744f, 8.3349853f, 5, angle95, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r95.x, r95.y);
                radardistance = (float) distance / 1000;
                r95.setDistance(radardistance);

                int angle96 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 18.6546, 73.4062);
                RadarPoint r96 = new RadarPoint(getBaseContext(), "96", 50.1153744f, 8.3349853f, 5, angle96, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r96.x, r96.y);
                radardistance = (float) distance / 1000;
                r96.setDistance(radardistance);

                int angle97 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 20.9374f, 77.7796f);
                RadarPoint r97 = new RadarPoint(getBaseContext(), "97", 50.1153744f, 8.3349853f, 5, angle97, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r97.x, r97.y);
                radardistance = (float) distance / 1000;
                r97.setDistance(radardistance);

                int angle98 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 24.118592f, 72.242053f);
                RadarPoint r98 = new RadarPoint(getBaseContext(), "98", 50.1153744f, 8.3349853f, 5, angle98, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r98.x, r98.y);
                radardistance = (float) distance / 1000;
                r98.setDistance(radardistance);

                int angle99 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r99 = new RadarPoint(getBaseContext(), "99", 50.1153744f, 8.3349853f, 5, angle99, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r99.x, r99.y);
                radardistance = (float) distance / 1000;
                r99.setDistance(radardistance);



                int angle100 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r100 = new RadarPoint(getBaseContext(), "100", 50.1153744f, 8.3349853f, 5, angle100, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r100.x, r100.y);
                radardistance = (float) distance / 1000;
                r100.setDistance(radardistance);


                int angle101 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r101 = new RadarPoint(getBaseContext(), "101", 50.0797089f, 8.2438408f, 5, angle101, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r101.x, r101.y);
                radardistance = (float) distance / 1000;
                r101.setDistance(radardistance);

                int angle102 = rand.nextInt(360) + 1;//(int) getBearing(reflat[0], reflat[1], 14.116289f, 72.240840f);
                RadarPoint r102 = new RadarPoint(getBaseContext(), "102", 50.0797089f, 8.2438408f, 5, angle102, true, false);
                distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x,
                        radarView.getRadar().getReferencePoint().y, r102.x, r102.y);
                radardistance = (float) distance / 1000;
                r102.setDistance(radardistance);

*/

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
                points.add(r11);
                points.add(r12);
                points.add(r13);
                points.add(r14);
                points.add(r15);
                points.add(r16);
                points.add(r17);
                points.add(r18);
                points.add(r19);
                points.add(r20);
                points.add(r21);
                points.add(r22);
                points.add(r23);
                points.add(r24);
                points.add(r25);
                points.add(r26);
                points.add(r27);
                points.add(r28);
                points.add(r29);
                points.add(r30);
                points.add(r31);
                points.add(r32);
                points.add(r33);
                points.add(r34);
                points.add(r35);
                points.add(r36);
                points.add(r37);
                points.add(r38);
                points.add(r39);
                points.add(r40);
                points.add(r41);
                points.add(r42);
                points.add(r43);
                points.add(r44);
                points.add(r45);
                points.add(r46);
                points.add(r47);
                points.add(r49);
                points.add(r50);
                points.add(r51);
                points.add(r52);
                points.add(r53);
                points.add(r54);
                points.add(r55);
                points.add(r56);
                points.add(r57);
                points.add(r58);
                points.add(r60);
                points.add(r61);
                points.add(r62);
                points.add(r63);
                points.add(r64);
                points.add(r65);
                points.add(r66);
                points.add(r67);
                points.add(r68);
                points.add(r69);
                points.add(r70);
                points.add(r71);
                points.add(r72);
                points.add(r73);
                points.add(r74);
                points.add(r75);
                points.add(r76);
                points.add(r77);
                points.add(r78);
                points.add(r79);
                points.add(r80);
                points.add(r81);
                points.add(r82);
                points.add(r83);
                points.add(r84);
                points.add(r85);
                points.add(r86);
                points.add(r87);
                points.add(r88);
                points.add(r89);
                points.add(r90);
               /*  points.add(r91);
                points.add(r92);
                points.add(r93);
                points.add(r94);
                points.add(r95);
                points.add(r96);
                points.add(r97);
                points.add(r98);
                points.add(r99);
                points.add(r100);
                points.add(r101);
                points.add(r102);*/


                for (RadarPoint rp : points) {

                    float refX = radarView.getRadar().getReferencePoint().x;
                    float refY = radarView.getRadar().getReferencePoint().y;

                    distance = RadarUtility.distanceBetween(refX, refY, rp.x, rp.y);
                    if (distance > maxDistance) {
                        maxDistance = distance / 1000;
                    }
                    rp.setDistance((float) distance);
                }
                //radarView.setMaxDistance((int) maxDistance);
                int distance_in_km = (int) Math.ceil(maxDistance) * 1000;

                radarView.getRadar().setMaxDistance(distance_in_km);

                radarView.getRadar().setScaleFactor(1);
                radarView.setPoints(points);

                mSeekbar.setMax(distance_in_km);
                mSeekbar.setProgress(distance_in_km / 2);
            }
        }, 1000);
        //radarView.checkComplete();
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
