package com.phongbm.musicplayer.fragments;

import android.content.Intent;
import android.os.Bundle;

import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.acitivities.ArtistActivity;
import com.phongbm.musicplayer.acitivities.MainActivity;
import com.phongbm.musicplayer.adapter.BaseAdapter;
import com.phongbm.musicplayer.base.BaseFragment;
import com.phongbm.musicplayer.databinding.FragmentArtistBinding;
import com.phongbm.musicplayer.model.Artist;

public class ArtistFragment extends BaseFragment<FragmentArtistBinding> implements MediaListener<Artist> {


    private BaseAdapter<Artist> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_artist;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter<>(getContext(), R.layout.item_artist);
        binding.lvArtist.setAdapter(adapter);
        adapter.setData(systemData.getArtist());
        adapter.setListener(this);
    }

    @Override
    public int getTitle() {
        return R.string.artist;
    }

    @Override
    public void onItemMediaClick(Artist artist) {


        Intent intent = new Intent(getContext(), ArtistActivity.class);
        intent.putExtra(MainActivity.REQUEST_ARTIST,artist.getArtist());
        startActivity(intent);

    }
}
