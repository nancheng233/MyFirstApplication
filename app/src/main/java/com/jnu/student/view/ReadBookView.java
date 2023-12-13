package com.jnu.student.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import com.jnu.student.myclass.Spriter;

import java.util.ArrayList;

public class ReadBookView extends SurfaceView
        implements SurfaceHolder.Callback, View.OnTouchListener {
    private int count = 0;
    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread = null;
    private ArrayList<Spriter> mySpriterArrayList =new ArrayList<>();
    public ReadBookView(Context context) {
        super(context);
        setClickable(true);
        this.setOnTouchListener(this);
        initView();
    }

    public ReadBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        this.setOnTouchListener(this);
        initView();
    }

    public ReadBookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        this.setOnTouchListener(this);
        initView();
    }

    public ReadBookView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setClickable(true);
        this.setOnTouchListener(this);
        initView();
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    private void initView()
    {
        // 初始化View时的操作
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);
        // 添加图书
        for (int i = 0; i < 3; i++)
        {
            mySpriterArrayList.add(new Spriter(getContext()));
        }
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        // 创建surface时的操作
        drawThread = new DrawThread();
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        // 删除View时的操作
        drawThread.stopThread();
    }

    // 实现监听功能
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            for (Spriter mySpriter : mySpriterArrayList) {
                if (mySpriter.isPointInside(x, y)) {
                    // 执行你的操作
                    count += 1;
                    mySpriter.setX((int) (view.getWidth() * Math.random()));
                    mySpriter.setY((int) (view.getHeight() * Math.random()));
                    break;
                }
            }
        }
        return true;
    }

    // 同时覆盖performClick方法，以确保辅助功能用户也可以与你的视图交互。
    @Override
    public boolean performClick() {
        // 调用父类的performClick方法
        return super.performClick();
    }

    class DrawThread extends Thread {
        private boolean isDrawing=true;

        public void stopThread()
        {
            // 停止线程时候的操作
            isDrawing=false;
            try {
                // join方法，该方法是Thread类的一个方法。它会导致当前运行的线程等待，直到调用join方法的线程结束为止。
                join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            // 线程进行时的操作
            while(isDrawing)
            {
                Canvas canvas =null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.GRAY);
                    for (Spriter mySpriter : mySpriterArrayList) {
                        mySpriter.move(canvas.getHeight(), canvas.getWidth());
                    }
                    for (Spriter mySpriter : mySpriterArrayList) {
                        mySpriter.draw(canvas);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    if(null!=canvas)surfaceHolder.unlockCanvasAndPost(canvas);
                }

                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //drawing
            }

        }
    }
}