package com.xiaojun.spacewar.scene.impl;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

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
*** @author –§ø° - ∫˙¬‹≤∑”Œœ∑π§◊˜ “
**/
public class OptionScene extends ManagedMenuScene {
	
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
	private static final OptionScene INSTANCE = new OptionScene();
	//private static final float mCameraHeight = ResourceManager.getInstance().CAMERA_HEIGHT;
	private static final float mCameraWidth = ResourceManager.getInstance().CAMERA_WIDTH;
	//private static final float mHalfCameraHeight = (ResourceManager.getInstance().CAMERA_HEIGHT / 2f);
	private static final float mHalfCameraWidth = (ResourceManager.getInstance().CAMERA_WIDTH / 2f);
	private static final float menuItemSpace = 40f;
	//private static final IEntity MusicToggleButton = null;
	
	// ====================================================
	// INSTANCE GETTER
	// ====================================================
	public static OptionScene getInstance() {
		return INSTANCE;
	}
	
	// ====================================================
	// VARIABLES
	// ====================================================
	public MainMenuScreens mCurrentScreen = MainMenuScreens.MainMenu;
	private Entity mOptionScreen;
	private Sprite BGSprite;
	private Text toggleSoundButton;
	private Text toggleMusicButton;
	private Text backButton;
	
	// ====================================================
	// CONSTRUCTOR
	// ====================================================
	public OptionScene() {
		super();
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
	}
	
	// ====================================================
	// METHODS
	// ====================================================
	public void goToOptionMenuScreen() {
		this.mCurrentScreen = MainMenuScreens.OptionMenu;
		//this.mHomeMenuScreen.registerEntityModifier(new MoveModifier(0.25f, this.mHomeMenuScreen.getX(), -mCameraWidth, 0f, 0f));
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
		// ±≥æ∞
		this.BGSprite = new Sprite(0f, 0f, ResourceManager.mainMenuBackground, ResourceManager.getActivity().getVertexBufferObjectManager());
		this.BGSprite.setSize(480, 800);
		this.BGSprite.setZIndex(-999);
		this.attachChild(BGSprite);
		
		this.mOptionScreen = new Entity(mCameraWidth, 0f) {
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
		
		/* …˘“Ù«–ªª∞¥≈•  */
		toggleSoundButton = new Text(0, 
				0, ResourceManager.fontMonospace72Bold,
				"“Ù–ß:ø™", ResourceManager.getActivity().getVertexBufferObjectManager()) {

					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						
					   if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
						   SoundManager.playClick(1f, 1f);
						   if(SoundManager.getInstance().mSoundsMuted) {
							   SoundManager.setSoundMuted(false);
							   toggleSoundButton.setText("“Ù–ß:ø™");
							   Log.d("OptionScene", "“Ù–ß:ø™");
						   }else{
							   SoundManager.setSoundMuted(true);
							   toggleSoundButton.setText("“Ù–ß:πÿ");
							   Log.d("OptionScene", "“Ù–ß:πÿ");
						   }
						   return true;
					   }
					   return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
					}
		};
		toggleSoundButton.setPosition(mHalfCameraWidth - toggleSoundButton.getWidth()/2 , 100f);
		this.registerTouchArea(toggleSoundButton);
		this.mOptionScreen.attachChild(toggleSoundButton);
		
		/* “Ù¿÷«–ªª∞¥≈•  */
		toggleMusicButton = new Text(0, 
				0, ResourceManager.fontMonospace72Bold,
				"“Ù¿÷:ø™", ResourceManager.getActivity().getVertexBufferObjectManager()) {

					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						
					   if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
						   SoundManager.playClick(1f, 1f);
						   if(SoundManager.getInstance().mMusicMuted) {
							   SoundManager.setMusicMuted(false);
							   SoundManager.playMenuMusic();
							   toggleMusicButton.setText("“Ù¿÷:ø™");
							   Log.d("OptionScene", "“Ù¿÷:πÿ");
							   
						   }else{
							   SoundManager.setMusicMuted(true);
							   SoundManager.pauseMenuMusic();
							   toggleMusicButton.setText("“Ù¿÷:πÿ");
							   Log.d("OptionScene", "“Ù¿÷:πÿ");
						   }
						   return true;
					   }
					   return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
					}
		};
		toggleMusicButton.setPosition(toggleSoundButton.getX() , 
				toggleSoundButton.getY() + toggleSoundButton.getHeight() + menuItemSpace);
		this.registerTouchArea(toggleMusicButton);
		this.mOptionScreen.attachChild(toggleMusicButton);
		
		/* ∑µªÿ∞¥≈•  */
		backButton = new Text(0, 
				0, ResourceManager.fontMonospace72Bold,
				"∑µªÿ", ResourceManager.getActivity().getVertexBufferObjectManager()) {

					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						
					   if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
						   SoundManager.playClick(1f, 1f);
						   SceneManager.getInstance().showScene(MainMenuScene.getInstance());
						   return true;
					   }
					   return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
					}
		};
		backButton.setPosition(toggleMusicButton.getX()  , 
				toggleMusicButton.getY() + toggleMusicButton.getHeight() + menuItemSpace);
		this.registerTouchArea(backButton);
		this.mOptionScreen.attachChild(backButton);
		
	}
	
	@Override
	public void onShowScene() {
		if(!this.mOptionScreen.hasParent()) {
			Log.d("MainMenuScene", "MainMenuScene.onShowScene()");
			this.attachChild(this.mOptionScreen);
			this.registerTouchArea(toggleSoundButton);
			this.registerTouchArea(toggleMusicButton);
			this.registerTouchArea(backButton);
			this.sortChildren();
		}
		Log.d("OptionScene", "MainMenuScene.onShowScene()22222");
	}
	
	@Override
	public void onHideScene() {
		// ‘›Õ£÷˜≤Àµ•±≥æ∞“Ù¿÷
		SoundManager.pauseMenuMusic();
		Log.d("OptionScene", "OptionScene.onHideScene()");
	}
	
	@Override
	public void onUnloadScene() {
//		this.clearEntityModifiers();
//		this.clearUpdateHandlers();
//		this.clearTouchAreas();
//		this.clearChildScene();
		Log.d("OptionScene", "OptionScene.onUnloadScene()");
	}
	
}