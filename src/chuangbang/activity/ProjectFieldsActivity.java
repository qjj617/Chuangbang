package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 项目领域界面
 * 
 * @author Administrator
 * 
 */
public class ProjectFieldsActivity extends Activity {
	// 确认TextView
	private TextView tv_set_confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_fields);
		tv_set_confirm = (TextView) findViewById(R.id.tv_set_confirm);
		// 为TextView添加监听器
		tv_set_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}
	/**
	 * 返回到创建项目界面
	 * @param v
	 */
	public void back(View v){
		Intent intent=new Intent(getApplicationContext(), NewMyProjectActivity.class);
		startActivity(intent);
		
	}
  
}
