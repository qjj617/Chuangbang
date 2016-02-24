package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * 法律咨询界面
 * 
 * @author Administrator
 * 
 */
public class LegalAdviceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.legal_advice);

	}

	/**
	 * 回到进驻孵化器Fragment
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();

	}
}
