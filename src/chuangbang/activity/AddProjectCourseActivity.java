package chuangbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 添加项目历程界面
 * 
 * @author Administrator
 * 
 */
public class AddProjectCourseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_project_course);
	}

	/**
	 * 返回到项目历程界面
	 * 
	 * @param v
	 */
	public void back(View v) {
		Intent intent = new Intent(AddProjectCourseActivity.this,
				ProjectCourseActivity.class);
		startActivity(intent);
	}
}
