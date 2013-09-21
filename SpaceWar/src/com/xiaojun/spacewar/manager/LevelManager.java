package com.xiaojun.spacewar.manager;

import java.util.Arrays;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.IModifier;

import android.util.Log;

import com.xiaojun.spacewar.entity.enemy.Paths;
import com.xiaojun.spacewar.entity.enemy.impl.NormalPlane;
import com.xiaojun.spacewar.entity.player.PlayerShip1;
import com.xiaojun.spacewar.scene.impl.GameScene;
import com.xiaojun.spacewar.util.GameTimer;

/**
 * 关卡管理器
 * @author 肖俊
 */
public class LevelManager implements IUpdateHandler {
	// 常量
	public static final int CAMERA_HEIGHT = 800;
	public static final int CAMERA_WIDTH = 480;
	public static boolean animationTitles = true;
	public static boolean gameStart = false;
	public static GameTimer timer = new GameTimer(); // 计时器(秒)
	
	/*public void initScenes() {
		for(int i=0;i<4;i++){
			// 加载关卡
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();

				LevelHandler myXMLHandler = new LevelHandler();
				xr.setContentHandler(myXMLHandler);
				switch(i){
				case 0:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level1)));
					break;
				case 1:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level2)));
					break;
				case 2:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level3)));
					break;
				case 3:
					xr.parse(new InputSource(activity.getResources().openRawResource(R.raw.level4)));
					break;
				}
				s.setWaves(LevelHandler.wavesList);
			} catch (Exception e) {
				System.out.println("XML Pasing Excpetion = " + e);
			}
			scenes.add(s);
		}
	}*/
	
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// 片头动画
		if(animationTitles) {
			/* 计算飞机的初始位置 */
			final float playerX = (CAMERA_WIDTH - ResourceManager.getInstance().playerTextureRegion
					.getWidth()) / 2;
			final float playerY = (CAMERA_HEIGHT - ResourceManager.getInstance().playerTextureRegion
					.getHeight()) / 2 + 140;

			// 飞机出场
			GameScene.playerShip = new PlayerShip1(playerX, CAMERA_HEIGHT + 30f);
			MoveModifier pathModifier = new MoveModifier(3, playerX, playerX, CAMERA_HEIGHT + 30f, playerY);
			pathModifier.addModifierListener(modifierListener);
			GameScene.playerShip.getAnimatedSprite().registerEntityModifier(pathModifier);
			GameScene.getInstance().attachChild(GameScene.playerShip.getAnimatedSprite());
			ResourceManager.getCamera().setCenterDirect(CAMERA_WIDTH/2f, CAMERA_HEIGHT/2f);
			animationTitles = false;
		}
		
		if(gameStart == false) return;
		
		NormalPlane plane1 = null;
		if (timer.passSecond(pSecondsElapsed)) {
			if(timer.seconds == 35) {
				GameScene.getInstance().parallaxLayer.setParallaxChangePerSecond(10);
				ResourceManager.getCamera().setZoomFactor(1f);
			}
			
			if (NormalPlane.pool.getEnitytiesToReuse().size() > 0) {
				NormalPlane.pool.reuse(Paths.TWO);
			} else {
				plane1 = new NormalPlane(Paths.THREE);
				NormalPlane.pool.getEntities().add(plane1);
				Log.i("NormalPlanePool", "新建飞机：" + plane1.toString()
						+ "！！！！！！！！！！！！！！！！！");
				plane1.addToSecne();
				// plane.moveByPath();
			}
			Log.i("NormalPlane1",
					"Plane:"
							+ Arrays.toString(NormalPlane.pool
									.getEntities().toArray()));
			Log.i("NormalPlane1",
					"PlaneToReuse:"
							+ Arrays.toString(NormalPlane.pool
									.getEnitytiesToReuse().toArray()));
		}
		// 进行碰撞检测
		GameScene.getInstance().collisionDetection();
	}
	

	@Override
	public void reset() {
		
	}
	
	// 开头动画玩家飞机监听器
	public IEntityModifierListener modifierListener = new IEntityModifierListener (){

		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
				IEntity item) {
		
		}

		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier,
				IEntity pItem) {
			// 可旋转的瞄准镜
			Sprite crossSprite = new Sprite(0, 0,  90f, 90f, ResourceManager.crossRegion,
					ResourceManager.getVbom()) {
				
						  @Override
						  public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
							  switch(pSceneTouchEvent.getAction()) {
							  	case TouchEvent.ACTION_DOWN:
							  		// 注册镜头监听
							  		GameScene.getInstance().setOnSceneTouchListener(GameScene.getInstance().touchListener);
							  		// 注册触摸监听事件
							  		GameScene.getInstance().registerTouchArea(GameScene.getInstance().autoParallaxBackground);
							  	
							  		GameScene.playerShip.fire();
							  		this.detachSelf();
							  		return true;
							  }
						      return false;
						  }
						  
			};
			RotationModifier modifier1 = new RotationModifier(1.5f, 0, 360);
			AlphaModifier modifier2 = new AlphaModifier(0.4f, 1f, 0.4f);
			LoopEntityModifier modifier4 = new LoopEntityModifier(modifier2);
			LoopEntityModifier modifier = new LoopEntityModifier(modifier1);
			ParallelEntityModifier parellelEntitiyModifier = new ParallelEntityModifier(modifier4, modifier);
			crossSprite.setPosition(pItem.getX() - GameScene.playerShip.getAnimatedSprite().getWidth(), pItem.getY() - 20f);
			crossSprite.registerEntityModifier(parellelEntitiyModifier);
			GameScene.getInstance().registerTouchArea(crossSprite);
			GameScene.getInstance().attachChild(crossSprite);
			GameScene.getInstance().parallaxLayer.setParallaxChangePerSecond(5f);
			ResourceManager.getCamera().setZoomFactor(1.20f);
			
			/* 结束片头动画,开始游戏  */
			LevelManager.animationTitles = false;  
			LevelManager.gameStart = true;
			GameScene.getInstance().parallaxLayer.setParallaxChangePerSecond(10);
		}
		
	};
}
