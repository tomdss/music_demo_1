package com.phongbm.musicplayer.acitivities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.phongbm.musicplayer.App;
import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.adapter.BaseAdapter;
import com.phongbm.musicplayer.adapter.PagerAdapter;
import com.phongbm.musicplayer.base.BaseActivity;
import com.phongbm.musicplayer.dao.SystemData;
import com.phongbm.musicplayer.databinding.ActivityMainBinding;
import com.phongbm.musicplayer.fragments.AlbumFragment;
import com.phongbm.musicplayer.fragments.ArtistFragment;
import com.phongbm.musicplayer.fragments.MediaListener;
import com.phongbm.musicplayer.fragments.MusicFragment;
import com.phongbm.musicplayer.model.Music;
import com.phongbm.musicplayer.service.MP3Service;

import java.util.ArrayList;

public class MainActivity extends BaseActivity<ActivityMainBinding>
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, View.OnFocusChangeListener, MediaListener<Music> {

    private MusicFragment fmMusic = new MusicFragment();
    private AlbumFragment fmAlbum = new AlbumFragment();
    private ArtistFragment fmArtist = new ArtistFragment();
    private PagerAdapter adapter;
    private ArrayList<Music> dataSearch;
    private ArrayList<Music> arrMusic;
    private SystemData systemData;
    private BaseAdapter<Music> musicAdapter;

    private ServiceConnection connection = new ServiceConnection() {

        //chu nhat 12/5 hoc tu 2h

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MP3Service.MP3Binder binder = (MP3Service.MP3Binder) service;
            App app = (App) getApplicationContext();
            app.setService(binder.getService());
            binding.playView.registerStage();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private final String[] PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private boolean checkPermission(){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            for (String p:PERMISSION) {
                if(checkSelfPermission(p)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(PERMISSION,0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(checkPermission()){
            initAct();
        }else {
            finish();
        }
    }

    @Override
    protected void initAct() {

        if(!checkPermission()){
            return;
        }


        Intent intent = new Intent(this,MP3Service.class);
        bindService(intent,connection,BIND_AUTO_CREATE);

        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        adapter = new PagerAdapter(this,
                getSupportFragmentManager(),
                fmMusic, fmAlbum, fmArtist);
        binding.pager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.pager);
//        arrMusic = new ArrayList<>();
        systemData = new SystemData(this);
        arrMusic = systemData.getSongs();


        musicAdapter = new BaseAdapter<>(this,R.layout.item_music);

        musicAdapter.setListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_music:
                binding.pager.setCurrentItem(0);
                break;
            case R.id.nav_album:
                binding.pager.setCurrentItem(1);
                break;
            case R.id.nav_artist:
                binding.pager.setCurrentItem(2);
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
//        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(this);

        searchView.setOnQueryTextFocusChangeListener(this);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Toast.makeText(this, "Search Success", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        binding.lvTest.setVisibility(View.VISIBLE);
        dataSearch = new ArrayList<>();
        if(s!=null){
            for (Music sv:arrMusic) {
                String sv1 = sv.getTitle().toLowerCase();
                String s1 = s.toLowerCase();

                if(sv1.indexOf(s1)!=-1){
                    dataSearch.add(sv);
//                    testAdapter.notifyDataSetChanged();
//                    int n=1;
                }
            }
        }

//        String sss = s;


//        dataSearch.add("Liem");
//        dataSearch.add("Linh");
//        dataSearch.add("Lan");






//        testAdapter = new TestAdapter(this);
//        binding.lvTest.setAdapter(testAdapter);
//        testAdapter.setData(dataSearch);
//        testAdapter.notifyDataSetChanged();

        binding.lvTest.setAdapter(musicAdapter);
        musicAdapter.setData(dataSearch);

        musicAdapter.notifyDataSetChanged();

        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //to do
        if(!hasFocus)
            binding.lvTest.setVisibility(View.GONE);
    }


    @Override
    public void onItemMediaClick(Music music) {

        int index = musicAdapter.getData().indexOf(music);

//        Toast.makeText(this, "Run "+index, Toast.LENGTH_SHORT).show();

        App app = (App) getApplicationContext();
        app.getService().setArrMusic(musicAdapter.getData());
        app.getService().create(index);


//        App app = new App();
//        app.getService().setArrMusic(musicAdapter.getData());
////        int index = musicAdapter.getData().indexOf(music);//ko co tra ve -1
//        app.getService().create(index);

    }
}
