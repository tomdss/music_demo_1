package com.phongbm.musicplayer.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.phongbm.musicplayer.App;
import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.acitivities.MainActivity;
import com.phongbm.musicplayer.acitivities.PlayActivity;
import com.phongbm.musicplayer.databinding.UiPlayViewBinding;
import com.phongbm.musicplayer.service.MP3Service;

public class MP3PlayView extends FrameLayout implements View.OnClickListener, MP3PlayViewListener {

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


        binding.setListener(this);
        setOnClickListener(this);

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

        app.getService().getArtists().observe(act, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.setArtists(s);
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

        Intent intent = new Intent(getContext(), PlayActivity.class);
        getContext().startActivity(intent);

    }

    @Override
    public void next() {
        app.getService().change(MP3Service.NEXT);
    }

    @Override
    public void prev() {

        app.getService().change(MP3Service.PREV);

    }

    @Override
    public void play() {
        if(app.getService().getIsPlaying().getValue()==true){
            app.getService().pause();
        }else {
            app.getService().start();
        }
    }

    @Override
    public void repeat() {
            app.getService().getIsRepeat();

    }

    @Override
    public void shuffle() {

            app.getService().shuffle();


    }
}
