package com.xiaojun.spacewar.layer.impl;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.ease.EaseElasticOut;

import android.util.Log;

import com.xiaojun.spacewar.layer.ManagedLayer;
import com.xiaojun.spacewar.manager.GameManager;
import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.manager.SceneManager;
import com.xiaojun.spacewar.manager.SoundManager;
import com.xiaojun.spacewar.scene.impl.GameScene;
import com.xiaojun.spacewar.scene.impl.MainMenuScene;

public class GameOverLayer extends ManagedLayer {
	// -----------------------------------------------------
	// 静态属性
	// -----------------------------------------------------
	private static final GameOverLayer INSTANCE = new GameOverLayer();
	private static final float CAMERA_WIDTH = ResourceManager.getInstance().CAMERA_WIDTH;
	private static final float CAMERA_HEIGHT = ResourceManager.getInstance().CAMERA_HEIGHT;
	private static Rectangle backgroundRec;
	private static Text gameOverText;
	private static Text restartButton;
	private static Text backToMenuButton;
	private static Text quitButton;
	
	private static MoveModifier moveModifier;
	
	private GameOverLayer() {
		super();
	}
	
	public static GameOverLayer getInstance() {
		return INSTANCE;
	}
	
	
	@Override
	public void onLoadLayer() {
		// 加载贴图资源
		ResourceManager.getInstance().loadPauseSceneTexture();
//		// 禁用场景本身的背景
//		PauseScene.getInstance().setBackgroundEnabled(false);
		// 黑色透明背景
		backgroundRec = new Rectangle(0f, 0f, CAMERA_WIDTH, CAMERA_HEIGHT, ResourceManager.getVbom());
		backgroundRec.setColor(0f, 0f, 0f);
		backgroundRec.setAlpha(0.7f);
		backgroundRec.setZIndex(-999);
		
		// 文字
		gameOverText = new Text(50f, 
				50f, ResourceManager.myFont48, "游戏结束", ResourceManager.getActivity().getVertexBufferObjectManager());
		gameOverText.setColor(1f, 0f, 0f);
		gameOverText.setPosition(CAMERA_WIDTH/2f, gameOverText.getHeight());
		moveModifier = new MoveModifier(1f, CAMERA_WIDTH/2f - gameOverText.getWidth()/2f, CAMERA_WIDTH/2f- gameOverText.getWidth()/2f, -gameOverText.getHeight(), 90f, EaseElasticOut.getInstance());
		
		/* 重新开始按钮  */ 
		restartButton = new Text(0, 
				0, ResourceManager.myFont48, "重新开始", ResourceManager.getActivity().getVertexBufferObjectManager()) {
			
			  @Override
			  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				  switch(pSceneTouchEvent.getAction()) {
				  	case TouchEvent.ACTION_DOWN:
				  		Log.d("PauseLayer", this.hashCode()+"被按到了！！！！！！！！！！！！！！！！！");
				  		SoundManager.playClick(1f, 1f);
				  		// 重置镜头
						ResourceManager.getCamera().setHUD(null);
						ResourceManager.getCamera().setZoomFactor(1);
						// 重置游戏信息
						GameManager.getInstance().reset();
						
				  		SceneManager.getInstance().showScene(GameScene.getInstance());
				  		return true;
				  }
			      return false;
			  }
			  
		};
		restartButton.setPosition((CAMERA_WIDTH - restartButton.getWidth())/2f, 200f);
		this.registerTouchArea(restartButton);
		
		
		backToMenuButton = new Text(0, 
				0, ResourceManager.myFont48, "返回主菜单", ResourceManager.getActivity().getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					SoundManager.playClick(1f, 1f);
					// 重置镜头
					ResourceManager.getCamera().setHUD(null);
					ResourceManager.getCamera().setZoomFactor(1);
					// 重置游戏信息
					GameManager.getInstance().reset();
					
					SceneManager.getInstance().showScene(MainMenuScene.getInstance());
					return true;
				}
				return false;
			}
			
		};
		backToMenuButton.setPosition((CAMERA_WIDTH - restartButton.getWidth())/2f, 200f + restartButton.getHeight() +50f);
		this.registerTouchArea(backToMenuButton);
		
		quitButton = new Text(0, 
				0, ResourceManager.myFont48, "退出游戏", ResourceManager.getActivity().getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					//SceneManager.getInstance().hideLayer();
					SoundManager.playClick(1f, 1f);
					ResourceManager.getActivity().finish();
					Log.d("PauseLayer", this.hashCode()+"被按到了！！！！！！！！！！！！！！！！！");
					return true;
				}
				return false;
			}
			
		};
		quitButton.setPosition((CAMERA_WIDTH - restartButton.getWidth())/2f, 200f + 2*restartButton.getHeight() +50f+50f);
		this.registerTouchArea(quitButton);
		
		this.attachChild(backgroundRec);
		this.attachChild(gameOverText);
		this.attachChild(restartButton);
		this.attachChild(quitButton);
		this.attachChild(backToMenuButton);
	}

	@Override
	public void onShowLayer() {
		// 暂停游戏音乐
		SoundManager.pauseMusic();
		SoundManager.playClose(1, 1);
		moveModifier.reset();
		gameOverText.registerEntityModifier(moveModifier);
	}

	@Override
	public void onHideLayer() {
		
	}

	@Override
	public void onUnloadLayer() {

	}

}
