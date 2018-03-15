package cn.lizhiyu.closeeye.CustomView;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import cn.lizhiyu.closeeye.R;
/**
 * Created by king on 2018/3/14.
 */

public class TagsView extends RelativeLayout
{
    public ImageView imageView;

    public TextView textView;

    public RelativeLayout relativeLayout;

    public TagsView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.tags_item_layout,this);

        imageView = findViewById(R.id.tags_sex_icon);

        textView = findViewById(R.id.tags_sex_title);

        relativeLayout = findViewById(R.id.tags_item_bgView);

    }

    public void setData(String title,int sex, String color)
    {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        relativeLayout.setBackgroundColor(Color.rgb(r,g,b));

        textView.setText(title);

        if(sex == 0)
        {
            imageView.setImageResource(R.mipmap.sex_girl);
        }
        else if(sex == 1)
        {
            imageView.setImageResource(R.mipmap.sex_boy);
        }
        else
        {
            imageView.setVisibility(GONE);
        }

    }

}
