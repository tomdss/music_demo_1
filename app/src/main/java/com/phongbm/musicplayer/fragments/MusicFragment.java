package com.phongbm.musicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.adapter.BaseAdapter;
import com.phongbm.musicplayer.base.BaseFragment;
import com.phongbm.musicplayer.databinding.FragmentMusicBinding;
import com.phongbm.musicplayer.model.Music;

public class MusicFragment extends BaseFragment<FragmentMusicBinding> implements MediaListener<Music> {

    private BaseAdapter<Music> adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter<>(getContext(),R.layout.item_music);
        binding.lvSong.setAdapter(adapter);
        adapter.setData(systemData.getSongs());
        adapter.setListener(this);
    }

    @Override
    public int getTitle() {
        return R.string.music;
    }

    @Override
    public void onItemMediaClick(Music music) {
        app.getService().setArrMusic(adapter.getData());
        int index = adapter.getData().indexOf(music);//ko co tra ve -1
        app.getService().create(index);
    }
}
