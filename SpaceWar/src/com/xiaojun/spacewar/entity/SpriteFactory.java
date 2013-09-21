package com.xiaojun.spacewar.entity;

import com.xiaojun.spacewar.entity.player.PlayerBullet;
import com.xiaojun.spacewar.entity.player.PlayerShip1;

public class SpriteFactory {
	/**
	 * 获得玩家飞船实例
	 * */
	public static PlayerShip1 getPlayerShip1(float pX, float pY) {
		return new PlayerShip1(pX, pY);
	}
	
	/**
	 * 获得子弹1实例
	 * */
	public static PlayerBullet getBullet1(float pX, float pY) {
		return new PlayerBullet(pX, pY);
	}
	
	
	
}
