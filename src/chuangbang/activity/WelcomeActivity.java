package chuangbang.activity;



import cn.bmob.v3.Bmob;
import chuangbang.fragment.WhatNewFragmentOne;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

public class WelcomeActivity extends Activity{
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		Bmob.initialize(this, "d4a08c95260790340a7d19379d67f852");
		
		
		// ����Ƿ��ǵ�1���������ж��Ƿ���Ҫ��ʾWhat's New
				SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
				boolean isFirstRun = sp.getBoolean("isFirstRun", true);
				final Class<? extends Activity> cls;
				if (isFirstRun) {
					cls = WhatNewActivity.class;
				} else {
					cls = LoginActivity.class;
				}
		
				// �ӳټ�������Activity��ʱ��
				long delayMillis =4000;

				// �ӳ�һ��ʱ��󼤻�����Activity
				handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(WelcomeActivity.this, cls);
						startActivity(intent);
						finish();
					}
				}, delayMillis);
	}
}
