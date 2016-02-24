package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 网站建设界面
 * 
 * @author Administrator
 * 
 */
public class WebsiteConstructionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.website_construction);
	}

	/**
	 * 返回到申请开发界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		Intent intent = new Intent(getApplicationContext(),
				ApplicationDevelopmentActivity.class);
		startActivity(intent);
	}
}
