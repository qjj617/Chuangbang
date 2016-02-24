package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * 关注项目界面
 * 
 * @author Administrator
 * 
 */
public class FocusOnProjectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.focus_on_project);

	}

	/**
	 * 返回到我的界面
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}
}
