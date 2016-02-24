package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * VIP界面
 * 
 * @author Administrator
 * 
 */
public class VIPActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vip);
	}

	/**
	 * 返回到我的界面
	 * 
	 * @param v
	 */
	public void back(View v) {
      finish();
	}
}
