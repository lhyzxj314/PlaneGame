package com.xiaojun.spacewar.entity.enemy;

import com.xiaojun.spacewar.entity.MyEntity;

/**
 * �����ӵ�������
 * @author Ф��
 */
public abstract class EnemyBullet extends MyEntity {
	protected float damage;			//������

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}
}
