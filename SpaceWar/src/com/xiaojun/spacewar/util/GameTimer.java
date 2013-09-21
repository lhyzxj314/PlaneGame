package com.xiaojun.spacewar.util;

import android.util.Log;

public class GameTimer {
	public static final String TAG = "GameTimer";
	
	private float time = 0f;
	public int seconds = 0;
	private int temp = 0;
	
	/**
	 * ��ʱ(��)
	 * @param offset ÿ�θ��¹������ŵ�ʱ��
	 * @return false,��ʱ��δ��һ��;������һ�뷵��true
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
	
	// �������
	public int getSeconds() {
		return seconds;
	}
	
	
	/**
	 * ���¿�ʼ��ʱ
	 */
	public void reset () {
		this.seconds = 0;
		this.time = 0;
		this.temp = 0;
	}
	
	
}
