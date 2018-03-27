package cn.lizhiyu.closeeye.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.VideoModel;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by lizhiyu on 2018/3/26.
 */

public class ChoiceDetailHeadTagView extends RelativeLayout
{
    public RelativeLayout relativeLayout;

    public ImageView imageViewBg;

    public TextView textView;

    public Context contextSupper;

    public ChoiceDetailHeadTagView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.choicedetail_headtags_layout,this);

        relativeLayout = findViewById(R.id.choicedetail_tag_layout);

        imageViewBg = findViewById(R.id.choicedetail_tag_bg);

        textView = findViewById(R.id.choicedetail_tag_title);

        contextSupper = context;
    }

    public void setData(VideoModel model)
    {
        Glide.with(contextSupper).load(model.coverUrl).bitmapTransform(new RoundedCornersTransformation(contextSupper,25,0, RoundedCornersTransformation.CornerType.ALL)).into(imageViewBg);

        textView.setText(model.title.length()>0?model.title:"");
    }
}
