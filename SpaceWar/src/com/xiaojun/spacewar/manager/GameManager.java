package com.xiaojun.spacewar.manager;

import com.xiaojun.spacewar.scene.impl.GameScene;

/**
 * 游戏管理器，用于管理游戏相关的数据记录
 * */
public class GameManager {
	private static GameManager instance;
	public static int score;
	
	private GameManager() {
	}
	
	/**
	 * 重置游戏相关的数据
	 * */
	public void reset() {
		GameScene.playerShip.reset();
		score = 0;
	}
	
	/**
	 * 增加积分
	 * @param addScore 需要增加的积分
	 */
	public void addScore(int addScore) {
		score += addScore;
	}
	
	/**
	 * 减少积分
	 * @param substractScore 积分
	 */
	public void substractScore(int substractScore) {
		score -= substractScore;
	}
	
	/**
	 * 获得游戏管理器实例
	 * */
	public synchronized static GameManager getInstance() {
		if(instance == null) {
			return new GameManager();
		}
		return instance;
	}
}
