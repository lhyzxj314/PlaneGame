package com.xiaojun.spacewar.util.pool.impl;

import android.util.Log;

import com.xiaojun.spacewar.entity.enemy.impl.NormalPlane;
import com.xiaojun.spacewar.util.pool.EntityPool;

public class NormalPlanePool extends EntityPool<NormalPlane> {
	public static final String TAG = "NormalPlanePool";
	@Override
	public synchronized void recycle(NormalPlane entity) {
		super.recycle(entity);
		Log.i(TAG, "回收飞机：" + entity.toString());
	}

	@Override
	public synchronized NormalPlane reuse(float x, float y) {
		NormalPlane plane = super.reuse(x, y);
		plane.reset();
		
		plane.getAnimatedSprite().registerEntityModifier(plane.getPathModifier());
		plane.getAnimatedSprite().registerUpdateHandler(plane.getTimeHandler());
		
		Log.i(TAG, "重用飞机：" + plane.toString());
		return plane;
	}
	
	@Override
	public synchronized NormalPlane reuse(int num) {
		NormalPlane plane = super.reuse(num);
		plane.reset();
		
		//plane.getAnimatedSprite().registerEntityModifier(plane.getPathModifier());
		plane.getAnimatedSprite().registerUpdateHandler(plane.getTimeHandler());
		
		Log.i(TAG, "重用飞机：" + plane.toString());
		return plane;
	}

	@Override
	public synchronized void destroy(NormalPlane entity) {
		super.destroy(entity);
		Log.i(TAG, "销毁飞机：" + entity.toString() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	
}
