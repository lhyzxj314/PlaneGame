package com.xiaojun.spacewar.entity.enemy;

import com.xiaojun.spacewar.entity.MyEntity;

/**
 * 敌人子弹抽象类
 * @author 肖俊
 */
public abstract class EnemyBullet extends MyEntity {
	protected float damage;			//攻击力

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}
}
