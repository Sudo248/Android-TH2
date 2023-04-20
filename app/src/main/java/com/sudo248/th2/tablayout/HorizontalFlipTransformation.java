package com.sudo248.th2.tablayout;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 14:47 - 15/03/2023
 */
public class HorizontalFlipTransformation implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setTranslationX(-position * page.getWidth());
        page.setCameraDistance(12000);
        if (position < 6.5 && position > -6.5) {
            page.setVisibility(View.VISIBLE);
        } else {
            page.setVisibility(View.INVISIBLE);
        }
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 0){
            page.setAlpha(1);
//            page.setRotationY(1);
        }
    }
}
