package com.phoenix.soft.costy.transition;

import android.content.Context;

import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * Created by awang on 2017/2/23.
 */

public class DetailTransition extends TransitionSet {
    public DetailTransition() {
        init();
    }

    /**
     * This constructor allows us to use this transition in XML
     */
    public DetailTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrdering(ORDERING_SEQUENTIAL);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
        addTransition(new ChangeClipBounds());
    }
}
