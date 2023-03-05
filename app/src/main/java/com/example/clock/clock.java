package com.example.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class clock extends View {
    private Paint mPaint;
    int hour = 12;
    int min = 30;
    int sec = 30;

    public clock(Context context) {
        super(context);
        init();
    }

    public clock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.grey));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(36);
        mPaint.setStrokeWidth(20);
    }

    private float angle2radian(double angle){
        return (float) (angle*Math.PI/180);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        float R = canvas.getWidth()/2-100;
        RectF myrect = new RectF(-R,-R,R,R);
        canvas.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        canvas.save();//保存中心位置
        canvas.translate(5,5);
        canvas.save();//保存阴影偏移位置
        canvas.drawArc(myrect,0,360,false,mPaint);//圆周

        Paint tempPaint = new Paint(mPaint);//line
        Paint tempPaint2 = new Paint(mPaint);//number

        Paint tempPaint3 = new Paint(mPaint);//number

        tempPaint.setStrokeWidth(10);
        tempPaint2.setStrokeWidth(2);
        tempPaint2.setTextSize(48);
        tempPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        tempPaint2.setARGB(200, 133, 133, 133);
        tempPaint3.setStrokeWidth(10);

        int y = 1;
        Path path = new Path();
        path.addArc(new RectF(-(R+40),-(R+40),(R+40),(R+40)),(float)-63.5,300);
        for(int i=0; i < 12; i++)
        {
            canvas.drawLine((float)(-R*Math.cos(Math.PI/6)),-R/2,
                    (float)(-(R-40)*Math.cos(Math.PI/6)),-(R-40)/2,tempPaint);

            canvas.rotate(30,0f,0f);
        }
        canvas.restore();//返回阴影偏移位置
        canvas.save();//保存阴影偏移位置
        canvas.translate(-13,17);
        double angle = -60;
        for (int i=0;i < 9; i++){
            canvas.drawText(""+y,(float) ((R+40)*Math.cos(angle2radian(angle))),
                    (float) ((R+40)*Math.sin(angle2radian(angle))),tempPaint2);
            angle = angle + 30;
            y = y + 1;
        }
        canvas.translate(-20,0);
        for (int i=0;i < 3; i++){
            canvas.drawText(""+y,(float) ((R+40)*Math.cos(angle2radian(angle))),
                    (float) ((R+40)*Math.sin(angle2radian(angle))),tempPaint2);
            angle = angle + 30;
            y = y + 1;
        }
        canvas.restore();//返回阴影偏移位置

        canvas.drawLine((float)(15*Math.cos(angle2radian(sec*6-90))),
                (float)(15*Math.sin(angle2radian(sec*6-90))),
                (float)(350*Math.cos(angle2radian(sec*6-90))),
                (float)(350*Math.sin(angle2radian(sec*6-90))),tempPaint3);

        canvas.drawLine((float)(15*Math.cos(angle2radian(min*6-90+sec*0.1))),
                (float)(15*Math.sin(angle2radian(min*6-90+sec*0.1))),
                (float)(250*Math.cos(angle2radian(min*6-90+sec*0.1))),
                (float)(250*Math.sin(angle2radian(min*6-90+sec*0.1))),tempPaint3);

        canvas.drawLine((float)(15*Math.cos(angle2radian(hour*30-90+(min*60+sec)/120))),
                (float)(15*Math.sin(angle2radian(hour*30-90+(min*60+sec)/120))),
                (float)(150*Math.cos(angle2radian(hour*30-90+(min*60+sec)/120))),
                (float)(150*Math.sin(angle2radian(hour*30-90+(min*60+sec)/120))),tempPaint3);

        canvas.drawCircle(0f,0f,15,tempPaint);

        canvas.restore();//返回中心位置


        Paint tempPaint4 = new Paint(mPaint);//line
        Paint tempPaint5 = new Paint(mPaint);//number

        Paint tempPaint6 = new Paint(mPaint);//number

        tempPaint4.setStrokeWidth(20);
        tempPaint5.setStrokeWidth(2);
        tempPaint5.setTextSize(48);
        tempPaint5.setStyle(Paint.Style.FILL_AND_STROKE);
        tempPaint6.setStrokeWidth(10);

        tempPaint4.setARGB(255,122, 19, 255);
        tempPaint5.setARGB(255,122, 19, 255);

        canvas.drawArc(myrect,0,360,false,tempPaint4);
        canvas.save();//保存中心位置
        tempPaint4.setStrokeWidth(10);
        for(int i=0; i < 12; i++)
        {
            canvas.drawLine((float)(-R*Math.cos(Math.PI/6)),-R/2,
                    (float)(-(R-40)*Math.cos(Math.PI/6)),-(R-40)/2,tempPaint4);

            canvas.rotate(30,0f,0f);
        }
        canvas.restore();//返回中心位置
        canvas.save();//保存中心位置
        canvas.translate(-13,17);
        y = 1;
        angle = -60;
        for (int i=0;i < 9; i++){
            canvas.drawText(""+y,(float) ((R+40)*Math.cos(angle2radian(angle))),
                    (float) ((R+40)*Math.sin(angle2radian(angle))),tempPaint5);
            angle = angle + 30;
            y = y + 1;
        }
        canvas.translate(-20,0);
        for (int i=0;i < 3; i++){
            canvas.drawText(""+y,(float) ((R+40)*Math.cos(angle2radian(angle))),
                    (float) ((R+40)*Math.sin(angle2radian(angle))),tempPaint5);
            angle = angle + 30;
            y = y + 1;
        }
        canvas.restore();//返回中心位置

        tempPaint6.setARGB(255,0, 202, 248);
        canvas.drawLine((float)(15*Math.cos(angle2radian(sec*6-90))),
                (float)(15*Math.sin(angle2radian(sec*6-90))),
                (float)(350*Math.cos(angle2radian(sec*6-90))),
                (float)(350*Math.sin(angle2radian(sec*6-90))),tempPaint6);

        tempPaint6.setARGB(255,89, 113, 253);
        canvas.drawLine((float)(15*Math.cos(angle2radian(min*6-90+sec*0.1))),
                (float)(15*Math.sin(angle2radian(min*6-90+sec*0.1))),
                (float)(250*Math.cos(angle2radian(min*6-90+sec*0.1))),
                (float)(250*Math.sin(angle2radian(min*6-90+sec*0.1))),tempPaint6);

        tempPaint6.setARGB(255,182, 64, 254);
        canvas.drawLine((float)(15*Math.cos(angle2radian(hour*30-90+(min*60+sec)/120))),
                (float)(15*Math.sin(angle2radian(hour*30-90+(min*60+sec)/120))),
                (float)(150*Math.cos(angle2radian(hour*30-90+(min*60+sec)/120))),
                (float)(150*Math.sin(angle2radian(hour*30-90+(min*60+sec)/120))),tempPaint6);

        canvas.drawCircle(0f,0f,15,tempPaint4);
    }
}

