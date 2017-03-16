package it.smasini.radar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.location.Location;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Simone Masini on 12/10/2016.
 */
public class Radar extends View {
    private ArrayList<RadarPoint> pinsInCanvas = new ArrayList<RadarPoint>();
    private Context context;
    private Canvas canvas;
    private int zoomDistance;
    private int scaleFactor;
    private boolean showRadarAnimation = false;
    public void setPoints(ArrayList<RadarPoint> points) {
        this.points = points;
        invalidate();
    }
    public ArrayList<RadarPoint> getPoints() {
        return points;
    }
    private ArrayList<RadarPoint> points = new ArrayList<RadarPoint>();
    private RadarPoint referencePoint;
    private final int DEFAULT_MAX_DISTANCE = 1000;
    private final int DEFAULT_SCALE_FACTOR = 1;
    private final int DEFAULT_PINS_RADIUS = 5;
    private final int DEFAULT_CENTER_PIN_RADIUS = 18;
    private final int DEFAULT_PINS_COLORS = Color.GREEN;
    private final int DEFAULT_CENTER_PIN_COLOR = Color.RED;
    private final int DEFAULT_BACKGROUND_COLOR = Color.CYAN;
    private int pinsImage;
    private int centerPinImage;
    private int maxDistance;
    private int pinsRadius;
    private int centerPinRadius;
    private int pinsColor;
    private int centerPinColor;
    private int backgroundColor;
    private int arrowColor;
    float alpha = 0;
    private int fps = 50;
    private final int POINT_ARRAY_SIZE = 25;
    Point latestPoint[] = new Point[POINT_ARRAY_SIZE];
    Paint latestPaint[] = new Paint[POINT_ARRAY_SIZE];
    private Paint mPaintLine;//画圆线需要用到的paint
    private Paint mPaintCircle;//画圆需要用到的paint
    private Paint mPaintScan;//画扫描需要用到的paint
    private int mWidth, mHeight;//整个图形的长度和宽度
    private Matrix matrix = new Matrix();//旋转需要的矩阵
    private int scanAngle;//扫描旋转的角度
    private Shader scanShader;//扫描渲染shader
    private Bitmap centerBitmap;//最中间icon
    //每个圆圈所占的比例
    private static float[] circleProportion = {1 / 13f, 2 / 13f, 3 / 13f, 4 / 13f, 5 / 13f, 6 / 13f};
    private int scanSpeed = 5;
    private int currentScanningCount;//当前扫描的次数
    private int currentScanningItem;//当前扫描显示的item
    private int maxScanItemCount;//最大扫描次数
    private boolean startScan = true;//只有设置了数据后才会开始扫描
    private IScanningListener iScanningListener;//扫描时监听回调接口
    public void setScanningListener(IScanningListener iScanningListener) {
        this.iScanningListener = iScanningListener;
    }
    public interface IScanningListener {
        //正在扫描（此时还没有扫描完毕）时回调
        void onScanning(int position, float scanAngle);
        //扫描成功时回调
        void onScanSuccess();
    }
    public Radar(Context context) {
        this(context, null);
        init(context, null);
    }
    public Radar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }
    public Radar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        setFocusableInTouchMode(true);
        setClickable(true);
        setFocusable(true);
        requestFocus();
        setPaint(DEFAULT_BACKGROUND_COLOR);
        referencePoint = new RadarPoint("example", 10.00000f, 22.0000f, 5, 0, false, false);
    }
    public void setPaint(int color) {
        Paint localPaint = new Paint();
        localPaint.setColor(color);
        localPaint.setAntiAlias(true);
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setStrokeWidth(1.0F);
        localPaint.setAlpha(0);
        int alpha_step = 255 / POINT_ARRAY_SIZE;
        for (int i = 0; i < latestPaint.length; i++) {
            latestPaint[i] = new Paint(localPaint);
            latestPaint[i].setAlpha(255 - (i * alpha_step));
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        makeRadar();
        init();
        drawCircle(canvas);
        drawScan(canvas);
       // drawCenterIcon(canvas);
        post(run);
    }
    private void drawCenterIcon(Canvas canvas) {
        canvas.drawBitmap(centerBitmap, null,
                new Rect((int) (mWidth / 2 - mWidth * circleProportion[0]), (int) (mHeight / 2 - mWidth * circleProportion[0]),
                        (int) (mWidth / 2 + mWidth * circleProportion[0]), (int) (mHeight / 2 + mWidth * circleProportion[0])), mPaintCircle);
    }
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            scanAngle = (scanAngle + scanSpeed) % 360;
            matrix.postRotate(scanSpeed, mWidth / 2, mHeight / 2);
            invalidate();
            //  postDelayed(run, 130);
            //开始扫描显示标志为true 且 只扫描一圈
            if (startScan && currentScanningCount <= (360 / scanSpeed)) {
                if (iScanningListener != null && currentScanningCount % scanSpeed == 0
                        && currentScanningItem < maxScanItemCount) {
                    iScanningListener.onScanning(currentScanningItem, scanAngle);
                    currentScanningItem++;
                } else if (iScanningListener != null && currentScanningItem == maxScanItemCount) {
                    iScanningListener.onScanSuccess();
                }
                currentScanningCount++;
            }
        }
    };
    private void drawScan(Canvas canvas) {
        canvas.save();
        mPaintScan.setShader(scanShader);
        canvas.concat(matrix);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * circleProportion[5], mPaintScan);
        canvas.restore();
    }
    private void init() {
        mPaintLine = new Paint();
        mPaintLine.setColor(Color.BLUE);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(1);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.WHITE);
        mPaintCircle.setAntiAlias(true);
        mPaintScan = new Paint();
        mPaintScan.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    private int measureSize(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 600;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return specSize;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(widthMeasureSpec));
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mWidth = mHeight = Math.min(mWidth, mHeight);
        centerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaultavatarnew);
        //设置扫描渲染的shader
        scanShader = new SweepGradient(mWidth / 2, mHeight / 2,
                new int[]{Color.TRANSPARENT, Color.parseColor("#84B5CA")}, null);
    }
    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * circleProportion[1], mPaintLine);     // 绘制小圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * circleProportion[2], mPaintLine);   // 绘制中圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * circleProportion[3], mPaintLine); // 绘制中大圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * circleProportion[4], mPaintLine);  // 绘制大圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth * circleProportion[5], mPaintLine);  // 绘制大大圆
    }
    public void refresh() {
        invalidate();
    }
    protected void makeRadar() {
        pinsInCanvas = new ArrayList<RadarPoint>();
        int width = getWidth();
        drawStroke(width / 2, width / 2, getBackgroundColor(), width / 2);
        if (centerPinImage != 0) {
            long pnt = (width / 2) - getCenterPinRadius();
            drawImage(pnt, pnt, centerPinImage, getCenterPinRadius() * 2);
        } else {
            drawPin(width / 2, width / 2, getCenterPinRadius(), false, false, false);
        }
        int pxCanvas = width / 2;
        int metterDistance;
        maxDistance = getMaxDistance();
        Location u0 = new Location("");
        u0.setLatitude(referencePoint.x);
        u0.setLongitude(referencePoint.y);
        ArrayList<Location> locations = buildLocations(u0);
        metterDistance = zoomDistance + (zoomDistance / 16);
        if (metterDistance > maxDistance) metterDistance = maxDistance;
        if (showRadarAnimation) {
            ///drawLine();
        } else {
            drawPins(u0, locations, pxCanvas, metterDistance);
        }
    }
    ArrayList<Location> buildLocations(Location referenceLocation) {
        zoomDistance = 0;
        ArrayList<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < points.size(); i++) {
            Location uLocation = new Location("");
            uLocation.setLatitude(points.get(i).x);
            uLocation.setLongitude(points.get(i).y);
            locations.add(uLocation);
            if (zoomDistance < distanceBetween(referenceLocation, uLocation)) {
                zoomDistance = Math.round(distanceBetween(referenceLocation, uLocation));
            }
        }
        return locations;
    }
    void drawPins(Location referenceLocation, ArrayList<Location> locations, int pxCanvas, int metterDistance) {
        Random rand = new Random();
        for (int i = 0; i < locations.size(); i++) {
            int distance = Math.round(distanceBetween(referenceLocation, locations.get(i)));
            if (distance > maxDistance) continue;
            int virtualDistance = (distance * pxCanvas / metterDistance);
            int angle = rand.nextInt(360) + 1;
            long cX = pxCanvas + Math.round(virtualDistance * Math.cos(points.get(i).angle * Math.PI / 180));
            long cY = pxCanvas + Math.round(virtualDistance * Math.sin(points.get(i).angle * Math.PI / 180));
            RadarPoint radarPoint = new RadarPoint(points.get(i).identifier, cX + scaleFactor, cY + scaleFactor, 20, points.get(i).angle, points.get(i).IsMeetup, points.get(i).IsSpecial);
            //   double distance = RadarUtility.distanceBetween(radarView.getRadar().getReferencePoint().x, radarView.getRadar().getReferencePoint().y, r1.x, r1.y);
            radarPoint.setDistance((float) distance);
            pinsInCanvas.add(radarPoint);
            if (pinsImage != 0) {
                long pnt = cX - getPinsRadius();
                long pnt2 = cY - getPinsRadius();
                drawImage(pnt, pnt2, pinsImage, getPinsRadius() * 2);
            } else {
                /*RadarPoint rp = points.get(i);
                if(!rp.isBitmapLoadedError() && rp.getBitmap() != null) {
                    long pnt = cX - getPinsRadius();
                    long pnt2 = cY - getPinsRadius();
                    drawImage(pnt, pnt2, points.get(i).getBitmap(), getPinsRadius());
                }else{

                }*/
                drawPin(cX + scaleFactor, cY + scaleFactor, 20, points.get(i).isSelected, points.get(i).IsMeetup, points.get(i).IsSpecial);
            }
        }
    }
    float distanceBetween(Location l1, Location l2) {
        float lat1 = (float) l1.getLatitude();
        float lon1 = (float) l1.getLongitude();
        float lat2 = (float) l2.getLatitude();
        float lon2 = (float) l2.getLongitude();
        return RadarUtility.distanceBetween(lat1, lon1, lat2, lon2);
    }
    public void drawImage(long x, long y, Bitmap bitmap, int size) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
        canvas.drawBitmap(scaledBitmap, x, y, null);
    }
    public void drawLine() {
        int width = getWidth();
        int height = getHeight();
        int r = Math.min(width, height);
        int i = r / 2;
        int j = i - 1;
        Paint localPaint = latestPaint[0]; // GREEN
        alpha -= 0.5;
        if (alpha < -360) alpha = 0;
        double angle = Math.toRadians(alpha);
        int offsetX = (int) (i + (float) (i * Math.cos(angle)));
        int offsetY = (int) (i - (float) (i * Math.sin(angle)));
        latestPoint[0] = new Point(offsetX, offsetY);
        for (int x = POINT_ARRAY_SIZE - 1; x > 0; x--) {
            latestPoint[x] = latestPoint[x - 1];
        }
        int lines = 0;
        for (int x = 0; x < POINT_ARRAY_SIZE; x++) {
            Point point = latestPoint[x];
            if (point != null) {
                canvas.drawLine(i, i, point.x, point.y, latestPaint[x]);
            }
        }
        lines = 0;
        for (Point p : latestPoint) if (p != null) lines++;
    }
    public void drawImage(long x, long y, int image, int size) {
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), image);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(myBitmap, size, size, true);
        canvas.drawBitmap(scaledBitmap, x, y, null);
    }
    public void drawStroke(long x, long y, int Color, int radius) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10.0F);
        paint.setColor(Color);
        canvas.drawCircle(x, y, radius, paint);
    }
    public void drawPin(long x, long y, int radius, boolean isSelected, boolean Ismeetup, boolean IsSpecial) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);
        if (Ismeetup) {
            paint.setColor(Color.BLUE);
            paint1.setColor(Color.BLUE);
            canvas.drawCircle(x, y, radius, paint);
            if (isSelected) {
                canvas.drawCircle(x, y, radius + 15, paint1);
            }
        } else {
            paint.setColor(Color.RED);
            paint1.setColor(Color.RED);
            canvas.drawCircle(x, y, radius, paint);
            if (isSelected) {
                canvas.drawCircle(x, y, radius + 15, paint1);
            }
        }
    }
    public ArrayList<RadarPoint> getTouchedPin(MotionEvent event) {
        int xTouch;
        int yTouch;
        ArrayList<RadarPoint> tempTouchedCircle = new ArrayList<RadarPoint>();
        // get touch event coordinates and make transparent circle from it
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // it's the first pointer, so clear all existing pointers data
                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);
                // check if we've touched inside some circle
                tempTouchedCircle = getTouchedCircle(xTouch, yTouch);
                for (RadarPoint touchPoint : tempTouchedCircle) {
                    for (RadarPoint rPoint : points) {
                        if (!rPoint.isVisited) {
                            if (rPoint.identifier.equals(touchPoint.identifier) && rPoint.angle == touchPoint.angle) {
                                rPoint.isSelected = true;
                                rPoint.isVisited = true;
                            } else {
                                rPoint.isSelected = false;
                            }
                        }
                    }
                }
                this.invalidate();
                return tempTouchedCircle;
        }
        return null;
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
    private ArrayList<RadarPoint> getTouchedCircle(final int xTouch, final int yTouch) {
        ArrayList<RadarPoint> touched = new ArrayList<>();
        int i = 0;
        inValidateAll();
        for (RadarPoint rPoint : pinsInCanvas) {
            if ((rPoint.x - xTouch) * (rPoint.x - xTouch) + (rPoint.y - yTouch) * (rPoint.y - yTouch) <= rPoint.radius * rPoint.radius * (rPoint.radius / 2)) {
                {
                    rPoint.isVisited = false;
                    rPoint.isSelected = true;
                    touched.add(rPoint);
                }
            }
            i++;
        }
        invalidate();
        if (touched.size() > 0) return touched;
        else {
            inValidateAll();
            return null;
        }
    }
    private void inValidateAll() {
        for (RadarPoint tPoint : points) {
            tPoint.isVisited = false;
            tPoint.isSelected = false;
        }
        invalidate();
    }
    public int getPinsRadius() {
        if (pinsRadius == 0) return DEFAULT_PINS_RADIUS;
        return pinsRadius;
    }
    public int getPinsColor() {
        if (pinsColor == 0) return DEFAULT_PINS_COLORS;
        return pinsColor;
    }
    public int getCenterPinRadius() {
        if (centerPinRadius == 0) return DEFAULT_CENTER_PIN_RADIUS;
        return centerPinRadius;
    }
    public int getCenterPinColor() {
        if (centerPinColor == 0) return DEFAULT_CENTER_PIN_COLOR;
        return centerPinColor;
    }
    public void setPinsColor(int pinsColor) {
        this.pinsColor = pinsColor;
    }
    public void setCenterPinColor(int centerPinColor) {
        this.centerPinColor = centerPinColor;
    }
    public int getBackgroundColor() {
        if (backgroundColor == 0) return DEFAULT_BACKGROUND_COLOR;
        return backgroundColor;
    }
    public void setPinsImage(int pinsImage) {
        this.pinsImage = pinsImage;
    }
    public void setCenterPinImage(int centerPinImage) {
        this.centerPinImage = centerPinImage;
    }
    public void setPinsRadius(int pinsRadius) {
        this.pinsRadius = pinsRadius;
    }
    public void setArrowColor(int arrowColor) {
        this.arrowColor = arrowColor;
        setPaint(getArrowColor());
    }
    public int getArrowColor() {
        if (arrowColor == 0) return DEFAULT_BACKGROUND_COLOR;
        return arrowColor;
    }
    public void setCenterPinRadius(int centerPinRadius) {
        this.centerPinRadius = centerPinRadius;
    }
    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
    public int getMaxDistance() {
        if (maxDistance == 0) return DEFAULT_MAX_DISTANCE;
        if (maxDistance < 0) return 1000;
        return maxDistance;
    }
    public void setScaleFactor(int scaleFactor) {
        this.scaleFactor = maxDistance;
    }
    public int getScaleFactor() {
        if (scaleFactor == 0) return DEFAULT_SCALE_FACTOR;
        if (scaleFactor < 0) return 1;
        return scaleFactor;
    }
    public void setReferencePoint(RadarPoint referencePoint) {
        this.referencePoint = referencePoint;
    }
    android.os.Handler mHandler = new android.os.Handler();
    Runnable mTick = new Runnable() {
        @Override
        public void run() {
            invalidate();
            mHandler.postDelayed(this, 100 / fps);
        }
    };
    public void startAnimation() {
        showRadarAnimation = true;
        mHandler.removeCallbacks(mTick);
        mHandler.post(mTick);
    }
    public void stopAnimation() {
        showRadarAnimation = false;
        mHandler.removeCallbacks(mTick);
    }
    public RadarPoint getReferencePoint() {
        return referencePoint;
    }
}
