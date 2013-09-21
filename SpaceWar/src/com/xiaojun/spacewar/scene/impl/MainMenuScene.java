package com.xiaojun.spacewar.scene.impl;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.manager.SceneManager;
import com.xiaojun.spacewar.manager.SoundManager;
import com.xiaojun.spacewar.scene.ManagedMenuScene;

/** The MainMenu scene holds two Entitys, one representing the title screen
 *  and one representing the level-select screen. The movement between the
 *  two screens is achieved using entity modifiers. During the first
 *  showing of the MainMenu, a loading screen is shown while the game
 *  resources are loaded.
 *  
*** @author 肖俊 - 胡萝卜游戏工作室
**/
public class MainMenuScene extends ManagedMenuScene {
	
	public static final String TAG = "MainMenu";
	
	// ====================================================
	// ENUMS
	// ====================================================
	public enum MainMenuScreens {
		MainMenu, OptionMenu
	}
	
	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final MainMenuScene INSTANCE = new MainMenuScene();
	//private static final float mCameraHeight = ResourceManager.getInstance().CAMERA_HEIGHT;
	private static final float mCameraWidth = ResourceManager.getInstance().CAMERA_WIDTH;
	//private static final float mHalfCameraHeight = (ResourceManager.getInstance().CAMERA_HEIGHT / 2f);
	private static final float mHalfCameraWidth = (ResourceManager.getInstance().CAMERA_WIDTH / 2f);
	private static final float menuItemSpace = 40f;
	//private static final IEntity MusicToggleButton = null;
	
	// ====================================================
	// INSTANCE GETTER
	// ====================================================
	public static MainMenuScene getInstance() {
		return INSTANCE;
	}
	
	// ====================================================
	// VARIABLES
	// ====================================================
	public MainMenuScreens mCurrentScreen = MainMenuScreens.MainMenu;
	private Entity mHomeMenuScreen;
	private Entity mOptionScreen;
	private Sprite BGSprite;
	private Text startGameButton;
	private Text optionButton;
	private Text quitButton;
	
	// ====================================================
	// CONSTRUCTOR
	// ====================================================
	public MainMenuScene() {
		super();
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
	}
	
	// ====================================================
	// METHODS
	// ====================================================
	public void goToOptionMenuScreen() {
		this.mCurrentScreen = MainMenuScreens.OptionMenu;
		this.mHomeMenuScreen.registerEntityModifier(new MoveModifier(0.25f, this.mHomeMenuScreen.getX(), -mCameraWidth, 0f, 0f));
		this.mOptionScreen.registerEntityModifier(new MoveModifier(0.25f, mCameraWidth, 0, 0, 0f));
		//this.BGSprite.registerEntityModifier(new MoveModifier(0.25f, this.BGSprite.getX(), this.BGSprite.getY(), (this.BGSprite.getWidth() * this.BGSprite.getScaleX()) / 2f, (this.BGSprite.getHeight() * this.BGSprite.getScaleY()) / 2f));
	}
	
	@Override
	public Scene onLoadingScreenLoadAndShown() {
		/*ResourceManager.getInstance().loadMainMenuResources();
		final Scene MenuLoadingScene = new Scene();
		MenuLoadingScene.setBackground(new Background(0.1f, 0.1f ,0.1f));
		MenuLoadingScene.attachChild(new Text(mHalfCameraWidth, mHalfCameraHeight, ResourceManager.fontDefault72Bold, "Loading...", ResourceManager.getActivity().getVertexBufferObjectManager()));
		return MenuLoadingScene;*/
		return null;
	}
	
	@Override
	public void onLoadingScreenUnloadAndHidden() {
		//this.BGSprite.detachSelf();
	}
	
