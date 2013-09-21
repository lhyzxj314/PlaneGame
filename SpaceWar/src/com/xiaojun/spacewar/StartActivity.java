package com.xiaojun.spacewar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void startGameForMenu(View v){
		Intent intent = new Intent(this,GameActivity.class);
		startActivity(intent);
	}
	
	public void startGameForTouch(View v){
		//Intent intent = new Intent(this,GameActivity.class);
		//startActivity(intent);
	}
	
	public void startGameForParticle(View v) {
		Intent intent = new Intent(this,ParticleSystemSimpleExample.class);
		startActivity(intent);
	}
}
