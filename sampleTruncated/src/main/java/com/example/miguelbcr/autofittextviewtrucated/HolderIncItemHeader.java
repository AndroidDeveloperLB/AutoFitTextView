package com.example.miguelbcr.autofittextviewtrucated;

import android.view.View;

public class HolderIncItemHeader {
    public CustomTextView ctvleft;
    public CustomTextView ctvright;


	public static HolderIncItemHeader load(View view) {
        HolderIncItemHeader holder = new HolderIncItemHeader();

        holder.ctvleft = (CustomTextView) view.findViewById(R.id.inc_item_header_ctv_left);
        holder.ctvright = (CustomTextView) view.findViewById(R.id.inc_item_header_ctv_right);

		return holder;
	}
}
