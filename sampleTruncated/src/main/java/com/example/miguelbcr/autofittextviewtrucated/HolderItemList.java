package com.example.miguelbcr.autofittextviewtrucated;

import android.view.View;

public class HolderItemList {
    public HolderIncItemHeader header;


	public static HolderItemList load(View view) {
        HolderItemList holder = new HolderItemList();

        holder.header = HolderIncItemHeader.load(view.findViewById(R.id.item_list_header));

		return holder;
	}

}