	@Override
	public void onLoadScene() {
		// 加载开始主菜单所需要的资源
		ResourceManager.getInstance().loadSharedResourcese();
		ResourceManager.getInstance().loadMainMenuResources();
		
		
		// 背景
		this.BGSprite = new Sprite(0f, 0f, ResourceManager.mainMenuBackground, ResourceManager.getActivity().getVertexBufferObjectManager());
		this.BGSprite.setSize(480, 800);
		this.BGSprite.setZIndex(-999);
		this.attachChild(BGSprite);
		
		this.mHomeMenuScreen = new Entity(mCameraWidth, 0f) {
			boolean hasloaded = false;
			
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if(!this.hasloaded) {
					this.hasloaded = true;
					this.registerEntityModifier(new MoveModifier(0.25f, mCameraWidth, 0f, 0f, 0f));
				}
			}
		};
		
		/* 开始按钮  */ 
		startGameButton = new Text(0, 
				0, ResourceManager.fontMonospace72Bold,
				"开始游戏", ResourceManager.getActivity().getVertexBufferObjectManager()) {

					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						
					   if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
						   // 开始游戏
						  /* Intent intent = new Intent(ResourceManager.getActivity(),GameActivity.class);
						   ResourceManager.getActivity().startActivity(intent);*/
						   SoundManager.playClick(1f, 1f);
						   SceneManager.getInstance().showScene(GameScene.getInstance());
						   return true;
					   }
					   return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
						
					}
		};
		startGameButton.setPosition(mHalfCameraWidth - startGameButton.getWidth()/2 , 100f);
		this.registerTouchArea(startGameButton);
		this.mHomeMenuScreen.attachChild(startGameButton);
		
		/* 选项按钮  */
		optionButton = new Text(0, 
				0, ResourceManager.fontMonospace72Bold,
				"游戏选项", ResourceManager.getActivity().getVertexBufferObjectManager()) {

					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						 SoundManager.playClick(1f, 1f);
						 SceneManager.getInstance().showScene(OptionScene.getInstance());
						 return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
					}
		};
		optionButton.setPosition(startGameButton.getX()  , 
				startGameButton.getY() + startGameButton.getHeight() + menuItemSpace);
		this.registerTouchArea(optionButton);
		this.mHomeMenuScreen.attachChild(optionButton);
		
		/* 退出按钮  */
		quitButton = new Text(0, 
				0, ResourceManager.fontMonospace72Bold,
				"退出游戏", ResourceManager.getActivity().getVertexBufferObjectManager()) {

					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						
					   if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
						   SoundManager.playClick(1f, 1f);
						   ResourceManager.getActivity().finish();
						   return true;
					   }
					   return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
					}
		};
		quitButton.setPosition(optionButton.getX()  , 
				optionButton.getY() + optionButton.getHeight() + menuItemSpace);
		this.registerTouchArea(quitButton);
		this.mHomeMenuScreen.attachChild(quitButton);
		
		/* 游戏选项界面 */ 
		this.mOptionScreen = new Entity(mCameraWidth, 0f) ;
		
		
		Log.d("MainMenuScene", "MainMenuScene.onLoadScene()");
	}
	
	@Override
	public void onShowScene() {
		if(!this.mHomeMenuScreen.hasParent()) {
			Log.d("MainMenuScene", "MainMenuScene.onShowScene()");
			this.attachChild(this.mHomeMenuScreen);
			this.registerTouchArea(optionButton);
			this.registerTouchArea(startGameButton);
			this.registerTouchArea(quitButton);
			this.sortChildren();
		}
		Log.d("MainMenuScene", "MainMenuScene.onShowScene()22222");
		// 开启菜单背景音乐
		SoundManager.playMenuMusic();
	}
	
	@Override
	public void onHideScene() {
		// 暂停主菜单背景音乐
		SoundManager.pauseMenuMusic();
		Log.d("MainMenuScene", "MainMenuScene.onHideScene()");
	}
	
	@Override
	public void onUnloadScene() {
//		this.clearEntityModifiers();
//		this.clearUpdateHandlers();
//		this.clearTouchAreas();
//		this.clearChildScene();
		Log.d("MainMenuScene", "MainMenuScene.onUnloadScene()");
	}
	
}