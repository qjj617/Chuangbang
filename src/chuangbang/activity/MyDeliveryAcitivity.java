package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * 我的投递界面（剩余约谈数）
 * 
 * @author Administrator
 * 
 */
public class MyDeliveryAcitivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_delivery);
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
