package com.tianbin.seekbarexpandview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;

/**
 * 扩展Seekbar，支持自定义background,解决默认 paddingLeft,paddingRight 不为0 的问题
 * Created by tianbin on 16/11/1.
 */
public class ExpandSeekBar extends FrameLayout {

    private final int mDefaultBackgroundHeight = dip2px(4);

    private SeekBar mSeekBar;
    private int mThumbDrawableWidth;
    private int mBackgroundHeight;
    private Drawable mThumbDrawable;

    public ExpandSeekBar(Context context) {
        this(context, null);
    }

    public ExpandSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(attrs);
        initView();
    }

    private void initAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandSeekBar);
        mBackgroundHeight = typedArray.getDimensionPixelSize(R.styleable.ExpandSeekBar_background_height, mDefaultBackgroundHeight);
        mThumbDrawable = typedArray.getDrawable(R.styleable.ExpandSeekBar_thumb);
        typedArray.recycle();
    }

    private void initView() {
        initSeekBar();
        addBackground();
        addSeekBar();
    }

    private void initSeekBar() {
        mSeekBar = new SeekBar(getContext());
        if (mThumbDrawable != null) {
            mThumbDrawableWidth = mThumbDrawable.getIntrinsicWidth();
            mSeekBar.setThumb(mThumbDrawable);
        } else {
            mThumbDrawableWidth = mSeekBar.getThumbOffset();
        }

        mSeekBar.setPadding(mThumbDrawableWidth / 2, 0, mThumbDrawableWidth / 2, 0);

        //设置 seekbar 背景透明
        mSeekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.TRANSPARENT, PorterDuff.Mode.CLEAR));
    }

    private void addBackground() {
        View background = LayoutInflater.from(getContext()).inflate(R.layout.layout_seekbar_background, this, false);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, mBackgroundHeight);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.leftMargin = mThumbDrawableWidth / 2;
        layoutParams.rightMargin = mThumbDrawableWidth / 2;
        background.setLayoutParams(layoutParams);
        addView(background);
    }

    private void addSeekBar() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mSeekBar.setLayoutParams(layoutParams);
        addView(mSeekBar);
    }

    public void setMax(int max) {
        mSeekBar.setMax(max);
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        mSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
