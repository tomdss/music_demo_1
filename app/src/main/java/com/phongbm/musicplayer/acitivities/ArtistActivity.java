package com.phongbm.musicplayer.acitivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.phongbm.musicplayer.App;
import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.adapter.BaseAdapter;
import com.phongbm.musicplayer.base.BaseActivity;
import com.phongbm.musicplayer.dao.SystemData;
import com.phongbm.musicplayer.databinding.ActivityArtistBinding;
import com.phongbm.musicplayer.fragments.MediaListener;
import com.phongbm.musicplayer.model.Music;

import java.util.ArrayList;

public class ArtistActivity extends BaseActivity<ActivityArtistBinding> implements MediaListener<Music> {

    private BaseAdapter<Music> adapter;
    private ArrayList<Music> arrMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_artist);



    }

    @Override
    protected void initAct() {
        Intent intent = getIntent();
        String artist =  intent.getStringExtra(MainActivity.REQUEST_ARTIST);

        binding.tvArtist1.setText(artist);
        arrMusic = new ArrayList<>();

        adapter = new BaseAdapter<>(this,R.layout.item_music_by_artist);

        binding.lvTest1.setAdapter(adapter);
        setDataMusic(artist);
        adapter.setData(arrMusic);

        adapter.setListener(this);

//        SystemData systemData = new SystemData(this);
//        systemData.getSongByArtist()


    }

    private void setDataMusic(String artist) {
        SystemData systemData = new SystemData(this);
        ArrayList<Music> data = systemData.getSongs();
        for (Music m:data) {
            if(artist.indexOf(m.getArtist())!=-1){
                arrMusic.add(m);
            }
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_artist;
    }

    @Override
    public void onItemMediaClick(Music music) {

        App app = (App) getApplicationContext();
        app.getService().setArrMusic(adapter.getData());
        int index = adapter.getData().indexOf(music);//ko co tra ve -1
        app.getService().create(index);

    }
}
