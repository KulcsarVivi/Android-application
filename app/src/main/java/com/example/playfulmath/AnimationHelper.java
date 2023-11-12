package com.example.playfulmath;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class AnimationHelper {
    public static void zoomAnim(ImageView imageView) {
        Animation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        imageView.setVisibility(View.VISIBLE);
        imageView.startAnimation(scaleAnimation);
    }
    public static void animStartFromX(View view, int startFromX) {
        Animation slideInAnimation = new TranslateAnimation(startFromX, 0, 0, 0);
        slideInAnimation.setDuration(2000);
        view.startAnimation(slideInAnimation);
    }
    public static void animStartFromY(View view, int startFromY) {
        Animation slideInAnimation = new TranslateAnimation(0, 0, startFromY, 0);
        slideInAnimation.setDuration(2000);
        view.startAnimation(slideInAnimation);
    }
}
