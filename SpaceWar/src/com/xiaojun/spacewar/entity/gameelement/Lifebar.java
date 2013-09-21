package com.xiaojun.spacewar.entity.gameelement;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.xiaojun.spacewar.entity.player.PlayerShip1;
import com.xiaojun.spacewar.manager.ResourceManager;

public class Lifebar {
	// 常量
	public static final int CAMERA_HEIGHT = 800;
	public static final int CAMERA_WIDTH = 480;
	public static float WIDTH = 220f;
	public static float HEIGHT = 15f;
	public Text text;		//提示字符
	public Rectangle lifeRec;  // 血条
	
	// 构造方法
	public Lifebar() {
		float x= CAMERA_WIDTH/2f + 10f;
		float y= 10f;
		this.lifeRec = new Rectangle(x, y, WIDTH, HEIGHT, ResourceManager.getVbom());
		this.lifeRec.setColor(Color.WHITE);
		this.lifeRec.setAlpha(0.5f);
		this.lifeRec.setZIndex(999);
		this.text = new Text(0, 
				0, ResourceManager.myFont14,
				"生命值", ResourceManager.getActivity().getVertexBufferObjectManager());
		this.text.setPosition(x-this.text.getWidth()-5f, y);
	}
	
	/**
	 * 显示血条
	 * @param scene 血条所在场景
	 */
	public void addToHud(Scene scene) {
		scene.attachChild(text);
		scene.attachChild(lifeRec);
	}
	
	/**
	 * 刷新血条
	 */
	public void refresh() {
		if(PlayerShip1.life > 0) {
			float width = PlayerShip1.life/PlayerShip1.HP * WIDTH;
			this.lifeRec.setWidth(width);
			return;
		}
		this.lifeRec.setWidth(0);
	}

	public Rectangle getLifeRec() {
		return lifeRec;
	}

	public void setLifeRec(Rectangle lifeRec) {
		this.lifeRec = lifeRec;
	}

	public Text getText() {
		return text;
	}
	public void setText(Text text) {
		this.text = text;
	}
	
}
