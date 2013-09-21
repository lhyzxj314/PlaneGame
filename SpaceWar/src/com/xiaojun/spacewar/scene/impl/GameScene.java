package com.xiaojun.spacewar.scene.impl;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import com.xiaojun.spacewar.entity.ParallaxLayer;
import com.xiaojun.spacewar.entity.enemy.impl.Bullet1;
import com.xiaojun.spacewar.entity.enemy.impl.NormalPlane;
import com.xiaojun.spacewar.entity.explosion.SparkExplosion;
import com.xiaojun.spacewar.entity.player.PlayerBullet;
import com.xiaojun.spacewar.entity.player.PlayerShip1;
import com.xiaojun.spacewar.layer.impl.GameLayer;
import com.xiaojun.spacewar.layer.impl.GameOverLayer;
import com.xiaojun.spacewar.layer.impl.PauseLayer;
import com.xiaojun.spacewar.manager.GameManager;
import com.xiaojun.spacewar.manager.LevelManager;
import com.xiaojun.spacewar.manager.ResourceManager;
import com.xiaojun.spacewar.manager.SceneManager;
import com.xiaojun.spacewar.manager.SoundManager;
import com.xiaojun.spacewar.scene.ManagedMenuScene;

public class GameScene extends ManagedMenuScene {

	private static final GameScene INSTANCE = new GameScene();

	public static boolean hasShownFlag = false; // 暂停界面是否显示的标志位
	public static boolean flag = false;
	// 常量
	public static final int CAMERA_HEIGHT = 800;
	public static final int CAMERA_WIDTH = 480;
	public static final int ANIMATION_FRAMELENGTH = 60;
	public static PlayerShip1 playerShip;
	public ParallaxLayer parallaxLayer;
	public Sprite autoParallaxBackground;
	public Sprite cloud1;
	public Sprite cloud2;
	

	// CAMERA
	private Camera mCamera = ResourceManager.getCamera();
	private VertexBufferObjectManager vbom = ResourceManager.getVbom();
	private Scene loadingScene;
	private LevelManager levelManager = new LevelManager();

	// 构造器
	private GameScene() {
		super(5f);
	}

	// 获得实例的方法
	public static GameScene getInstance() {
		return INSTANCE;
	}

