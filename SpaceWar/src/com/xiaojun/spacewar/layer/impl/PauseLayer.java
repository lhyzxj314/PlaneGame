package com.xiaojun.spacewar.layer.impl;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.ease.EaseElasticOut;

import android.util.Log;

import com.xiaojun.spacewar.layer.ManagedLayer;
import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.manager.SceneManager;
import com.xiaojun.spacewar.manager.SoundManager;

public class PauseLayer extends ManagedLayer {
	// -----------------------------------------------------
	// 静态属性
	// -----------------------------------------------------
	private static final PauseLayer INSTANCE = new PauseLayer();
	public static float crossX;  
	public static float crossY;
	private static final float CAMERA_WIDTH = ResourceManager.getInstance().CAMERA_WIDTH;
	private static final float CAMERA_HEIGHT = ResourceManager.getInstance().CAMERA_HEIGHT;
	private static Sprite crossSprite;
	private static Rectangle backgroundRec;
	private static Text gamePauseText;
	private static Text menuButton;
	
	private static MoveModifier moveModifier;
	
	private PauseLayer() {
		super();
	}
	
	public static PauseLayer getInstance() {
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
		gamePauseText = new Text(50f, 
				50f, ResourceManager.myFont48, "游戏暂停", ResourceManager.getActivity().getVertexBufferObjectManager());
		gamePauseText.setPosition(CAMERA_WIDTH/2f, gamePauseText.getHeight());
		moveModifier = new MoveModifier(1f, CAMERA_WIDTH/2f - gamePauseText.getWidth()/2f, CAMERA_WIDTH/2f- gamePauseText.getWidth()/2f, -gamePauseText.getHeight(), 90f, EaseElasticOut.getInstance());
		
		// 菜单按钮
		menuButton = new Text(50f, 
				50f, ResourceManager.myFont24, "菜单", ResourceManager.getActivity().getVertexBufferObjectManager()) {
			
			  @Override
			  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				  switch(pSceneTouchEvent.getAction()) {
				  	case TouchEvent.ACTION_DOWN:
				  		//SceneManager.getInstance().hideLayer();
				  		SceneManager.getInstance().showLayer(GameMenuLayer.getInstance(), false, false, false);
				  		SoundManager.playClick(1f, 1f);
				  		Log.d("PauseLayer", this.hashCode()+"被按到了！！！！！！！！！！！！！！！！！");
				  		return true;
				  }
			      return false;
			  }
			  
		};
		menuButton.setPosition(menuButton.getHeight() - 25f , CAMERA_HEIGHT/2f - menuButton.getWidth()/2f + 70f);
		menuButton.setRotation(90);
		this.registerTouchArea(menuButton);
		
		
		// 可旋转的瞄准镜
		crossSprite = new Sprite(crossX, crossY,  90f, 90f, ResourceManager.crossRegion,
				ResourceManager.getVbom()) {
			
					  @Override
					  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						  switch(pSceneTouchEvent.getAction()) {
						  	case TouchEvent.ACTION_DOWN:
//								  		PauseScene.getInstance().hidePauseScene();
						  		SceneManager.getInstance().hideLayer();
						  		SceneManager.getInstance().showLayer(GameLayer.getInstance(), false, false, false);
						  		Log.d("PauseScene", this.hashCode()+"被按到了！！！！！！！！！！！！！！！！！");
						  		return true;
						  }
					      return false;
					  }
					  
		};
		this.registerTouchArea(crossSprite);
		RotationModifier modifier1 = new RotationModifier(1.5f, 0, 360);
		AlphaModifier modifier2 = new AlphaModifier(0.4f, 1f, 0.4f);
		LoopEntityModifier modifier4 = new LoopEntityModifier(modifier2);
		LoopEntityModifier modifier = new LoopEntityModifier(modifier1);
		ParallelEntityModifier parellelEntitiyModifier = new ParallelEntityModifier(modifier4, modifier);
		crossSprite.registerEntityModifier(parellelEntitiyModifier);
		this.attachChild(backgroundRec);
		this.attachChild(menuButton);
		this.attachChild(crossSprite);
		this.attachChild(gamePauseText);
		//backgroundRec.attachChild(crossSprite);
		
		//Log.d("PauseScene", backgroundRec.getChildByIndex(1)+"我的");
	}

	@Override
	public void onShowLayer() {
		// 暂停游戏音乐
		SoundManager.pauseMusic();
		SoundManager.playClose(1, 1);
		crossSprite.setPosition(crossX, crossY);
		moveModifier.reset();
		gamePauseText.registerEntityModifier(moveModifier);
		Log.d("PauseLayer", "onShowLayer" + crossX);
	}

	@Override
	public void onHideLayer() {
		
	}

	@Override
	public void onUnloadLayer() {

	}

}
