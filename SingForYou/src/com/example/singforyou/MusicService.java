package com.example.singforyou;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {
	public final IBinder binder = new myBinder();
	public MediaPlayer mp = new MediaPlayer();
	public MusicService() {
		try{
			mp.setDataSource("/data/My.mp3");
			mp.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void playorpause() {
		if (mp.isPlaying()) {
			mp.pause();
		} else {
			mp.start();
		}
	}
	
	public void stop() {
		if (mp != null) {
			mp.stop();
			try {
				mp.prepare();
				mp.seekTo(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onDestroy() {
		mp.stop();
		mp.release();
		super.onDestroy();
	}
	
	public class myBinder extends Binder {
		MusicService getService() {
			return MusicService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}
}
