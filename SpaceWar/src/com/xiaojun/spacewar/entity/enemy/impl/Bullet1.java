package com.xiaojun.spacewar.entity.enemy.impl;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;

import com.xiaojun.spacewar.entity.enemy.EnemyBullet;
import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.scene.impl.GameScene;
import com.xiaojun.spacewar.util.pool.EntityPool;
import com.xiaojun.spacewar.util.pool.impl.EnemyBullet1Pool;

public class Bullet1 extends EnemyBullet {
	public static int ID;
	private int id;
	
	// 变量
	private float velocity = 200f;				//子弹速度大小
	private PhysicsHandler physicsHandler;   	//子弹速度控制器
	public static EntityPool<Bullet1> pool = new EnemyBullet1Pool(15);     //对象池
	
	// 构造器
	public Bullet1(float x, float y) {
		ID++;
		this.damage = 20f;       // 设定攻击力
		this.animatedSprite = new AnimatedSprite(x, y, 10f, 10f,
				ResourceManager.getInstance().bulletForNormalPlaneTextureRegion,
				ResourceManager.getVbom()){

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if( this.isCulled(ResourceManager.getCamera()) ) {
					//该线程仅在每个update周期最后执行
					ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							try {
								pool.recycle(Bullet1.this);
							} catch (Throwable e) {
								e.printStackTrace();
							}
						}
						
					});
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
	
		};
		this.id = ID;
		
		//设置子弹的初速度
	    physicsHandler = new PhysicsHandler(animatedSprite);
	    float[] velocity = calculateVelocity();
	    physicsHandler.setVelocity(velocity[0], velocity[1]);
		this.animatedSprite.registerUpdateHandler(physicsHandler);
	}
	
	/**
	 * 计算子弹的速度(矢量)
	 * @return 速度矢量
	 */
	public float[] calculateVelocity() {
		float[] velocity = new float[2];   // 速度,velocity[0]为横向分速度,velocity[1]为纵向分速度
		// 计算攻击目标的坐标
		float targetCenterX = GameScene.playerShip.getAnimatedSprite().getWidth()/2f;
		float targetCenterY = GameScene.playerShip.getAnimatedSprite().getHeight()/2f;
		float[] targetCoordinates = GameScene.playerShip.getAnimatedSprite().convertLocalToSceneCoordinates(targetCenterX, targetCenterY);
		
		float eX = this.animatedSprite.getX();
		float eY = this.animatedSprite.getY();
		float angle =(float) Math.atan2(targetCoordinates[0]-eX, targetCoordinates[1]-eY);
		float velocityX = (float) Math.sin(angle)*this.velocity;
		float velocityY = (float) Math.cos(angle)*this.velocity;
		velocity[0] = velocityX;
		velocity[1] = velocityY;
		return velocity;
	} 
	
	// 添加到屏幕
	public void addToSecne(float x, float y) {
		this.animatedSprite.setPosition(x, y);
		ResourceManager.getEngine().getScene().attachChild(animatedSprite);
	}
	
	public void addToSecne() {
		ResourceManager.getEngine().getScene().attachChild(animatedSprite);
	}
	
	@Override
	public String toString() {
		return "EnemyBullet" + id;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public PhysicsHandler getPhysicsHandler() {
		return physicsHandler;
	}

	public void setPhysicsHandler(PhysicsHandler physicsHandler) {
		this.physicsHandler = physicsHandler;
	}

}
