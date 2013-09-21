package com.xiaojun.spacewar;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;
import android.view.KeyEvent;

import com.xiaojun.spacewar.manager.GameManager;
import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.manager.SceneManager;
import com.xiaojun.spacewar.scene.impl.GameScene;
import com.xiaojun.spacewar.scene.impl.MainMenuScene;
import com.xiaojun.spacewar.scene.impl.SplashScreens;

public class GameActivity extends BaseGameActivity{
	// 常量
	public static final int CAMERA_HEIGHT = 800;
	public static final int CAMERA_WIDTH = 480;
	
	// CAMERA
	private SmoothCamera camera;
	//private Scene scene;
	//private VertexBufferObjectManager vbom;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// 设定镜头
		camera = new SmoothCamera(0, 
				0, CAMERA_WIDTH, CAMERA_HEIGHT, 120f, 10f, 3f);
		camera.setBounds(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		camera.setBoundsEnabled(true);
		
		EngineOptions mEngineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		mEngineOptions.getAudioOptions().setNeedsMusic(true);
		mEngineOptions.getAudioOptions().setNeedsSound(true);
		return mEngineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		ResourceManager.setUp(this.getEngine(), this,
				this.getApplicationContext(), camera,
				getVertexBufferObjectManager(), 480f, 800f);
		//ResourceManager.getInstance().loadGameTextures();
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		SplashScreens splashScreen =new SplashScreens();
		SceneManager.getInstance().showScene(splashScreen);
		pOnCreateSceneCallback.onCreateSceneFinished(mEngine.getScene());
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	// Some devices do not exit the game when the activity is destroyed.
	// This ensures that the game is closed.
	@Override
	protected void onDestroy() {
		Log.d("PauseLayer", "进来销毁Activity！！！");
		super.onDestroy();
		System.exit(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if(mEngine.getScene() instanceof GameScene){
				// 重置镜头
				ResourceManager.getCamera().setHUD(null);
				ResourceManager.getCamera().setZoomFactor(1);
				// 重置游戏信息
				GameManager.getInstance().reset();
				
				SceneManager.getInstance().showScene(MainMenuScene.getInstance());
				Log.d("MainMenuScene", "进来了,真的进来了!!");
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
		
	}
	
	

}
