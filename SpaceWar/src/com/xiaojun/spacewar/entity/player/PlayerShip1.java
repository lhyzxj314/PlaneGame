package com.xiaojun.spacewar.entity.player;

import java.util.Arrays;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;

import com.xiaojun.spacewar.entity.MyEntity;
import com.xiaojun.spacewar.entity.enemy.EnemyBullet;
import com.xiaojun.spacewar.entity.enemy.EnemyUnit;
import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.manager.SoundManager;

public class PlayerShip1 extends MyEntity {
	public static final String TAG = "PlayerShip1";
	
	//常量
	public static final float WIDTH = 30f;
	public static final float HEIGHT = 60f;
	public static final float HP = 100f;  //满血量
	public static float life = 100f;	 //剩余血量
	
	//变量
	private int bulletLevel;

	//构造器
	public PlayerShip1(float x, float y) {
		animatedSprite = new AnimatedSprite(x, y,  WIDTH, HEIGHT, ResourceManager.getInstance().playerTextureRegion,
				ResourceManager.getVbom());
		this.bulletLevel = 1;
		animatedSprite.animate(new long[]{100, 100, 100, 100}, 0, 3, true);
	}
	
	
	/***
	 * 销毁飞机
	 */
	public void destroy() {
		//???
		animatedSprite.detachSelf();
	}
	

	/***
	 * 控制子弹发射的监听器
	 */
	private TimerHandler timeHandler = new TimerHandler(0.1f, true, new ITimerCallback(){

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			PlayerBullet bullet = null;
			if(PlayerBullet.pool.getEnitytiesToReuse().size() > 0) {
				PlayerBullet.pool.reuse(PlayerShip1.this.animatedSprite.getX(), PlayerShip1.this.animatedSprite.getY()-30);
			}else{
				bullet = new PlayerBullet(PlayerShip1.this.animatedSprite.getX(), PlayerShip1.this.animatedSprite.getY()-30);
				PlayerBullet.pool.getEntities().add(bullet);
				bullet.addToSecne();
				Log.i("Bullet1", "新生成子弹:"+bullet.toString());
			}
			SoundManager.playShoot(1f, 0.5f);
			Log.i(TAG, "bullets:" + PlayerBullet.pool.getEntities().size());
			Log.i(TAG, "" + Arrays.toString(PlayerBullet.pool.getEntities().toArray()));
			Log.i(TAG, "bulletsToReuse:" + PlayerBullet.pool.getEnitytiesToReuse().size());
			Log.i(TAG, "" + Arrays.toString(PlayerBullet.pool.getEnitytiesToReuse().toArray()));
		}
		
	});
	
	/**
	 * 开火
	 * */
	public void fire() {
		this.animatedSprite.registerUpdateHandler(timeHandler);
	}

	/***
	 * 停止开火
	 */
	public void stopFire() {
		this.animatedSprite.unregisterUpdateHandler(timeHandler);
	}
	
	/**
	 * 被敌军击中
	 * @return
	 */
	public void isHit(MyEntity obj) {
		//被子弹击中
		if(obj instanceof EnemyBullet) {
			EnemyBullet bullet = (EnemyBullet)obj;
			life -= bullet.getDamage();
		}
		//被敌军单位击中
		if(obj instanceof EnemyUnit) {
			life -= 70f;
		}
	}
	
	/***
	 * 是否阵亡
	 * @return 阵亡为true
	 */
	public boolean isDead() {
		return life<0;
	}
	
	/**
	 * 重置飞机的各项属性
	 */
	public void reset() {
		this.bulletLevel = 1;
		life = HP;
	}
	
	public int getBulletLevel() {
		return bulletLevel;
	}

	public void setBulletLevel(int bulletLevel) {
		this.bulletLevel = bulletLevel;
	}
	
	
	
	
}
