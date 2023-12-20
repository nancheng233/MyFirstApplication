package com.jnu.student.myclass;

import com.jnu.student.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Spriter {
    float x,y;
    int drawableResourceId;
    float direction;
    Context context;
    Bitmap bitmap;

    public Spriter(Context context)
    {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.book_shi_ji);
        // 依据传入的宽和高，计算缩放比例
        float scale = Math.min((float) 112 / bitmap.getWidth(), (float) 160 / bitmap.getHeight());

        // 创建一个新的矩阵
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // 使用新的矩阵创建一个新的位图
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        this.context=context;
    }
    public void move(float maxHeight, float maxWidth)
    {
        if(Math.random()<0.05)
        {
            setDirection((float) (Math.random()*2*Math.PI));
        }
        x = (float) (x + 10 * Math.cos(direction));
        y = (float) (y + 10 * Math.sin(direction));
        if (x<0)
            x += maxWidth;
        if (x>maxWidth)
            x -= maxWidth;
        if (y<0)
            y += maxHeight;
        if (y>maxHeight)
            y -= maxHeight;

    }
    public void draw(Canvas canvas)
    {
        // 这行代码创建了一个新的Paint对象，mBitPaint，并启用了抗锯齿标志。抗锯齿是一种图形渲染技术，
        // 用于消除或减少图形和字体的锯齿状边缘，使其看起来更加平滑。
        Paint mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 这行代码启用了位图过滤。当位图被拉伸或压缩时（例如，当位图的大小与源矩形或目标矩形的大小不匹配时），
        // 过滤可以提供更平滑的边缘和更好的图像质量。
        mBitPaint.setFilterBitmap(true);

        // 这行代码启用了颜色抖动。抖动是一种图形渲染技术，用于模拟我们的视觉系统无法直接显示的颜色。
        // 通过抖动，可以使颜色过渡看起来更加平滑，而不是突然变化。
        mBitPaint.setDither(true);

        // 这行代码在画布上绘制位图。getX()和getY()函数确定位图在画布上的位置，
        // mBitPaint对象包含了绘制位图时要使用的样式和颜色信息。
        canvas.drawBitmap(bitmap, getX(), getY(), mBitPaint);
    }

    public boolean isPointInside(float touch_x, float touch_y) {
        if (touch_x > x && touch_x < x + bitmap.getWidth())
            return touch_y > y && touch_y < y + bitmap.getHeight();
        return false;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getDrawableResourceId() {
        return drawableResourceId;
    }

    public void setDrawableResourceId(int drawableResourceId) {
        this.drawableResourceId = drawableResourceId;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

}