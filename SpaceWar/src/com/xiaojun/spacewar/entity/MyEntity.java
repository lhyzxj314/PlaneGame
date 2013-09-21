package com.xiaojun.spacewar.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.xiaojun.spacewar.manager.ResourceManager;

public abstract class MyEntity {
	
	// ����
    protected AnimatedSprite animatedSprite;
    
    /**
	 * ��ӵ���Ļ
	 */
    public void addToSecne() {
    	ResourceManager.getEngine().getScene().attachChild(animatedSprite);
    }
    
	public AnimatedSprite getAnimatedSprite() {
		return animatedSprite;
	}

	public void setAnimatedSprite(AnimatedSprite animatedSprite) {
		this.animatedSprite = animatedSprite;
	}
	
	
}
