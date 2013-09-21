package com.xiaojun.spacewar.entity;

import com.xiaojun.spacewar.entity.player.PlayerBullet;
import com.xiaojun.spacewar.entity.player.PlayerShip1;

public class SpriteFactory {
	/**
	 * �����ҷɴ�ʵ��
	 * */
	public static PlayerShip1 getPlayerShip1(float pX, float pY) {
		return new PlayerShip1(pX, pY);
	}
	
	/**
	 * ����ӵ�1ʵ��
	 * */
	public static PlayerBullet getBullet1(float pX, float pY) {
		return new PlayerBullet(pX, pY);
	}
	
	
	
}
