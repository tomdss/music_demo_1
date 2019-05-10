package com.phongbm.musicplayer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.adapter.BaseAdapter;
import com.phongbm.musicplayer.base.BaseFragment;
import com.phongbm.musicplayer.dao.SystemData;
import com.phongbm.musicplayer.databinding.FragmentAlbumBinding;
import com.phongbm.musicplayer.model.Album;

public class AlbumFragment extends BaseFragment<FragmentAlbumBinding> implements MediaListener<Album>{

    private BaseAdapter<Album> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_album;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter<>(getContext(),R.layout.item_album);
        binding.lvAlbum.setAdapter(adapter);
        adapter.setData(systemData.getAbums());

        adapter.setListener(this);

        //chu nhat hoc tu 14h
    }

    @Override
    public int getTitle() {
        return R.string.album;
    }

    @Override
    public void onItemMediaClick(Album album) {

    }
}
