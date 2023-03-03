package com.example.eductionery;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class FragmentContainer extends FrameLayout {

    private static FragmentContainer sInstance;

    public FragmentContainer(Context context) {
        super(context);
        init();
    }

    public FragmentContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FragmentContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Save a reference to the singleton instance
        sInstance = this;
    }

        // Define a static method to get the singleton instance
        public static FragmentContainer getInstance() {
        return sInstance;
    }
}
