package cn.lizhiyu.closeeye.ViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by king on 2018/2/8.
 */

public class AutoBannerViewPager extends ViewPager
{
    private int showTime = 3*1000;

    private Direction direction = Direction.Left;

    private Runnable player = new Runnable() {
        @Override
        public void run()
        {
            play(direction);
        }
    };

    public enum Direction
    {
        Left,
        Right
    }

    public AutoBannerViewPager(Context context) {
        super(context);
    }

    public AutoBannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void start()
    {
        stop();

        postDelayed(player,showTime);
    }

    public void stop()
    {
        removeCallbacks(player);
    }

    private synchronized void play(Direction direction)
    {
        PagerAdapter pagerAdapter = getAdapter();

        if (pagerAdapter != null)
        {
            int count = pagerAdapter.getCount();

            int currentIndex = getCurrentItem();

            switch (direction)
            {
                case Left:
                {
                    currentIndex++;

                    if (currentIndex>count)
                    {
                        currentIndex = 0;
                    }

                    break;
                }

                case Right:
                {
                    currentIndex --;

                    if (currentIndex<0)
                    {
                        currentIndex = count;
                    }

                    break;
                }
            }

            setCurrentItem(currentIndex);

            start();
        }
    }

    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                if (state == SCROLL_STATE_IDLE)
                {
                    start();
                }else if (state == SCROLL_STATE_DRAGGING)
                {
                    stop();
                }
            }
        });
    }
}
