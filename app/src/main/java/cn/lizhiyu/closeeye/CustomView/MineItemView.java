package cn.lizhiyu.closeeye.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lizhiyu.closeeye.R;

/**
 * Created by king on 2018/3/2.
 */

public class MineItemView extends ConstraintLayout
{
    public ImageView imageView;

    public TextView textView;

    public MineItemView(Context context)
    {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.mineitem_layout,this);

        imageView = findViewById(R.id.mineitem_icon);

        textView = findViewById(R.id.mineitem_text);
    }

}
