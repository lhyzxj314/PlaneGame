package com.xiaojun.spacewar.scene.impl;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.manager.SceneManager;
import com.xiaojun.spacewar.scene.ManagedSplashScreen;

/** 
 *  The SplashScreen class uses entity modifiers and resolution-independent
 *  positioning to show the splash screens of the game. Each logo is
 *  clickable and starts an Intent related to the logo.
 *  
 *  This class is unique because it is seen only at the beginning of the game.
 *  After it is hidden, it's never used again. It also has to load as quickly
 *  as possible, and unload without a noticeable lag from garbage collection.
 *  
*** @author Ð¤¿¡ - ºúÂÜ²·ÓÎÏ·¹¤×÷ÊÒ
**/
public class SplashScreens extends ManagedSplashScreen {

	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final float mEachAnimationDuration = 3f;
//	private static final float mEachAnimationPauseDuration = 2.2f;
//	private static final float mEachScaleToSize = 0.7f * ResourceManager.getInstance().CAMERA_HEIGHT/3f;
	
	
	
	private static final BitmapTextureAtlas CarrotStutioTexture;
	private static final ITextureRegion CarrotStutioTextureRegion ;
	private static final Sprite logoSprite;
	static{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/start_activity/");
		CarrotStutioTexture = new BitmapTextureAtlas(ResourceManager.getEngine().getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		CarrotStutioTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(CarrotStutioTexture, ResourceManager.getActivity(), "ae_logo.png", 0, 0);
		logoSprite = new Sprite(0 , 0, CarrotStutioTextureRegion, ResourceManager.getVbom());
	}
	
	private static ScaleAtModifier scaleModifier = new ScaleAtModifier(mEachAnimationDuration, 5f, 1f, logoSprite.getWidth()/2f, logoSprite.getHeight()/2f) ;
	private static AlphaModifier alphaModifier = new AlphaModifier(mEachAnimationDuration, 0f, 1f);
	private static ParallelEntityModifier logoModifier = new ParallelEntityModifier(scaleModifier, alphaModifier);
	
	//private static FadeOutModifier fadeInModifier = new FadeOutModifier(mEachAnimationDuration);
	//private static final SequenceEntityModifier logoModifier = new SequenceEntityModifier(new ParallelEntityModifier(new ScaleAtModifier(mEachAnimationDuration, 25f, mEachScaleToSize, 0.5f, 0.5f), new FadeInModifier(mEachAnimationDuration)), new DelayModifier(mEachAnimationPauseDuration), new ParallelEntityModifier(new ScaleAtModifier(mEachAnimationDuration, mEachScaleToSize, 0f, 0.5f, 0.5f), new FadeOutModifier(mEachAnimationDuration)));
	
	// ====================================================
	// METHODS
	// ====================================================
	@Override
	public void onLoadScene() {
		CarrotStutioTexture.load();
		
		this.setBackgroundEnabled(true);
		this.setBackground(new Background(0f, 0f, 1f));
		
		/*logoSprite.setAlpha(0.001f);
		SplashScreens.logoSprite.setScale(0.01f);*/
		logoSprite.setPosition((ResourceManager.getInstance().CAMERA_WIDTH - logoSprite.getWidth())/2f, (ResourceManager.getInstance().CAMERA_HEIGHT - logoSprite.getHeight())/2f);
		
		SplashScreens.logoModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(final IModifier<IEntity> pModifier, final IEntity pItem) {
				SceneManager.getInstance().showMainMenu();
			}
			
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {}
		});
		
		this.registerUpdateHandler(new IUpdateHandler() {
			int counter = 0;
			
			@Override
			public void onUpdate(final float pSecondsElapsed) {
				this.counter++;  // Ë¢ÐÂÖ¡Êý
				if(this.counter > 2) {
					SplashScreens.this.attachChild(SplashScreens.logoSprite);
					SplashScreens.logoSprite.registerEntityModifier(SplashScreens.logoModifier);
					SplashScreens.this.thisManagedSplashScene.unregisterUpdateHandler(this);
				}
			}
			
			@Override
			public void reset() {}
		});
	}
	
	@Override
	public void unloadSplashTextures() {
		CarrotStutioTexture.unload();
	}
	
}