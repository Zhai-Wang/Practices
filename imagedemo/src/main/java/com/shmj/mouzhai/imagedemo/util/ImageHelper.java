package com.shmj.mouzhai.imagedemo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 图像处理工具类
 * <p>
 * Created by Mouzhai on 2016/12/7.
 */

public class ImageHelper {

    /**
     * 根据传入的属性值，改变 Bitmap 属性
     */
    public static Bitmap handleImageEffect(Bitmap bitmap, float hue, float saturation, float lum) {
        //传入的 Bitmap 对象默认是不能修改的，需要重新创建一个
        Bitmap mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //设置色调
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        //设置饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        //设置亮度
        ColorMatrix lunMatrix = new ColorMatrix();
        lunMatrix.setScale(lum, lum, lum, 1);

        //融合属性
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lunMatrix);

        //绘制图形
        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return mBitmap;
    }

    /**
     * 通过改变像素点属性，来改变图片的显示效果
     * 实现了底片效果
     */
    public static Bitmap handleImageNegative(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int color;//当前取到的颜色值
        int r, g, b, a;//对应具体的 RGBA 值
        int[] oldPx = new int[width * height];//存储全部像素点
        int[] newPx = new int[width * height];//存储经过处理后的像素点

        Bitmap mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);
            //处理像素点的各项值
            r = 255 - r;
            g = 255 - g;
            b = 255 - b;
            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        //设置经过处理之后的 Bitmap
        mBitmap.setPixels(newPx, 0, width, 0, 0, width, height);
        return mBitmap;
    }

    /**
     * 通过改变像素点属性，来改变图片的显示效果
     * 实现了怀旧效果
     */
    public static Bitmap handleImageOldPhoto(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int color;//当前取到的颜色值
        int r, g, b, a, r1, g1, b1;//对应具体的 RGBA 值
        int[] oldPx = new int[width * height];//存储全部像素点
        int[] newPx = new int[width * height];//存储经过处理后的像素点

        Bitmap mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);
            //处理像素点的各项值
            r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);
            if (r1 > 255) {
                r1 = 255;
            } else if (r1 < 0) {
                r1 = 0;
            }
            if (g1 > 255) {
                g1 = 255;
            } else if (g1 < 0) {
                g1 = 0;
            }
            if (b1 > 255) {
                b1 = 255;
            } else if (b1 < 0) {
                b1 = 0;
            }
            newPx[i] = Color.argb(a, r1, g1, b1);
        }
        mBitmap.setPixels(newPx, 0, width, 0, 0, width, height);
        return mBitmap;
    }

    /**
     * 通过改变像素点属性，来改变图片的显示效果
     * 实现了浮雕效果
     */
    public static Bitmap handleImagePixelRelif(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int color, colorBefore;//当前取到的颜色值
        int r, g, b, a, r1, g1, b1;//对应具体的 RGBA 值
        int[] oldPx = new int[width * height];//存储全部像素点
        int[] newPx = new int[width * height];//存储经过处理后的像素点

        Bitmap mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 1; i < width * height; i++) {
            colorBefore = oldPx[i - 1];
            r = Color.red(colorBefore);
            g = Color.green(colorBefore);
            b = Color.blue(colorBefore);
            a = Color.alpha(colorBefore);

            color = oldPx[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);

            //处理像素点的各项值
            r = r - r1 + 127;
            g = g - g1 + 127;
            b = b - b1 + 127;
            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        mBitmap.setPixels(newPx, 0, width, 0, 0, width, height);
        return mBitmap;
    }
}
