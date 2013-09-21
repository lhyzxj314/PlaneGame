package com.xiaojun.spacewar.layer.impl;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.xiaojun.spacewar.layer.ManagedLayer;
import com.xiaojun.spacewar.manager.GameManager;
import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.manager.SceneManager;
import com.xiaojun.spacewar.manager.SoundManager;
import com.xiaojun.spacewar.scene.impl.GameScene;
import com.xiaojun.spacewar.scene.impl.MainMenuScene;

public class GameMenuLayer extends ManagedLayer {
	// -----------------------------------------------------
	// 静态属性
	// -----------------------------------------------------
	private static final GameMenuLayer INSTANCE = new GameMenuLayer();
	private static final float CAMERA_WIDTH = ResourceManager.getInstance().CAMERA_WIDTH;
	//private static final float CAMERA_HEIGHT = ResourceManager.getInstance().CAMERA_HEIGHT;
	private static Text restartButton;	  //重新开始
	private static Text quitButton;		  //退出游戏
	private static Text backToMenuButton; //返回主菜单按钮
	
	private GameMenuLayer() {
		super();
	}
	
	public static GameMenuLayer getInstance() {
		return INSTANCE;
	}
	
	
	@Override
	public void onLoadLayer() {
		this.setBackground(new Background(0f, 0f, 0f));
		this.setBackgroundEnabled(true);
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
		
		this.attachChild(restartButton);
		this.attachChild(quitButton);
		this.attachChild(backToMenuButton);
	}

	@Override
	public void onShowLayer() {
		
	}

	@Override
	public void onHideLayer() {
		
	}

	@Override
	public void onUnloadLayer() {

	}
}
