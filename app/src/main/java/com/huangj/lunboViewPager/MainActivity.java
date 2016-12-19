package com.huangj.lunboViewPager;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import org.xutils.x;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout group;
    //         private int[] images = { R.drawable.a, R.drawable.b, R.drawable.b,
    //                         R.drawable.d, R.drawable.e };
    private ImageView text;
    private String[] images = {"http://121.41.49.62:8090/MeiShengUploader/gamepicture/20161111/c3c86bd54d804fd5984e7153bc020f15.png"
            , "http://121.41.49.62:8090/MeiShengUploader/gamepicture/20161111/65de8a002549453690b13e993577b160.png"
            , "http://121.41.49.62:8090/MeiShengUploader/gamepicture/20161111/c329d8046f324839a21977ccde6723eb.png"
            , "http://121.41.49.62:8090/MeiShengUploader/gamepicture/20161111/71c9384a946348aaae5881714b2d3934.png"
            , "http://121.41.49.62:8090/MeiShengUploader/gamepicture/20161111/106a68989cb94ca1a0b63184fd6f17d8.png"};
    private ArrayList<ImageView> mImageViewList;
    private ImageView[] diandian;// ViewPager上的点点
    private int selectedItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.vp_viewPager);
        group = (LinearLayout) findViewById(R.id.ll_viewGroup);
        text = (ImageView) findViewById(R.id.text);
        init();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(selectedItem + 1);
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };

    private void init() {
        // 引导3个ViewPager
        mImageViewList = new ArrayList<ImageView>();
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            //                         imageView.setBackgroundResource(images[i]);
            x.image().bind(imageView, images[i]);
            mImageViewList.add(imageView);
        }

        // 将点点加入到ViewGroup中
        diandian = new ImageView[images.length];
        for (int i = 0; i < images.length; i++) {
            // 循环加入点点图片组
            diandian[i] = new ImageView(this);
            if (i == 0) {
                diandian[i].setImageResource(R.drawable.oval2);
            } else {
                diandian[i]
                        .setImageResource(R.drawable.oval);
            }
            diandian[i].setPadding(8, 8, 8, 8);
            group.addView(diandian[i]);
        }
        viewPager.setAdapter(new GuideAdapter());
        // 设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(new GuidePagerListener());
        // 设置ViewPager的默认项，设置为长度的1000倍，这样开始就能向左滑动了
        viewPager.setCurrentItem((images.length) * 1000);
    }

    /**
     * 每次当onResume有焦点时发送个空消息开始轮播
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * 当暂停时停止轮播
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * ViewPager的适配器
     */
    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        // @Override
        // public void destroyItem(View container, int position, Object object)
        // {
        // // TODO Auto-generated method stub
        // ((ViewGroup) container).removeView(images[position
        // % images.length]);
        // }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }

        /**
         * 加载图片进去，用当前的position除以图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            final int currentIten = position % images.length;
            ImageView iView = mImageViewList.get(currentIten);
            try {

                if (iView.getParent() == null) {
                    container.addView(iView);
                }
                iView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Log.e("======", "" + currentIten);
                    }
                });
                iView.setOnTouchListener(new NewTouchListener(currentIten));
            } catch (Exception e) {
                Log.e("======", e.getMessage());
            }

            return iView;
        }

    }

    /**
     * ViewPager 的滑动监听
     *
     * @author Administrator
     */
    private class GuidePagerListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            System.out.println(arg0 + "  dd " + arg1 + "  dddd  " + arg2);
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            selectedItem = position;
            System.out.println("第几个页面" + position + "  " + position
                    % mImageViewList.size());
            for (int i = 0; i < diandian.length; i++) {
                if (i == position % images.length) {
                    diandian[i]
                            .setImageResource(R.drawable.oval2);
                } else {
                    diandian[i]
                            .setImageResource(R.drawable.oval);
                }
            }
        }

    }

    /**
     * ViewPager的触摸事件当按下图片停止轮播
     *
     * @author Administrator
     */
    private class NewTouchListener implements View.OnTouchListener {
        int position;

        public NewTouchListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            // TODO Auto-generated method stub
            switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x.image().bind(text, images[position]);
                    mHandler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                    break;
                case MotionEvent.ACTION_UP:
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                    break;
                default:
                    break;
            }
            return true;
        }

    }

}
