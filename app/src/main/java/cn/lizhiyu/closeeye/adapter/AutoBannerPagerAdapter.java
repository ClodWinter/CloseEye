package cn.lizhiyu.closeeye.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;

/**
 * Created by king on 2018/2/8.
 */

public class AutoBannerPagerAdapter extends PagerAdapter
{
    private ArrayList datas;

    private Context mContext;

    public AutoBannerPagerAdapter(Context context)
    {
        mContext = context;
    }

    public void updateDatas(ArrayList<String> list)
    {
        if (datas != null)
        {
            datas.clear();
        }

        if (list != null)
        {
            datas = list;
        }
    }

    @Override
    public int getCount()
    {
        return datas == null?0:datas.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
//        NativeExpressADView view = (NativeExpressADView) datas.get(position % datas.size());
//
//        container.addView(view);
//
//        view.render();

        ImageView imageView = new ImageView(mContext);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setBackgroundResource((Integer) datas.get(position%datas.size()));

        container.addView(imageView);

        return imageView;
    }

}
