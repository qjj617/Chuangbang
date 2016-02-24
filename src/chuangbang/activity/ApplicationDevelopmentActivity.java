package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;

/**
 * 申请开发界面
 * 
 * @author Administrator
 * 
 */
public class ApplicationDevelopmentActivity extends Activity implements
		OnClickListener {
	// 微信/APP开发、网站建设
	private TableRow tr_set_wechat_or_App, tr_set_website_construction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.application_development);
		tr_set_wechat_or_App = (TableRow) findViewById(R.id.tr_set_wechat_or_App);
		tr_set_website_construction = (TableRow) findViewById(R.id.tr_set_website_construction);
		setListener();
	}

	// 为两个子TableRow设置监听器
	private void setListener() {
		tr_set_website_construction.setOnClickListener(this);
		tr_set_wechat_or_App.setOnClickListener(this);
	}

	/**
	 * 返回到技术开发Fragment
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();
	}

	// 点击进入微信/APP开发、网站建设界面
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tr_set_wechat_or_App:
			Intent intent = new Intent(getApplicationContext(),
					WeChatOrAppActivity.class);
			startActivity(intent);
			break;
		case R.id.tr_set_website_construction:
			Intent i = new Intent(getApplicationContext(),
					WebsiteConstructionActivity.class);
			startActivity(i);
			break;

		}
	}

}
