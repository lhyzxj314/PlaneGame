package com.xiaojun.spacewar.entity.gameelement;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;

import com.xiaojun.spacewar.manager.GameManager;
import com.xiaojun.spacewar.manager.ResourceManager;

/**
 * ��һ���
 * @author Ф��
 */
public class ScoreText {
	private Text prompText;
	private Text score;
	
	public ScoreText() {
		this.prompText = new Text(0, 
				0, ResourceManager.myFont14,
				"����", ResourceManager.getActivity().getVertexBufferObjectManager());
		this.score = new Text(0, 
				0, ResourceManager.myFont24,
				"0           ", ResourceManager.getActivity().getVertexBufferObjectManager());
		// �趨��ʾλ��
		float x = 20f;
		float y = 10f;
		this.prompText.setPosition(x, y);
		this.score.setPosition(this.prompText.getWidth()+ x + 5f, 1f);
	}
	
	/**
	 * ��ʾ����
	 * @param scene Ѫ�����ڳ���
	 */
	public void addToScene(Scene scene) {
		float x = 20f;
		float y = 10f;
		this.prompText.setPosition(x, y);
		this.score.setPosition(this.prompText.getWidth()+ x + 5f, 1f);
		scene.attachChild(prompText);
		scene.attachChild(score);
	}
	
	/**
	 * ˢ�»���
	 */
	public void refresh() {
		if(GameManager.score >= 0) {
			this.score.setText(Integer.toString(GameManager.score));
		}
	}
	
	public Text getPrompText() {
		return prompText;
	}
	public void setPrompText(Text prompText) {
		this.prompText = prompText;
	}
	public Text getScore() {
		return score;
	}
	public void setScore(Text score) {
		this.score = score;
	}
	
	
}
