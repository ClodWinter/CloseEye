package cn.lizhiyu.closeeye.CustomClass;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by king on 2018/3/22.
 */

public class CommonTool
{
    //    设置图片虚化背景
    public static void setViewBlur(final Context context, String path, int defImage, ImageSwitcher imgView) {
        final BlurTransformation btf = new BlurTransformation(context, 10, 10);

        imgView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView view = new ImageView(context);
                view.setLayoutParams(new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return view;
            }
        });
        Glide.with(context)
                .load(isReal(path) ? path : defImage)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .bitmapTransform(btf)
                .crossFade()
                .into((ImageView) imgView.getCurrentView());
    }
    private static boolean isReal(String string) {
        return string != null && string.length() > 0 && !string.equals("null");
    }
}
