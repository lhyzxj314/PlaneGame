package com.xiaojun.spacewar.util.pool.impl;

import org.andengine.engine.handler.physics.PhysicsHandler;

import android.util.Log;

import com.xiaojun.spacewar.entity.enemy.impl.Bullet1;
import com.xiaojun.spacewar.util.pool.EntityPool;

public class EnemyBullet1Pool extends EntityPool<Bullet1> {
	public static final String TAG = "EnemyBullet1Pool";
	
	public EnemyBullet1Pool() {
		super();
	}
	
	public EnemyBullet1Pool(int maximum) {
		super(maximum);
	}
	
	@Override
	public Bullet1 reuse(float x, float y) {
		Bullet1 bullet = super.reuse(x, y);
		float[] velocity = bullet.calculateVelocity();
		PhysicsHandler physicsHandler = bullet.getPhysicsHandler();
		physicsHandler.setVelocity(velocity[0], velocity[1]);
		bullet.getAnimatedSprite().registerUpdateHandler(bullet.getPhysicsHandler());
		Log.i(TAG, "�л��ӵ����ã�" + bullet.toString());
		return bullet;
	}

	@Override
	public void recycle(Bullet1 bullet) {
		super.recycle(bullet);
		Log.i(TAG, "�л��ӵ����գ�" + bullet.toString());
	}

	@Override
	public void destroy(Bullet1 bullet) {
		super.destroy(bullet);
		Log.i(TAG, "�л��ӵ����٣�" + bullet.toString() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
}
