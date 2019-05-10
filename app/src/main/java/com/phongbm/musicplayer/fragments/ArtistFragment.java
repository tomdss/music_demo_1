package com.phongbm.musicplayer.fragments;

import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.base.BaseFragment;
import com.phongbm.musicplayer.databinding.FragmentArtistBinding;

public class ArtistFragment extends BaseFragment<FragmentArtistBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_artist;
    }

    @Override
    public int getTitle() {
        return R.string.artist;
    }
}
