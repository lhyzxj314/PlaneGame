package com.xiaojun.spacewar.scene;


import org.andengine.entity.scene.Scene;

import com.xiaojun.spacewar.manager.ResourceManager;

/** Based on the ManagedMenuScene class.
 * 
*** @author Ð¤¿¡ - ºúÂÜ²·ÓÎÏ·¹¤×÷ÊÒ
**/
public abstract class ManagedSplashScreen extends ManagedScene {
	
	public ManagedSplashScreen thisManagedSplashScene = this;
	
	public ManagedSplashScreen() {
		this(0f);
	};
	
	public ManagedSplashScreen(float pLoadingScreenMinimumSecondsShown) {
		super(pLoadingScreenMinimumSecondsShown);
		this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
		this.setPosition(0, 0);
		this.dispose();
	}
	
	@Override
	public Scene onLoadingScreenLoadAndShown() {
		return null;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() {
	}
	
	@Override
	public void onShowScene() {
	}
	
	@Override
	public void onHideScene() {
	}
	
	@Override
	public void onUnloadScene() {
		ResourceManager.getInstance().engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				thisManagedSplashScene.detachChildren();
				for(int i = 0; i < thisManagedSplashScene.getChildCount(); i++)
					thisManagedSplashScene.getChildByIndex(i).dispose();
				thisManagedSplashScene.clearEntityModifiers();
				thisManagedSplashScene.clearTouchAreas();
				thisManagedSplashScene.clearUpdateHandlers();
				thisManagedSplashScene.unloadSplashTextures();
			}});
	}
	
	public abstract void unloadSplashTextures();
}