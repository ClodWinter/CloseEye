package cn.lizhiyu.closeeye.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.lizhiyu.closeeye.R;
/**
 * Created by king on 2018/3/14.
 */

public class TagsView extends RelativeLayout
{
    public ImageView imageView;

    public TextView textView;

    public TagsView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.tags_item_layout,this);

        imageView = findViewById(R.id.tags_sex_icon);

        textView = findViewById(R.id.tags_sex_title);

    }


}
