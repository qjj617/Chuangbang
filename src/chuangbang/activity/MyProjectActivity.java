package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * 我的项目界面
 * 
 * @author Administrator
 * 
 */
public class MyProjectActivity extends Activity {
	// 项目listView
	private ListView lvProject;
	// 添加我的项目
	private ImageButton imag_add_my_project;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_project);
		lvProject = (ListView) findViewById(R.id.lv_project_my);
		imag_add_my_project = (ImageButton) findViewById(R.id.imag_add_my_project);
		imag_add_my_project.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyProjectActivity.this,
						NewMyProjectActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 返回到我的Fragment
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();
	}
}
