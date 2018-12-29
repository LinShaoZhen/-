package com.example.administrator.essayjoke.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nana on 2018/9/10.
 * 圆的指示器
 */

public class DotIndicatorView extends View {
    private Drawable drawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (drawable != null) {
            //画圆
            Bitmap bitmap = drawableToBitmap(drawable);
            //把bitmap变为圆的
            Bitmap circleBitmap = getCircleBitmap(bitmap);
            //把圆形的bitmap绘制到画布上
            canvas.drawBitmap(circleBitmap, 0, 0, null);

        }

    }

    /**
     * 获取圆形的bitmap
     *
     * @param bitmap
     * @return
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建一个Bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        //在画布上画个圆
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);
       //取圆和bitmap矩形的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //再把原来的Bitmap绘制到新的圆上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //回收bitmap
        bitmap.recycle();
        bitmap=null;
        return circleBitmap;
    }

    /**
     * 从drawable中得到bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        //如果drawable是BitmapDrawable类型
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        //其他类型 ColorDrawable
        //创建一个什么都没有的bitmao
        Bitmap outbitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas = new Canvas(outbitmap);
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        //把drawable画到bitmap上
        drawable.draw(canvas);
        return outbitmap;
    }

    /**
     * 设置Drawable
     *
     * @param drawable
     */
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        invalidate();//刷新
    }
}
