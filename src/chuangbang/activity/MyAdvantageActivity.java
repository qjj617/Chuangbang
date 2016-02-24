package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 我的优势界面
 * 
 * @author Administrator
 * 
 */
public class MyAdvantageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_advantage);
	}

	/**
	 * 返回到我的简历界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		Intent intent = new Intent(MyAdvantageActivity.this,
				MyResumeActivity.class);
		startActivity(intent);
	}
}
