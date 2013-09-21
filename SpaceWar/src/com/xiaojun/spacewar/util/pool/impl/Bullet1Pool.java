package com.xiaojun.spacewar.util.pool.impl;

import android.util.Log;

import com.xiaojun.spacewar.entity.player.PlayerBullet;
import com.xiaojun.spacewar.util.pool.EntityPool;

public class Bullet1Pool extends EntityPool<PlayerBullet>{
	public static final String TAG = "Bullet1Pool";
	
	public Bullet1Pool() {
		super();
	}
	
	public Bullet1Pool(int maximum) {
		super(maximum);
	}
	
	@Override
	public PlayerBullet reuse(float x, float y) {
		PlayerBullet bullet = super.reuse(x, y);
		bullet.getAnimatedSprite().registerUpdateHandler(bullet.getPhysicsHandler());
		Log.i(TAG, "�ӵ����ã�" + bullet.toString());
		return bullet;
	}

	@Override
	public void recycle(PlayerBullet entity) {
		super.recycle(entity);
		entity.setHasDetected(false);
		Log.i(TAG, "�ӵ����գ�" + entity.toString());
	}

	@Override
	public void destroy(PlayerBullet entity) {
		super.destroy(entity);
		Log.i(TAG, "�ӵ����٣�" + entity.toString() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	
}
