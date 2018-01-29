package cn.lizhiyu.closeeye.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lizhiyu on 2017/8/27.
 */

public class NestedListView extends ListView
{
    public NestedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    我们知道，MeasureSpec的specMode一共三种类型:
//    EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
//    AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
//    UNSPECIFIED：表示子布局想要多大就多大（很少使用）
//    将里面的View的onMeasure里面的高设置为最大（AT_MOST）即可解决冲突

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }

}
