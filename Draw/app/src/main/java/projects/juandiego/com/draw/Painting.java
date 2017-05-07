package projects.juandiego.com.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by Juan Diego on 29/11/2016.
 */

public class Painting extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = 0xFFFF0000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float[] sizesPoint = {10,20,30};
    private float sizePoint;
    private boolean erase;

    public Painting(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing(){
        erase = false;
        drawPath = new Path();
        drawPaint = new Paint();
        setSizePoint(10);
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(sizePoint);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap,0,0,canvasPaint);
        canvas.drawPath(drawPath,drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(x,y);
                drawCanvas.drawPath(drawPath,drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void setSizePoint(float newSize){
        sizePoint = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,newSize,
                getResources().getDisplayMetrics());
        drawPaint.setStrokeWidth(sizePoint);
    }

    public float getSizePoint(int i){
        return sizesPoint[i];
    }

    public void setErase(boolean modo){
        erase = modo;
        if (erase)
            drawPaint.setColor(Color.WHITE);
            //drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else
            drawPaint.setColor(paintColor);
            //drawPaint.setXfermode(null);
    }

    public void newDraw(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void saveDraw(){
        setDrawingCacheEnabled(true);
        String imagenSaved = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),getDrawingCache(),
                UUID.randomUUID().toString()+".png","drawing");
        if (imagenSaved != null){
            Toast.makeText(getContext(),getContext().getString(R.string.image_saved).toString(),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),getContext().getString(R.string.error_saving).toString(),Toast.LENGTH_SHORT).show();
        }
        destroyDrawingCache();
    }
}
