package com.xiaojun.spacewar.entity.player;


import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;

import com.xiaojun.spacewar.entity.MyEntity;
import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.util.pool.EntityPool;
import com.xiaojun.spacewar.util.pool.impl.Bullet1Pool;

/***
 * ����ӵ�����1
 * @author XiaoJun
 */
public class PlayerBullet extends MyEntity{
	public static final String TAG = "Bullet1";
	public static int TEMP = 0;
	private int id = 0;
	// ����
	public static final float WIDTH = 28f;
	public static final float HEIGH = 50f;
	public static EntityPool<PlayerBullet> pool = new Bullet1Pool(15);     //�����
	
	// ����
	private float velocity = 1000f;
	private float damage = 40f;				 	//������
	private PhysicsHandler physicsHandler;   	//�ӵ��ٶȿ�����
	private boolean hasDetected = false;
	
	// ---------------------------------------------------
	// constructor
	// ---------------------------------------------------
	/***
	 * ������
	 * @param pX �ӵ���ʼ������
	 * @param pY �ӵ���ʼ������
	 */
	public PlayerBullet(float pX, float pY) {
		TEMP++;
		id = TEMP;
		this.animatedSprite = new AnimatedSprite(pX, pY, WIDTH, HEIGH, ResourceManager.getInstance().bulletTextureRegion,
				ResourceManager.getInstance().vbom){

					@Override
					protected void onManagedUpdate(float pSecondsElapsed) {
						if( this.isCulled(ResourceManager.getCamera()) ) {
							//���߳̽���ÿ��update�������ִ��
							ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									try {
										pool.recycle(PlayerBullet.this);
									} catch (Throwable e) {
										e.printStackTrace();
									}
								}
								
							});
						}
						super.onManagedUpdate(pSecondsElapsed);
					}
			
		};
		
		//�����ӵ��ĳ��ٶ�
	    physicsHandler = new PhysicsHandler(animatedSprite);
		physicsHandler.setVelocityY(-velocity);
		this.animatedSprite.registerUpdateHandler(physicsHandler);
	}
	
	
	// ---------------------------------------------------
	// method
	// ---------------------------------------------------
	
	// ---------------------------------------------------
	// getter��setter
	// ---------------------------------------------------
	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}


	public float getDamage() {
		return damage;
	}


	public void setDamage(float damage) {
		this.damage = damage;
	}


	public PhysicsHandler getPhysicsHandler() {
		return physicsHandler;
	}


	public void setPhysicsHandler(PhysicsHandler physicsHandler) {
		this.physicsHandler = physicsHandler;
	}


	@Override
	public String toString() {
		return "Bullet1("+ id +")";
	}


	public boolean isHasDetected() {
		return hasDetected;
	}


	public void setHasDetected(boolean hasDetected) {
		this.hasDetected = hasDetected;
	}
	
	
	/*@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		
		if(this.getY() < -this.getHeight() ) {
			Log.i(TAG, "�����ˣ���������������������������������������������������������������");
			//���߳̽���ÿ��update�������ִ��
			ResourceManager.getInstance().engine.runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					try {
						Bullet1.this.detachSelf();
						ResourceManager.bullets.remove(Bullet1.this);
						ResourceManager.bulletsToReuse.add(Bullet1.this);
						if(ResourceManager.bulletsToReuse.size() > 12) {
							ResourceManager.bulletsToReuse.remove(0);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
				
			});
		}
		super.onManagedUpdate(pSecondsElapsed);
	}*/


}
