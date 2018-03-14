package cn.lizhiyu.closeeye.adapter;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyhdyh.adapters.AbstractRecyclerAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.lizhiyu.closeeye.R;
import cn.lizhiyu.closeeye.model.FollowCardItemModel;
import cn.lizhiyu.closeeye.model.PairingItemModel;

public class FollowCardAdapter extends AbstractRecyclerAdapter<PairingItemModel, FollowCardAdapter.ItemViewHolder> {

    private ArrayList datas;

    public FollowCardAdapter(ArrayList<PairingItemModel> data)
    {
        super(data);
        datas = data;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, PairingItemModel item)
    {
        holder.imageView.setImageResource(item.icon);

        holder.textViewName.setText(item.userName);

        holder.textViewSign.setText(item.signature.length()>0?item.signature:"");
    }

    public static Drawable resizeImage(Bitmap bitmap, int w, int h)
    {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.followcard_layout, parent, false));
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;

        TextView textViewName;

        TextView textViewSign;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.followcard_imageview);

            textViewName = itemView.findViewById(R.id.followcard_name);

            textViewSign = itemView.findViewById(R.id.followcard_sign);
        }
    }

}
