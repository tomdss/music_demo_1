package com.phongbm.musicplayer.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.phongbm.musicplayer.App;
import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.acitivities.MainActivity;
import com.phongbm.musicplayer.databinding.UiPlayViewBinding;
import com.phongbm.musicplayer.service.MP3Service;

public class MP3PlayView extends FrameLayout implements View.OnClickListener {

    private UiPlayViewBinding binding;
    private App app;

    public MP3PlayView(Context context) {
        super(context);
        init();
    }

    public MP3PlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MP3PlayView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MP3PlayView( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        binding = UiPlayViewBinding.inflate(LayoutInflater.from(getContext()));
        addView(binding.getRoot());
        app = (App) getContext().getApplicationContext();
        setVisibility(GONE);


        binding.imvPlay.setOnClickListener(this);
        binding.imvPrevious.setOnClickListener(this);
        binding.imvNext.setOnClickListener(this);

    }

    //btvn search,artist ...

    public void registerStage() {
        MainActivity act = (MainActivity) getContext();

        app.getService().getIsLife().observe(act, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    setVisibility(VISIBLE);
                }else {
                    setVisibility(GONE);
                }
            }
        });

        app.getService().getName().observe(act, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.setName(s);
            }
        });

        app.getService().getIsPlaying().observe(act, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.setIsPlaying(aBoolean);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imv_previous:
                app.getService().change(MP3Service.PREV);
                break;
            case R.id.imv_play:
                if(binding.getIsPlaying()){
                    app.getService().pause();
                    binding.setIsPlaying(false);
                }else {
                    app.getService().start();
                    binding.setIsPlaying(true);
                }
                break;
            case R.id.imv_next:
                app.getService().change(MP3Service.NEXT);
                break;
            default:
                break;
        }

    }
}
