package com.ks_pem01.thesis_app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/**
 * Created by katrinschauer on 07.11.15.
 */
public class Crossfader {
    private View mView1, mView2;
    private int mDuration;

    public Crossfader(View view1, View view2, int fadeDuration) {
        mView1 = view1;
        mView2 = view2;
        mDuration = fadeDuration;
    }

    public void start() {
        mView2.setAlpha(1f);
        mView2.setVisibility(View.VISIBLE);
        mView1.animate()
                .alpha(0f)
                .setDuration(mDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mView1.setVisibility(View.GONE);
                        mView2.animate()
                                .alpha(1f)
                                .setDuration(mDuration)
                                .setListener(null);
                    }
                });
    }
}
