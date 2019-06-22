package com.example.lenovo.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.example.lenovo.myapplication.widget.GlideApp;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * Created by duanzheng on 2018/1/20.
 */

public class ImageUtils {
    public static void displayImages(final Context context, String url, ImageView iv) {
        if (TextUtils.isEmpty(url) || context == null) {
            return;
        }
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= 17 && ((Activity) context).isDestroyed()) {
                return;
            }
        }
        GlideApp.with(context)
                .load(url)
                .into(iv);
    }


    public static void displayTransformImages(final Context context, String url, ImageView iv, Transformation<Bitmap> transformation) {
        if (TextUtils.isEmpty(url) || context == null) {
            return;
        }
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= 17 && ((Activity) context).isDestroyed()) {
                return;
            }
        }
        GlideApp.with(context)
                .load(url).apply(bitmapTransform(transformation))
                .into(iv);
    }

    public static void displayTransformImages(final Context context, int placeholder, String url, ImageView iv, Transformation<Bitmap> transformation) {
        if (TextUtils.isEmpty(url) || context == null) {
            return;
        }
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= 17 && ((Activity) context).isDestroyed()) {
                return;
            }
        }
        GlideApp.with(context)
                .load(url).apply(bitmapTransform(transformation)).placeholder(placeholder)
                .into(iv);
    }

    public static void displayTransformImages(final Context context, int placeholder, int error, String url, ImageView iv, Transformation<Bitmap> transformation) {
        if (TextUtils.isEmpty(url) || context == null) {
            return;
        }
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= 17 && ((Activity) context).isDestroyed()) {
                return;
            }
        }
        GlideApp.with(context)
                .load(url).apply(bitmapTransform(transformation)).placeholder(placeholder).error(error)
                .into(iv);
    }



    public static void displayImages(Context context, String url, @DrawableRes int defaultImg,
                                     ImageView iv) {
        if (TextUtils.isEmpty(url) || context == null) {
            return;
        }
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= 17 && ((Activity) context).isDestroyed()) {
                return;
            }
        }
        GlideApp.with(context)
                .load(url)
                .placeholder(defaultImg)
                .error(defaultImg)
                .into(iv);
    }

    public static void displayImages(final Context context, String url, RequestListener listener, SimpleTarget simpleTarget) {
        if (TextUtils.isEmpty(url) || context == null) {
            return;
        }
        GlideApp.with(context).asBitmap().load(url).listener(listener).into(simpleTarget);

    }


    public static void loadGif(Context context, int gif, final ImageView imageView, final int showCount) {
        GlideApp.with(context).load(gif).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                if (drawable instanceof GifDrawable) {
                    GifDrawable gifDrawable = (GifDrawable) drawable;
                    gifDrawable.setLoopCount(showCount);
                    imageView.setImageDrawable(drawable);
                    gifDrawable.start();
                }
            }
        });
    }

    public static void loadGif(Context context, int gif, final ImageView imageView) {
        GlideApp.with(context).load(gif).into(imageView);
    }

    public static void displayImagesSignature(Context context, String path, ImageView imageView) {
        RequestOptions userAvatarOptions = new RequestOptions()
                .signature(new ObjectKey(System.currentTimeMillis()))
                .encodeQuality(70);
        GlideApp.with(context).load(path).apply(userAvatarOptions).into(imageView);
    }


}
