package com.xiaojun.spacewar.util;

import android.util.Log;

public class GameTimer {
	public static final String TAG = "GameTimer";
	
	private float time = 0f;
	public int seconds = 0;
	private int temp = 0;
	
	/**
	 * 计时(秒)
	 * @param offset 每次更新过程流逝的时间
	 * @return false,若时间未过一秒;经过了一秒返回true
	 */
	public boolean passSecond(float offset) {
		time += offset;
	    seconds = (int) Math.floor(time);
		if(seconds > temp) {
			temp = seconds;
			return true;
		}
		return false;
	}
	
	// 获得秒数
	public int getSeconds() {
		return seconds;
	}
	
	
	/**
	 * 重新开始计时
	 */
	public void reset () {
		this.seconds = 0;
		this.time = 0;
		this.temp = 0;
	}
	
	
}
