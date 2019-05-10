package com.phongbm.musicplayer.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;

import com.phongbm.musicplayer.R;
import com.phongbm.musicplayer.model.Music;

import java.util.ArrayList;

public class MP3Service extends Service implements MediaPlayer.OnCompletionListener {

    private ArrayList<Music> arrMusic;
    private MediaPlayer player;
    private int currentIndex;

    private MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLife = new MutableLiveData<>();



    @Override
    public void onCreate() {
        super.onCreate();
        pushNotification();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MP3Binder(this);
    }

    private void pushNotification() {

        Intent intent1 = new Intent(this,getClass());
        startService(intent1);


        String CHANNEL_ID = "CHANNEL_ID";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(this, getClass());
        intent.putExtra("EXTRA_NEED_STOP", true);
        PendingIntent pending = PendingIntent.getService(this,
                0, intent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("Push notification");
        builder.setContentTitle("Keep service running");
        builder.setContentText("Using foreground service");

        builder.setContentIntent(pending);

        startForeground(1213232, builder.build());
    }


    public void setArrMusic(ArrayList<Music> arrMusic) {
        this.arrMusic = arrMusic;
    }

    public void create(int index){

        release();
        if(arrMusic==null){
            new Throwable("New Exception");
            return;
        }
        Uri uri= Uri.parse(arrMusic.get(index).getData());
        player = MediaPlayer.create(this, uri);
        start();
        player.setOnCompletionListener(this);
        currentIndex = index;

        name.postValue(arrMusic.get(index).getTitle());
        isLife.postValue(true);

    }

    public void release(){
        if(player!=null){
            player.release();
            isPlaying.postValue(false);
        }
    }

    public void start(){
        if (player!=null){
            player.start();
            isPlaying.postValue(true);
        }
    }

    public void pause(){
        if (player!=null){
            player.pause();
            isPlaying.postValue(false);
        }
    }

    public void seekTo(int position){
        if (player!=null){
            player.seekTo(position);
        }
    }

    public void loop(boolean isloop){
        if (player!=null){
            player.setLooping(isloop);
        }
    }

    public int getDuration(){
        if (player!=null){
            player.getDuration();
        }
        return 0;
    }

    public int getPosition(){
        if (player!=null){
            player.getCurrentPosition();
        }
        return 0;
    }

    public static final int NEXT = 1;
    public static final int PREV = -1;
    @IntDef({NEXT,PREV})
    public @interface TypeChange{

    }

    public void change(@TypeChange int value){
        currentIndex = value;
        if(currentIndex>=arrMusic.size()){
            currentIndex=0;
        }else if(currentIndex<0){
            currentIndex = arrMusic.size()-1;
        }
        create(currentIndex);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        change(NEXT);
    }

    public class MP3Binder extends Binder{
        private  MP3Service service;

        public MP3Binder(MP3Service service) {
            this.service = service;
        }

        public MP3Service getService() {
            return service;
        }
    }

    public MutableLiveData<Boolean> getIsPlaying() {
        return isPlaying;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Boolean> getIsLife() {
        return isLife;
    }
}
