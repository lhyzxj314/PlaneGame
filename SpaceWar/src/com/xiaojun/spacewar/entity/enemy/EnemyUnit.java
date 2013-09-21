package com.xiaojun.spacewar.entity.enemy;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.IModifier;

import com.xiaojun.spacewar.entity.MyEntity;

/**
 * �о���λ������
 * @author Ф��
 */
public abstract class EnemyUnit extends MyEntity{
	protected int score;
	protected IEntityModifier pathModifier;
	
	
	public EnemyUnit(int num) {
		super();
		// ���÷ɻ���·��
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
	
	// ����
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
