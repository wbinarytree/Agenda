package com.phoenix.soft.costy.hidden;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by yaoda on 07/03/17.
 */

public class HiddenImgAdaper extends PagerAdapter {
    List<Integer> imgRes;

    public HiddenImgAdaper(List<Integer> imgRes) {
        this.imgRes = imgRes;
    }

    @Override
    public int getCount() {
        return imgRes.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setFitsSystemWindows(true);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        container.addView(imageView);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap blurTemplate = BitmapFactory.decodeResource(container.getResources(), imgRes.get(position), options);
        Blurry.with(container.getContext()).radius(12).from(blurTemplate).into(imageView);
        return imageView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "image " + position;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }



}
