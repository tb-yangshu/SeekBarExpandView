package com.tianbin.seekbarexpandview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;

/**
 * 扩展Seekbar，支持自定义background,解决默认 paddingLeft,paddingRight 问题
 * Created by tianbin on 16/11/1.
 */
public class ExpandSeekBar extends FrameLayout {

    private SeekBar mSeekBar;

    public ExpandSeekBar(Context context) {
        this(context, null);
    }

    public ExpandSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        initSeekBar();
        addBackground();
        addSeekBar();
    }

    private void initSeekBar() {
        mSeekBar = new SeekBar(getContext());
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getResources().getDrawable(R.drawable.ic_large_thumb, null);
        } else {
            drawable = getResources().getDrawable(R.drawable.ic_large_thumb);
        }
        mSeekBar.setThumb(drawable);
        mSeekBar.setMax(40);

        //设置 seekbar 背景透明
        mSeekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY));
    }

    private void addBackground() {
        View background = LayoutInflater.from(getContext()).inflate(R.layout.layout_seekbar_background, this, false);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, dip2px(4));
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.leftMargin = getBackgroundLeftMargin();
        layoutParams.rightMargin = getBackgroundRightMargin();
        background.setLayoutParams(layoutParams);
        addView(background);
    }

    private void addSeekBar() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mSeekBar.setLayoutParams(layoutParams);
        addView(mSeekBar);
    }

    private int getBackgroundLeftMargin() {
        return getSeekBarPaddingLeft() + getThunbOffset() / 2;
    }

    private int getBackgroundRightMargin() {
        return getSeekBarPaddingRight() + getThunbOffset() / 2;
    }

    public int getThunbOffset() {
        return mSeekBar.getThumbOffset();
    }

    private int getSeekBarPaddingLeft() {
        return mSeekBar.getPaddingLeft();
    }

    private int getSeekBarPaddingRight() {
        return mSeekBar.getPaddingRight();
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
