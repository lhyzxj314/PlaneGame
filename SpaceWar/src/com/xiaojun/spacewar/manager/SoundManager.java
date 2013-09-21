package com.xiaojun.spacewar.manager;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.util.debug.Debug;

/** 
 * 音效管理器
 **/
public class SoundManager {
	// ====================================================
	// 常量
	// ====================================================
	private static final SoundManager INSTANCE = new SoundManager();
	
	private static Music mGameMusic;
	private static Music mMenuMusic;
	private static Sound mExplosion;
	private static Sound mShoot;
	private static Sound mClick;
	private static Sound mClose;
	//private static Sound mClick;
	//private static Sound mCrate;
	//private static Sound mWood;
	
	// ====================================================
	// 获得音效管理器实例
	// ====================================================
	public static SoundManager getInstance(){
		return INSTANCE;
	}
	
	// ====================================================
	// 变量
	// ====================================================
	public boolean mSoundsMuted;
	public boolean mMusicMuted;
	
	// ====================================================
	// 构造器
	// ====================================================
	public SoundManager() {
		MusicFactory.setAssetBasePath("snd/");
		try {
			mGameMusic = MusicFactory.createMusicFromAsset(ResourceManager.getActivity().getMusicManager(), ResourceManager.getActivity(), "game_music.ogg");
			mGameMusic.setLooping(true);
			mMenuMusic = MusicFactory.createMusicFromAsset(ResourceManager.getActivity().getMusicManager(), ResourceManager.getActivity(), "menu_music.ogg");
			mMenuMusic.setLooping(true);		
		} catch (final IOException e) {
			Debug.e(e);
		}

		SoundFactory.setAssetBasePath("snd/");
		try {
			mExplosion = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "explosion1.ogg");
		} catch (final IOException e) { Debug.e(e); }
		try {
			mShoot = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "shoot1.ogg");
		} catch (final IOException e) { Debug.e(e); }
		try {
			mClick = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "click.ogg");
		} catch (final IOException e) { Debug.e(e); }
		try {
			mClose = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "close_sound.ogg");
		} catch (final IOException e) { Debug.e(e); }
	}
	
	// ====================================================
	// 方法
	// ====================================================
	private static void setVolumeForAllSounds(final float pVolume) {
		mExplosion.setVolume(pVolume);
		mShoot.setVolume(pVolume);
		mClick.setVolume(pVolume);
	}
	
	private static void setVolumeForAllMusic(final float pVolume) {
		mGameMusic.setVolume(pVolume);
		mMenuMusic.setVolume(pVolume);
	}
	
	public static boolean isSoundMuted() {
		return getInstance().mSoundsMuted;
	}
	
	public static void setSoundMuted(final boolean pMuted) {
		getInstance().mSoundsMuted = pMuted;
		//setVolumeForAllSounds((getInstance().mSoundsMuted? 0f:1f));
		//MagneTankActivity.writeIntToSharedPreferences(MagneTankActivity.SHARED_PREFS_SOUNDS_MUTED, (getInstance().mSoundsMuted? 1:0));
	}
	
	public static boolean toggleSoundMuted() {
		getInstance().mSoundsMuted = !getInstance().mSoundsMuted;
		setVolumeForAllSounds((getInstance().mSoundsMuted? 0f:1f));
		//MagneTankActivity.writeIntToSharedPreferences(MagneTankActivity.SHARED_PREFS_SOUNDS_MUTED, (getInstance().mSoundsMuted? 1:0));
		return getInstance().mSoundsMuted;
	}
	
	public static boolean isMusicMuted() {
		return getInstance().mMusicMuted;
	}
	
	public static void setMusicMuted(final boolean pMuted) {
		getInstance().mMusicMuted = pMuted;
		//setVolumeForAllMusic((getInstance().mMusicMuted? 0f:1f));
		//MagneTankActivity.writeIntToSharedPreferences(MagneTankActivity.SHARED_PREFS_MUSIC_MUTED, (getInstance().mMusicMuted? 1:0));
	}
	
	public static boolean toggleMusicMuted() {
		getInstance().mMusicMuted = !getInstance().mMusicMuted;
		if(getInstance().mMusicMuted) mGameMusic.pause(); else mGameMusic.play();
		//MagneTankActivity.writeIntToSharedPreferences(MagneTankActivity.SHARED_PREFS_MUSIC_MUTED, (getInstance().mMusicMuted? 1:0));
		return getInstance().mMusicMuted;
	}
	
	
	public static void playMusic() {
		if(!isMusicMuted()) {
			mGameMusic.seekTo(0);
			mGameMusic.play();
		}
		return ;
	}
	
	
	public static void pauseMusic() {
		mGameMusic.pause();
	}
	
	
	public static void resumeMusic() {
		if(!isMusicMuted())
			mGameMusic.resume();
	}
	
	public static void playMenuMusic() {
		if(!isMusicMuted()) {
			mMenuMusic.seekTo(0);
			mMenuMusic.play();
		} 
		return ;
	}
	
	public static void pauseMenuMusic() {
		mMenuMusic.pause();
	}
	
	public static void resumeMenuMusic() {
		if(!isMusicMuted())
			mMenuMusic.resume();
	}
	
	
	public static float getMusicVolume() {
		return mGameMusic.getVolume();
	}
	
	public static void setMusicVolume(final float pVolume) {
		mGameMusic.setVolume(pVolume);
	}
	
	public static void playExplosion(final float pRate, final float pVolume) {
		playSound(mExplosion,pRate,pVolume);
	}
	
	public static void playShoot(final float pRate, final float pVolume) {
		playSound(mShoot,pRate,pVolume);
	}
	
	public static void playClick(final float pRate, final float pVolume) {
		playSound(mClick, pRate, pVolume);
	}
	
	public static void playClose(final float pRate, final float pVolume) {
		playSound(mClose,pRate,pVolume);
	}
	
	
	private static void playSound(final Sound pSound, final float pRate, final float pVolume) {
		if(SoundManager.isSoundMuted()) return;
		pSound.setRate(pRate);
		pSound.setVolume(pVolume);
		pSound.play();
	}
}