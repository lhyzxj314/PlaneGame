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
	
	// ����
	private float velocity = 200f;				//�ӵ��ٶȴ�С
	private PhysicsHandler physicsHandler;   	//�ӵ��ٶȿ�����
	public static EntityPool<Bullet1> pool = new EnemyBullet1Pool(15);     //�����
	
	// ������
	public Bullet1(float x, float y) {
		ID++;
		this.damage = 20f;       // �趨������
		this.animatedSprite = new AnimatedSprite(x, y, 10f, 10f,
				ResourceManager.getInstance().bulletForNormalPlaneTextureRegion,
				ResourceManager.getVbom()){

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if( this.isCulled(ResourceManager.getCamera()) ) {
					//���߳̽���ÿ��update�������ִ��
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
		
		//�����ӵ��ĳ��ٶ�
	    physicsHandler = new PhysicsHandler(animatedSprite);
	    float[] velocity = calculateVelocity();
	    physicsHandler.setVelocity(velocity[0], velocity[1]);
		this.animatedSprite.registerUpdateHandler(physicsHandler);
	}
	
	/**
	 * �����ӵ����ٶ�(ʸ��)
	 * @return �ٶ�ʸ��
	 */
	public float[] calculateVelocity() {
		float[] velocity = new float[2];   // �ٶ�,velocity[0]Ϊ������ٶ�,velocity[1]Ϊ������ٶ�
		// ���㹥��Ŀ�������
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
	
	// ��ӵ���Ļ
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
