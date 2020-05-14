package com.example.mygamelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {
    private ArrayList<String> listData;
    private ArrayList<String> textData;
    private ArrayList<String> nameData;
    private ArrayList<String> rateData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomPagerAdapter(Context context, ArrayList<String> listData, ArrayList<String> textData, ArrayList<String> nameData, ArrayList<String> rateData) {
        this.listData = listData;
        this.textData = textData;
        this.nameData = nameData;
        this.rateData = rateData;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = layoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.page, container, false);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView text = (TextView) view.findViewById(R.id.text);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView rate = (TextView) view.findViewById(R.id.rate_number);
        text.setText(textData.get(position));
        name.setText(nameData.get(position));
        rate.setText(rateData.get(position));
        if (icon != null) {
            new BitmapWorkerTask(icon).execute(listData.get(position));
        }
        container.addView(view);
        return view;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return nameData.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) { return view == ((LinearLayout) object); }
}