package com.example.miguelbcr.autofittextviewtrucated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    public ListAdapter(Context context, List<Object> objects) {
        super(context, 0, objects);

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object item = getItem(position);
        HolderItemList holder;

        // With holder pattern. Same behaviour
        if ( convertView == null ) {
            convertView = inflater.inflate(R.layout.item_list, null);
            holder = HolderItemList.load(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HolderItemList) convertView.getTag();
        }

        // without holder patter. Same behaviour
//        convertView = inflater.inflate(R.layout.item_list, null);

        return convertView;
    }

}
