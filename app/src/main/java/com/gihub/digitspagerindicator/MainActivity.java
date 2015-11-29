package com.gihub.digitspagerindicator;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gihub.digitspagerindicator.widget.DigitsPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DigitsPagerIndicator mDigitsIndicator;
    private ViewPager mViewPager;
    List<View> mViewLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mViewLists = new ArrayList<View>();
        for (int i = 0; i <6; i++) {
            View view = View.inflate(this, R.layout.item, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(""+(i+1));
            mViewLists.add(view);
        }

        mDigitsIndicator = (DigitsPagerIndicator) findViewById(R.id.digits_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyViewPagerAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mDigitsIndicator.setPageOffset(positionOffset);
                mDigitsIndicator.setCurrentPage(position);


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mDigitsIndicator.setPageCount(mViewLists.size());
        mDigitsIndicator.setCurrentPage(0);
        mDigitsIndicator.setPaintColor(0xFFFFFFFF, 0xFFFF4081, 0xFFFFFFFF);
        mDigitsIndicator.setRadius(10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewLists.get(position));
            return mViewLists.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewLists.get(position));
        }
    }
}
