package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 教育背景界面
 * 
 * @author Administrator
 * 
 */
public class EducationalBackgroundAcitivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.educational_background);
	}

	/**
	 * 返回到我的简历界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		Intent intent = new Intent(EducationalBackgroundAcitivity.this,
				MyResumeActivity.class);
		startActivity(intent);
	}
}
