package com.xiaojun.spacewar;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.IParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.opengl.GLES20;
import android.widget.Toast;


public class ParticleSystemSimpleExample extends SimpleBaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mParticleTextureRegion;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		Toast.makeText(this, "Touch the screen to move the particlesystem.", Toast.LENGTH_LONG).show();

		this.mCamera = new Camera(0, 0, ParticleSystemSimpleExample.CAMERA_WIDTH, ParticleSystemSimpleExample.CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(ParticleSystemSimpleExample.CAMERA_WIDTH, ParticleSystemSimpleExample.CAMERA_HEIGHT), this.mCamera);
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mParticleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "particle_point.png", 0, 0);

		this.mBitmapTextureAtlas.load();
	}
	
	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();

		final CircleOutlineParticleEmitter particleEmitter = new CircleOutlineParticleEmitter(ParticleSystemSimpleExample.CAMERA_WIDTH * 0.5f, ParticleSystemSimpleExample.CAMERA_HEIGHT * 0.5f + 20, 10);
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(particleEmitter, 40, 90, 460, this.mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
//				pointParticleEmitter.setCenter(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				
				startSpark(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				return true;
			}
		});
		
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.3f, 0.7f, 0.5f));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0.3f));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-10, 20, -10, 20));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(1.2f));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0f, 5f, 1f, 0.1f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0, 1.5f, 1, 1, 0, 0.5f, 0, 0));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0, 1.5f, 1, 1, 0.5f, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(1f, 0.2f, 0.5f, 1.5f));
		
		
		
		
		
		
/*		
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0, 2, 1, 1, 0, 0.5f, 0, 0));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(4, 6, 1, 1, 0.5f, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));
*/
		//scene.attachChild(particleSystem);
		
		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	
	public void startExplosion(float pX, float pY) {
		final CircleOutlineParticleEmitter pointParticleEmitter = new CircleOutlineParticleEmitter(ParticleSystemSimpleExample.CAMERA_WIDTH , ParticleSystemSimpleExample.CAMERA_HEIGHT,5);
		final SpriteParticleSystem particleSystem1 = new SpriteParticleSystem(pointParticleEmitter, 50, 50, 50, this.mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		particleSystem1.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem1.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.3f, 0.7f, 0.5f));
		particleSystem1.addParticleInitializer(new ExpireParticleInitializer<Sprite>(11.5f));
		//particleSystem1.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3.5f));
		
		particleSystem1.addParticleModifier(new ScaleParticleModifier<Sprite>(0f, 5f, 1f, 0.1f));
		//particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(1.5f, 2.5f, 1.0f, 0.0f));
		//particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(3.5f, 9.5f, 1.0f, 0.0f));
		particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(2.5f, 3.5f, 1.0f, 0.0f));
		particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(4.5f, 9.5f, 1.0f, 0.0f));
		particleSystem1.addParticleInitializer(new IParticleInitializer<Sprite>(){
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
	              pParticle.getPhysicsHandler().setAcceleration(fVelXopposite * 0.3f, fVelYopposite * 0.3f);
	              
			}
		});
		
		this.mEngine.getScene().attachChild(particleSystem1);
		pointParticleEmitter.setCenter(pX, pY);
		
		this.mEngine.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback(){

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				final RunnableHandler runnableRemoveHandler = new RunnableHandler();
			    ParticleSystemSimpleExample.this.mEngine.getScene().registerUpdateHandler
			    			(runnableRemoveHandler);
			    runnableRemoveHandler.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                    	ParticleSystemSimpleExample.this.getEngine().getScene().
                    				detachChild(particleSystem1);
                    }
                });
			}
			
		}));
	}
	
	public void startSpark(float pX, float pY) {
		final PointParticleEmitter pointParticleEmitter = new PointParticleEmitter(ParticleSystemSimpleExample.CAMERA_WIDTH , ParticleSystemSimpleExample.CAMERA_HEIGHT);
		final SpriteParticleSystem pointSystem = new SpriteParticleSystem(pointParticleEmitter, 50, 50, 50, this.mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		pointSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		pointSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(0.7f, 0.1f, 0.1f));
		pointSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(0.5f));
		//particleSystem1.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3.5f));
		
		pointSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0f, 0.5f, 1f, 0.9f));
		pointSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0.2f, 0.4f, 0.8f, 0.5f, 0.2f, 0.4f, 0.1f, 0.2f));
		//particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(3.5f, 9.5f, 1.0f, 0.0f));
		pointSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.3f, 0.4f, 1.0f, 0.4f));
//		pointSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.3f, 0.5f, 1.0f, 0.0f));
		pointSystem.addParticleInitializer(new IParticleInitializer<Sprite>(){
		Random generator = new Random();
			
			@Override
			public void onInitializeParticle(Particle<Sprite> pParticle) {
				  int ang = generator.nextInt(359);
	              float fVelocityX = (float) (Math.cos(Math.toRadians(ang)) * 100);
	              float fVelocityY = (float) (Math.sin(Math.toRadians(ang)) * 100);
	              pParticle.getPhysicsHandler().setVelocity(fVelocityX, fVelocityY);
	              // calculate air resistance that acts opposite to particle
	              // velocity
	              float fVelXopposite = -fVelocityX;
	              float fVelYopposite = -fVelocityY;
	              // x% of deceleration is applied (that is opposite to velocity)
	              pParticle.getPhysicsHandler().setAcceleration(fVelXopposite * 0.4f, fVelYopposite * 0.4f);
	              
			}
		});
		
		this.mEngine.getScene().attachChild(pointSystem);
		pointParticleEmitter.setCenter(pX, pY);
		
		this.mEngine.registerUpdateHandler(new TimerHandler(0.5f, new ITimerCallback(){

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				final RunnableHandler runnableRemoveHandler = new RunnableHandler();
			    ParticleSystemSimpleExample.this.mEngine.getScene().registerUpdateHandler
			    			(runnableRemoveHandler);
			    runnableRemoveHandler.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                    	ParticleSystemSimpleExample.this.getEngine().getScene().
                    				detachChild(pointSystem);
                    }
                });
			}
			
		}));
	}
	
	
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
