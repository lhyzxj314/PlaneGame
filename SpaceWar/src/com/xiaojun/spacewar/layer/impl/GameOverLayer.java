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
	// ��̬����
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
		// ������ͼ��Դ
		ResourceManager.getInstance().loadPauseSceneTexture();
//		// ���ó�������ı���
//		PauseScene.getInstance().setBackgroundEnabled(false);
		// ��ɫ͸������
		backgroundRec = new Rectangle(0f, 0f, CAMERA_WIDTH, CAMERA_HEIGHT, ResourceManager.getVbom());
		backgroundRec.setColor(0f, 0f, 0f);
		backgroundRec.setAlpha(0.7f);
		backgroundRec.setZIndex(-999);
		
		// ����
		gameOverText = new Text(50f, 
				50f, ResourceManager.myFont48, "��Ϸ����", ResourceManager.getActivity().getVertexBufferObjectManager());
		gameOverText.setColor(1f, 0f, 0f);
		gameOverText.setPosition(CAMERA_WIDTH/2f, gameOverText.getHeight());
		moveModifier = new MoveModifier(1f, CAMERA_WIDTH/2f - gameOverText.getWidth()/2f, CAMERA_WIDTH/2f- gameOverText.getWidth()/2f, -gameOverText.getHeight(), 90f, EaseElasticOut.getInstance());
		
		/* ���¿�ʼ��ť  */ 
		restartButton = new Text(0, 
				0, ResourceManager.myFont48, "���¿�ʼ", ResourceManager.getActivity().getVertexBufferObjectManager()) {
			
			  @Override
			  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				  switch(pSceneTouchEvent.getAction()) {
				  	case TouchEvent.ACTION_DOWN:
				  		Log.d("PauseLayer", this.hashCode()+"�������ˣ���������������������������������");
				  		SoundManager.playClick(1f, 1f);
				  		// ���þ�ͷ
						ResourceManager.getCamera().setHUD(null);
						ResourceManager.getCamera().setZoomFactor(1);
						// ������Ϸ��Ϣ
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
				0, ResourceManager.myFont48, "�������˵�", ResourceManager.getActivity().getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					SoundManager.playClick(1f, 1f);
					// ���þ�ͷ
					ResourceManager.getCamera().setHUD(null);
					ResourceManager.getCamera().setZoomFactor(1);
					// ������Ϸ��Ϣ
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
				0, ResourceManager.myFont48, "�˳���Ϸ", ResourceManager.getActivity().getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					//SceneManager.getInstance().hideLayer();
					SoundManager.playClick(1f, 1f);
					ResourceManager.getActivity().finish();
					Log.d("PauseLayer", this.hashCode()+"�������ˣ���������������������������������");
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
		// ��ͣ��Ϸ����
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
