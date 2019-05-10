package com.phongbm.musicplayer.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phongbm.musicplayer.App;
import com.phongbm.musicplayer.dao.SystemData;

public abstract class BaseFragment<BD extends ViewDataBinding>
        extends Fragment {
    protected BD binding;

    protected SystemData systemData;
    protected App app;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId()
                , container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        systemData = new SystemData(getContext());
        app = (App) getContext().getApplicationContext();
    }

    protected abstract int getLayoutId();

    public abstract @StringRes int getTitle();
}
