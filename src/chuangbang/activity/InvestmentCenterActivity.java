package chuangbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * 投资中心界面
 * 
 * @author Administrator
 * 
 */
public class InvestmentCenterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.investment_center);

	}

	/**
	 * 回到我的界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();

	}
}