	@Override
	public Scene onLoadingScreenLoadAndShown() {
		loadingScene = new Scene();
		loadingScene.setBackground(new Background(Color.RED));
		Text loadingText = new Text(0, 0, ResourceManager.fontMonospace72Bold,
				"Loading", ResourceManager.getActivity()
						.getVertexBufferObjectManager());
		loadingText.setPosition((CAMERA_WIDTH - loadingText.getWidth()) / 2f,
				(CAMERA_HEIGHT - loadingText.getHeight()) / 2f);
		loadingScene.attachChild(loadingText);
		return loadingScene;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() {

	}

	@Override
	public void onLoadScene() {
		ResourceManager.getInstance().loadGameTextures();
		// 加载字体资源
		ResourceManager.getInstance().loadMyFonts();

		// 滚动背景
		parallaxLayer = new ParallaxLayer(mCamera, false, 4000);
		parallaxLayer.setParallaxChangePerSecond(35);
		parallaxLayer.setParallaxScrollFactor(1);
		autoParallaxBackground = new Sprite(0, 0,
				ResourceManager.getInstance().backgroundRegion, vbom) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					
				case TouchEvent.ACTION_MOVE:
					playerShip.getAnimatedSprite().setPosition(
							pSceneTouchEvent.getX()
									- playerShip.getAnimatedSprite().getWidth()
									/ 2f, pSceneTouchEvent.getY() - 100);
					return false;
				case TouchEvent.ACTION_UP:
					hasShownFlag = true; // 更改标志位
					if (hasShownFlag) {
						PauseLayer.crossX = pSceneTouchEvent.getX() - 90f / 2f;
						PauseLayer.crossY = pSceneTouchEvent.getY() - 90f / 2f;
						// 显示暂停界面
						SceneManager.getInstance().showLayer(PauseLayer.getInstance(), false, true, true);
						return false;
					}
					return false;
				}
				return false;
			}
		};
		parallaxLayer.attachParallaxEntity(new ParallaxLayer.ParallaxEntity(30,
				autoParallaxBackground));
		
		// 云层
		cloud1 = new Sprite(0, 0,
				ResourceManager.getInstance().cloud2, vbom);
		cloud1.setAlpha(0.6f);
		cloud1.setSize(600, 1024);
		parallaxLayer.attachParallaxEntity(new ParallaxLayer.ParallaxEntity(42,
				cloud1));
		
		this.attachChild(parallaxLayer);
	}

	@Override
	public void onShowScene() {
		// 开启游戏背景音乐
		SoundManager.playMusic();
		this.registerUpdateHandler(levelManager);
		//this.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
		// 刷新，显示分数和血条
		SceneManager.getInstance().showLayer(GameLayer.getInstance(), false, false, false);
		GameLayer.lifeBar.refresh();
		GameLayer.scoreText.refresh();
		
		this.sortChildren();
	}

	@Override
	public void onUnloadScene() {
		this.clearChildScene();
		this.clearEntityModifiers();
		this.detachChildren();
		this.clearTouchAreas();
		this.clearUpdateHandlers();
		this.setOnSceneTouchListener(null);
		// 清除对象池中的对象
		NormalPlane.pool.getEntities().clear();
		NormalPlane.pool.getEnitytiesToReuse().clear();
		Bullet1.pool.getEntities().clear();
		Bullet1.pool.getEnitytiesToReuse().clear();
		PlayerBullet.pool.getEntities().clear();
		PlayerBullet.pool.getEnitytiesToReuse().clear();
		// 重置关卡信息
		LevelManager.animationTitles = true;
		LevelManager.gameStart = false;
		LevelManager.timer.reset();
		System.gc();
		Log.d("GameScene", "进来释放资源了！！！！");
		this.isLoaded = false;
	}

	@Override
	public void onHideScene() {
		GameManager.getInstance().reset(); // 充值游戏各项参数
		SoundManager.pauseMusic();
	}

	public void collisionDetection() {

		// 检测子弹和敌机之间的碰撞
		for (int i = 0; i < PlayerBullet.pool.getEntities().size(); i++) {
			for (int j = 0; j < NormalPlane.pool.getEntities().size(); j++) {
				final PlayerBullet bullet = PlayerBullet.pool.getEntities()
						.get(i);
				final NormalPlane enemy = NormalPlane.pool.getEntities().get(j);
				if (bullet.getAnimatedSprite().collidesWith(
						enemy.getAnimatedSprite())) {
					// 若子弹击中了敌机，对子弹设置标志位为true，防止重复检测
					if (bullet.isHasDetected() == true)
						break;
					bullet.setHasDetected(true);

					ResourceManager.getActivity().runOnUpdateThread(
							new Runnable() {

								@Override
								public void run() {
									PlayerBullet.pool.recycle(bullet); // 回收子弹
									enemy.isHit(bullet);
									if (enemy.isDead()) {
										// 添加积分并刷新
										GameManager.getInstance().addScore(
												enemy.getScore());
										GameLayer.scoreText.refresh();

										SparkExplosion explosion = new SparkExplosion();
										NormalPlane.pool.recycle(enemy);
										explosion.startExplosion(bullet
												.getAnimatedSprite().getX(),
												bullet.getAnimatedSprite()
														.getY());
										SoundManager.playExplosion(1f, 1f);
									}
								}

							});

				}

			}
		}

		// 检测敌机子弹和玩家之间的碰撞
		if (Bullet1.pool.getEntities().size() > 0) {
			for (int i = 0; i < Bullet1.pool.getEntities().size(); i++) {
				final Bullet1 bullet = Bullet1.pool.getEntities().get(i);
				if (playerShip.getAnimatedSprite().collidesWith(
						bullet.getAnimatedSprite())) {

					ResourceManager.getActivity().runOnUpdateThread(
							new Runnable() {

								@Override
								public void run() {
									playerShip.isHit(bullet);
									Bullet1.pool.recycle(bullet);
									//ResourceManager.getActivity();
									Vibrator vibrator = (Vibrator) ResourceManager
											.getActivity().getSystemService(
													Context.VIBRATOR_SERVICE);
									long[] vibratorPattern = { 30, 50, 30, 50 };
									vibrator.vibrate(vibratorPattern, -1);
									GameLayer.lifeBar.refresh();
									if(playerShip.isDead()) {
										SceneManager.getInstance().showLayer(GameOverLayer.getInstance(), false, true, true);
									}
								}

							});

				}
			}
		}
	}

	// 镜头控制监听器
	public IOnSceneTouchListener touchListener = new IOnSceneTouchListener(){

		@Override
		public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
			if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_MOVE) {
				float x = pSceneTouchEvent.getMotionEvent().getX();
				ResourceManager.getCamera().setCenter(x, CAMERA_HEIGHT/2f);
				Log.d("GameScene", "touch move(" + ResourceManager.getCamera().getCenterX() +","+ ResourceManager.getCamera().getCenterY() +")");
			}
			return false;
		}
		
	};
	
}
