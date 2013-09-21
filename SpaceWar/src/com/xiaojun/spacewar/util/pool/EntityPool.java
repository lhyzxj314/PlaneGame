package com.xiaojun.spacewar.util.pool;

import java.util.ArrayList;
import java.util.List;

import com.xiaojun.spacewar.entity.MyEntity;
import com.xiaojun.spacewar.entity.enemy.Paths;
import com.xiaojun.spacewar.exception.RecycleException;

/***
 * ����أ����ڶ��������
 * @author XiaoJun
 * @param <E> �̳���MyEntity������
 */
public abstract class EntityPool<E extends MyEntity> {
	public static final String TAG = "EntityPool";
	
	protected List<E> entities = new ArrayList<E>();
	protected List<E> enitytiesToReuse = new ArrayList<E>();
	private int maximum;  // ����ص��������
	
	public EntityPool() {
		this.maximum = 15;
	}
	
	/**
	 * ������
	 * @param maximum ����ص��������
	 */
	public EntityPool(int maximum) {
		this.maximum = maximum;
	}
	
	/**
	 * ���ն���
	 * @param entity ��Ҫ���յĶ���
	 * */ 
	public void recycle(E entity) throws RecycleException{
		// �������˶���ص�����޶ȷ�Χ��ֱ�����ٸö���
		if(enitytiesToReuse.size() >= maximum) {
			this.destroy(entity);
			return ;
		}

		if(enitytiesToReuse.contains(entity)) {
			this.entities.remove(entity);
			return ;
			//throw new RecycleException("ʵ��"+entity.toString()+"�ѱ�����!");
		}
		
		if(entity == null) {
			throw new RecycleException("���ܻ���nullʵ��!");
		}
		
		this.entities.remove(entity);
		this.enitytiesToReuse.add(entity);
		entity.getAnimatedSprite().setVisible(false);
		entity.getAnimatedSprite().setIgnoreUpdate(true);
		entity.getAnimatedSprite().clearEntityModifiers();
		entity.getAnimatedSprite().clearUpdateHandlers();
	}
	
	/***
	 * ���ö���
	 * @param x ���������
	 * @param y ����������
	 */
	public E reuse(float x, float y) {
		E entity = enitytiesToReuse.get(0);
		enitytiesToReuse.remove(entity);
		entities.add(entity);
		entity.getAnimatedSprite().setPosition(x, y);
		entity.getAnimatedSprite().setAlpha(1f);
		entity.getAnimatedSprite().setVisible(true);
		entity.getAnimatedSprite().setColor(1, 1, 1);
		entity.getAnimatedSprite().setIgnoreUpdate(false);
		return entity;
	}
	
	/***
	 * ���ö���
	 * @param num �˶�·�����
	 */
	public E reuse(int num) {
		E entity = enitytiesToReuse.get(0);
		enitytiesToReuse.remove(entity);
		entities.add(entity);
		entity.getAnimatedSprite().registerEntityModifier(Paths.getLinearPath(num));
		entity.getAnimatedSprite().setAlpha(1f);
		entity.getAnimatedSprite().setVisible(true);
		entity.getAnimatedSprite().setColor(1, 1, 1);
		entity.getAnimatedSprite().setIgnoreUpdate(false);
		return entity;
	}
	
	/***
	 * �������
	 * @param entity ��Ҫ����Ķ���
	 */
	public void destroy(E entity) {
		this.entities.remove(entity);
		this.enitytiesToReuse.remove(entity);
		entity.getAnimatedSprite().detachSelf();
		entity = null;
	}


	public List<E> getEntities() {
		return entities;
	}

	public void setEntities(List<E> entities) {
		this.entities = entities;
	}

	public List<E> getEnitytiesToReuse() {
		return enitytiesToReuse;
	}

	public void setEnitytiesToReuse(List<E> enitytiesToReuse) {
		this.enitytiesToReuse = enitytiesToReuse;
	}
	
	
}
