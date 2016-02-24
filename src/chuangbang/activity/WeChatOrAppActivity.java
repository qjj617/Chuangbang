package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 微信/APP开发界面
 * 
 * @author Administrator
 * 
 */
public class WeChatOrAppActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wechat_or_app);
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
