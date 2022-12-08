package com.jd.saas.pdacommon.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;
import com.jd.saas.pdacommon.GlideApp;
import com.jd.saas.pdacommon.GlideRequest;
import com.jd.saas.pdacommon.R;

/**
 * 网络图片加载
 *
 * @author majiheng
 */
public class ImageLoader {

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    public static void loadCircleImage(ImageView target, Object url, @DrawableRes int place) {
        loadAppImage(target, true, 0, url, place);
    }

    public static void loadImage(ImageView target, Object url, @DrawableRes int place) {
        loadAppImage(target, false, 0, url, place);
    }

//    @BindingAdapter(value = {"loadCircleImage"}, requireAll = false)
//    public static void loadCircleImage(ImageView target, Object url) {
//        loadAppImage(target, true, 0, url, UserManager.isLogin()?R.drawable.ic_avatar:R.drawable.ic_default_head);
//    }

    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void loadImage(ImageView target, Object url) {
        loadAppImage(target, false, 5, url, R.drawable.shape_rect_round_place_holder);
    }

    public static void loadRoundImage(ImageView target, int round, Object url) {
        loadAppImage(target, false, round, url, R.drawable.shape_rect_round_place_holder);
    }

    public interface onBitmapLoadFinishCallback {
        void onLoadFinish(Bitmap bitmap);
    }

    public static void loadBitmap(Context context, String url,
                                  final onBitmapLoadFinishCallback callback) {
        GlideApp.with(context).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                callback.onLoadFinish(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                callback.onLoadFinish(null);
            }
        });
    }

    public static void loadSmallBitmap(Context context, String url,
                                       final onBitmapLoadFinishCallback callback) {
        GlideApp.with(context).asBitmap().load(url).into(new CustomTarget<Bitmap>(240,135) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                callback.onLoadFinish(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                callback.onLoadFinish(null);
            }
        });
    }

    public static boolean isDestroy(Activity activity) {
        return activity == null || activity.isFinishing() || activity.isDestroyed();
    }

    private static void loadAppImage(ImageView target, boolean isCircle, int round, Object url, @DrawableRes int place) {
        if (target == null) return;
        Context context = target.getContext();
        if (context == null) return;
        if (context instanceof Activity) {
            if (isDestroy((Activity) context)) {
                return;
            }
        }
        GlideRequest<Drawable> glideRequest = GlideApp.with(context).asDrawable();
        if (round != 0) {
            glideRequest = glideRequest.transform(new RoundedCorners(round));
        }
        glideRequest = glideRequest.load(url);

        if (place != 0) {
            glideRequest = glideRequest.error(place)
                    .placeholder(place)
                    .fallback(place);
        }

        if (isCircle) {
            glideRequest = glideRequest.circleCrop();
        }

        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();
        glideRequest = glideRequest.transition(DrawableTransitionOptions.with(drawableCrossFadeFactory));
        glideRequest.into(target);
    }
}
