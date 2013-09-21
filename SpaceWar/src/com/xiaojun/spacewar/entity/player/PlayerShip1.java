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
	
	//����
	public static final float WIDTH = 30f;
	public static final float HEIGHT = 60f;
	public static final float HP = 100f;  //��Ѫ��
	public static float life = 100f;	 //ʣ��Ѫ��
	
	//����
	private int bulletLevel;

	//������
	public PlayerShip1(float x, float y) {
		animatedSprite = new AnimatedSprite(x, y,  WIDTH, HEIGHT, ResourceManager.getInstance().playerTextureRegion,
				ResourceManager.getVbom());
		this.bulletLevel = 1;
		animatedSprite.animate(new long[]{100, 100, 100, 100}, 0, 3, true);
	}
	
	
	/***
	 * ���ٷɻ�
	 */
	public void destroy() {
		//???
		animatedSprite.detachSelf();
	}
	

	/***
	 * �����ӵ�����ļ�����
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
				Log.i("Bullet1", "�������ӵ�:"+bullet.toString());
			}
			SoundManager.playShoot(1f, 0.5f);
			Log.i(TAG, "bullets:" + PlayerBullet.pool.getEntities().size());
			Log.i(TAG, "" + Arrays.toString(PlayerBullet.pool.getEntities().toArray()));
			Log.i(TAG, "bulletsToReuse:" + PlayerBullet.pool.getEnitytiesToReuse().size());
			Log.i(TAG, "" + Arrays.toString(PlayerBullet.pool.getEnitytiesToReuse().toArray()));
		}
		
	});
	
	/**
	 * ����
	 * */
	public void fire() {
		this.animatedSprite.registerUpdateHandler(timeHandler);
	}

	/***
	 * ֹͣ����
	 */
	public void stopFire() {
		this.animatedSprite.unregisterUpdateHandler(timeHandler);
	}
	
	/**
	 * ���о�����
	 * @return
	 */
	public void isHit(MyEntity obj) {
		//���ӵ�����
		if(obj instanceof EnemyBullet) {
			EnemyBullet bullet = (EnemyBullet)obj;
			life -= bullet.getDamage();
		}
		//���о���λ����
		if(obj instanceof EnemyUnit) {
			life -= 70f;
		}
	}
	
	/***
	 * �Ƿ�����
	 * @return ����Ϊtrue
	 */
	public boolean isDead() {
		return life<0;
	}
	
	/**
	 * ���÷ɻ��ĸ�������
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
