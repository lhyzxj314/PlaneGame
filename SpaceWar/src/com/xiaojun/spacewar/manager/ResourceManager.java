package com.xiaojun.spacewar.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.FloatMath;
import android.util.Log;

/**
 * 资源管理器类
 * @author XiaoJun
 * */
public class ResourceManager {
	public static final String TAG = "ResourceManager";
	
	//资源管理器实例
	private static ResourceManager manager = new ResourceManager();
	
	//开始菜单贴图区域
	
	
	//游戏贴图区域
	public ITextureRegion backgroundRegion;
	public ITextureRegion cloud1;
	public ITextureRegion cloud2;
	public ITextureRegion particleTextureRegion1;
	
	public ITiledTextureRegion playerTextureRegion;
	public ITiledTextureRegion bulletTextureRegion;
	public ITiledTextureRegion normalPlaneTextureRegion;
	public ITiledTextureRegion bulletForNormalPlaneTextureRegion;
	
	//常用的上下文类
	public Engine engine;
	public SmoothCamera camera;
	public BaseGameActivity activity;
	public VertexBufferObjectManager vbom;
	public float CAMERA_WIDTH;
	public float CAMERA_HEIGHT;
	
	
	private ResourceManager() {
	}
	
	public static void setUp(Engine engine, BaseGameActivity activity,Context context, SmoothCamera camera,VertexBufferObjectManager vbom,
			float CAMERA_WIDTH,float CAMERA_HEIGHT) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = engine.getVertexBufferObjectManager();
		getInstance().CAMERA_HEIGHT = CAMERA_HEIGHT;
		getInstance().CAMERA_WIDTH = CAMERA_WIDTH;
	}
	
	/**
	 * 加载游戏贴图资源
	 * @param engine 引擎
	 * @param context 上下文
	 * */
	public synchronized void loadGameTextures() {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		
		BitmapTextureAtlas backgroundTexture = new BitmapTextureAtlas(engine.getTextureManager(), 480, 800, TextureOptions.BILINEAR);
		this.backgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTexture, activity, "background1.png", 0, 0);
		backgroundTexture.load();    
		
		BitmapTextureAtlas cloudTexture = new BitmapTextureAtlas(engine.getTextureManager(), 400, 400, TextureOptions.BILINEAR);
		this.cloud2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cloudTexture, activity, "white_cloud.png", 0, 0);
		cloudTexture.load();
	          
		Log.i(TAG, BitmapTextureAtlasTextureRegionFactory.getAssetBasePath());
		BitmapTextureAtlas unitTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1024, 1024,TextureOptions.BILINEAR);  
	    this.playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(unitTexture, activity, "ship1.png", 0, 0, 4, 1);  
	    this.bulletTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(unitTexture, activity, "bullet.png", 220, 0, 1, 1); 
	    this.normalPlaneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(unitTexture, activity, "enemy2.png", 300, 0, 4, 1); 
	    this.particleTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(unitTexture, activity, "particle_point.png", 800, 800);
	    this.bulletForNormalPlaneTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(unitTexture, activity, "bulletForNormalPlane.png", 400, 400, 2, 1);
	    unitTexture.load();
	    
	    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/pause_menu/");
	    BitmapTextureAtlas crossTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
	    crossRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(crossTexture, activity, "crosshairs.png", 0, 0);
	    crossTexture.load();
	}
	
	/**
	 * 卸载游戏贴图资源
	 * */
	public synchronized void unloadGameTexture() {
		BitmapTextureAtlas backgroundTexture =(BitmapTextureAtlas) this.backgroundRegion.getTexture();
		backgroundTexture.unload();
		
		BitmapTextureAtlas unitTexture =(BitmapTextureAtlas) this.playerTextureRegion.getTexture();
		unitTexture.unload();
		
		System.gc();
	}
	
	// ------------------------------------------------------------------
	// 游戏暂停界面的贴图资源
	// ------------------------------------------------------------------
	public static BitmapTextureAtlas mPauseMenuTexture;
	public static ITextureRegion crossRegion;
	
	/**
	 * 加载游戏暂停界面贴图资源
	 * */
	public synchronized void loadPauseSceneTexture() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/pause_menu/");
		mPauseMenuTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		mPauseMenuTexture.load();
	}
	
	/*public synchronized void unloadloadPauseSceneTexture() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/pause_menu");
		mPauseMenuTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		crossRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mPauseMenuTexture, activity, "crosshairs.png", 0, 0);
	}*/
	
	// ------------------------------------------------------------------
	// 开始主菜单的贴图资源
	// ------------------------------------------------------------------
	public static ITextureRegion mainMenuBackground;
	//public ITiledTextureRegion mainMenuBackground;

	//private Context activivy;
	
	/***
	 * 加载开始菜单贴图资源
	 */
	public synchronized void loadMainMenuResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/start_activity/");
		
		BitmapTextureAtlas backgroundTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		mainMenuBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTexture, activity, "menu_background.jpg", 0, 0);
		backgroundTexture.load();    
	}
	
	
	/**
	 * 加载游戏界面和开始菜单界面共同的资源;
	 * 包括：
	 * 1.字体资源
	 */
	public synchronized void loadSharedResourcese() {
		getInstance().loadFonts();
	}
	
	
	// ------------------------------------------------------------------
	// 加载字体资源
	// ------------------------------------------------------------------
	public static Font fontDefault32Bold;
	public static Font fontDefault72Bold;
	public static Font fontMonospace72Bold;
	public static Font myFont14;
	public static Font myFont24;
	public static Font myFont48;
	//public static Font fontDefaultMagneTank48;
	/**
	 * 加载字体
	 * */ 
	public synchronized void loadMyFonts() {
		FontFactory.setAssetBasePath("gfx/fonts/");
		if(myFont14==null) {
			final ITexture fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			myFont14 =  FontFactory.createFromAsset(engine.getFontManager(), fontTexture, activity.getAssets(), "font1.ttf", 14, true, android.graphics.Color.WHITE);
			myFont14.load();
		}
		if(myFont24==null) {
			final ITexture fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			myFont24 =  FontFactory.createFromAsset(engine.getFontManager(), fontTexture, activity.getAssets(), "font1.ttf", 24, true, android.graphics.Color.WHITE);
			myFont24.load();
		}
		if(myFont48==null) {
			final ITexture fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			myFont48 =  FontFactory.createFromAsset(engine.getFontManager(), fontTexture, activity.getAssets(), "font1.ttf", 48, true, android.graphics.Color.WHITE);
			myFont48.load();
		}
	}
	
	
	private void loadFonts(){
		// Create the Font objects via FontFactory class
		if(fontDefault32Bold==null) {
			fontDefault32Bold = FontFactory.create(engine.getFontManager(), engine.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD),  32f, true, Color.WHITE_ARGB_PACKED_INT);
			fontDefault32Bold.load();
		}
		if(fontDefault72Bold==null) {
			fontDefault72Bold = FontFactory.create(engine.getFontManager(), engine.getTextureManager(), 512, 512, Typeface.create(Typeface.DEFAULT, Typeface.BOLD),  72f, true, Color.WHITE_ARGB_PACKED_INT);
			fontDefault72Bold.load();
		}
		if(fontMonospace72Bold==null) {
			fontMonospace72Bold = FontFactory.create(engine.getFontManager(), engine.getTextureManager(), 512, 512, Typeface.create(Typeface.MONOSPACE, Typeface.BOLD),  72f, true, Color.WHITE_ARGB_PACKED_INT);
			fontMonospace72Bold.load();
		}
		/*if(fontDefaultMagneTank48==null) {
			fontDefaultMagneTank48 = getFont(Typeface.createFromAsset(activity.getAssets(), "fonts/X_SCALE_by_Factor_i.ttf"), 48f, true);
			fontDefaultMagneTank48.load();
		}*/
	}
	
	private static String DEFAULT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890~`!@#$%^&*()-_=+[] {};:'\",<.>?/\\";
	/**
	 * The following methods load and return a Font using a minimal amount of texture memory
	 * */ 
	public Font getFont(Typeface pTypeface, float pSize, boolean pAntiAlias) {
		return getFont(pTypeface, pSize, pAntiAlias, DEFAULT_CHARS);
	}
	
	private static float FONT_TEXTURE_PADDING_RATIO = 1.04f;
	private float FontTextureWidth = 0f;
	private float FontTextureHeight = 0f;
	private int FontTextureRows;
	public Font getFont(Typeface pTypeface, float pSize, boolean pAntiAlias, String pCharsToUse) {
		updateTextBounds(pTypeface,pSize,pAntiAlias,pCharsToUse);
		FontTextureWidth = (float) (mTextBounds.width()-mTextBounds.left);
		FontTextureHeight = (float) (mTextBounds.height()-mTextBounds.top);
		FontTextureRows = (int) Math.round(FloatMath.sqrt(FontTextureWidth/FontTextureHeight));
		FontTextureWidth = ((FontTextureWidth / FontTextureRows) * FONT_TEXTURE_PADDING_RATIO);
		FontTextureHeight = ((FontTextureHeight * FontTextureRows) * FONT_TEXTURE_PADDING_RATIO);
		return new Font(engine.getFontManager(), new BitmapTextureAtlas(engine.getTextureManager(), (int) FontTextureWidth, (int) FontTextureHeight, BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT), pTypeface, pSize, pAntiAlias, Color.WHITE_ARGB_PACKED_INT);
	}
	
	private Paint mPaint;
	private Rect mTextBounds = new Rect();
	private void updateTextBounds(final Typeface pTypeface, final float pSize, final boolean pAntiAlias, final String pCharacterAsString) {
		mPaint = new Paint();
		mPaint.setTypeface(pTypeface);
		mPaint.setTextSize(pSize);
		mPaint.setAntiAlias(pAntiAlias);
		mPaint.getTextBounds(pCharacterAsString, 0, pCharacterAsString.length(), this.mTextBounds);
	}
	
	/**
	 * 获得资源管理器的实例
	 * */
	public static ResourceManager getInstance() {
		return manager;
	}
	
	//getter方法
	public static Engine getEngine() {
		return getInstance().engine;
	}


	public static SmoothCamera getCamera() {
		return getInstance().camera;
	}


	public static BaseGameActivity getActivity() {
		return getInstance().activity;
	}


	public static VertexBufferObjectManager getVbom() {
		return  getInstance().vbom;
	}

}
