package com.xiaojun.spacewar.entity.explosion;

import java.util.Random;

import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.IParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.Sprite;

import android.opengl.GLES20;

import com.xiaojun.spacewar.manager.ResourceManager;

public class SparkExplosion {

	private  CircleOutlineParticleEmitter circleOutlineParticleEmitter;
	private  SpriteParticleSystem circleSystem;
	
	public SparkExplosion() {
		initialParticleSystem();
	}
	
	/***
	 * 开始爆炸
	 * @param x 爆炸点的横坐标
	 * @param y	爆炸点的纵坐标
	 */
	public synchronized void startExplosion(final float x, final float y) {
		circleOutlineParticleEmitter.setCenter(x, y);
		ResourceManager.getEngine().getScene().attachChild(circleSystem);
		
		ResourceManager.getEngine().registerUpdateHandler(new TimerHandler(1f, new ITimerCallback(){
	
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				
				final RunnableHandler runnableRemoveHandler = new RunnableHandler();
				ResourceManager.getEngine().getScene().registerUpdateHandler(runnableRemoveHandler);
				
			    runnableRemoveHandler.postRunnable(new Runnable() {
	                @Override
	                public void run() {
	                	ResourceManager.getEngine().getScene().detachChild(circleSystem);
	                }
	            });
			}
			
		}));
	}

	
	/***
	 * 初始化粒子系统
	 */
	private void initialParticleSystem() {
		
		circleOutlineParticleEmitter = new CircleOutlineParticleEmitter(0, 0,5);
		this.circleSystem = new SpriteParticleSystem(circleOutlineParticleEmitter, 50, 50, 50, ResourceManager.getInstance().particleTextureRegion1, ResourceManager.getVbom());
		
		circleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		circleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.3f, 0.7f, 0.5f));
		circleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(11.5f));
		//particleSystem1.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3.5f));
		
		circleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0f, 5f, 1f, 0.1f));
		//particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(1.5f, 2.5f, 1.0f, 0.0f));
		//particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(3.5f, 9.5f, 1.0f, 0.0f));
		circleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(2.5f, 3.5f, 1.0f, 0.0f));
		circleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(4.5f, 9.5f, 1.0f, 0.0f));
		circleSystem.addParticleInitializer(new IParticleInitializer<Sprite>(){
		Random generator = new Random();
			
			@Override
			public void onInitializeParticle(Particle<Sprite> pParticle) {
				  int ang = generator.nextInt(359);
	              float fVelocityX = (float) (Math.cos(Math.toRadians(ang)) * 120);
	              float fVelocityY = (float) (Math.sin(Math.toRadians(ang)) * 120);
	              pParticle.getPhysicsHandler().setVelocity(fVelocityX, fVelocityY);
	              // calculate air resistance that acts opposite to particle
	              // velocity
	              float fVelXopposite = -fVelocityX;
	              float fVelYopposite = -fVelocityY;
	              // x% of deceleration is applied (that is opposite to velocity)
	              pParticle.getPhysicsHandler().setAcceleration(fVelXopposite * 0.2f, fVelYopposite * 0.2f);
	              
			}
		});
	}
	
}


