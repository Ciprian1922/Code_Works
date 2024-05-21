package com.example.mypiano;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.SparseIntArray;

public class SoundManager {
    private SoundPool mSoundPool;
    private SparseIntArray mSoundPoolMap;
    private boolean mMute = false;
    private Context mContext;

    private static final int MAX_STREAM = 10;
    private static final int STOP_DELAY_MILLIS = 1000;
    private Handler mHandler;

    private static SoundManager instance = null;

    public SoundManager() {
        mSoundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap = new SparseIntArray();
        mHandler = new Handler();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
        initStreamTypeMedia((Activity) mContext);
        addSound(R.raw.c3, R.raw.c3);
        addSound(R.raw.c4, R.raw.c4);
        addSound(R.raw.d3, R.raw.d3);
        addSound(R.raw.d4, R.raw.d4);
        addSound(R.raw.e3, R.raw.e3);
        addSound(R.raw.e4, R.raw.e4);
        addSound(R.raw.f3, R.raw.f3);
        addSound(R.raw.f4, R.raw.f4);
        addSound(R.raw.g3, R.raw.g3);
        addSound(R.raw.g4, R.raw.g4);
        addSound(R.raw.a3, R.raw.a3);
        addSound(R.raw.a4, R.raw.a4);
        addSound(R.raw.b3, R.raw.b3);
        addSound(R.raw.db3, R.raw.db3);
        addSound(R.raw.db4, R.raw.db4);
        addSound(R.raw.eb3, R.raw.eb3);
        addSound(R.raw.eb4, R.raw.eb4);
        addSound(R.raw.gb3, R.raw.gb3);
        addSound(R.raw.gb4, R.raw.gb4);
        addSound(R.raw.ab3, R.raw.ab3);
        addSound(R.raw.ab4, R.raw.ab4);
        addSound(R.raw.bb3, R.raw.bb3);
        addSound(R.raw.bb4, R.raw.bb4);
    }

    private void initStreamTypeMedia(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    private void addSound(int resId, int soundID) {
        mSoundPoolMap.put(resId, mSoundPool.load(mContext, soundID, 1));
    }

    public void playSound(int resId) {
        if (mMute) return;
        boolean hasSound = mSoundPoolMap.indexOfKey(resId) >= 0;
        if (!hasSound) return;
        final int soundId = mSoundPool.play(mSoundPoolMap.get(resId), 1, 1, 1, 0, 1f);
        scheduleSoundStop(soundId);
    }

    private void scheduleSoundStop(final int soundId) {
        mHandler.postDelayed(() -> mSoundPool.stop(soundId), STOP_DELAY_MILLIS);
    }
}
