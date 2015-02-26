package com.example.miguelbcr.autofittextviewtrucated;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;


public class CustomTextView extends AutoResizeTextView {
    private Context context;
    private int minTextSize;
    private int textStyle;


	public CustomTextView(final Context context) {
		this(context, null, 0);
	}

	public CustomTextView(final Context context, final AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomTextView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		// Gets custom attributes in xml
		getStyledAttributes(context, attrs, defStyle);
	}

	private void getStyledAttributes(Context context, AttributeSet attrs, int defStyle) {
        this.context = context;

		if (attrs != null) {
			int[] arrayAttr = {R.attr.customTvMinTextSize };
			int[] arrayIndex = { 0 }; // {0,1,2,3,... arrayAttr.length-1}
			TypedArray a = context.obtainStyledAttributes(attrs, arrayAttr);
            minTextSize = a.getDimensionPixelSize(arrayIndex[0], 0);
			a.recycle();


            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyle, 0);
            textStyle = a.getResourceId(R.styleable.CustomTextView_customTvTextStyle, 0);

            setCustomTextStyle(textStyle);
            ta.recycle();
        }
    }

    public void setCustomTextStyle(int resource) {
        textStyle = resource;
        if (resource > 0) {
            setTextAppearance(getContext(), resource);

            // Sets the min text size when it is defined into the style.xml
            if (minTextSize == 0) {
                int[] arrayAttrTextStyle = {R.attr.customTvMinTextSize};
                int[] arrayIndexTextStyle = {0}; // {0,1,2,3,... arrayAttr.length-1}
                TypedArray aTextStyle = context.obtainStyledAttributes(resource, arrayAttrTextStyle);
                minTextSize = aTextStyle.getDimensionPixelSize(arrayIndexTextStyle[0], 10);
            }

            setMinTextSize(minTextSize);
        }
    }

}