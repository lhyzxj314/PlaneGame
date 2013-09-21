package com.xiaojun.spacewar.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.xiaojun.spacewar.manager.ResourceManager;

public abstract class MyEntity {
	
	// 变量
    protected AnimatedSprite animatedSprite;
    
    /**
	 * 添加到屏幕
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
