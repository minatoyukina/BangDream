package com.ccq.bangdream.gacha;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ccq.bangdream.R;

import java.util.ArrayList;

public class GachaListAdapter extends ArrayAdapter {
    private Context context;

    private ArrayList<String> pics;

    GachaListAdapter(@NonNull Context context, ArrayList<String> pics) {
        super(context, R.layout.gacha_ltem, pics);
        this.context = context;
        this.pics = pics;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gacha_ltem, parent, false);
        }
        Glide.with(context).load(pics.get(position)).into((ImageView) convertView);
        return convertView;
    }
}
