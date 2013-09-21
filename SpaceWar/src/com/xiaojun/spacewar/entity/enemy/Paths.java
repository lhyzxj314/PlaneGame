package com.xiaojun.spacewar.entity.enemy;

import java.util.List;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.CardinalSplineMoveModifier;
import org.andengine.entity.modifier.CardinalSplineMoveModifier.CardinalSplineMoveModifierConfig;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.util.modifier.IModifier;

import com.xiaojun.spacewar.manager.ResourceManager;


public class Paths {
	
	public List<IModifier<IEntity>> paths;
	
	// 常量
	public static final int CAMERA_HEIGHT = 800;
	public static final int CAMERA_WIDTH = 480;
	public static final int HALF_CAMERA_HEIGHT = 400;
	public static final int HALF_CAMERA_WIDTH = 240;
	
	public static final int ONE = 0;
	public static final int TWO = 1;
	public static final int THREE = 2;
	public static final int FOUR = 3;
	
	
	
	public Paths() {
		
	}
	
	/**
	 * 获得飞机运动路径
	 * @param num 路径编号
	 * @return 返回路径实体
	 */
	public static IEntityModifier getLinearPath(int num) {
		IEntityModifier modifier = null;
		switch(num) {
		case ONE:
			CardinalSplineMoveModifierConfig config = new CardinalSplineMoveModifierConfig(4, -1); 
			config.setControlPoint(0, 0, 0);
			config.setControlPoint(1, ResourceManager.getCamera().getWidth()/2, ResourceManager.getCamera().getHeight()/2);
			config.setControlPoint(2, 0f, ResourceManager.getCamera().getHeight());
			CardinalSplineMoveModifier modifier1 = new CardinalSplineMoveModifier(15f, config);
			RotationModifier modifier2 = new RotationModifier(10, 0f, -30f);
			modifier = new ParallelEntityModifier(modifier1, modifier2);
			return modifier;
		case TWO:
			MoveModifier modifier3 = new MoveModifier(4, 0, CAMERA_WIDTH + 40f, 0, CAMERA_HEIGHT + 40f);
			RotationModifier modifier4 = new RotationModifier(5, 0f, -30f);
			modifier = new ParallelEntityModifier(modifier3, modifier4);
			return modifier;
		case THREE:
			modifier3 = new MoveModifier(4, CAMERA_WIDTH + 20f, -40f, -20f, CAMERA_HEIGHT + 40f);
			modifier4 = new RotationModifier(5, 0f, 30f);
			modifier = new ParallelEntityModifier(modifier3, modifier4);
			return modifier;
		case FOUR:
			config = new CardinalSplineMoveModifierConfig(4, -1); 
			config.setControlPoint(0, 0, 0);
			config.setControlPoint(1, 40f, HALF_CAMERA_HEIGHT);
			config.setControlPoint(2, HALF_CAMERA_WIDTH, HALF_CAMERA_HEIGHT);
			config.setControlPoint(3, HALF_CAMERA_WIDTH, CAMERA_HEIGHT + 60f);
			modifier1 = new CardinalSplineMoveModifier(10f, config);
			modifier2 = new RotationModifier(8, 0f, -30f);
			modifier = new ParallelEntityModifier(modifier1, modifier2);
			return modifier;
		default:
			return null;
		}
	}

	
	
}
