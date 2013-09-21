package com.xiaojun.spacewar.util.pool;

import java.util.ArrayList;
import java.util.List;

import com.xiaojun.spacewar.entity.MyEntity;
import com.xiaojun.spacewar.entity.enemy.Paths;
import com.xiaojun.spacewar.exception.RecycleException;

/***
 * 对象池，用于对象的重用
 * @author XiaoJun
 * @param <E> 继承于MyEntity的子类
 */
public abstract class EntityPool<E extends MyEntity> {
	public static final String TAG = "EntityPool";
	
	protected List<E> entities = new ArrayList<E>();
	protected List<E> enitytiesToReuse = new ArrayList<E>();
	private int maximum;  // 对象池的最大容量
	
	public EntityPool() {
		this.maximum = 15;
	}
	
	/**
	 * 构造器
	 * @param maximum 对象池的最大容量
	 */
	public EntityPool(int maximum) {
		this.maximum = maximum;
	}
	
	/**
	 * 回收对象
	 * @param entity 需要回收的对象
	 * */ 
	public void recycle(E entity) throws RecycleException{
		// 若超出了对象池的最大限度范围，直接销毁该对象
		if(enitytiesToReuse.size() >= maximum) {
			this.destroy(entity);
			return ;
		}

		if(enitytiesToReuse.contains(entity)) {
			this.entities.remove(entity);
			return ;
			//throw new RecycleException("实体"+entity.toString()+"已被重用!");
		}
		
		if(entity == null) {
			throw new RecycleException("不能回收null实体!");
		}
		
		this.entities.remove(entity);
		this.enitytiesToReuse.add(entity);
		entity.getAnimatedSprite().setVisible(false);
		entity.getAnimatedSprite().setIgnoreUpdate(true);
		entity.getAnimatedSprite().clearEntityModifiers();
		entity.getAnimatedSprite().clearUpdateHandlers();
	}
	
	/***
	 * 重用对象
	 * @param x 对象横坐标
	 * @param y 对象纵坐标
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
	 * 重用对象
	 * @param num 运动路径编号
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
	 * 清除对象
	 * @param entity 需要清除的对象
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
