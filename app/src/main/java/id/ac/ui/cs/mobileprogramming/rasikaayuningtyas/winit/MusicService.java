package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    private MediaPlayer musicplayer;
    private boolean isplaying = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if(!isplaying) {
            musicplayer = MediaPlayer.create(this, R.raw.replay);
            musicplayer.setLooping(true);
            musicplayer.start();
            isplaying = true;
        }
        else{
            super.onDestroy();
            musicplayer.stop();
            isplaying = false;
            musicplayer = MediaPlayer.create(this, R.raw.replay);
            musicplayer.setLooping(true);
            musicplayer.start();
            isplaying = true;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicplayer.stop();
        isplaying = false;
    }


}
