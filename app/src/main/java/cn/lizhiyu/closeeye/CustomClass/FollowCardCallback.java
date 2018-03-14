package cn.lizhiyu.closeeye.CustomClass;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import cn.lizhiyu.closeeye.model.CardConfig;

import java.util.Collections;
import java.util.List;

import cn.lizhiyu.closeeye.model.FollowCardItemModel;
import cn.lizhiyu.closeeye.model.PairingItemModel;

/**
 * Created by king on 2018/3/9.
 */

public class FollowCardCallback extends ItemTouchHelper.SimpleCallback
{

    protected RecyclerView mRv;
    protected List mDatas;
    protected RecyclerView.Adapter mAdapter;

    private int mHorizontalDeviation;

    public FollowCardCallback(int dragDirs, int swipeDirs,RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
        super(dragDirs, swipeDirs);
        mRv = rv;
        mAdapter = adapter;
        mDatas = datas;

        mHorizontalDeviation = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, mRv.getContext().getResources().getDisplayMetrics());
    }


    //水平方向是否可以被回收掉的阈值
    public float getThreshold(RecyclerView.ViewHolder viewHolder) {
        //2016 12 26 考虑 探探垂直上下方向滑动，不删除卡片，这里参照源码写死0.5f
        return mRv.getWidth() * /*getSwipeThreshold(viewHolder)*/ 0.5f;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
        int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDatas, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDatas, i, i - 1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        View topView = mRv.getChildAt(mRv.getChildCount() - 1);
        if (isTopViewCenterInHorizontal(topView)) {
            return Float.MAX_VALUE;
        }
        return super.getSwipeEscapeVelocity(defaultValue);
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {

        View topView = mRv.getChildAt(mRv.getChildCount() - 1);
        if (isTopViewCenterInHorizontal(topView)) {
            return Float.MAX_VALUE;
        }
        return super.getSwipeVelocityThreshold(defaultValue);
    }

    /**
     * 返回TopView此时在水平方向上是否是居中的
     *
     * @return
     */
    public boolean isTopViewCenterInHorizontal(View topView) {
        Log.d("TAG", "getSwipeThreshold() called with: viewHolder.itemView.getX() = [" + topView.getX() + "]");
        Log.d("TAG", "getSwipeThreshold() called with:  viewHolder.itemView.getWidth() / 2  = [" + topView.getWidth() / 2 + "]");
        Log.d("TAG", "getSwipeThreshold() called with:  mRv.getX() = [" + mRv.getX() + "]");
        Log.d("TAG", "getSwipeThreshold() called with:  mRv.getWidth() / 2 = [" + mRv.getWidth() / 2 + "]");
        return Math.abs(mRv.getWidth() / 2 - topView.getX() - (topView.getWidth() / 2)) < mHorizontalDeviation;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        //★实现循环的要点
        PairingItemModel remove = (PairingItemModel) mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0, remove);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold(viewHolder);
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        }
        //对每个ChildView进行缩放 位移
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = childCount - i - 1;
            if (level > 0) {
                child.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));

                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    child.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    child.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                }
            }
        }
    }
}
