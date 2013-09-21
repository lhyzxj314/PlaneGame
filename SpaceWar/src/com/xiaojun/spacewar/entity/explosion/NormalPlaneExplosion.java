package com.xiaojun.spacewar.entity.explosion;

import java.util.Random;

import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.IParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.Sprite;

import android.opengl.GLES20;

import com.xiaojun.spacewar.manager.ResourceManager;

/***
 * ��ͨ�л��ı�ը
 * @author XiaoJun
 */
public class NormalPlaneExplosion {
	
	private  PointParticleEmitter pointParticleEmitter;
	private  SpriteParticleSystem pointSystem;
	
	public NormalPlaneExplosion() {
		initialParticleSystem();
	}
	
	/***
	 * ��ʼ��ը
	 * @param x ��ը��ĺ�����
	 * @param y	��ը���������
	 */
	public synchronized void startExplosion(final float x, final float y) {
		pointParticleEmitter.setCenter(x, y);
		ResourceManager.getEngine().getScene().attachChild(pointSystem);
		
		ResourceManager.getEngine().registerUpdateHandler(new TimerHandler(0.5f, new ITimerCallback(){

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				
				final RunnableHandler runnableRemoveHandler = new RunnableHandler();
				ResourceManager.getEngine().getScene().registerUpdateHandler(runnableRemoveHandler);
				
			    runnableRemoveHandler.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                    	ResourceManager.getEngine().getScene().detachChild(pointSystem);
                    }
                });
			}
			
		}));
	}
	
	/***
	 * ��ʼ������ϵͳ
	 */
	private void initialParticleSystem() {
		this.pointParticleEmitter = new PointParticleEmitter(0 , 0);
		this.pointSystem = new SpriteParticleSystem(pointParticleEmitter, 50, 50, 50, 
				ResourceManager.getInstance().particleTextureRegion1, ResourceManager.getVbom());
		
		pointSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		pointSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.7f, 0.1f, 0.1f));
		pointSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(0.5f));
		
		pointSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0f, 0.5f, 1f, 0.9f));
		pointSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0.2f, 0.4f, 0.8f, 0.5f, 0.2f, 0.4f, 0.1f, 0.2f));
		pointSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.3f, 0.4f, 1.0f, 0.4f));
		pointSystem.addParticleInitializer(new IParticleInitializer<Sprite>(){
			
			Random generator = new Random();  //�����������
			
			@Override
			public void onInitializeParticle(Particle<Sprite> pParticle) {
				  int ang = generator.nextInt(359);
	              float fVelocityX = (float) (Math.cos(Math.toRadians(ang)) * 100);
	              float fVelocityY = (float) (Math.sin(Math.toRadians(ang)) * 100);
	              pParticle.getPhysicsHandler().setVelocity(fVelocityX, fVelocityY);
	              // ����ÿ�����Ӽ��ٵļ��ٶ�
	              float fVelXopposite = -fVelocityX;
	              float fVelYopposite = -fVelocityY;
	              pParticle.getPhysicsHandler().setAcceleration(fVelXopposite * 0.4f, fVelYopposite * 0.4f);
	              
			}
		});
	}
	
}
