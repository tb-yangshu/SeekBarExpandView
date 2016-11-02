package com.tianbin.seekbarexpandview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private int mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandSeekBar seekBar = (ExpandSeekBar) findViewById(R.id.seek_bar);
        seekBar.setMax(40);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mProgress % 10 >= 5) {
                    seekBar.setProgress((mProgress / 10 + 1) * 10);
                } else {
                    seekBar.setProgress(mProgress - mProgress % 10);
                }
            }
        });
    }
}
