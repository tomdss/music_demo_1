package com.phongbm.musicplayer.acitivities;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

import com.phongbm.musicplayer.App;
import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.base.BaseActivity;
import com.phongbm.musicplayer.databinding.ActivityPlayBinding;
import com.phongbm.musicplayer.model.Music;
import com.phongbm.musicplayer.service.MP3Service;
import com.phongbm.musicplayer.view.MP3PlayViewListener;

public class PlayActivity extends BaseActivity<ActivityPlayBinding> implements MP3PlayViewListener, SeekBar.OnSeekBarChangeListener {

    private App app;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play;
    }


    @Override
    protected void initAct() {
        app = (App) getApplicationContext();
        app.getService().getIsPlaying().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.setIsPlaying(aBoolean);
            }
        });
        app.getService().getMusic().observe(this, new Observer<Music>() {
            @Override
            public void onChanged(@Nullable Music music) {
                binding.setMusic(music);
            }
        });

        app.getService().getCurrent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                binding.setCurrent(integer);
                int m=1;
            }
        });

        binding.setListener(this);
        binding.sbTime.setOnSeekBarChangeListener(this);

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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            app.getService().seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
