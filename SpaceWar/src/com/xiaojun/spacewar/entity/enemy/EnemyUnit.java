package com.xiaojun.spacewar.entity.enemy;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.IModifier;

import com.xiaojun.spacewar.entity.MyEntity;

/**
 * 敌军单位抽象类
 * @author 肖俊
 */
public abstract class EnemyUnit extends MyEntity{
	protected int score;
	protected IEntityModifier pathModifier;
	
	
	public EnemyUnit(int num) {
		super();
		// 设置飞机的路径
		pathModifier = Paths.getLinearPath(num);
		pathModifier.addModifierListener(modifierListener);
	}
	
	protected IEntityModifierListener modifierListener = new IEntityModifierListener (){

		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
				IEntity item) {
		
		}

		@Override
		public void onModifierFinished(IModifier<IEntity> pModifier,
				IEntity pItem) {
			
			recycle();
		}
		
	};
	
	// 回收
	protected abstract void recycle();
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public IEntityModifier getPathModifier() {
		return pathModifier;
	}

	public void setPathModifier(IEntityModifier pathModifier) {
		this.pathModifier = pathModifier;
	}
	
}
