package com.example.movieapp.utils.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;

public class GlideImageLoader implements IImageLoader<ImageView> {

    @Override
    public void loadImage(String url, ImageView container) {
        if (container != null) {
            Glide.with(container.getContext())
                    .load(url)
                    .dontAnimate()
                    .error(R.drawable.ic_baseline_broken_image)
                    .into(container);
        }
    }
}