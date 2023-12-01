package com.jnu.student.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jnu.student.R;

import java.util.Calendar;

public class ClockView extends View {
    private Bitmap mHourHand;
    private Bitmap mMinuteHand;
    private Bitmap mSecondHand;
    private Bitmap mClockFace;

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 加载图片资源
        mHourHand = BitmapFactory.decodeResource(getResources(), R.drawable.shi_zhen);
        mMinuteHand = BitmapFactory.decodeResource(getResources(), R.drawable.fen_zhen);
        mSecondHand = BitmapFactory.decodeResource(getResources(), R.drawable.miao_zhen);
        mClockFace = BitmapFactory.decodeResource(getResources(), R.drawable.shizhong_biaopan);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        // 计算每个指针的角度和位置，后面常数是对指针图像的修正
        float hourAngle = hours * 30 + minutes * 0.5f + 133;
        float minuteAngle = minutes * 6 + seconds * 0.1f + 131;
        float secondAngle = seconds * 6 + 130;
        int newWidth;
        int newHeight;

        // 绘制时钟背景
        newWidth = (getWidth() - mClockFace.getWidth()) / 2;
        newHeight = (getHeight() - mClockFace.getHeight()) / 2;
        canvas.drawBitmap(mClockFace, newWidth, newHeight, null);

        /*
        canvas.save();：这个方法用于保存当前的画布状态。画布状态包括当前的变换矩阵（用于缩放、旋转等操作）和剪切区域。
                       保存画布状态后，你可以自由地对画布进行操作，而不用担心影响后续的绘制操作。
        canvas.rotate();：这个方法用于旋转画布。你需要传入一个角度值（以度为单位），画布会围绕其原点进行旋转。
                         旋转操作会影响后续的所有绘制操作。
        canvas.restore();：这个方法用于恢复最近保存的画布状态。当你完成了一系列的绘制操作后，
                          你可以调用这个方法来恢复画布状态，这样就不会影响后续的绘制操作。
        */
        // 绘制时针
        newWidth = getWidth() / 2 - mHourHand.getWidth();
        newHeight = getHeight() / 2;
        canvas.save();
        canvas.rotate(hourAngle, getWidth() / 2.0f, getHeight() / 2.0f);
        canvas.drawBitmap(mHourHand, newWidth * 1.05f, newHeight * 0.98f, null);
        canvas.restore();

        // 绘制分针
        newWidth = getWidth() / 2 - mMinuteHand.getWidth();
        newHeight = getHeight() / 2;
        canvas.save();
        canvas.rotate(minuteAngle, getWidth() / 2.0f, getHeight() / 2.0f);
        canvas.drawBitmap(mMinuteHand, newWidth * 1.1f, newHeight * 0.98f, null);
        canvas.restore();

        // 绘制秒针
        newWidth = getWidth() / 2 - mSecondHand.getWidth();
        newHeight = getHeight() / 2;
        canvas.save();
        canvas.rotate(secondAngle, getWidth() / 2.0f, getHeight() / 2.0f);
        canvas.drawBitmap(mSecondHand, newWidth * 1.04f, newHeight * 0.99f, null);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        super.onSizeChanged(w, h, old_w, old_h);

        // 缩放位图
        mHourHand = scaleAndCenterBitmap(mHourHand, w, h, 0.25f);
        mMinuteHand = scaleAndCenterBitmap(mMinuteHand, w, h, 0.32f);
        mSecondHand = scaleAndCenterBitmap(mSecondHand, w, h, 0.36f);
        mClockFace = scaleAndCenterBitmap(mClockFace, w, h, 1.0f);
    }


    private Bitmap scaleAndCenterBitmap(Bitmap bitmap, int viewWidth, int viewHeight, float b) {
        // 依据传入的宽和高，计算缩放比例
        float scale = Math.min((float) viewWidth / bitmap.getWidth(), (float) viewHeight / bitmap.getHeight()) * b;

        // 创建一个新的矩阵
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // 使用新的矩阵创建一个新的位图
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    private final Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();  // 强制重绘视图
            mHandler.postDelayed(this, 1000);  // 每秒执行一次
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.post(mRunnable);  // 当视图附加到窗口时，开始执行
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(mRunnable);  // 当视图从窗口分离时，停止执行
    }

}
