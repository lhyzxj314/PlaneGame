package com.xiaojun.spacewar.manager;

import com.xiaojun.spacewar.scene.impl.GameScene;

/**
 * ��Ϸ�����������ڹ�����Ϸ��ص����ݼ�¼
 * */
public class GameManager {
	private static GameManager instance;
	public static int score;
	
	private GameManager() {
	}
	
	/**
	 * ������Ϸ��ص�����
	 * */
	public void reset() {
		GameScene.playerShip.reset();
		score = 0;
	}
	
	/**
	 * ���ӻ���
	 * @param addScore ��Ҫ���ӵĻ���
	 */
	public void addScore(int addScore) {
		score += addScore;
	}
	
	/**
	 * ���ٻ���
	 * @param substractScore ����
	 */
	public void substractScore(int substractScore) {
		score -= substractScore;
	}
	
	/**
	 * �����Ϸ������ʵ��
	 * */
	public synchronized static GameManager getInstance() {
		if(instance == null) {
			return new GameManager();
		}
		return instance;
	}
}
