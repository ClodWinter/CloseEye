package cn.lizhiyu.closeeye.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dyhdyh.adapters.databinding.DataBindingListAdapter;
import com.dyhdyh.adapters.databinding.holder.DataBindingListHolder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.DiscItemModel;

public class DiscArrayAdapter extends ArrayAdapter
{
    private ArrayList datas;

    public DiscArrayAdapter(@NonNull Context context, int resource, @NonNull List objects)
    {
        super(context, resource, objects);

        datas = (ArrayList) objects;
    }

    @Override
    public int getCount()
    {
        return datas.size();
    }

    @Nullable
    @Override
    public Object getItem(int position)
    {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Object item)
    {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.disc_list_item,null);

        DiscItemModel model = (DiscItemModel) datas.get(position);

        ImageView imageViewIcon = (ImageView)view.findViewById(R.id.discitem_usericon);

        TextView textViewName = (TextView)view.findViewById(R.id.discitem_username);

        TextView textViewDate = (TextView)view.findViewById(R.id.discitem_date);

        TextView textViewTitle = (TextView)view.findViewById(R.id.discitem_title);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.discitem_imageslayout);

        Button buttonShare = (Button)view.findViewById(R.id.discitem_sharebtn);

        Button buttonReplay = (Button)view.findViewById(R.id.discitem_replaybtn);

        Button buttonPraise = (Button)view.findViewById(R.id.discitem_praisebtn);

        TextView textViewShareCount = (TextView)view.findViewById(R.id.discitem_sharecount);

        TextView textViewReplayCount = (TextView)view.findViewById(R.id.discitem_replaycount);

        TextView textViewPraiseCount = (TextView)view.findViewById(R.id.discitem_praisecount);

        textViewName.setText(model.getPosterScreenName());

        textViewDate.setText(model.getPublishDateStr());

        textViewTitle.setText(model.getTitle());

        Random rand = new Random();

        int i = rand.nextInt(10);

        String imageName = "user_icon_"+ i;

        imageViewIcon.setImageResource(getResource(imageName));

        if (model.getImageUrls() != null && model.getImageUrls().size()>0)
        {
            linearLayout.setVisibility(View.VISIBLE);

            int width = getContext().getResources().getDisplayMetrics().widthPixels;

            for (int j = 0; j < model.getImageUrls().size() ; j++)
            {
                String url = (String) model.getImageUrls().get(j);

                ImageView imageView = new ImageView(getContext());

                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                Picasso.with(getContext()).load(url).into(imageView);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (width/3.0f),dp2px(getContext(),140));

                layoutParams.leftMargin = 10;

                imageView.setLayoutParams(layoutParams);

                linearLayout.addView(imageView);

                Log.d("lzyssg",url);
            }

        }else
        {
            linearLayout.setVisibility(View.GONE);
        }

        return view;
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int  getResource(String imageName){
        Class mipmap = R.mipmap.class;
        try {
            Field field = mipmap.getField(imageName);
            int resId = field.getInt(imageName);
            return resId;
        } catch (NoSuchFieldException e) {//如果没有在"mipmap"下找到imageName,将会返回0
            return 0;
        } catch (IllegalAccessException e) {
            return 0;
        }

    }
}
