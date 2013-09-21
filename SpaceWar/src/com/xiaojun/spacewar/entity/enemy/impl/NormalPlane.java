package com.xiaojun.spacewar.entity.enemy.impl;

import java.util.Arrays;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.sprite.AnimatedSprite;

import android.util.Log;

import com.xiaojun.spacewar.entity.enemy.EnemyUnit;
import com.xiaojun.spacewar.entity.player.PlayerBullet;
import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.util.pool.impl.NormalPlanePool;

/**
 * 敌机(普通飞机)
 * */
public class NormalPlane extends EnemyUnit {
	public static final String TAG = "NormalPlane";
	public static int TEMP = 0;
	public int id = 0;
	//常量
	public static final float WIDTH = 30f;
	public static final float HEIGHT = 60f;
	public static NormalPlanePool pool = new NormalPlanePool();
	
	//变量
	private float life = 100f;

	/**
	 * 构造器
	 * @param num 路径编号
	 */
	public NormalPlane(int num) {
		super(num);
		this.score = 100;  //消灭该敌机可以获得的积分
		this.animatedSprite = new AnimatedSprite(0, 0,  WIDTH, HEIGHT, ResourceManager.getInstance().normalPlaneTextureRegion,
				ResourceManager.getVbom());
		this.animatedSprite.animate(new long[]{100, 100, 100, 100}, 0, 3, true);
		this.animatedSprite.registerEntityModifier(pathModifier);
		id = TEMP++;
		
		this.startfire();
	}
	
	
	
	// 被子弹击中
	public void isHit(PlayerBullet playerBullet) {
		this.life -= playerBullet.getDamage();
		//ColorModifier modifier = new ColorModifier(1, Color.WHITE, Color.BLACK);
		AlphaModifier modifier = new AlphaModifier(0.2f, 0.2f, 1f);
		this.animatedSprite.registerEntityModifier(modifier);
	}
	
	// 飞机被摧毁
	public boolean isDead() {
		return life<0;
	}
	
	// 添加到屏幕
	public void addToSecne(float x, float y) {
		this.animatedSprite.setPosition(x, y);
		ResourceManager.getEngine().getScene().attachChild(animatedSprite);
	}
	
	public void addToSecne() {
		ResourceManager.getEngine().getScene().attachChild(animatedSprite);
	}
	
	/**
	 * 开始攻击
	 * */
	private void startfire() {
		this.animatedSprite.registerUpdateHandler(timeHandler);
	}

	public float getLife() {
		return life;
	}

	public void setLife(float life) {
		this.life = life;
	}
	
	
	public TimerHandler getTimeHandler() {
		return timeHandler;
	}

	public void setTimeHandler(TimerHandler timeHandler) {
		this.timeHandler = timeHandler;
	}

	/**
	 * 初始化飞机
	 */
	public void reset() {
		this.life = 100;
		this.pathModifier.reset();
		this.animatedSprite.reset();
	}
	
	@Override
	public String toString() {
		return "Plane" + this.id;
	}
	
	
	/***
	 * 控制子弹发射的监听器
	 */
	private TimerHandler timeHandler = new TimerHandler(1.1f, true, new ITimerCallback(){

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			Bullet1 bullet = null;
			// 计算子弹发出点坐标
			float pX = NormalPlane.this.animatedSprite.getWidth()/2f;
			float pY = NormalPlane.this.animatedSprite.getHeight();
			float[] shootCoordinates = NormalPlane.this.animatedSprite.convertLocalToSceneCoordinates(pX, pY);
			
			if(Bullet1.pool.getEnitytiesToReuse().size() > 0) {
				Bullet1.pool.reuse(shootCoordinates[0], shootCoordinates[1]);
			}else{
				bullet = new Bullet1(shootCoordinates[0], shootCoordinates[1]);
				Bullet1.pool.getEntities().add(bullet);
				bullet.addToSecne();
				Log.i("EnemyBullet", "新生成子弹:"+bullet.toString());
			}
			
			Log.i(TAG, "bullets:" + Bullet1.pool.getEntities().size());
			Log.i(TAG, "" + Arrays.toString(Bullet1.pool.getEntities().toArray()));
			Log.i(TAG, "bulletsToReuse:" + Bullet1.pool.getEnitytiesToReuse().size());
			Log.i(TAG, "" + Arrays.toString(Bullet1.pool.getEnitytiesToReuse().toArray()));
		}
		
	});

	// 回收对象
	@Override
	protected void recycle() {
		ResourceManager.getActivity().runOnUpdateThread(
				new Runnable() {

					@Override
					public void run() {
						pool.recycle(NormalPlane.this);
						Log.d("NormalPlane1", "动画结束了！！！！！！！！！！！！");
					}

				});
	}



	
	
}
