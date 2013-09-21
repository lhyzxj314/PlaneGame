package com.xiaojun.spacewar.layer.impl;

import android.util.Log;

import com.xiaojun.spacewar.entity.gameelement.Lifebar;
import com.xiaojun.spacewar.entity.gameelement.ScoreText;
import com.xiaojun.spacewar.layer.ManagedLayer;

/**
 * ��Ϸ����ͼ�㣬��ʾѪ���ͷ���
 * @author Ф��
 */
public class GameLayer extends ManagedLayer {
	// ����ģʽʵ��
	private static final GameLayer INSTANCE = new GameLayer();
	
	public static Lifebar lifeBar; 		//Ѫ��
	public static ScoreText scoreText; 	//����
	
	private GameLayer() {
		super();
	}
	
	public static GameLayer getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void onLoadLayer() {
		lifeBar = new Lifebar();
		scoreText = new ScoreText();
		this.attachChild(lifeBar.getLifeRec());
		this.attachChild(lifeBar.getText());
		this.attachChild(scoreText.getPrompText());
		this.attachChild(scoreText.getScore());
	}

	@Override
	public void onShowLayer() {
		
		Log.d("GameLayer", "onShowLayer()");
	}

	@Override
	public void onHideLayer() {
//		lifeBar.getLifeRec().setVisible(false);
//		lifeBar.getText().setVisible(false);
//		scoreText.getPrompText().setVisible(false);
//		scoreText.getScore().setVisible(false);
//		Log.d("GameLayer", "onHideLayer()");
	}

	@Override
	public void onUnloadLayer() {
		
	}

}
