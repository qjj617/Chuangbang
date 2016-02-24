package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 融资信息界面
 * 
 * @author Administrator
 * 
 */
public class FinancingInformationActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.financing_information);
	}

	/**
	 * 返回到创建项目界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		Intent intent = new Intent(getApplicationContext(),
				NewMyProjectActivity.class);
		startActivity(intent);
	}
}
